package com.teampingui.models;

import java.util.prefs.Preferences;

public class Settings { // TODO: static... singleton..., ...?

    private static final Preferences prefs;

    private static final String USERNAME = "username";
    private static final String DATE_FORMAT = "date_format";

    static {
        prefs = Preferences.userNodeForPackage(Settings.class);
    }

    public static String getUsername() {
        return prefs.get(USERNAME, "");
    }

    public static void setUsername(String username) {
        prefs.put(USERNAME, username);
    }

    public static String getDateFormat() {
        return prefs.get(DATE_FORMAT, "dd.mm.yyyy");
    }

    public static void setDateFormat(String dateFormat) {
        prefs.put(DATE_FORMAT, dateFormat);
    }

}
