import java.io.Serializable;

public class Task implements Serializable {
    private final String DESCRIPTION;
    private boolean isDone;

    public Task(String description) {
        this.DESCRIPTION = description;
        this.isDone = false;
    }

    private String getStatusIcon() {
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
        return getStatusIcon() + " " + DESCRIPTION;
    }
}