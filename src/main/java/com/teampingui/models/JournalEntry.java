package com.teampingui.models;

import java.time.LocalDate;

public class JournalEntry {

    private LocalDate dataDate;

    private String entry, date;

    public JournalEntry (String date, String entry) {
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
