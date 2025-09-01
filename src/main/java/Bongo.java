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

    private final TaskList TASKS;
    private final Ui UI;

    public Bongo() {
        UI = new Ui();
        TASKS = Io.loadTaskList();
    }

    public void run() {
        bongoLoop: while (true) {
            String input = UI.queryUser();
            String[] inputParts = input.split("\\s+", 2);  // split first word
            Command command = Command.from(inputParts[0]);
            try {
                switch (command) {
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
                    case TODO, DEADLINE, EVENT -> addTask(command, inputParts[1]);

                    case MARK, UNMARK -> handleMarkUnmark(command, inputParts[1]);

                    case DELETE -> UI.print("Get out of here!\n  " + TASKS.remove(inputParts[1]));

                    case UNKNOWN -> UI.print("What are you going on about..?");
                }
            } catch (BongoException e) {
                UI.print(e.getMessage());
            } catch (ArrayIndexOutOfBoundsException e) {
                UI.print("I can't do anything with just \"" + input + "\"");
            }
        }

        // Save task list on exit
        try {
            Io.saveTaskList(TASKS);
        } catch (BongoException e) {
            UI.print(e.getMessage());
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
        UI.print("Great, another thing to keep track of:\n  "+ task);
    }

    private void handleMarkUnmark(Command command, String input) throws BongoException {
        Task task = TASKS.get(input);
        String msg = switch (command) {
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

    protected static class BongoException extends Exception {
        public BongoException(String msg) {
            super(msg);
        }
    }
}