package org.example.demo.util.dbhelpers;
import javafx.collections.ObservableList;
import org.example.demo.models.Bookings;
import org.example.demo.util.dbHelper;

public class BookingsDB {
    public static void insert(Bookings booking) {
        dbHelper.insertData(booking);
    }

    public static void delete(Bookings booking) {
        dbHelper.deleteData(booking);
    }

    public static void update(Bookings booking) {
        dbHelper.updateData(booking);
    }

    public static ObservableList<Bookings> getBookings() {
        return dbHelper.getData("select * from bookings", Bookings.class);
    }
}