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

    private final Ui UI;
    private final TaskList TASKS;

    public Bongo() {
        UI = new Ui();
        TASKS = Io.loadTaskList();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        return "Duke heard: " + input;
    }

    private void run() {
        bongoLoop: while (true) {
            String input = UI.queryUser();
            Command command = Command.fromInput(input);
            try {
                switch (command.getType()) {
                    // Simple commands
                    // These will execute if there are words following, e.g. "bye bye"
                    // Final desired behaviour TBD
                    case BYE -> {
                        UI.print("Bye Bye!");
                        break bongoLoop;
                    }
                    case LIST -> {
                        if (TASKS.isEmpty()) {
                            UI.print("You've got nothing to do except bother me, apparently");
                        } else {
                            UI.print(TASKS.toString());
                        }
                    }

                    // Compound commands
                    case FIND -> {
                        String foundTasks = TASKS.find(command.getArgs());
                        if (foundTasks.isBlank()) {
                            UI.print("Nada. And I'm not checking again.");
                        } else {
                            UI.print("You'd better not lose track of them again, okay?\n"
                                    + foundTasks
                            );
                        }
                    }

                    case TODO, DEADLINE, EVENT -> addTask(command);

                    case MARK, UNMARK -> handleMarkUnmark(command);

                    case DELETE -> UI.print("Get out of here!\n  " + TASKS.remove(command.getArgs()));

                    case UNKNOWN -> UI.print("What are you going on about..?");
                }
            } catch (BongoException e) {
                UI.print(e.getMessage());
            }
        }

        // Save task list on exit
        try {
            Io.saveTaskList(TASKS);
        } catch (BongoException e) {
            UI.print(e.getMessage());
        }
    }

    private void addTask(Command command) throws BongoException {
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
            UI.print("Great, another thing to keep track of:\n  " + task);

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new BongoException("Nice try, but I need the time too. This isn’t a guessing game.");
        }
    }

    private void handleMarkUnmark(Command command) throws BongoException {
        String args = command.getArgs();
        Task task = TASKS.get(args);
        String msg = switch (command.getType()) {
            case MARK -> task.mark()
                    ? "Finally done? I'm not impressed..."
                    : "You already did that one...";
            case UNMARK -> task.unmark()
                    ? "Made a mistake, did you?"
                    : "It wasn't even marked in the first place...";
            default -> throw new BongoException("Wrong input: " + command);
        };
        UI.print(msg + "\n  " + task);
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
