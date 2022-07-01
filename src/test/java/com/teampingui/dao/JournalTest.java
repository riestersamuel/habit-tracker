package com.teampingui.dao;

import com.teampingui.exceptions.JournalDaoException;
import com.teampingui.models.JournalEntryItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class JournalTest {
    JournalDAO journalDAO = new JournalDAO();
    JournalEntryItem journalEntry = new JournalEntryItem("01.01.2020", "This text came from JournalTest.java");

    @Test
    public void testInsertJournalEntry() {
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
        Assumptions.assumeTrue(journalDAO.getAll().contains(journalEntry));
        System.out.println(journalDAO.getAll());

        // Remove last entry from list and database
        journalDAO.delete(journalEntry);
        System.out.println(journalDAO.getAll());
    }
}