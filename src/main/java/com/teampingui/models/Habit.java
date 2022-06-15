package com.teampingui.models;

import javafx.beans.property.*;

public class Habit {

    private final StringProperty name;
    private final IntegerProperty reps;
    private final BooleanProperty[] checkedDays = new BooleanProperty[7];
    private final boolean[] haveTodoDays = new boolean[7];

    //For database
    private final int id = 1;

    public Habit(String name, boolean[] haveTodoDays, boolean[] checkedDays) {
        this.name = new SimpleStringProperty(name);
        int reps = 0;
        for (int i = 0; i < checkedDays.length; i++) {
            reps += haveTodoDays[i] ? 1 : 0;
            this.haveTodoDays[i] = haveTodoDays[i];
            this.checkedDays[i] = new SimpleBooleanProperty(checkedDays[i]);
        }
        this.reps = new SimpleIntegerProperty(reps);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public BooleanProperty checkedDays(Day day) {
        return checkedDays[day.ordinal()];
    }

    public IntegerProperty repsProperty() {
        return reps;
    }

    public boolean hasToBeDone(Day day) {
        return haveTodoDays[day.ordinal()];
    }

}
