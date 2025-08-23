import java.util.Scanner;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Bongo {
    public static void main(String[] args) {
        // Art and helper functions
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

            // Simple cases
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
            String[] inputWords = input.split("\\s+");  // split input by whitespace
            if (inputWords.length > 1) {
                String command = inputWords[0].toLowerCase();
                if (command.equals("mark") || command.equals("unmark")) {
                    handleMarkUnmark(inputWords, tasks, command);
                    continue;
                }
            }

            // Add task if no command is matched
            tasks.add(new Task(input));
            bongoPrint("Another thing to keep track of...\n task added: " + input);
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

    private static void handleMarkUnmark(String[] inputWords, ArrayList<Task> tasks, String command) {
        try {
            int taskIndex = Integer.parseInt(inputWords[1]) - 1;
            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                Task task = tasks.get(taskIndex);
                if (command.equals("mark")) {
                    task.mark();
                    bongoPrint("Finally done? I'm not impressed...\n  "
                        + task);
                } else {
                    // Unmark
                    task.unmark();
                    bongoPrint("Made a mistake, did you?\n  "
                            + task);
                }
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            bongoPrint('"' + inputWords[1] + "\" Isn't a real task number!");
        }
    }
}
