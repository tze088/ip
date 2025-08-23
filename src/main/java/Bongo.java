import java.util.Scanner;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Bongo {

    public static void main(String[] args) {
        String hello = """
             
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
        System.out.println(hello);

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
            if (inputParts.length > 1) {
                String command = inputParts[0].toLowerCase();

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
                }
            }

            // No command is matched
            bongoPrint("What are you going on about..?");
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

    private static void addTask(String command, String input, ArrayList<Task> tasks) {
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
            default -> throw new IllegalArgumentException("Unknown task type: " + command);
        };
        tasks.add(task);
        bongoPrint("Another thing to keep track of...\n " + task);
    }

    private static void handleMarkUnmark(String command, String input, ArrayList<Task> tasks) {
        try {
            int taskIndex = Integer.parseInt(input) - 1;
            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                Task task = tasks.get(taskIndex);
                if (command.equals("mark")) {
                    if (task.mark()) {
                        bongoPrint("Finally done? I'm not impressed...\n  "
                                + task);
                    } else {
                        bongoPrint("You already did that one...\n  "
                                + task);
                    }
                } else {
                    if (task.unmark()) {
                        bongoPrint("Made a mistake, did you?\n  "
                                + task);
                    } else {
                        bongoPrint("It wasn't even marked in the first place...\n  "
                                + task);
                    }
                }
            } else {
                throw new IllegalArgumentException("Task number outside of range");
            }
        } catch (IllegalArgumentException e) {
            bongoPrint("\"" + input + "\" Isn't a real task number!");
        }
    }
}
