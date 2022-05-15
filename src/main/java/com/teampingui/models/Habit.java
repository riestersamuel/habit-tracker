package com.teampingui.models;

import javafx.beans.property.*;

public class Habit {

    private final StringProperty name = new SimpleStringProperty();

    private final BooleanProperty checked = new SimpleBooleanProperty();

    private final IntegerProperty reps = new SimpleIntegerProperty();  

    public Habit(String name, boolean checked, int reps) {
        this.name.set(name);
        this.checked.set(checked);
        this.reps.set(reps);
    }

    public String getName() {
        return name.get();
    }

    public boolean isChecked() {
        return checked.get();
    }

    public int getReps() {
        return reps.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setChecked(boolean checked) {
        this.checked.set(checked);
    }

    public void setReps(int reps) {
        this.reps.set(reps);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public  BooleanProperty doneProperty() {
        return checked;
    }

    public IntegerProperty repsProperty() {return reps;}

}
