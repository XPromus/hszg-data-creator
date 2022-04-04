package com.hszg.backend.api.error;

public class ErrorText {

    public static String getObjectString(Long id) {
        return "Object with id " + id + " does not exist.";
    }

    public static String getYearString(Long id) {
        return "Year with id " + id + " does not exist.";
    }

}
