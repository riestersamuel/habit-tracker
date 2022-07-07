package com.teampingui.models;

import com.teampingui.exceptions.NotInDatabaseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JournalEntryTest {

    JournalEntry journalEntry;

    @BeforeEach
    void setUp() {
        journalEntry = new JournalEntry(999, "20.07.2020", "This is a test entry for UnitTests");
    }

    @Test
    void getDate() {
        Assertions.assertEquals("20.07.2020", journalEntry.getDate());
    }

    @Test
    void setDate() {
        journalEntry.setDate("31.01.2021");
        Assertions.assertEquals("31.01.2021", journalEntry.getDate());
    }

    @Test
    void getContent() {
        Assertions.assertEquals("This is a test entry for UnitTests", journalEntry.getContent());
    }

    @Test
    void setContent() {
        journalEntry.setContent("This is another Testtext for Unit Testing");
        Assertions.assertEquals("This is another Testtext for Unit Testing", journalEntry.getContent());
    }

    @Test
    void getID() throws NotInDatabaseException {
        Assertions.assertEquals(999, journalEntry.getID());
    }

    @Test
    void setID() throws NotInDatabaseException {
        journalEntry.setID(888);
        Assertions.assertEquals(888, journalEntry.getID());
    }
}