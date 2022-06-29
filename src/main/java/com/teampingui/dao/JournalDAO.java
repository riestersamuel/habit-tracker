package com.teampingui.dao;

import com.teampingui.exceptions.JournalDaoException;
import com.teampingui.exceptions.NotInDatabaseException;
import com.teampingui.interfaces.IDao;
import com.teampingui.models.JournalEntryItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JournalDAO implements IDao<JournalEntryItem> {
    private static final String DB_TABLE_NAME = "journal";
    private static final String DB_COLUMN_ID = "id";
    private static final String DB_COLUMN_DATE = "journal_date";
    private static final String DB_COLUMN_ENTRY = "entry";

    private static final Logger log = LogManager.getLogger(JournalDAO.class);

    private final ObservableList<JournalEntryItem> mosJournalEntries;

    public JournalDAO() {
        mosJournalEntries = FXCollections.observableArrayList();
        try {
            mosJournalEntries.addAll(read());
            log.info("Successfully load journal entries from database.");
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Could not load journal entries from database." + e.getMessage());

        }
    }

    private List<JournalEntryItem> read() throws SQLException {
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
                        resultSet.getInt(DB_COLUMN_ID),
                        resultSet.getString(DB_COLUMN_DATE),
                        resultSet.getString(DB_COLUMN_ENTRY)
                );
                log.debug("Loaded JournalEntry: " + journalEntry);
                journalEntries.add(journalEntry);
            }
        } catch (SQLException exception) {
            log.error("An error occured while reading journal entries from database: " + exception.getMessage());
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

    @Override
    public Optional<JournalEntryItem> get(long id) {
        return Optional.ofNullable(mosJournalEntries.get((int) id));
    }

    @Override
    public ObservableList<JournalEntryItem> getAll() {
        return mosJournalEntries;
    }

    public int insert(JournalEntryItem journalEntry) throws SQLException, JournalDaoException { // TODO: Need for returning inserted id..?
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int id = 0;

        try {
            connection = Database.connect();
            connection.setAutoCommit(false);
            String query = "INSERT INTO "+DB_TABLE_NAME+"("+DB_COLUMN_DATE+", "+DB_COLUMN_ENTRY+") VALUES(?, ?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int counter = 0;
            statement.setString(++counter, journalEntry.getDate());
            statement.setString(++counter, journalEntry.getContent());
            statement.executeUpdate();
            connection.commit();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
                journalEntry.setID(id);
                mosJournalEntries.add(journalEntry);
            }
            log.info("Successfully insert journal entry '" + journalEntry + "' into database.");
        } catch (SQLException exception) {
            log.error("An error occured while inserting journal entry into database: " + exception.getMessage());
            connection.rollback();
            throw new JournalDaoException(exception);
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

        return id;
    }

    @Override
    public void update(int index, JournalEntryItem journalEntryItem) {
    }

    @Override
    public void delete(JournalEntryItem journalEntryItem) {
        // This method is currently only used for a unit test
        // Delete Habit from Database
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = Database.connect();
            connection.setAutoCommit(false);

            // Delete Habit from Database
            String query = "DELETE FROM "+DB_TABLE_NAME+" WHERE id=?;";
            statement = connection.prepareStatement(query);
            statement.setInt(1, journalEntryItem.getID());
            statement.executeUpdate();
            connection.commit();
            log.info("Journal entry '" + journalEntryItem + "' was successfully deleted from the database.");
        } catch (SQLException exception) {
            log.error("An error occurred while deleting a journal entry  from the database." + exception.getMessage());
        } catch (NotInDatabaseException notInDatabaseException) {
            log.warn("Journal entry is not linked to a database entry", notInDatabaseException);
        }

        // Delete habit from List
        mosJournalEntries.remove(journalEntryItem);
    }

}
