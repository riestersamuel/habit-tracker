package com.teampingui.dao;

import com.teampingui.exceptions.JournalDaoException;
import com.teampingui.models.JournalEntryItem;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

class JournalTest {
    JournalDAO journalDAO = new JournalDAO();
    JournalEntryItem journalEntry = new JournalEntryItem("01.01.2020", "This text came from JournalTest.java");

    @Test
    public void testInsertJournalEntry() {
        //TODO: Check if that's correct testing method
        try {
            journalDAO.insert(journalEntry);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JournalDaoException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    public void tearDown() {

        // Does the list of entries contain the new entry?
        //Assumptions.assumeTrue(journalDAO.mosJournalEntries.contains(journalEntry)); // TODO: Dont change access modifier because of unit test!
        Assumptions.assumeTrue(journalDAO.getAll().contains(journalEntry));

        // Remove last entry from list and database
        //TODO: After implemeting the delete method, check or manage that the new entry is also removed from the database
        journalDAO.delete(journalEntry);
    }
}