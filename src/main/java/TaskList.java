import java.io.Serializable;
import java.util.ArrayList;

public class TaskList implements Serializable {
    private final ArrayList<Task> TASKS = new ArrayList<>();

    public boolean isEmpty() {
        return TASKS.isEmpty();
    }

    public void add(Task task) {
        TASKS.add(task);
    }

    public Task get(int index) throws Bongo.BongoException {
        validateIndex(index);
        return TASKS.get(index);
    }

    public Task get(String input) throws Bongo.BongoException {
        return get(parseIndex(input));
    }

    public Task remove(int index) throws Bongo.BongoException {
        validateIndex(index);
        return TASKS.remove(index);
    }

    public Task remove(String input) throws Bongo.BongoException {
       return TASKS.remove(parseIndex(input));
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
