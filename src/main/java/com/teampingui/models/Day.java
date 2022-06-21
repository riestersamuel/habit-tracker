package com.teampingui.models;

import java.time.DayOfWeek;

public enum Day {

    MONDAY(DayOfWeek.MONDAY,"Mon"),
    TUESDAY(DayOfWeek.TUESDAY,"Tue"),
    WEDNESDAY(DayOfWeek.WEDNESDAY,"Wed"),
    THURSDAY(DayOfWeek.THURSDAY,"Thu"),
    FRIDAY(DayOfWeek.FRIDAY,"Fri"),
    SATURDAY(DayOfWeek.SATURDAY,"Sat"),
    SUNDAY(DayOfWeek.SUNDAY,"Sun");

    final DayOfWeek mDayOfWeek;
    final String mDay;

    Day(DayOfWeek dayOfWeek, String day) {
        this.mDayOfWeek = dayOfWeek;
        this.mDay = day;
    }

    public DayOfWeek getDayOfWeek() {
        return mDayOfWeek;
    }

    public String getDay() {
        return mDay;
    }
}
