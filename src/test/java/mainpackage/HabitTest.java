package mainpackage;

import com.teampingui.dao.Database;
import com.teampingui.dao.HabitDAO;
import com.teampingui.models.Habit;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

public class HabitTest extends Database {

    @Test
    public void testInsertHabitEntry() {
        //TODO: Check if that's correct testing method
        Database.connect();
        HabitDAO habitDAO = new HabitDAO();
        Habit habit = new Habit("Test", new boolean[]{true, false, true, true, true, false, false});

        try {
            habitDAO.insert(habit);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Does the list of entries contain the new entry?
        Assumptions.assumeTrue(habitDAO.mosHabits.contains(habit));

        // Remove last entry from list and database
        habitDAO.delete(habit);
    }

    @Test
    public void testRemoveHabitEntry() {
        // First we need to add a new habit
        Database.connect();
        HabitDAO habitDAO = new HabitDAO();
        Habit habit = new Habit("Remove this", new boolean[]{true, false, true, true, true, false, false});
        try {
            habitDAO.insert(habit);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // See if the habit is now in the list
        System.out.println("Before deleting it: " + habitDAO.mosHabits);

        // Remove last entry from list and database
        habitDAO.delete(habit);

        // See if the habit is still in the list
        System.out.println("After deleting it: " + habitDAO.mosHabits);
        Assumptions.assumeFalse(habitDAO.mosHabits.contains(habit));
    }
}
