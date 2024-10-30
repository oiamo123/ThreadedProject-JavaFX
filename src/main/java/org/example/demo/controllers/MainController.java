package org.example.demo.controllers;

import java.lang.reflect.Field;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.demo.TravelExpertsApplication;
import org.example.demo.models.*;
import org.example.demo.util.annotations.FieldInfo;
import org.example.demo.util.dbhelpers.BookingDetailsDB;
import org.example.demo.util.dbhelpers.BookingsDB;
import org.example.demo.util.dbhelpers.CustomerDB;
import org.example.demo.util.dbhelpers.InvoicesDB;

public class MainController {

    @FXML private Button btnAdd;
    @FXML private Tab tabBooking;
    @FXML private Tab tabBookingDetail;
    @FXML private Tab tabCustomer;
    @FXML private Tab tabInvoices;
    @FXML private TabPane tabPane;
    @FXML private TableView<BookingDetails> tvBookingDetails;
    @FXML private TableView<Bookings> tvBookings;
    @FXML private TableView<Customers> tvCustomers;
    @FXML private TableView<Invoices> tvInvoices;
    private Object obj;

    @FXML
    void initialize() {
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'main-view.fxml'.";
        assert tabBooking != null : "fx:id=\"tabBooking\" was not injected: check your FXML file 'main-view.fxml'.";
        assert tabBookingDetail != null : "fx:id=\"tabBookingDetail\" was not injected: check your FXML file 'main-view.fxml'.";
        assert tabCustomer != null : "fx:id=\"tabCustomer\" was not injected: check your FXML file 'main-view.fxml'.";
        assert tabInvoices != null : "fx:id=\"tabInvoices\" was not injected: check your FXML file 'main-view.fxml'.";
        assert tvBookingDetails != null : "fx:id=\"tvBookingDetails\" was not injected: check your FXML file 'main-view.fxml'.";
        assert tvBookings != null : "fx:id=\"tvBookings\" was not injected: check your FXML file 'main-view.fxml'.";
        assert tvCustomers != null : "fx:id=\"tvCustomers\" was not injected: check your FXML file 'main-view.fxml'.";
        assert tvInvoices != null : "fx:id=\"tvInvoices\" was not injected: check your FXML file 'main-view.fxml'.";

        // Set bookings table view data when tab is clicked
        tabBooking.setOnSelectionChanged(_ -> {
            if (tabBooking.isSelected()) {
                setButtons(false);
                obj = new Bookings();

                createColumns(new Bookings(), tvBookings);
                ObservableList<Bookings> data = BookingsDB.getBookings();
                tvBookings.setItems(data);
            }
        });

        // Set customer table view data when tab is clicked
        tabCustomer.setOnSelectionChanged(_ -> {
            if (tabCustomer.isSelected()) {
                setButtons(false);
                obj = new Customers();

                createColumns(new Customers(), tvCustomers);
                ObservableList<Customers> data = CustomerDB.getCustomers();
                tvCustomers.setItems(data);
            }
        });

        // Set bookings details table view data when tab is clicked
        tabBookingDetail.setOnSelectionChanged(_ -> {
            if (tabBookingDetail.isSelected()) {
                setButtons(false);
                obj = new BookingDetails();

                createColumns(new BookingDetails(), tvBookingDetails);
                ObservableList<BookingDetails> data = BookingDetailsDB.getBookingDetails();
                tvBookingDetails.setItems(data);
            }
        });

        tabInvoices.setOnSelectionChanged(_ -> {
            if (tabInvoices.isSelected()) {
                setButtons(true);
                obj = new Invoices();

                createColumns(new Invoices(), tvInvoices);
                ObservableList<Invoices> data = InvoicesDB.getInvoices();
                tvInvoices.setItems(data);
            }
        });

        // add event listeners to table view click
        addTvClickListener(tvBookings);
        addTvClickListener(tvBookingDetails);
        addTvClickListener(tvCustomers);
        addTvClickListener(tvInvoices);

        btnAdd.setOnAction(_ -> openDialog(new Bookings(), "Add "));

        // Show customer data when application starts
        createColumns(new Customers(), tvCustomers);
        ObservableList<Customers> data = CustomerDB.getCustomers();
        tvCustomers.setItems(data);
        obj = Customers.class;
    }

    // Opens a new add/edit dialog form
    private void openDialog(Object obj, String mode) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(TravelExpertsApplication.class.getResource("edit-form.fxml"));
            Parent root = fxmlLoader.load();
            EditController controller = fxmlLoader.getController();

            controller.initData(obj, mode); // pass in the mode and the obj

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            stage.setTitle(mode + obj.getClass().getAnnotation(FieldInfo.class).name());

            stage.showAndWait();

            SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();

            if (mode.equalsIgnoreCase("edit ")) {
                if (obj instanceof Bookings) {
                    selectionModel.select(tabBooking);
                } else if (obj instanceof Customers) {
                    selectionModel.select(tabCustomer);
                } else if (obj instanceof Invoices) {
                    selectionModel.select(tabInvoices);
                } else {
                    selectionModel.select(tabBookingDetail);
                }
            } else {
                selectionModel.select(tabInvoices);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Dynamically create columns based off object passed in into table view
    public void createColumns(Object obj, TableView table) {

        table.getColumns().clear(); // clear all columns

        Field[] fields = obj.getClass().getDeclaredFields(); // get all fields

        for (Field field : fields) { // loop over fields
            FieldInfo column = field.getAnnotation(FieldInfo.class); // get the column name
            String name = column.name();

            TableColumn<?, ?> tableColumn = new TableColumn<>(name); // create a column with the name from the annotation

            tableColumn.setCellValueFactory(new PropertyValueFactory<>(field.getName())); // set the value as the objects field value

            table.getColumns().add(tableColumn); // add the column
        }
    }

    // Adds click listener to passed in table view to columns
    private void addTvClickListener(TableView tv) {
        tv.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            int index = tv.getSelectionModel().getSelectedIndex();

            if (tv.getSelectionModel().isSelected(index)) {
                Platform.runLater(() -> openDialog(newValue, "Edit "));
            }
        });
    }

    // sets buttons based on passed in boolean
    private void setButtons (boolean val) {
        btnAdd.setDisable(val);
    }
}
