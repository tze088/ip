public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    protected String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]"); // mark done task with X
    }

    public boolean mark() {
        if (isDone) {
            return false;
        }
        isDone = true;
        return true;
    }

    public boolean unmark() {
        if (!isDone) {
            return false;
        }
        isDone = false;
        return true;
    }

    @Override
    public String toString() {
        return getStatusIcon() + " " + description;
    }
}