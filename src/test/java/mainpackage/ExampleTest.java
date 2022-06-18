package mainpackage;

import com.teampingui.dao.Database;
import com.teampingui.dao.JournalDAO;
import com.teampingui.exceptions.JournalDaoException;
import com.teampingui.models.JournalEntryItem;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExampleTest extends Database {

    @Test
    public void testInsertJournalEntry() {
        // This test might delete all existing data in the database

        Database.connect();
        JournalDAO journalDAO = new JournalDAO();

        // A loop to delete every item from the journal database
        JournalEntryItem journalEntry = new JournalEntryItem("01.01.2020", "This text came from Unit Testing");
        try {
            journalDAO.insert(journalEntry);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JournalDaoException e) {
            throw new RuntimeException(e);
        }
        assertEquals(1, journalDAO.getAll().size());

        // Remove last entry from database
        journalDAO.delete(journalEntry);
    }
}
