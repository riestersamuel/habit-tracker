package com.teampingui.models;

public enum Day {

    MONDAY("Mon"),
    TUESDAY("Tue"),
    WEDNESDAY("Wed"),
    THURSDAY("Thu"),
    FRIDAY("Fri"),
    SATURDAY("Sat"),
    SUNDAY("Sun");

    final String mDay;

    Day(String day) {
        this.mDay = day;
    }

    public String getDay() {
        return mDay;
    }
}
