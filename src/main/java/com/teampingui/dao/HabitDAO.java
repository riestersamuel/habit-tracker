package com.teampingui.dao;

import com.teampingui.models.Habit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HabitDAO {
    private static final String DB_TABLE_NAME = "Habits";
    private static final String DB_COLUMN_NAME = "name";
    private static final String DB_COLUMN_REPS = "reps";
    private static final ObservableList<Habit> mosHabits; // TODO: put in Controller..?
    //Initializing the logger
    private static Logger log = LogManager.getLogger(HabitDAO.class);

    static {
        mosHabits = FXCollections.observableArrayList();
        updateHabitsFromDB();
    }

    public static ObservableList<Habit> getHabits() {
        return FXCollections.observableList(mosHabits);
    }

    private static void updateHabitsFromDB() {
        //TODO
        String getTableQuery = "SELECT * FROM" + DB_TABLE_NAME;

       /* try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(getTableQuery);
            ResultSet rs = statement.executeQuery();
            habits.clear();
            while (rs.next()) {
                habits.add(new Habit(
                  /*      rs.getString(colHabitName),
                        rs.getArray(new boolean[]{true, true, true, true, true, true, true}),
                        rs.getArray(new boolean[]{false, false, true, false, true, false, false}
                );
            }
        } catch (SQLException e) {
            log.error(LocalDateTime.now() + ": could not load Habits from database.");
            habits.clear();
        } */

    }

    public static void insertHabit(Habit habit) {
        //TODO
    }

    public static void updateHabit(int id, Habit habit) {
        //TODO
    }

    public static void deleteHabit(int id) {
        //TODO
    }


}

