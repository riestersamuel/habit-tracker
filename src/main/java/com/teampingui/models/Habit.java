package com.teampingui.models;

import javafx.beans.property.*;

public class Habit {

    private final StringProperty name = new SimpleStringProperty();

    private final BooleanProperty done = new SimpleBooleanProperty();

    private final IntegerProperty reps = new SimpleIntegerProperty();  

    public Habit(String name, boolean done, Integer reps) {
        this.name.set(name);
        this.done.set(done);
        this.reps.set(reps);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public  BooleanProperty doneProperty() {
        return done;
    }

}
