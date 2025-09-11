package bongo;

import bongo.task.Task;
import bongo.task.Event;
import bongo.task.Deadline;
import bongo.task.TaskList;

/**
 * Bongo is a chatbot application for managing tasks.
 * It allows the user to add, list, find, mark, unmark, and delete tasks through various commands.
 */
public class Bongo {

    public static final String BYE_MESSAGE = "Bye Bye!";
    private final TaskList TASKS;

    public Bongo() {
        TASKS = Io.loadTaskList();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            Command command = Command.fromInput(input);

            return switch (command.getType()) {
                // Simple commands
                // These will execute if there are words following, e.g. "bye bye"
                // Final desired behaviour TBD
                case BYE -> bye();
                case LIST -> list();

                // Compound commands
                case FIND -> find(command);
                case TODO, DEADLINE, EVENT -> addTask(command);
                case MARK, UNMARK -> handleMarkUnmark(command);
                case DELETE -> "Get out of here!\n  " + TASKS.remove(command.getArgs());
                case UNKNOWN -> "What are you going on about..?";
            };
        } catch (BongoException e) {
            return e.getMessage();
        }
    }

    private String bye() throws BongoException {
        Io.saveTaskList(TASKS);
        return BYE_MESSAGE;
    }

    private String list() throws BongoException {
        if (TASKS.isEmpty()) {
            return "You've got nothing to do except bother me, apparently";
        } else {
            return TASKS.toString();
        }
    }

    private String find(Command command) throws BongoException {
        String foundTasks = TASKS.find(command.getArgs());
        if (foundTasks.isBlank()) {
            return "Nada. And I'm not checking again.";
        } else {
            return "You'd better not lose track of them again, okay?\n"
                    + foundTasks;
        }
    }

    private String addTask(Command command) throws BongoException {
        try {
            String args = command.getArgs();

            Task task = switch (command.getType()) {
                case TODO -> new Task(args);
                case DEADLINE -> {
                    // Split task description and deadline for proper task parsing
                    // using the "/by" delimiter
                    String[] taskParts = args.split("\\s+/by\\s+", 2);
                    yield new Deadline(taskParts[0], taskParts[1]);
                }
                case EVENT -> {
                    // Split task description, start time, and end time for proper time range parsing
                    // using the "/from" and "/to" delimiters
                    String[] taskParts = args.split("\\s+/from\\s+|\\s+/to\\s+", 3);
                    yield new Event(taskParts[0], taskParts[1], taskParts[2]);
                }
                default -> throw new BongoException("Unknown task type: " + command.getType());
            };
            TASKS.add(task);
            return "Great, another thing to keep track of:\n  " + task;

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new BongoException("Nice try, but I need the time too. This isn’t a guessing game.");
        }
    }

    private String handleMarkUnmark(Command command) throws BongoException {
        Task task = TASKS.get(command.getArgs());

        String msg = switch (command.getType()) {
            case MARK -> task.mark()
                    ? "Finally done? I'm not impressed..."
                    : "You already did that one...";
            case UNMARK -> task.unmark()
                    ? "Made a mistake, did you?"
                    : "It wasn't even marked in the first place...";
            default -> throw new BongoException("Wrong input: " + command);
        };
        return msg + "\n  " + task;
    }

    /**
     * Exception class for errors occurring in the Bongo application.
     */
    public static class BongoException extends Exception {
        public BongoException(String msg) {
            super(msg);
        }
    }
}
