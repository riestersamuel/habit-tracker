package com.teampingui.dao;

import com.teampingui.exceptions.JournalDaoException;
import com.teampingui.models.JournalEntry;
import org.junit.Test;

import java.sql.SQLException;

public class NegativeJournalTest {
    JournalDAO journalDAO = new JournalDAO();
    JournalEntry journalEntry = new JournalEntry("05.05.1999", null);

    //We assume that a runtime exception is thrown when the entry is null
    @Test(expected = RuntimeException.class)
    public void testInsertJournalEntry() {
        try {
            journalDAO.insert(journalEntry);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JournalDaoException e) {
            throw new RuntimeException(e);
        }
    }

    //This test is for checking that an exception gets thrown, when the setID method in JournalEntryItem gets passed
    //a negative value
    @Test(expected = IllegalArgumentException.class)
    public void testSetIdOfJournalEntry() {
        try {
            JournalEntry journalEntry = new JournalEntry("20.04.2020", "This text came from testSetIdOfJournalEntry");
            journalEntry.setID(-1);
        } catch (IllegalArgumentException i) {
            throw new IllegalArgumentException(i);
        }
    }
}
