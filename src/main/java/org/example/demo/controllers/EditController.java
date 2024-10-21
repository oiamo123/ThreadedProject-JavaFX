package org.example.demo.controllers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.example.demo.models.*;
import org.example.demo.util.*;
import org.example.demo.util.annotations.FieldInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class EditController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private Button btnAccept;
    @FXML private Button btnExit;
    @FXML private Button btnPrevious;
    @FXML private GridPane grdInputs;
    @FXML private Label lblMode;

    ArrayList<TextField> inputs = new ArrayList<>(); // Keep track of all inputs that were created
    String mode = ""; // Keeps track of the current mode
    Object booking = new Bookings();
    Object bookingDetail = new BookingDetails();
    Object curObj;

    @FXML
    void initialize() {
        assert btnAccept != null : "fx:id=\"btnAccept\" was not injected: check your FXML file 'edit-form.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'edit-form.fxml'.";
        assert grdInputs != null : "fx:id=\"grdInputs\" was not injected: check your FXML file 'edit-form.fxml'.";
        assert lblMode != null : "fx:id=\"lblMode\" was not injected: check your FXML file 'edit-form.fxml'.";
        assert btnPrevious != null : "fx:id\"btnPrevious\" was not injected: check your FXML file 'edit-form.fxml'";

        btnPrevious.setVisible(false); // Hide the previous btn

        btnAccept.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    if (mode.equalsIgnoreCase("edit")) { // mode is edit
                        dbHelper.updateData(curObj);
                    } else { // mode is add
                        validateAndBind();

                        if (curObj instanceof Bookings) {
                            btnAccept.setText("Submit");
                            btnPrevious.setVisible(true);

                            generateGrid(bookingDetail);
                        } else {
                            dbHelper.insertData(booking); // insert booking
                            dbHelper.insertData(bookingDetail); // insert booking details
                            dbHelper.insertData(createInvoice()); // insert invoice
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });
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
                ChoiceBox choiceBox = createChoiceBox(field, obj); // create a choice box
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

                inputs.add(input);
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
//            validateInputs();
            bindData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // binds data from inputs to current object
    private void bindData() {
        Field[] fields = curObj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            TextField input = inputs.get(i);
            field.setAccessible(true);
            try {
                if (field.getType().equals(SimpleStringProperty.class)) {
                    ((SimpleStringProperty) field.get(curObj)).set(input.getText());
                } else if (field.getType().equals(SimpleDoubleProperty.class)) {
                    ((SimpleDoubleProperty) field.get(curObj)).set(Double.parseDouble(input.getText()));
                } else if (field.getType().equals(SimpleIntegerProperty.class)) {
                    ((SimpleIntegerProperty) field.get(curObj)).set(Integer.parseInt(input.getText()));
                } else if (field.getType().equals(SimpleObjectProperty.class) && field.getGenericType().getTypeName().contains("LocalDate")) {
                    Date date = Date.from(Instant.parse(inputs.get(i).getText()));
                    ((SimpleObjectProperty<Date>) field.get(curObj)).set(date);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private ChoiceBox createChoiceBox(Field field, Object obj) {
        Class<?> linkedClass = field.getAnnotation(FieldInfo.class).className(); // get the class
        field.setAccessible(true);
        try {
            Statement stmt = dbHelper.getConnection().createStatement(); // create a statement
            ResultSet rs = stmt.executeQuery("select * from " + linkedClass.getSimpleName()); // get all data from the database using class type
            ChoiceBox<Object> choiceBox = new ChoiceBox<>(); // create a new choice box
            ObservableList<Object> items = FXCollections.observableArrayList(); // create an observable list

            Object fieldValue = field.get(obj); // Get the field value (e.g., schoolId)

            while (rs.next()) { // loop over result set
                Object objInstance = dbHelper.formatter(linkedClass, rs); // bind the result set data to the given class

                Method toStringMethod = linkedClass.getMethod("toString"); // get the toString method

                // Check the field type and compare accordingly
                if (fieldValue instanceof SimpleIntegerProperty) {
                    int resultSetId = rs.getInt(1); // get ID as integer
                    if (((SimpleIntegerProperty) fieldValue).get() == resultSetId) {
                        choiceBox.setValue(objInstance); // Set the selected value in the choice box
                    }
                } else if (fieldValue instanceof SimpleStringProperty) {
                    String resultSetId = rs.getString(1); // get ID as string
                    if (((SimpleStringProperty) fieldValue).get().equals(resultSetId)) {
                        choiceBox.setValue(objInstance); // Set the selected value in the choice box
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

    // validate all of the inputs by comparing inputs with objects fields restriction annotations
//    private void validateInputs() {
//        Field[] fields = curObj.getClass().getDeclaredFields();
//
//        for (int i = 0; i < fields.length; i++) {
//            if (fields[i].getType().equals(SimpleStringProperty.class)) {
//                Validation.isLessThan(inputs.get(i).getText(), fields[i].getAnnotation(Restriction.class).max(), fields[i].getAnnotation(Column.class).name());
//            } else if (fields[i].getType().equals(SimpleDoubleProperty.class)) {
//
//            } else if (fields[i].getType().equals(SimpleIntegerProperty.class)) {
//
//            } else if (fields[i].getType().equals(SimpleObjectProperty.class)) {
//
//            }
//        }
//    }

    private Invoices createInvoice() {
        Invoices invoice = new Invoices();

        // calculate stuff

        return invoice;
    }
}
