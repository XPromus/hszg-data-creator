package com.hszg.backend.api.error;

public class ErrorText {

    private ErrorText() {

    }

    public static String getObjectString(Long id) {
        return "Object with id " + id + " does not exist.";
    }

    public static String getYearString(Long id) {
        return "Year with id " + id + " does not exist.";
    }

    public static String getImageString(Long id) {
        return "Image with id " + id + "does not exist.";
    }

}
