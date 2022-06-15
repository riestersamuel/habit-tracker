package com.teampingui.dao;

import com.teampingui.models.Habit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HabitDAO {
    private static final String DB_TABLE_HABIT = "Habits";
    private static final String DB_TABLE_HAVETODODAYS = "haveTodoDays";
    private static final String DB_COLUMN_NAME = "name";


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
        String getStringQuery = """
                SELECT habit.id, habit.name, GROUP_CONCAT(haveTodoDays.weekday) AS weekdays
                FROM habit, haveTodoDays
                WHERE habit.id = haveTodoDays.habit_id
                AND haveTodoDays.havetodo = 1
                GROUP BY habit.id;
                """;

        try  {
            Connection connection = Database.connect();
            PreparedStatement statement = connection.prepareStatement(getStringQuery);
            ResultSet rs = statement.executeQuery();
            mosHabits.clear();

            while (rs.next()) {
                List<String> temp = Arrays.asList(rs.getString("weekdays").split(","));
                List<Integer> zahlen = temp.stream().map(Integer::parseInt).toList();

                boolean[] havetodoweekdays = new boolean[7];

                for (int i : zahlen) {
                    havetodoweekdays[i] = true;
                }

              /*  new Habit(
                        rs.getString(DB_COLUMN_NAME),
                        havetodoweekdays,
                        boolean[]
                ); */
            }
        } catch (SQLException e) {
            log.error(LocalDateTime.now() + ": could not load Habits from database.");
           mosHabits.clear();
        }

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

