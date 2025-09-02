package bongo.task;

import bongo.Bongo;

/**
 * Deadline represents a task with a specific due date.
 * It extends the Task class and adds functionality for managing the due date.
 */
public class Deadline extends Task {

    private DateTime by;

    /**
     * Constructs a Deadline with the specified description and due date.
     * The due date must be in the "dd/MM/yyyy" format.
     *
     * @param description The description of the deadline task.
     * @param by The due date of the task, in the "dd/MM/yyyy" format.
     * @throws Bongo.BongoException If the date string is not in the correct format.
     */
    public Deadline(String description, String by) throws Bongo.BongoException {
        super(description);
        this.by = new DateTime(by);
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + by + ")";
    }
}