import java.util.Scanner;
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

    private static final ArrayList<Task> TASKS = new ArrayList<>();

    public static void main(String[] args) {
        // Declarations
        final String HELLO = """
            
             /\\__/\\    ／ ‾ ‾ ‾ ‾
            （　´∀｀） ＜　 Oh, it's you. What is it now?
            （　　　） 　 ＼ ＿ ＿ ＿
             ｜ ｜　|
            （＿_)＿）
            """;
        Scanner scanner = new Scanner(System.in);
        String input;

        // Program start
        System.out.println(HELLO);

        bongoLoop: while (true) {
            // Prompt user input
            System.out.print("> ");
            input = scanner.nextLine().trim();

            String[] inputParts = input.split("\\s+", 2);  // split first word
            Command command = Command.from(inputParts[0]);
            try {
                switch (command) {
                    // Simple commands
                    // These will execute if there are words following, e.g. "bye bye"
                    // Final desired behaviour TBD
                    case BYE -> {
                        bongoPrint("Bye Bye!");
                        break bongoLoop;
                    }
                    case LIST -> {
                        if (TASKS.isEmpty()) {
                            bongoPrint("You've got nothing to do except bother me, apparently");
                        } else {
                            StringBuilder sb = new StringBuilder();
                            int i = 1;
                            for (Task task : TASKS) {
                                sb.append(i++).append(". ").append(task).append('\n');
                            }
                            bongoPrint(sb.toString().trim());
                        }
                    }

                    // Compound commands
                    case TODO, DEADLINE, EVENT -> addTask(command, inputParts[1]);

                    case MARK, UNMARK -> handleMarkUnmark(command, inputParts[1]);

                    case DELETE -> bongoPrint("Get out of here!\n  " + TASKS.remove(getIndex(inputParts[1])));

                    case UNKNOWN -> bongoPrint("What are you going on about..?");
                }
            } catch (BongoException e) {
                bongoPrint(e.getMessage());
            } catch (ArrayIndexOutOfBoundsException e) {
                bongoPrint("I can't do anything with just \"" + input + "\"");
            }
        }
    }

    private static void bongoPrint(String msg) {
        String sep = "════════════════════════════════════════════════════════════";
        StringBuilder sb = new StringBuilder();

        sb.append(sep);
        msg.lines().forEach(line -> sb.append("\n    ").append(line));
        sb.append('\n').append(sep);

        System.out.println(sb);
    }

    private static void addTask(Command command, String input) throws BongoException {
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
        bongoPrint("Great, another thing to keep track of:\n  "+ task);
    }

    private static void handleMarkUnmark(Command command, String input) throws BongoException {
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

        bongoPrint(msg + "\n  " + task);
    }

    private static int getIndex(String input) throws BongoException {
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