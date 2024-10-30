package org.example.demo.controllers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.demo.models.*;
import org.example.demo.models.additionals.Fees;
import org.example.demo.models.additionals.Packages;
import org.example.demo.util.*;
import org.example.demo.util.annotations.FieldInfo;
import org.example.demo.util.dbhelpers.BookingDetailsDB;
import org.example.demo.util.dbhelpers.BookingsDB;
import org.example.demo.util.dbhelpers.InvoicesDB;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class EditController {

    @FXML private Button btnAccept;
    @FXML private Button btnExit;
    @FXML private GridPane grdInputs;
    @FXML private Label lblMode;

    ArrayList<Object> inputs = new ArrayList<>();
    String mode = ""; // Keeps track of the current mode
    Bookings booking = new Bookings();
    BookingDetails bookingDetail = new BookingDetails();
    Object curObj;

    @FXML
    void initialize() {
        assert btnAccept != null : "fx:id=\"btnAccept\" was not injected: check your FXML file 'edit-form.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'edit-form.fxml'.";
        assert grdInputs != null : "fx:id=\"grdInputs\" was not injected: check your FXML file 'edit-form.fxml'.";
        assert lblMode != null : "fx:id=\"lblMode\" was not injected: check your FXML file 'edit-form.fxml'.";

        btnExit.setOnAction(_ -> {
            Stage stage = (Stage) btnExit.getScene().getWindow();
            stage.close();
        });

        btnAccept.setOnAction(_ -> {
            try {
                if (mode.equalsIgnoreCase("edit ")) { // mode is edit
                    validateAndBind();
                    dbHelper.updateData(curObj);

                    showAlert("Success", "Success", Alert.AlertType.CONFIRMATION);

                    Stage stage = (Stage) btnAccept.getScene().getWindow();
                    stage.close();
                } else { // mode is equals to add
                    validateAndBind();

                    if (curObj instanceof Bookings) {
                        booking = (Bookings) curObj;

                        ObservableList<Bookings> bookings = BookingsDB.getBookings(); // get all bookings
                        Bookings newBooking = bookings.getLast(); // get the most recent booking

                        bookingDetail.setBookingId(newBooking.getBookingId()); // add the id
                        btnAccept.setText("Submit"); // change the text
                        curObj = bookingDetail; // cur obj = booking detail
                        generateGrid(bookingDetail); // generate grid
                    } else {
                        BookingsDB.insert(booking); // insert booking
                        BookingDetailsDB.insert(bookingDetail); // insert booking detail
                        InvoicesDB.insert(createInvoice()); // insert invoice

                        showAlert("Success", "Success", Alert.AlertType.CONFIRMATION);

                        Stage stage = (Stage) btnAccept.getScene().getWindow();
                        stage.close();
                    }
                }
            } catch (Exception e) {
                inputs.clear();
                showAlert(e.getMessage().split(":")[1], "Error", Alert.AlertType.ERROR);
                throw new RuntimeException(e);
            }
        });
    }

    public void showAlert(String message, String title, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void initData(Object obj, String mode) {
        this.mode = mode;
        this.curObj = obj;
        if (mode.equalsIgnoreCase("edit")) {
            generateGrid(obj);
            btnAccept.setText("Submit");
        } else {
            generateGrid(obj);
            btnAccept.setText("Next");
        }

        lblMode.setText(obj.getClass().getAnnotation(FieldInfo.class).name());
    }

    private void generateGrid(Object obj) {
        /*
        Say you have:
        public class Bookings {
            @FieldInfo(id = true, name = "ID")
            private SimpleIntegerProperty BookingId;

            @FieldInfo(name = "Booking Date", isDate = true)
            private SimpleObjectProperty<Date> BookingDate;

            @FieldInfo(name = "Fee ID", isString = true, maxLength = 10, className = Fees.class)
            private SimpleStringProperty FeeId;
         }

         We can grab all the fields, and then loop over them, we can use the FieldInfo name value
         for the label and then create an input

         If the className value is not empty though, then the field has a relationship with another
         table in the database. When this happens, we can create choice box
         */
        grdInputs.getChildren().clear();
        Field[] fields = obj.getClass().getDeclaredFields();
        int curGridPane = 0;

        for (Field field : fields) {
            // Create a label and set text to field name
            if (field.getAnnotation(FieldInfo.class).id()) {
                continue;
            }

            // create a label
            Label label = new Label(field.getAnnotation(FieldInfo.class).name());

            if (field.getAnnotation(FieldInfo.class).className() != Void.class) { // check if field is a table
                ChoiceBox<?> choiceBox = createChoiceBox(field, obj); // create a choice box
                grdInputs.add(label, 0, curGridPane); // add label and choice box
                grdInputs.add(choiceBox, 1, curGridPane);
                curGridPane++;
                continue;
            }

            // Create an input based on the field's type
            TextField input = new TextField();
            try {
                field.setAccessible(true); // In case of private fields
                Object value = dbHelper.unwrapProperty(field.get(obj));

                if (value == null) {
                    input.setText("");
                } else {
                    input.setText(value.toString());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            // Add label and input to grid at curGridPane position
            grdInputs.add(label, 0, curGridPane);
            grdInputs.add(input, 1, curGridPane);
            curGridPane++;
        }
    }

    private void validateAndBind() {
        try {
            validateInputs();
            bindData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //validate all inputs by comparing inputs with objects fields restriction annotations
    private void validateInputs() {
        /*
        Say we have:
        public class Bookings {
            @FieldInfo(id = true, name = "ID")
            private SimpleIntegerProperty BookingId;

            @FieldInfo(name = "Booking Date", isDate = true)
            private SimpleObjectProperty<Date> BookingDate;

            @FieldInfo(name = "Booking No", isString = true, maxLength = 50)
            private SimpleStringProperty BookingNo;
         }

         We can loop over all the fields (BookingId, BookingDate, BookingNo) and check the
         data type (SimpleStringProperty, SimpleIntegerProperty) and then use that to validate

         You can also store all inputs in a list, and loop over them with the fields
         */
        Field[] fields = curObj.getClass().getDeclaredFields(); // get all fields
        ObservableList<Node> children = grdInputs.getChildren(); // get all children from grid
        inputs.clear(); // clear inputs list

        for (Node child : children) {
            if (child instanceof TextField || child instanceof ChoiceBox) {
                // if child is text field or choice box, add it to the list of inputs
                inputs.add(child);
            }
        }

        int curInput = 0;
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getAnnotation(FieldInfo.class).id()) {
                // if field contains id annotation, continue
                continue;
            }

            // get the name of the field using the FieldInfo annotation
            FieldInfo fieldInfo = fields[i].getAnnotation(FieldInfo.class);
            String name = fieldInfo.name();

            // if input is a choice box, ensure that an item is selected
            if (inputs.get(curInput) instanceof ChoiceBox) {
                Validation.isSelected((ChoiceBox<?>) inputs.get(curInput), name);
                curInput++;
                continue;
            }

            // else validate text field text
            TextField input = (TextField) inputs.get(curInput);
            System.out.println(input.getText());
            if (fieldInfo.isString()) {
                Validation.isLessThan(input.getText().trim(), fieldInfo.maxLength(), name);
            } else if (fieldInfo.isDouble()) {
                Validation.isDouble(input.getText(), name);
            } else if (fieldInfo.isInt()) {
                Validation.isInteger(input.getText(), name);
            } else if (fieldInfo.isDate()) {
                Validation.isDate(input.getText(), name);
            }
            curInput++;
        }
    }

    // binds data from inputs to current object
    private void bindData() {
        Field[] fields = curObj.getClass().getDeclaredFields(); // get declared fields

        int curInput = 0; // keep track of the current inputs
        for (int i = 0; i < fields.length; i++) { // loop over all fields in object
            if (fields[i].getAnnotation(FieldInfo.class).id()) { // if the annotation is an ID, continue over it (we want to ignore the bookingId for example)
                continue;
            }

            Field field = fields[i]; // Get the current field
            field.setAccessible(true); // set it as accessible
            try {
                /*
                Say we have:
                public class Fees {
                    @FieldInfo(name = "Fee ID", id = true)
                    private SimpleStringProperty FeeId; // Value is 10
                }

                and then we have
                public class BookingDetails {
                    @FieldInfo(name = "Fee ID", isString = true, maxLength = 10, className = Fees.class)
                    private SimpleStringProperty FeeId;
                }

                We can start by looping over BookingDetails and getting all the
                fields ie BookingDetailId, TripStart, TripEnd, FeeId etc

                We can then check the annotation to see if it has "className" set in the annotation
                if true, the input will be a choice box.

                We can then get the class using the className annotation, grab all the fields
                from that subclass and then loop over those fields

                we can then check to see if the subclass @FieldInfo() "id" value is true, and we can grab the value
                and set it to the FeeId
                 */
                if (inputs.get(curInput) instanceof ChoiceBox) {
                    Object subClass = ((ChoiceBox<?>) inputs.get(curInput)).getValue(); // get the selected object from the choice box

                    for (Field subClassField : subClass.getClass().getDeclaredFields()) { // loop over subclass fields ie fees or packages
                        subClassField.setAccessible(true); // set accessible
                        FieldInfo fieldInfo = subClassField.getAnnotation(FieldInfo.class); // get the annotation
                        if (fieldInfo.id()) { // check if the field is an id in the subclass
                            Object value = subClassField.get(subClass); // get the value

                            if (value instanceof SimpleIntegerProperty) { // set the value to the main class
                                ((SimpleIntegerProperty) field.get(curObj)).set(((SimpleIntegerProperty) value).get());
                            } else if (value instanceof SimpleStringProperty) {
                                ((SimpleStringProperty) field.get(curObj)).set(((SimpleStringProperty) value).get());
                            }
                            break;
                        }
                    }
                    curInput++;
                    continue;
                }

                // Check the type of the field and set it appropriately by parsing the input
                Object value = field.get(curObj);
                TextField input = (TextField) inputs.get(curInput);
                if (value instanceof SimpleStringProperty) {
                    ((SimpleStringProperty) value).set(input.getText());
                } else if (value instanceof SimpleDoubleProperty) {
                    ((SimpleDoubleProperty) value).set(Double.parseDouble(input.getText()));
                } else if (value instanceof SimpleIntegerProperty) {
                    ((SimpleIntegerProperty) value).set(Integer.parseInt(input.getText()));
                } else if (value instanceof SimpleObjectProperty<?>) {
                    LocalDate localDate = LocalDate.parse(input.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    ((SimpleObjectProperty<Date>) value).set(date);
                }
                curInput++;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    /**
     * Create a choice box
     * @param field field containing the table ie TripTypes
     * @param obj object the field belongs to
     * @return returns a choice box
     */
    private ChoiceBox<?> createChoiceBox(Field field, Object obj) {
        /*
        Say we have
        public class BookingDetails {
            @FieldInfo(name = "Fee ID", isString = true, maxLength = 10, className = Fees.class)
            private SimpleStringProperty FeeId;
        }

        if the className != null or void, then that means it references another table in
        the database/has a relationship

        this method queries the database using the annotations class name and then creates
        an observable list and adds the list to a choice box. This choice box is then returned
        and added to the grid
         */
        Class<?> linkedClass = field.getAnnotation(FieldInfo.class).className(); // get the class
        field.setAccessible(true);
        try {
            // query the database
            Statement stmt = dbHelper.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select * from " + linkedClass.getSimpleName());

            // create choice box and observable list
            ChoiceBox<Object> choiceBox = new ChoiceBox<>();
            ObservableList<Object> items = FXCollections.observableArrayList();

            Object fieldValue = field.get(obj); // get the field value

            // loop over the result set, create a new object (ie TripType) and add to choice box
            while (rs.next()) {
                Object objInstance = dbHelper.formatter(linkedClass, rs);

                // check if the object created from the result set matches the value of the parent object
                if (fieldValue instanceof SimpleIntegerProperty intProperty) {
                    if (intProperty.get() != 0 && intProperty.get() == rs.getInt(1)) {
                        choiceBox.setValue(objInstance); // set the selected value in the choice box
                    }
                } else if (fieldValue instanceof SimpleStringProperty stringProperty) {
                    if (stringProperty.get() != null && stringProperty.get().equals(rs.getString(1))) {
                        choiceBox.setValue(objInstance); // set the selected value in the choice box
                    }
                }

                items.add(objInstance); // add to observable list
            }

            choiceBox.setItems(items); // set items
            return choiceBox;
        } catch (SQLException | ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private Invoices createInvoice() {
        Invoices invoice = new Invoices();
        // package, fee, commission total package prices
        try {
            ObservableList<Packages> packages = dbHelper.getData("select * from packages where PackageId = ?", Packages.class, booking.getPackageId());
            ObservableList<Fees> fees = dbHelper.getData("select * from fees where FeeId = ?", Fees.class, bookingDetail.getFeeId());
            ObservableList<BookingDetails> bookingDetails = dbHelper.getData("select * from bookingdetails", BookingDetails.class);

            Packages bookingPackage = packages.getFirst();
            Fees bookingDetailFee = fees.getFirst();
            BookingDetails newBookingDetails = bookingDetails.getLast();

            double totalPrice = bookingPackage.getPkgBasePrice() + bookingDetail.getBasePrice();
            double tax = totalPrice * 0.05;

            invoice.setInvoiceDate(new Date());
            invoice.setFees(bookingDetailFee.getFeeAmt());
            invoice.setTotal(totalPrice);
            invoice.setTotalTax(tax);
            invoice.setCommissionTotal(newBookingDetails.getAgencyCommission() + bookingPackage.getPkgAgencyCommission());
            invoice.setBookingDetailId(newBookingDetails.getBookingDetailId());
            invoice.setCustomerId(booking.getCustomerId());
            invoice.setPackageId(booking.getPackageId());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return invoice;
    }
}
