package com.teampingui.models;

import javafx.beans.property.*;

public class Habit {

    private final StringProperty name;
    private final IntegerProperty reps;
    private final BooleanProperty[] checkedDays = new BooleanProperty[7];

    public Habit(String name, boolean[] checkedDays, int reps) {
        this.name = new SimpleStringProperty(name);
        for (int i = 0; i < checkedDays.length; i++) {
            this.checkedDays[i] = new SimpleBooleanProperty(checkedDays[i]);
        }
        this.reps = new SimpleIntegerProperty(reps);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public BooleanProperty checkedDays(int day) {
        return checkedDays[day];
    }

    public IntegerProperty repsProperty() {
        return reps;
    }

}
