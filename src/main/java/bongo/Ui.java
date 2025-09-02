package bongo;

import java.util.Scanner;

/**
 * The Ui class handles user interactions in the Bongo chatbot.
 * It displays messages and prompts the user for input.
 */
public class Ui {

    private final Scanner scanner = new Scanner(System.in);

    public Ui() {
        final String HELLO = """
            
             /\\__/\\     ／ ‾ ‾ ‾ ‾
            （　´∀｀） ＜　 Oh, it's you. What is it now?
            （　　　） 　 ＼ ＿ ＿ ＿
             ｜ ｜　|
            （＿_)＿）
            """;
        System.out.println(HELLO);
    }

    /**
     * Formats and prints a message to the user.
     *
     * @param msg The message to be printed.
     */
    public void print(String msg) {
        final String SEP = "════════════════════════════════════════════════════════════\n";
        StringBuilder sb = new StringBuilder();

        sb.append(SEP);
        msg.lines().forEach(line -> sb.append("    ").append(line).append('\n'));
        sb.append(SEP);

        System.out.print(sb);
    }

    /**
     * Prompts the user for input and returns their response.
     *
     * @return The user's input, trimmed of leading/trailing whitespace.
     */
    public String queryUser() {
        System.out.print("> ");
        return scanner.nextLine().trim();
    }
}
