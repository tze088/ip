package bongo.task;

import bongo.Bongo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskListTest {

    private TaskList taskList;
    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
        task1 = new Task("Test Task 1");
        task2 = new Task("Test Task 2");
    }

    @Test
    void testIsEmpty_WhenListIsEmpty() {
        assertTrue(taskList.isEmpty(), "Task list should be empty initially");
    }

    @Test
    void testIsEmpty_WhenListHasTasks() {
        taskList.add(task1);
        assertFalse(taskList.isEmpty(), "Task list should not be empty after adding a task");
    }

    @Test
    void testAdd_Task() {
        taskList.add(task1);
        assertEquals(1, taskList.size(), "Task list should contain 1 task after adding one task");
    }

    @Test
    void testGet_TaskByIndex() throws Bongo.BongoException {
        taskList.add(task1);
        Task retrievedTask = taskList.get(0);
        assertEquals(task1, retrievedTask, "Task should be retrieved by index");
    }

    @Test
    void testGet_TaskByIndex_Invalid() {
        taskList.add(task1);
        assertThrows(Bongo.BongoException.class, () -> taskList.get(1), "Should throw exception for invalid index");
    }

    @Test
    void testGet_TaskByStringInput() throws Bongo.BongoException {
        taskList.add(task1);
        Task retrievedTask = taskList.get("1");
        assertEquals(task1, retrievedTask, "Task should be retrieved by string input");
    }

    @Test
    void testGet_TaskByStringInput_Invalid() {
        taskList.add(task1);
        assertThrows(Bongo.BongoException.class, () -> taskList.get("abc"), "Should throw exception for invalid string input");
    }

    @Test
    void testRemove_TaskByIndex() throws Bongo.BongoException {
        taskList.add(task1);
        Task removedTask = taskList.remove(0);
        assertEquals(task1, removedTask, "Removed task should match the task that was added");
        assertTrue(taskList.isEmpty(), "Task list should be empty after removing the task");
    }

    @Test
    void testRemove_TaskByIndex_Invalid() {
        taskList.add(task1);
        assertThrows(Bongo.BongoException.class, () -> taskList.remove(1), "Should throw exception for invalid index");
    }

    @Test
    void testRemove_TaskByStringInput() throws Bongo.BongoException {
        taskList.add(task1);
        Task removedTask = taskList.remove("1");
        assertEquals(task1, removedTask, "Removed task should match the task that was added");
        assertTrue(taskList.isEmpty(), "Task list should be empty after removing the task");
    }

    @Test
    void testRemove_TaskByStringInput_Invalid() {
        taskList.add(task1);
        assertThrows(Bongo.BongoException.class, () -> taskList.remove("abc"), "Should throw exception for invalid string input");
    }

    @Test
    void testToString() {
        taskList.add(task1);
        taskList.add(task2);
        String expected = "1. [ ] Test Task 1\n2. [ ] Test Task 2";
        assertEquals(expected, taskList.toString(), "toString should return the correct string representation of the task list");
    }
}
