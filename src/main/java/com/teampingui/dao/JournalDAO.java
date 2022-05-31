package com.teampingui.dao;

import com.teampingui.models.Habit;
import com.teampingui.models.JournalEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class JournalDAO {
    //Initializing the logger
    private static Logger log = LogManager.getLogger(JournalDAO.class);

    private static final String tableName = "journal";
    private static final String colEntry = "content";
    private static final String colDate = "datum";

    private static final ObservableList<JournalEntry> journal;

    static {
        journal = FXCollections.observableArrayList();
        updateJournalFromDB();
    }

    public static ObservableList<JournalEntry> getJournalEntrys() {
        return FXCollections.observableList(journal);
    }

    private static void updateJournalFromDB() {
        String getTableQuery ="SELECT * FROM " + tableName;
        System.out.println(getTableQuery);

       try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(getTableQuery);
            ResultSet rs = statement.executeQuery();
            journal.clear();
            while (rs.next()) {
                journal.add(new JournalEntry(
                        rs.getString(colDate),
                        rs.getString(colEntry)
                ));
            }
        } catch (SQLException e) {
            log.error(LocalDateTime.now(), e);
            journal.clear();
        }
    }

    public static void insertJournal(JournalEntry entry) {
        //TODO
    }
}
