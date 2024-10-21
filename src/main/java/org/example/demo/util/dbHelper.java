package org.example.demo.util;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.demo.TravelExpertsApplication;
import org.example.demo.util.annotations.FieldInfo;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Date;
import java.util.Properties;
import java.util.function.Function;

public class dbHelper {
    public static Connection getConnection() {
        Connection conn = null;

        String username;
        String password;
        String uri;

        try {
            InputStream file = TravelExpertsApplication.class.getResourceAsStream("/config.properties");
            Properties prop = new Properties();
            prop.load(file);

            username = prop.getProperty("username");
            password = prop.getProperty("password");
            uri = prop.getProperty("uri");
        } catch (Exception err) {
            throw new RuntimeException("Unable to read file");
        }

        try {
            conn = DriverManager.getConnection(uri, username, password);
        } catch (Exception err) {
            throw new RuntimeException(err.getMessage());
        }
        return conn;
    }

    public static <T> ObservableList<T> getData(String query, Class<T> clazz) {
        ObservableList<T> data = FXCollections.observableArrayList();
        try {
            Statement stmt = dbHelper.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                data.add(dbHelper.formatter(clazz, rs));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    // inserts an object into the db
    public static void insertData(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        StringBuilder query = new StringBuilder("INSERT INTO " + obj.getClass().getSimpleName().toLowerCase() + " (");
        StringBuilder placeholders = new StringBuilder(") VALUES (");
        int fieldIndex = 1; // To track parameter index for PreparedStatement

        try {
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true); // Make private fields accessible

                // Assume the first field is the auto-generated ID and skip it
                if (!fields[i].getAnnotation(FieldInfo.class).id() || fields[i].get(obj) == null) {
                    query.append(fields[i].getName());
                    placeholders.append("?");

                    if (i < fields.length - 1) {
                        query.append(", ");
                        placeholders.append(", ");
                    }

                    fieldIndex++; // Increment field index
                }
            }

            // Complete the query
            query.append(placeholders).append(")");

            System.out.println(query.toString());
            // Prepare statement
            PreparedStatement stmt = dbHelper.getConnection().prepareStatement(query.toString());
            fieldIndex = 1; // Reset the index for prepared statement parameters

            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true); // Access the field

                // Skip the ID field or null values
                if (!fields[i].getAnnotation(FieldInfo.class).id() || fields[i].get(obj) == null) {
                    Object value = unwrapProperty(fields[i].get(obj));
                    stmt.setObject(fieldIndex++, value);
                }
            }

            stmt.executeUpdate(); // Execute the insertion
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // deletes an object from the db
    public static void deleteData(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        StringBuilder query = new StringBuilder("DELETE FROM " + obj.getClass().getSimpleName().toLowerCase() + " WHERE ");

        try {
            fields[0].setAccessible(true);
            query.append(fields[0].getName()).append("=?");

            PreparedStatement stmt = dbHelper.getConnection().prepareStatement(query.toString());
            stmt.setObject(1, unwrapProperty(fields[0].get(obj)));

            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // updates an object in the db
    public static void updateData(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        StringBuilder query = new StringBuilder("UPDATE " + obj.getClass().getSimpleName().toLowerCase() + " SET ");
        int fieldIndex = 1; // To track parameter index for PreparedStatement

        try {
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true); // Make fields accessible

                if (!fields[i].getAnnotation(FieldInfo.class).id()) {
                    query.append(fields[i].getName()).append(" = ?");
                    if (i < fields.length - 1) {
                        query.append(", ");
                    }
                }
            }

            // Append the WHERE clause
            query.append(" WHERE ").append(fields[0].getName()).append(" = ?");

            // Prepare the statement
            PreparedStatement stmt = dbHelper.getConnection().prepareStatement(query.toString());

            // Set values for the PreparedStatement
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true); // Access each field
                Object value = unwrapProperty(fields[i]); // Get the value

                // Set values for all fields except the ID first
                if (!fields[i].getAnnotation(FieldInfo.class).id()) {
                    stmt.setObject(fieldIndex++, value);
                }
            }

            stmt.setObject(fieldIndex, unwrapProperty(fields[0]));

            stmt.executeUpdate(); // Execute the update
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Simple___properties return objects (key, value) pairs and need to be unwrapped
    public static Object unwrapProperty(Object fieldValue) {
        if (fieldValue instanceof SimpleObjectProperty) {
            return ((SimpleObjectProperty<?>) fieldValue).get();
        } else if (fieldValue instanceof SimpleStringProperty) {
            return ((SimpleStringProperty) fieldValue).get();
        } else if (fieldValue instanceof SimpleIntegerProperty) {
            return ((SimpleIntegerProperty) fieldValue).get();
        } else if (fieldValue instanceof SimpleDoubleProperty) {
            return ((SimpleDoubleProperty) fieldValue).get();
        } else if (fieldValue instanceof SimpleFloatProperty) {
            return ((SimpleFloatProperty) fieldValue).get();
        } else if (fieldValue instanceof SimpleBooleanProperty) {
            return ((SimpleBooleanProperty) fieldValue).get();
        } else if (fieldValue instanceof SimpleLongProperty) {
            return ((SimpleLongProperty) fieldValue).get();
        }
        // If it's not a SimpleXProperty, return the value as is
        return fieldValue;
    }

    public static <T> T formatter(Class<T> clazz, ResultSet rs) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getType().equals(SimpleStringProperty.class)) {
                    field.set(instance, new SimpleStringProperty(rs.getString(field.getName())));
                } else if (field.getType().equals(SimpleIntegerProperty.class)) {
                    field.set(instance, new SimpleIntegerProperty(rs.getInt(field.getName())));
                } else if (field.getType().equals(SimpleDoubleProperty.class)) {
                    field.set(instance, new SimpleDoubleProperty(rs.getDouble(field.getName())));
                } else if (field.getType().equals(SimpleObjectProperty.class)) {
                    field.set(instance, new SimpleObjectProperty<Date>(rs.getDate(field.getName())));
                }
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}