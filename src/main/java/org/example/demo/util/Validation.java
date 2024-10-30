package org.example.demo.util;

import javafx.scene.control.ChoiceBox;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Validation {
    /**
     * Ensures a given value is an integer
     * @param val value to be evaluated
     * @param field field name of value
     */
    public static void isInteger(String val, String field) {
        try {
            if (val.isEmpty()) {
                throw new RuntimeException(field + " cannot be empty");
            }
            if (Integer.parseInt(val) < 0) {
                throw new NumberFormatException(field + " must be a positive number");
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException(field + " must be a number");
        }
    }

    /**
     * Ensures a given value is a double
     * @param val value to be evaluated
     * @param field field name of value
     */
    public static void isDouble(String val, String field) {
        try {
            if (val.isEmpty()) {
                throw new RuntimeException(field + " must not be empty");
            }

            if (Double.parseDouble(val) < 0) {
                throw new RuntimeException(field + " must be greater than 0");
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException(field + " must be a number");
        }
    }

    /**
     * Ensures a value is a date
     * @param val value to be evaluated
     * @param field field name of value
     */
    public static void isDate(String val, String field) {
        try {
            if (val.isBlank()) {
                throw new RuntimeException(field + " must not be empty");
            }
            LocalDate today = LocalDate.now();
            if (today.isAfter(LocalDate.parse(val))) {
                throw new RuntimeException(field + " must be after " + today);
            }
        } catch (DateTimeParseException e) {
            throw new RuntimeException(field + " must be a valid date");
        }
    }

    /**
     * Ensures a string is less than a given length
     * @param val value to be evaluated
     * @param max max number of allowed characters
     * @param field field name of value
     */
    public static void isLessThan(String val, int max, String field) {
        try {
            if (val.isEmpty()) {
                throw new RuntimeException(field + " must not be empty");
            }
            if (val.length() > max) {
                throw new Exception(field + "must be less than " + max + " characters");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void isSelected(ChoiceBox choiceBox, String field) {
        try {
            if (choiceBox.getSelectionModel().isEmpty()) {
                throw new RuntimeException(field + " must have a selection");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
