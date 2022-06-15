package com.teampingui.dao;

import com.teampingui.models.JournalEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;

public class JournalDAO {
    private static final String tableName = "journal";
    private static final String colDate = "datum";
    private static final String colEntry = "entry";
    private static final ObservableList<JournalEntry> journal;
    //Initializing the logger
    private static Logger log = LogManager.getLogger(JournalDAO.class);

    static {
        journal = FXCollections.observableArrayList();
        updateJournalFromDB();
    }

    public static ObservableList<JournalEntry> getJournalEntries() {
        return FXCollections.observableList(journal);
    }

    private static void updateJournalFromDB() {
        String getTableQuery = "SELECT * FROM " + tableName + " ORDER BY ID DESC";

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

    public int insert(JournalEntry journalEntry) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = Database.connect();
            connection.setAutoCommit(false);
            String query = "INSERT INTO journal(datum, entry) VALUES(?, ?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int counter = 1;
            statement.setString(counter++, journalEntry.getDate());
            statement.setString(counter++, journalEntry.getEntry());
            statement.executeUpdate();
            connection.commit();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException exception) {
            log.error(exception.getMessage());
            connection.rollback();
        } finally {
            if (null != resultSet) {
                resultSet.close();
            }

            if (null != statement) {
                statement.close();
            }

            if (null != connection) {
                connection.close();
            }
        }

        return 0;
    }

}
