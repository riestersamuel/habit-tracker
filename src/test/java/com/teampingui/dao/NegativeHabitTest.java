package com.teampingui.dao;

import com.teampingui.exceptions.JournalDaoException;
import com.teampingui.exceptions.NotInDatabaseException;
import com.teampingui.models.Habit;
import org.junit.Test;

import java.sql.SQLException;

public class NegativeHabitTest {
    @Test(expected = RuntimeException.class)
    public void testHabitID() {
        try {
            Habit.setDB_ID(-10);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException();
        }
    }
}
