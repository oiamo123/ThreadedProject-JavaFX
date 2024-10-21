package org.example.demo.util.dbhelpers;

import javafx.collections.ObservableList;
import org.example.demo.models.BookingDetails;
import org.example.demo.util.dbHelper;

public class BookingDetailsDB {
    public static void insert(BookingDetails bk) {
            dbHelper.insertData(bk);
    }

    public static void delete(BookingDetails bk) {
        dbHelper.deleteData(bk);
    }

    public static void update(BookingDetails bk) {
        dbHelper.updateData(bk);
    }

    public static ObservableList<BookingDetails> getBookingDetails() {
        return dbHelper.getData("select * from bookingdetails", BookingDetails.class);
    }
}