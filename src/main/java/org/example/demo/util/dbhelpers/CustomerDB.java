package org.example.demo.util.dbhelpers;

import javafx.collections.ObservableList;
import org.example.demo.models.Customers;
import org.example.demo.util.dbHelper;

public class CustomerDB {
    public static void insert(Customers cust) {
        dbHelper.insertData(cust);
    }

    public static void delete(Customers cust) {
        dbHelper.deleteData(cust);
    }

    public static void update(Customers cust) {
        dbHelper.updateData(cust);
    }

    public static ObservableList<Customers> getCustomers() {
        return dbHelper.getData("select * from customers", Customers.class);
    }
}