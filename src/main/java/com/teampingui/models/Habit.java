package com.teampingui.models;

import javafx.beans.property.*;

public class Habit {

    private final StringProperty name = new SimpleStringProperty();

    private final BooleanProperty checkedMon = new SimpleBooleanProperty();
    private final BooleanProperty checkedTue = new SimpleBooleanProperty();
    private final BooleanProperty checkedWed = new SimpleBooleanProperty();

    private final IntegerProperty reps = new SimpleIntegerProperty();  

    public Habit(String name, boolean checkedMon, boolean checkedTue, boolean checkedWed, int reps) {
        this.name.set(name);
        this.checkedMon.set(checkedMon);
        this.checkedTue.set(checkedTue);
        this.checkedWed.set(checkedWed);
        this.reps.set(reps);
    }

    public String getName() {
        return name.get();
    }

    public boolean isCheckedMon() {
        return checkedMon.get();
    }
    public boolean isCheckedTue() {
        return checkedTue.get();
    }
    public boolean isCheckedWed() {
        return checkedWed.get();
    }

    public int getReps() {
        return reps.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setCheckedMon(boolean checkedMon) {
        this.checkedMon.set(checkedMon);
    }
    public void setCheckedTue(boolean checkedTue) {
        this.checkedTue.set(checkedTue);
    }
    public void setCheckedWed(boolean checkedWed) {
        this.checkedWed.set(checkedWed);
    }

    public void setReps(int reps) {
        this.reps.set(reps);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public  BooleanProperty checkedPropertyMon() {
        return checkedMon;
    }
    public  BooleanProperty checkedPropertyTue() {
        return checkedTue;
    }
    public  BooleanProperty checkedPropertyWed() {
        return checkedWed;
    }

    public IntegerProperty repsProperty() {return reps;}

}
