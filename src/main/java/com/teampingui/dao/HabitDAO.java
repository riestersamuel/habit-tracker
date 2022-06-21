package com.teampingui.dao;

import com.teampingui.exceptions.HabitDaoException;
import com.teampingui.exceptions.NotInDatabaseException;
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

    private final ObservableList<Habit> mosHabits;

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
                        resultSet.getInt("id"),
                        resultSet.getString(DB_COLUMN_NAME),
                        haveTodoDays)
                );
            }
            log.info("Habits were loaded successfully.");
        } catch (SQLException e) {
            log.error(LocalDateTime.now() + ": could not load habits from database." + e.getMessage());
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
            log.error(LocalDateTime.now() + ": could not load habits from database." + e.getMessage());
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

                habit.setDB_ID(id);
                mosHabits.add(habit);
                log.info("Habit was inserted successfully into the database.");
            }
        } catch (SQLException exception) {
            log.error("An error occured while inserting a habit into the database." + exception.getMessage());
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
            String query = "DELETE FROM habit WHERE id=?;";
            statement = connection.prepareStatement(query);
            statement.setInt(1, habit.getDB_ID());
            statement.executeUpdate();
            connection.commit();
            log.info("Habit was successfully deleted from the database.");
            } catch (SQLException exception) {
            log.error("An error occurred while deleting a habit from the database." + exception.getMessage());
        } catch (NotInDatabaseException notInDatabaseException) {
            log.warn("Habit is not linked to a database entry", notInDatabaseException);
        }

        // Delete habit from List
        mosHabits.remove(habit);
    }

    public int indexOf(Habit habit) {
        System.out.println(mosHabits.indexOf(habit));
        return mosHabits.indexOf(habit);
    }

    public void setIsChecked(Habit habit, LocalDate date, boolean isChecked) {
        Connection connection = null;
        PreparedStatement statement = null;

        String checkDate = date.toString();

        try {
            connection = Database.connect();
            connection.setAutoCommit(false);

            String query = "";

            if (isChecked) {
                query = "INSERT INTO checkedDays (habit_id, entry_date, done) VALUES (?, date('" + checkDate + "'), 1);";
            } else {
                query = "DELETE FROM checkedDays WHERE habit_id =? AND entry_date = date('" + checkDate + "');";
            }

            statement = connection.prepareStatement(query);
            statement.setInt(1, habit.getDB_ID());
            statement.executeUpdate();
            connection.commit();


            log.info("Habit checks were successfully updated from the database.");
        } catch (SQLException exception) {
            log.error("An error occurred while loading checkedDays from the database." + exception.getMessage());
        } catch (NotInDatabaseException notInDatabaseException) {
            log.warn("Habit is not linked to a database entry", notInDatabaseException);
        }




    }
}

