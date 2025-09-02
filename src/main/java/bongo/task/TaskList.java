package bongo.task;

import bongo.Bongo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * TaskList manages a collection of Task objects.
 */
public class TaskList implements Serializable {
    private final ArrayList<Task> TASKS = new ArrayList<>();

    /**
     * Returns whether the task list is empty.
     *
     * @return true if the task list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return TASKS.isEmpty();
    }

    /**
     * Adds a new task to the task list.
     *
     * @param task The task to be added.
     */
    public void add(Task task) {
        TASKS.add(task);
    }

    /**
     * Retrieves a task from the task list by index.
     *
     * @param index The index of the task to retrieve.
     * @return The task at the specified index.
     * @throws Bongo.BongoException If the index is invalid.
     */
    public Task get(int index) throws Bongo.BongoException {
        validateIndex(index);
        return TASKS.get(index);
    }

    /**
     * Retrieves a task from the task list by string input, which is parsed to an index.
     *
     * @param input The string input representing the task index.
     * @return The task at the specified index.
     * @throws Bongo.BongoException If the input is not a valid index.
     */
    public Task get(String input) throws Bongo.BongoException {
        return get(parseIndex(input));
    }

    /**
     * Removes a task from the task list by index.
     *
     * @param index The index of the task to be removed.
     * @return The removed task.
     * @throws Bongo.BongoException If the index is invalid.
     */
    public Task remove(int index) throws Bongo.BongoException {
        validateIndex(index);
        return TASKS.remove(index);
    }

    /**
     * Removes a task from the task list by string input, which is parsed to an index.
     *
     * @param input The string input representing the task index.
     * @return The removed task.
     * @throws Bongo.BongoException If the input is not a valid index.
     */
    public Task remove(String input) throws Bongo.BongoException {
        return remove(parseIndex(input));
    }

    private int parseIndex(String input) throws Bongo.BongoException {
        try {
            return Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            throw new Bongo.BongoException("\"" + input + "\" Isn't even a number!");
        }
    }

    private void validateIndex(int index) throws Bongo.BongoException {
        if (index < 0 || index >= TASKS.size()) {
            throw new Bongo.BongoException("Can't find that one...");
        }
    }

    public String find(String key) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Task task : TASKS) {
            if (task.getDescription().contains(key)) {
                sb.append(i).append(". ").append(task).append('\n');
            }
            i++;
        }
        return sb.toString().trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Task task : TASKS) {
            sb.append(i++).append(". ").append(task).append('\n');
        }
        return sb.toString().trim();
    }
}
