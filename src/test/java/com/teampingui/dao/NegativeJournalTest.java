package com.teampingui.dao;

import com.teampingui.exceptions.JournalDaoException;
import com.teampingui.models.JournalEntryItem;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NegativeJournalTest {
    JournalDAO journalDAO = new JournalDAO();
    JournalEntryItem journalEntry = new JournalEntryItem("05.05.1999", null);

    //We assume that a runtime exception is thrown when the entry is null
    @Test (expected = RuntimeException.class)
    public void testInsertJournalEntry() {
        try {
            journalDAO.insert(journalEntry);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JournalDaoException e) {
            throw new RuntimeException(e);
        }
    }
}
