package com.teampingui.dao;

import com.teampingui.exceptions.HabitDaoException;
import com.teampingui.interfaces.IDao;
import com.teampingui.models.Day;
import com.teampingui.models.Habit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class HabitDAO implements IDao<Habit> {
    private static final String DB_TABLE_HABIT = "Habits";
    private static final String DB_TABLE_HAVETODODAYS = "haveTodoDays";
    private static final String DB_TABLE_CHECKEDDAYS = "checkedDays";
    private static final String DB_COLUMN_NAME = "name";

    private static final Logger log = LogManager.getLogger(HabitDAO.class);

    public final ObservableList<Habit> mosHabits;

    public HabitDAO() {
        mosHabits = FXCollections.observableArrayList();
        try {
            mosHabits.addAll(read());
            loadCheckedData(LocalDate.now());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private List<Habit> read() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Habit> habitEntries = new ArrayList<>();

        try {
            connection = Database.connect();

            String getStringQuery = """
                    SELECT habit.id, habit.name, GROUP_CONCAT(haveTodoDays.weekday) AS weekdays
                    FROM habit, haveTodoDays
                    WHERE habit.id = haveTodoDays.habit_id
                    AND haveTodoDays.havetodo = 1
                    GROUP BY habit.id;
                    """;
            statement = connection.prepareStatement(getStringQuery);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.println("Loaded: " + resultSet.getString(DB_COLUMN_NAME));
                // Getting haveTodoDays Boolean Array
                List<String> splitConcat = Arrays.asList(resultSet.getString("weekdays").split(","));
                List<Integer> weekdays = splitConcat.stream().map(Integer::parseInt).toList();
                boolean[] haveTodoDays = new boolean[7];
                weekdays.forEach(i -> haveTodoDays[i] = true);

                habitEntries.add(new Habit(

                        resultSet.getString(DB_COLUMN_NAME),
                        haveTodoDays)
                );
            }
        } catch (SQLException e) {
            log.info("Habits couldn't be loaded. Please try again!");
            log.error(LocalDateTime.now() + ": could not load habits from database." + e);
            mosHabits.clear();
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

        return habitEntries;
    }

    public void loadCheckedData(LocalDate date) throws SQLException { // TODO: cleanup function
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String fromDate = date.with(DayOfWeek.MONDAY).toString();
        String toDate = date.with(DayOfWeek.SUNDAY).toString();

        try {
            connection = Database.connect();

            String getStringQuery = "SELECT habit.id, habit.name, GROUP_CONCAT(checkedDays.entry_date) AS done_days " +
                    "FROM habit, checkedDays " +
                    "WHERE habit.id = checkedDays.habit_id " +
                    "AND checkedDays.done = 1 " +
                    "AND entry_date >= date('" + fromDate + "') AND entry_date <= date('" + toDate + "') " +
                    "GROUP BY habit.id;";

            statement = connection.prepareStatement(getStringQuery);
            resultSet = statement.executeQuery();

            //reset
            mosHabits.forEach(i -> i.setCheckedDays(new boolean[]{false, false, false, false, false, false, false}));

            while (resultSet.next()) {
                // Getting haveTodoDays Boolean Array
                List<String> splitConcat = Arrays.asList(resultSet.getString("done_days").split(","));

                boolean[] doneDays = new boolean[7];
                splitConcat.forEach(sDate -> doneDays[LocalDate.parse(sDate).getDayOfWeek().getValue() - 1] = true);
                mosHabits.get(resultSet.getInt("id") - 1).setCheckedDays(doneDays); // TODO: get habit by DB ID!!! otherwise gets buggy with deleted habits)
            }
        } catch (SQLException e) {
            log.info("Habits couldn't be loaded. Please try again!");
            log.error(LocalDateTime.now() + ": could not load habits from database." + e);
            //mosHabits.clear();
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

    }


    @Override
    public Optional<Habit> get(long id) {
        return Optional.ofNullable(mosHabits.get((int) id));
    }

    @Override
    public ObservableList<Habit> getAll() {
        return mosHabits;
    }

    @Override
    public int insert(Habit habit) throws Exception { // TODO: clean this mess up
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int id = 0;

        try {
            connection = Database.connect();
            connection.setAutoCommit(false);

            String query = "INSERT INTO habit (name, reps) VALUES (?,?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int counter = 0;
            statement.setString(++counter, habit.nameProperty().getValue());
            statement.setString(++counter, null);
            statement.executeUpdate();
            connection.commit();
            resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                id = resultSet.getInt(1);

                query = "INSERT INTO main.haveTodoDays (habit_id, weekday, havetodo) VALUES (?,?,?)";
                statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                for (Day day : Day.values()) {
                    if (!habit.hasToBeDone(day))
                        continue;

                    counter = 0;
                    statement.setInt(++counter, id);
                    statement.setInt(++counter, day.ordinal());
                    statement.setInt(++counter, 1);
                    statement.executeUpdate();
                }
                connection.commit();

                //habit.setID(id); // TODO: ID for Habit?
                mosHabits.add(habit);
                System.out.println("Sollte geklappt haben...");
            }
        } catch (SQLException exception) {
            log.error(exception.getMessage());
            System.out.println("Nope: "+ exception);
            connection.rollback();
            throw new HabitDaoException(exception);
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
    public void update(int index, Habit habit) {

    }

    @Override
    public void delete(Habit habit) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = Database.connect();
            connection.setAutoCommit(false);

            // Delete Habit from Database
            String query = "DELETE FROM habit WHERE name = ?;";
            statement = connection.prepareStatement(query);
            //TODO: Delete id?
            statement.setString(1, habit.nameProperty().getValue());
            statement.executeUpdate();
            connection.commit();

            } catch (SQLException exception) {
            log.error(exception.getMessage());
        }

        // Delete habit from List
        mosHabits.remove(habit);
    }

    public int indexOf(Habit habit) {
        System.out.println(mosHabits.indexOf(habit));
        return mosHabits.indexOf(habit);
    }

    public void setIsChecked(Habit habit, Day day, boolean isChecked) {
        // TODO: Update checkedDays Database
        // remove if isChecked=false
        // add    if isChecked=true
    }
}

