package com.teampingui.dao;

import com.teampingui.models.JournalEntryItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JournalDAO {
    private static final String DB_TABLE_NAME = "journal";
    private static final String DB_COLUMN_DATE = "datum"; // TODO: set table column to "date" instead of "datum"
    private static final String DB_COLUMN_ENTRY = "entry";

    private static Logger log = LogManager.getLogger(JournalDAO.class);

    public List<JournalEntryItem> read() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        List<JournalEntryItem> journalEntries = new ArrayList<>();

        try {
            connection = Database.connect();
            connection.setAutoCommit(false);
            String query = "SELECT * FROM " + DB_TABLE_NAME + " ORDER BY ID DESC";
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                JournalEntryItem journalEntry = new JournalEntryItem(
                        resultSet.getString(2), // 2: Date
                        resultSet.getString(3)  // 3: Entry
                );

                journalEntries.add(journalEntry);
            }
        } catch (SQLException exception) {
            log.error(exception.getMessage());
            connection.close();
        } finally {
            if (null != statement) {
                statement.close();
            }

            if (null != connection) {
                connection.close();
            }
        }

        return journalEntries;
    }

    public int insert(JournalEntryItem journalEntry) throws SQLException {
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
            statement.setString(counter++, journalEntry.getContent());
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
