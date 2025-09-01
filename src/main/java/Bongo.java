import java.util.ArrayList;

public class Bongo {

    private enum Command {
        LIST, TODO, DEADLINE, EVENT, MARK, UNMARK, DELETE, BYE, UNKNOWN;

        private static Command from(String input) {
            return switch (input.toLowerCase()) {
                case "list" -> LIST;
                case "todo" -> TODO;
                case "deadline" -> DEADLINE;
                case "event" -> EVENT;
                case "mark" -> MARK;
                case "unmark" -> UNMARK;
                case "delete" -> DELETE;
                case "bye" -> BYE;
                default -> UNKNOWN;
            };
        }
    }

    private final ArrayList<Task> TASKS = IO.loadTaskList();
    private final Ui ui;

    public Bongo() {
        ui = new Ui();
    }

    public void run() {
        bongoLoop: while (true) {
            String input = ui.queryUser();
            String[] inputParts = input.split("\\s+", 2);  // split first word
            Command command = Command.from(inputParts[0]);
            try {
                switch (command) {
                    // Simple commands
                    // These will execute if there are words following, e.g. "bye bye"
                    // Final desired behaviour TBD
                    case BYE -> {
                        ui.print("Bye Bye!");
                        break bongoLoop;
                    }
                    case LIST -> {
                        if (TASKS.isEmpty()) {
                            ui.print("You've got nothing to do except bother me, apparently");
                        } else {
                            StringBuilder sb = new StringBuilder();
                            int i = 1;
                            for (Task task : TASKS) {
                                sb.append(i++).append(". ").append(task).append('\n');
                            }
                            ui.print(sb.toString().trim());
                        }
                    }

                    // Compound commands
                    case TODO, DEADLINE, EVENT -> addTask(command, inputParts[1]);

                    case MARK, UNMARK -> handleMarkUnmark(command, inputParts[1]);

                    case DELETE -> ui.print("Get out of here!\n  " + TASKS.remove(getIndex(inputParts[1])));

                    case UNKNOWN -> ui.print("What are you going on about..?");
                }
            } catch (BongoException e) {
                ui.print(e.getMessage());
            } catch (ArrayIndexOutOfBoundsException e) {
                ui.print("I can't do anything with just \"" + input + "\"");
            }
        }

        // Save task list on exit
        try {
            IO.saveTaskList(TASKS);
        } catch (BongoException e) {
            ui.print(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Bongo().run();
    }

    private void addTask(Command command, String input) throws BongoException {
        Task task = switch (command) {
            case TODO -> new Task(input);
            case DEADLINE -> {
                String[] taskParts = input.split("\\s+/by\\s+", 2);
                yield new Deadline(taskParts[0], taskParts[1]);
            }
            case EVENT -> {
                String[] taskParts = input.split("\\s+/from\\s+|\\s+/to\\s+", 3);
                yield new Event(taskParts[0], taskParts[1], taskParts[2]);
            }
            default -> throw new BongoException("Unknown task type: " + command);
        };
        TASKS.add(task);
        ui.print("Great, another thing to keep track of:\n  "+ task);
    }

    private void handleMarkUnmark(Command command, String input) throws BongoException {
        Task task = TASKS.get(getIndex(input));
        String msg = switch (command) {
            case MARK -> task.mark()
                    ? "Finally done? I'm not impressed..."
                    : "You already did that one...";
            case UNMARK -> task.unmark()
                    ? "Made a mistake, did you?"
                    : "It wasn't even marked in the first place...";
            default -> throw new BongoException("Wrong input: " + command);
        };

        ui.print(msg + "\n  " + task);
    }

    private int getIndex(String input) throws BongoException {
        try {
            int taskIndex = Integer.parseInt(input) - 1;
            if (taskIndex < 0 || taskIndex >= TASKS.size()) {
                throw new BongoException("Can't find that one...");
            }
            return taskIndex;
        } catch (NumberFormatException e) {
            throw new BongoException("\"" + input + "\" Isn't even a number!");
        }
    }

    protected static class BongoException extends Exception {
        public BongoException(String msg) {
            super(msg);
        }
    }
}