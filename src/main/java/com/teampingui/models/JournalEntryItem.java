package com.teampingui.models;

public class JournalEntryItem {
    private String mContent;
    private String mDate;

    public JournalEntryItem(String date, String entry) {
        this.mDate = date;
        this.mContent = entry;
    }

    public String getDate() {
        return mDate;
    }

    public String getContent() {
        return mContent;
    }

}
