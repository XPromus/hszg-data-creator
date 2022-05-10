package com.hszg.backend.data.privileges;

public class Privileges {

    public final static String READ_PERMISSION = "READ_DATA";
    public final static String WRITE_PERMISSION = "WRITE_DATA";
    public final static String EDIT_PAGE_PERMISSION = "EDIT_PAGE";

    public static String[] getAllPrivileges() {
        return new String[]{READ_PERMISSION, WRITE_PERMISSION, EDIT_PAGE_PERMISSION};
    }

}
