package com.teampingui.dao;

import com.teampingui.exceptions.JournalDaoException;
import com.teampingui.models.Habit;
import com.teampingui.models.JournalEntryItem;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class JorunalTest {
    @Test
    public void testInsertJournalEntry() {
        //TODO: Check if that's correct testing method
        Database.connect();
        JournalDAO journalDAO = new JournalDAO();
        JournalEntryItem journalEntry = new JournalEntryItem("01.01.2020", "This text came from Unit Testing");
        try {
            journalDAO.insert(journalEntry);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JournalDaoException e) {
            throw new RuntimeException(e);
        }

        // Does the list of entries contain the new entry?
        //Assumptions.assumeTrue(journalDAO.mosJournalEntries.contains(journalEntry)); // TODO: Dont change access modifier because of unit test!

        // Remove last entry from list and database
        //TODO: After implemeting the delete method, check or manage that the new entry is also removed from the database
        journalDAO.delete(journalEntry);
    }
}