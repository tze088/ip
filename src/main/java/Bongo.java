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
            System.out.print("> "); // prompt
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
                            .map(task -> tasks.indexOf(task) + 1 + "." + task)
                            .collect(Collectors.joining("\n"));
                    bongoPrint(listOutput);
                }
                continue;
            }

            // Compound commands
            String[] inputWords = input.split("\\s+", 2);  // split first word
            if (inputWords.length > 1) {
                String command = inputWords[0].toLowerCase();
                input = inputWords[1];

                if (command.equals("todo") || command.equals("deadline") || command.equals("event")) {
                    addTask(command, input, tasks);
                } else if (command.equals("mark") || command.equals("unmark")) {
                    handleMarkUnmark(command, input, tasks);
                }
            } else {
                // No command is matched
                bongoPrint("What are you going on about..?");
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

    private static void addTask(String command, String input, ArrayList<Task> tasks) {
        Task task;

        if (command.equals("todo")) {
            task = new Task(input);
        } else if (command.equals("deadline")) {
            String[] taskParts = input.split("\\s+/by\\s+", 2);
            // Should check for validity
            task = new Deadline(taskParts[0], taskParts[1]);
        } else {
            String[] taskParts = input.split("\\s+/from\\s+|\\s+/to\\s+", 3);
            // Should check for validity
            task = new Event(taskParts[0], taskParts[1], taskParts[2]);
        }
        tasks.add(task);
        bongoPrint("Another thing to keep track of...\n " + task);
    }

    private static void handleMarkUnmark(String command, String input, ArrayList<Task> tasks) {
        try {
            int taskIndex = Integer.parseInt(input) - 1;
            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                Task task = tasks.get(taskIndex);
                if (command.equals("mark")) {
                    task.mark();
                    bongoPrint("Finally done? I'm not impressed...\n  "
                        + task);
                } else {
                    task.unmark();
                    bongoPrint("Made a mistake, did you?\n  "
                        + task);
                }
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            bongoPrint("\"" + input + "\" Isn't a real task number!");
        }
    }
}
