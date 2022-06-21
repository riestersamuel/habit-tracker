package com.teampingui.dao;

import com.teampingui.exceptions.JournalDaoException;
import com.teampingui.models.JournalEntryItem;
import org.junit.Test;

import java.sql.SQLException;

public class NegativeJournalTest {
    JournalDAO journalDAO = new JournalDAO();
    JournalEntryItem journalEntry = new JournalEntryItem("05.05.1999", null);

    @Test
    public void testInsertJournalEntry() {
        try {
            journalDAO.insert(journalEntry);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JournalDaoException e) {
            throw new RuntimeException(e);
        }
        //TODO: Update database: Add NOT NULL statement to entry column
    }
}
