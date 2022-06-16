package com.teampingui.dao;

import com.teampingui.interfaces.IDao;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class HabitDAO implements IDao<Habit> {
    private static final String DB_TABLE_HABIT = "Habits";
    private static final String DB_TABLE_HAVETODODAYS = "haveTodoDays";
    private static final String DB_COLUMN_NAME = "name";

    private static final Logger log = LogManager.getLogger(HabitDAO.class);

    private final ObservableList<Habit> mosHabits;

    public HabitDAO() {
        mosHabits = FXCollections.observableArrayList();
        try {
            mosHabits.addAll(read());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Habit> read() throws SQLException {
        // TODO
        //  - read habits from database
        //  - return as list
        //  - read havetodo days and put into boolean Array (see dummy data)
        //  - read checked of habit between two dates (2022-06-13 to 2022-06-19)
        //     - put also in boolean array (see dummy data)


        /*String getStringQuery = """
                SELECT habit.id, habit.name, GROUP_CONCAT(haveTodoDays.weekday) AS weekdays
                FROM habit, haveTodoDays
                WHERE habit.id = haveTodoDays.habit_id
                AND haveTodoDays.havetodo = 1
                GROUP BY habit.id;
                """;

        try {
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

              //  new Habit(
                //        rs.getString(DB_COLUMN_NAME),
                //        havetodoweekdays,
                //        boolean[]
                //);
            }
        } catch (SQLException e) {
            log.error(LocalDateTime.now() + ": could not load Habits from database.");
            mosHabits.clear();
        }*/

        // TODO: REMOVE ME AFTER READ FROM DATABASE FEATURE IS IMPLEMENTED!!!
        //  Dummy Data
        List<Habit> dummyData = new ArrayList<>();
        dummyData.add(
                new Habit(
                        "Könken",
                        new boolean[]{true, true, true, true, true, true, true},
                        new boolean[]{false, false, true, false, true, false, false})
        );
        dummyData.add(
                new Habit(
                        "Könken",
                        new boolean[]{true, false, false, false, false, false, false},
                        new boolean[]{true, true, false, false, false, false, false})
        );
        dummyData.add(
                new Habit(
                        "saufen",
                        new boolean[]{true, false, false, true, false, false, false},
                        new boolean[]{true, false, false, true, false, false, false})
        );
        dummyData.add(
                new Habit(
                        "lesen",
                        new boolean[]{true, false, false, false, false, false, false},
                        new boolean[]{true, false, false, false, false, false, false})
        );
        dummyData.add(
                new Habit(
                        "lernen",
                        new boolean[]{true, false, false, false, false, false, false},
                        new boolean[]{true, false, false, false, false, false, false})
        );
        return dummyData;
    }


    @Override
    public Optional<Habit> get(long id) {
        return Optional.ofNullable(mosHabits.get((int)id));
    }

    @Override
    public ObservableList<Habit> getAll() {
        return mosHabits;
    }

    @Override
    public int insert(Habit habit) throws Exception {
        return 0;
    }

    @Override
    public void update(int index, Habit habit) {

    }

    @Override
    public void delete(Habit habit) {

    }

    public int indexOf(Habit habit) {
        return mosHabits.indexOf(habit);
    }
}

