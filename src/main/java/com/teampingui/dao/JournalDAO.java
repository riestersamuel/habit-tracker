package com.teampingui.dao;

import com.teampingui.models.JournalEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.teampingui.dao.Database.location;

public class JournalDAO {
    //Initializing the logger
    private static Logger log = LogManager.getLogger(JournalDAO.class);

    private static final String tableName = "journal";
    private static final String colDate = "datum";
    private static final String colEntry = "entry";

    private static final ObservableList<JournalEntry> journal;

    static {
        journal = FXCollections.observableArrayList();
        updateJournalFromDB();
    }

    public static ObservableList<JournalEntry> getJournalEntrys() {
        return FXCollections.observableList(journal);
    }

    private static void updateJournalFromDB() {
        String getTableQuery ="SELECT * FROM " + tableName + " ORDER BY ID DESC";

       try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(getTableQuery);
            ResultSet rs = statement.executeQuery();
            journal.clear();
            while (rs.next()) {
               // String date = LocalDate.parse(rs.getString(colDate)).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
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

    // This method puts the journal entries into the database
    public static void insertJournal(String content, String currentDate) {
        String query = "INSERT INTO journal (datum, entry) VALUES ('" + currentDate + "', '" + content + "');";

        try {
            Connection con = Database.connect();
            System.out.println("You sent the following query to the database: " + query);
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.executeUpdate();

            stmt.close();
            con.close();
        }

        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
