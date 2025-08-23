import java.util.Scanner;

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
        String sep = "════════════════════════════════════════════════════════════════";

        java.util.function.Consumer<String> boxPrint = msg -> {
            System.out.println(sep);
            System.out.println("    " + msg);
            System.out.println(sep);
        };

        Scanner scanner = new Scanner(System.in);
        String input;

        // Program start
        System.out.println(hello);

        while (true) {
            System.out.print("> "); // prompt
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("Bye Bye!");
                break;
            }
            boxPrint.accept(input); // echo
        }
    }
}
