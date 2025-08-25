import java.util.Scanner;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Bongo {

    public static void main(String[] args) {
        String HELLO = """
             
             /\\__/\\    ／ ‾ ‾ ‾ ‾
            （　´∀｀） ＜　 Oh, it's you. What is it now?
            （　　　） 　 ＼ ＿ ＿ ＿
             ｜ ｜　|
            （＿_)＿）
            """;

        Scanner scanner = new Scanner(System.in);
        String input;
        ArrayList<Task> tasks = new ArrayList<>();

        // Program start
        System.out.println(HELLO);

        while (true) {
            // Prompt user input
            System.out.print("> ");
            input = scanner.nextLine().trim();

            // Simple commands
            if (input.equalsIgnoreCase("bye")) {
                bongoPrint("Bye Bye!");
                break;
            } else if (input.equalsIgnoreCase("list")) {
                if (tasks.isEmpty()) {
                    bongoPrint("You've got nothing to do except bother me, apparently");
                } else {
                    String listOutput = tasks.stream()
                            .map(task -> (tasks.indexOf(task) + 1) + "." + task)
                            .collect(Collectors.joining("\n"));
                    bongoPrint(listOutput);
                }
                continue;
            }

            // Compound commands
            String[] inputParts = input.split("\\s+", 2);  // split first word
            String command = inputParts[0].toLowerCase();
            try {
                switch (command) {
                    case "todo":
                    case "deadline":
                    case "event":
                        addTask(command, inputParts[1], tasks);
                        continue;

                    case "mark":
                    case "unmark":
                        handleMarkUnmark(command, inputParts[1], tasks);
                        continue;

                    case "delete":
                        int taskIndex = getIndex(inputParts[1], tasks);
                        if (taskIndex < 0) continue;
                        bongoPrint("Get out of here!\n  "
                                + tasks.remove(taskIndex));
                        continue;
                    default:
                        // No command is matched
                        bongoPrint("What are you going on about..?");
                }
            } catch (BongoException e) {
                bongoPrint(e.getMessage());
            } catch (ArrayIndexOutOfBoundsException e) {
                bongoPrint("What am I supposed to do with just \""
                    + command + "\"?");
            }
        }
    }

    private static void bongoPrint(String msg) {
        String sep = "════════════════════════════════════════════════════════════";

        // Add indentation to each line
        String indented = msg.lines()
                .map(line -> "    " + line)
                .collect(Collectors.joining("\n"));

        System.out.println(sep);
        System.out.println(indented);
        System.out.println(sep);
    }

    private static void addTask(String command, String input, ArrayList<Task> tasks)
        throws BongoException {
        Task task = switch (command) {
            case "todo" -> new Task(input);
            case "deadline" -> {
                String[] taskParts = input.split("\\s+/by\\s+", 2);
                yield new Deadline(taskParts[0], taskParts[1]);
            }
            case "event" -> {
                String[] taskParts = input.split("\\s+/from\\s+|\\s+/to\\s+", 3);
                yield new Event(taskParts[0], taskParts[1], taskParts[2]);
            }
            default -> throw new BongoException("Unknown task type: " + command);
        };
        tasks.add(task);
        bongoPrint("Great, another thing to keep track of:\n  "+ task);
    }

    private static void handleMarkUnmark(String command, String input, ArrayList<Task> tasks)
        throws BongoException {
        int taskIndex = getIndex(input, tasks);
        Task task = tasks.get(taskIndex);
        String msg;

        if (command.equals("mark")) {
            msg = task.mark()
                    ? "Finally done? I'm not impressed...\n  "
                    : "You already did that one...\n  ";
        } else {
            msg = task.unmark()
                    ? "Made a mistake, did you?\n  "
                    : "It wasn't even marked in the first place...\n  ";
        }
        bongoPrint(msg + task);
    }

    private static int getIndex(String input, ArrayList<Task> tasks)
        throws BongoException {
        try {
            int taskIndex = Integer.parseInt(input) - 1;
            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                throw new BongoException("Can't find that one...");
            }
            return taskIndex;
        } catch (NumberFormatException e) {
            throw new BongoException("\"" + input + "\" Isn't even a number!");
        }
    }

    public static class BongoException extends Exception {
        public BongoException(String msg) {
            super(msg);
        }
    }
}
