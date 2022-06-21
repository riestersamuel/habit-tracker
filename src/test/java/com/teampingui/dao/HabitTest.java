package com.teampingui.dao;

import com.teampingui.models.Habit;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

class HabitTest {
    HabitDAO habitDAO = new HabitDAO();
    Habit habit = new Habit("Test", new boolean[]{true, false, true, true, true, false, false});

    @Test
    public void testRemoveHabitEntry() {
        // First we need to add a new habit
        try {
            habitDAO.insert(habit);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // See if the habit is now in the list
        System.out.println(habitDAO.getAll());
        // Remove last entry from list and database
        habitDAO.delete(habit);

        // See if the habit is still in the list
        System.out.println(habitDAO.getAll());
        Assumptions.assumeFalse(habitDAO.getAll().contains(habit));
    }

    @Test
    public void testInsertHabitEntry() {
        //TODO: Check if that's correct testing method
        try {
            habitDAO.insert(habit);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // Does the list of entries contain the new entry?
        //Assumptions.assumeTrue(habitDAO.mosHabits.contains(habit)); // TODO: Dont change access modifier because of unit test!
        Assumptions.assumeTrue(habitDAO.getAll().contains(habit));

        // Remove last entry from list and database
        habitDAO.delete(habit);
    }
}