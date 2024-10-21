package org.example.demo.util;

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
            Integer.parseInt(val);
        } catch (NumberFormatException e) {
            throw new RuntimeException(field + " must be a number");
        }
    }

    /**
     * Ensures a given integer is greater than a value
     * @param val value to be evaluated
     * @param min minimum allowed value
     * @param field field name of value
     */
    public static void isInteger(String val, String min, String field) {
        try {
            isInteger(val, field);
            int parsed = Integer.parseInt(val);

            if (parsed < Integer.parseInt(min)) {
                throw new RuntimeException(field + " must be greater than " + min);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Ensures a given integer is between two values
     * @param val value to be evaluated
     * @param min minimum allowed value
     * @param max maximum allowed value
     * @param field field name of value
     */
    public static void isInteger(String val, String min, String max, String field) {
        try {
            isInteger(val, min, field);
            int parsed = Integer.parseInt(val);

            if (parsed > Integer.parseInt(max)) {
                throw new Exception(field + "must be less than " + max);
            }
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Ensures a given value is a double
     * @param val value to be evaluated
     * @param field field name of value
     */
    public static void isDouble(String val, String field) {
        try {
            Double.parseDouble(val);
        } catch (NumberFormatException e) {
            throw new RuntimeException(field + " must be a number");
        }
    }

    /**
     * Ensures a double is less than a given number
     * @param val value to be evaluated
     * @param min minimum allowed value
     * @param field field name of value
     */
    public static void isDouble(String val, String min, String field) {
        try {
            isDouble(val, field);
            double parsed = Double.parseDouble(val);
            if (parsed < Double.parseDouble(min)) {
                throw new Exception(field + "must be greater than " + min);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Ensures a double is between two given numbers
     * @param val value to be evaluated
     * @param min minimum allowed value
     * @param max maximum allowed value
     * @param field field name of value
     */
    public static void isDouble(String val, String min, String max, String field) {
        try {
            isDouble(val, min, field);
            double parsed = Double.parseDouble(val);

            if (parsed > Double.parseDouble(max)) {
                throw new Exception(field + "must be less than " + max);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Ensures a value is a date
     * @param val value to be evaluated
     * @param field field name of value
     */
    public static void isDate(String val, String field) {
        try {
            LocalDate date = LocalDate.parse(val);
            LocalDate today = LocalDate.now();
            if (today.isAfter(date)) {
                throw new RuntimeException(field + " must be after " + today);
            }
        } catch (DateTimeParseException e) {
            throw new RuntimeException(field + "must be a valid date");
        }
    }

    /**
     * Ensures a string is less than a given length
     * @param val value to be evaluated
     * @param max max number of allowed characters
     * @param field field name of value
     */
    public static void isLessThan(String val, String max, String field) {
        try {
            if (val.length() > Integer.parseInt(max)) {
                throw new Exception(field + "must be less than " + max + " characters");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
