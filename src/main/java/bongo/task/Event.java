package bongo.task;

import bongo.Bongo;

/**
 * Event represents a task that occurs at a specific time range, with a start and end date.
 * It extends the Task class and adds functionality for managing event dates.
 */
public class Event extends Task {
    private DateTime from;
    private DateTime to;

    /**
     * Constructs an Event with the specified description, start date, and end date.
     * The start and end dates must be in the "dd/MM/yyyy" format.
     *
     * @param description The description of the event.
     * @param from The start date of the event, in the "dd/MM/yyyy" format.
     * @param to The end date of the event, in the "dd/MM/yyyy" format.
     * @throws Bongo.BongoException If the date strings are not in the correct format.
     */
    public Event(String description, String from, String to) throws Bongo.BongoException {
        super(description);
        this.from = new DateTime(from);
        this.to = new DateTime(to);
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
