import java.util.Scanner;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Bongo {
    public static void main(String[] args) {
        // Art and helper functions
        String hello = """
             /\\__/\\    ／ ‾ ‾ ‾ ‾
            （　´∀｀） ＜　 Hello! How can I help?
            （　　　） 　 ＼ ＿ ＿ ＿
             ｜ ｜　|
            （＿_)＿）
            """;
        String sep = "════════════════════════════════════════════════════════════";

        java.util.function.Consumer<String> boxPrint = msg -> {
            // Add indentation to each line
            String indented = msg.lines()
                    .map(line -> "    " + line)
                    .collect(Collectors.joining("\n"));

            System.out.println(sep);
            System.out.println(indented);
            System.out.println(sep);
        };

        Scanner scanner = new Scanner(System.in);
        String input;
        ArrayList<String> tasks = new ArrayList<>();

        // Program start
        System.out.println(hello);

        while (true) {
            System.out.print("> "); // prompt
            input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                boxPrint.accept("Bye Bye!");
                break;

            } else if (input.equalsIgnoreCase("list")) {
                if (tasks.isEmpty()) {
                    boxPrint.accept("There's nothing to do!");
                } else {
                    String listOutput = tasks.stream()
                            .map(task -> tasks.indexOf(task) + 1 + ". " + task)
                            .collect(Collectors.joining("\n"));
                    boxPrint.accept(listOutput);
                }

            } else {
                tasks.add(input);
                boxPrint.accept("added task: " + input);
            }
        }
    }
}
