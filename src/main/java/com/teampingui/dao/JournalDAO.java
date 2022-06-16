package com.teampingui.dao;

import com.teampingui.exceptions.JournalDaoException;
import com.teampingui.interfaces.IDao;
import com.teampingui.models.JournalEntryItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JournalDAO implements IDao<JournalEntryItem> {
    private static final String DB_TABLE_NAME = "journal";
    private static final String DB_COLUMN_ID = "id";
    private static final String DB_COLUMN_DATE = "datum"; // TODO: set table column to "date" instead of "datum"
    private static final String DB_COLUMN_ENTRY = "entry";

    private static final Logger log = LogManager.getLogger(JournalDAO.class);

    private final ObservableList<JournalEntryItem> mosJournalEntries;

    public JournalDAO() {
        mosJournalEntries = FXCollections.observableArrayList();
        try {
            mosJournalEntries.addAll(read());
        } catch (SQLException e) {
            e.printStackTrace();
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
                //log.debug("Loaded JournalEntry: " + journalEntry);
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
        try {
            connection = Database.connect();
            connection.setAutoCommit(false);
            String query = "INSERT INTO journal(datum, entry) VALUES(?, ?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int counter = 0;
            statement.setString(++counter, journalEntry.getDate());
            statement.setString(++counter, journalEntry.getContent());
            statement.executeUpdate();
            connection.commit();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                journalEntry.setID(id);
                mosJournalEntries.add(journalEntry);
                return id;
            }
        } catch (SQLException exception) {
            log.error(exception.getMessage());
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

        return 0;
    }

    @Override
    public void update(int index, JournalEntryItem journalEntryItem) {
        mosJournalEntries.set(index, journalEntryItem);
        // TODO: Database
    }

    @Override
    public void delete(JournalEntryItem journalEntryItem) {
        // TODO: Object & Database
    }

}
