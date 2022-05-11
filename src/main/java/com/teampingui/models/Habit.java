package com.teampingui.models;

import javafx.beans.property.*;

public class Habit {

    private final StringProperty mName = new SimpleStringProperty();

    private final BooleanProperty done = new SimpleBooleanProperty();

    private final IntegerProperty reps = new SimpleIntegerProperty();

    public Habit(String name, boolean done, Integer reps) {
        this.mName.set(name);
        this.done.set(done);
        this.reps.set(reps);
    }

    public StringProperty nameProperty() {
        return mName;
    }

    public  BooleanProperty deleteProperty() {
        return done;
    }

}
