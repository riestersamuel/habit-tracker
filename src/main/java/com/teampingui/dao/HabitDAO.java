package com.teampingui.dao;

import com.teampingui.models.Habit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HabitDAO {
    private static final String tableName = "Habits";
    private static final String colHabitName = "name";
    /*private static final String colMon = "Mon";
    private static final String colTue = "Tue";
    private static final String colWed = "Wed";
    private static final String colThu = "Thu";
    private static final String colFri = "Fri";
    private static final String colSat = "Sat";
    private static final String colSun = "Sun"; */
    private static final String colDone = "reps";
    private static final ObservableList<Habit> habits;
    //Initializing the logger
    private static Logger log = LogManager.getLogger(HabitDAO.class);

    static {
        habits = FXCollections.observableArrayList();
        updateHabitsFromDB();
    }

    public static ObservableList<Habit> getHabits() {
        return FXCollections.observableList(habits);
    }

    private static void updateHabitsFromDB() {
        //TODO
        String getTableQuery = "SELECT * FROM" + tableName;

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

