package com.teampingui.models;

import com.teampingui.exceptions.NotInDatabaseException;
import javafx.beans.property.StringProperty;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HabitTest {

    private final boolean[] haveTodoDays = new boolean[7];
    Habit habit = new Habit("Test", haveTodoDays);


    @Test
    void setDBID() throws NotInDatabaseException {
        habit.setDBID(999999);
        assertEquals(999999, habit.getDBID());
    }

    @Test
    void setCheckedDays() {
//        final boolean[] checkedDaysBoolean = {true, true, false, false, false, true, false};
//        habit.setCheckedDays(checkedDaysBoolean);
//        Assertions.assertEquals(checkedDaysBoolean, habit.get);
    }

    @Test
    void setChecked() {
    }

    @Test
    void testSetChecked() {
    }

    @Test
    void nameProperty() {
        assertEquals("Test", habit.nameProperty().getValue());
    }

    @Test
    void checkedDays() {
    }

    @Test
    void repsProperty() {
    }

    @Test
    void hasToBeDone() {
    }

    @Test
    void hasToBeDoneList() {
    }

    @Test
    void testToString() {
    }
}