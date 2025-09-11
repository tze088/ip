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

    private final TaskList TASKS;

    public Bongo() {
        TASKS = Io.loadTaskList();
        assert TASKS != null : "Task list should not be null.";
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        Command command = Command.fromInput(input);
        try {
            return switch (command.getType()) {
                // Simple commands
                // These will execute if there are words following, e.g. "bye bye"
                // Final desired behaviour TBD
                case BYE -> {
                    // Save task list on exit
                    Io.saveTaskList(TASKS);
                    yield "Bye Bye!";
                }
                case LIST -> {
                    if (TASKS.isEmpty()) {
                        yield "You've got nothing to do except bother me, apparently";
                    } else {
                        yield TASKS.toString();
                    }
                }

                // Compound commands
                case FIND -> {
                    String foundTasks = TASKS.find(command.getArgs());
                    if (foundTasks.isBlank()) {
                        yield "Nada. And I'm not checking again.";
                    } else {
                        yield "You'd better not lose track of them again, okay?\n"
                                + foundTasks;
                    }
                }

                case TODO, DEADLINE, EVENT -> addTask(command);

                case MARK, UNMARK -> handleMarkUnmark(command);

                case DELETE -> {
                    Task removedTask = TASKS.remove(command.getArgs());
                    assert removedTask != null : "Task to delete should not be null.";
                    yield "Get out of here!\n  " + removedTask;
                }

                case UNKNOWN -> "What are you going on about..?";
            };
        } catch (BongoException e) {
            return e.getMessage();
        }
    }

    private String addTask(Command command) throws BongoException {
        String args = command.getArgs();
        try {
            Task task = switch (command.getType()) {
                case TODO -> new Task(args);
                case DEADLINE -> {
                    String[] taskParts = args.split("\\s+/by\\s+", 2);
                    yield new Deadline(taskParts[0], taskParts[1]);
                }
                case EVENT -> {
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
        String args = command.getArgs();
        Task task = TASKS.get(args);
        assert task != null : "Task should exist in the list before marking or unmarking.";

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
