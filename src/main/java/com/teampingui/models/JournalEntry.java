package com.teampingui.models;

public class JournalEntry {
    private String entry;
    private String date;

    public JournalEntry(String date, String entry) {
        this.date = date;
        this.entry = entry;
    }

    public String getDate() {
        return date;
    }

    public String getEntry() {
        return entry;
    }

}
