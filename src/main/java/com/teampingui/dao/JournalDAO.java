package com.teampingui.dao;

import com.teampingui.models.Habit;
import com.teampingui.models.JournalEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JournalDAO {
    //Initializing the logger
    private static Logger log = LogManager.getLogger(JournalDAO.class);

    private static final ObservableList<JournalEntry> journal;

    static {
        journal = FXCollections.observableArrayList();
        updateJournalFromDB();
    }

    public static ObservableList<JournalEntry> getJournalEntrys() {
        return FXCollections.observableList(journal);
    }

    private static void updateJournalFromDB() {
        //TODO
    }

    public static void insertJournal(JournalEntry entry) {
        //TODO
    }
}
