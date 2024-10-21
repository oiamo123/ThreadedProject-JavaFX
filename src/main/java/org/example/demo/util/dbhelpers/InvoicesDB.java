package org.example.demo.util.dbhelpers;

import javafx.collections.ObservableList;
import org.example.demo.models.Invoices;
import org.example.demo.util.dbHelper;

public class InvoicesDB {
    public static void insert(Invoices inv) {
        dbHelper.insertData(inv);
    }

    public static void delete(Invoices inv) {
        dbHelper.deleteData(inv);
    }

    public static void update(Invoices inv) {
        dbHelper.updateData(inv);
    }

    public static ObservableList<Invoices> getInvoices() {
        return dbHelper.getData("select * from invoices", Invoices.class);
    }
}