package bongo.task;

import java.io.Serializable;

/**
 * Task represents a simple task with a description and completion status.
 * It provides methods to mark a task as done or undone, and to display its status.
 */
public class Task implements Serializable {
    private final String DESCRIPTION;
    private boolean isDone;

    /**
     * Constructs a Task with the specified description, initially not marked as done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.DESCRIPTION = description;
        this.isDone = false;
    }

    public String getDescription() {
        return DESCRIPTION;
    }

    private String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]"); // mark done task with X
    }

    /**
     * Marks the task as done.
     *
     * @return true if the task was successfully marked as done, false if it was already done.
     */
    public boolean mark() {
        if (isDone) {
            return false;
        }
        isDone = true;
        return true;
    }

    /**
     * Marks the task as not done.
     *
     * @return true if the task was successfully unmarked, false if it was already not done.
     */
    public boolean unmark() {
        if (!isDone) {
            return false;
        }
        isDone = false;
        return true;
    }

    @Override
    public String toString() {
        return getStatusIcon() + " " + DESCRIPTION;
    }
}
