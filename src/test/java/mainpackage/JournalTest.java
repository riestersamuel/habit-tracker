package mainpackage;

import com.teampingui.dao.Database;
import com.teampingui.dao.JournalDAO;
import com.teampingui.exceptions.JournalDaoException;
import com.teampingui.models.JournalEntryItem;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JournalTest extends Database {

    @Test
    public void testInsertJournalEntry() {
        //TODO: Check if that's correct testing method
        Database.connect();
        JournalDAO journalDAO = new JournalDAO();
        JournalEntryItem journalEntry = new JournalEntryItem("01.01.2020", "This text came from Unit Testing");
        try {
            journalDAO.insert(journalEntry);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JournalDaoException e) {
            throw new RuntimeException(e);
        }

        // Does the list of entries contain the new entry?
        Assumptions.assumeTrue(journalDAO.mosJournalEntries.contains(journalEntry));

        // Remove last entry from list and database
        //TODO: After implemeting the delete method, check or manage that the new entry is also removed from the database
        journalDAO.delete(journalEntry);
    }
}
