package bongo.task;

import bongo.Bongo;

public class Event extends Task {
    private DateTime from;
    private DateTime to;

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
