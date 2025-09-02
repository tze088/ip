package bongo;

/**
 * Command represents a user input command, parsing and categorizing it into a specific type and arguments.
 * It is used to interpret and handle different user commands such as TODO, DEADLINE, EVENT, etc.
 */
public class Command {

    /**
     * Enum representing the different command types.
     */
    public enum CommandType {
        LIST, FIND, TODO, DEADLINE, EVENT, MARK, UNMARK, DELETE, BYE, UNKNOWN;

        private static CommandType from(String input) {
            return switch (input.toLowerCase()) {
                case "list" -> LIST;
                case "find" -> FIND;
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

    private final CommandType type;
    private final String args;

    private Command(CommandType type, String args) {
        this.type = type;
        this.args = args;
    }

    /**
     * Parses the user input into a Command object, identifying the command type and its arguments.
     *
     * @param input The raw user input.
     * @return A Command object with the appropriate CommandType and arguments.
     */
    public static Command fromInput(String input) {
        String[] parts = input.split("\\s+", 2);
        CommandType type = CommandType.from(parts[0]);
        String args = parts.length > 1 ? parts[1] : "";
        return new Command(type, args);
    }

    public CommandType getType() {
        return type;
    }

    /**
     * Returns the arguments associated with the command.
     *
     * @return The arguments string.
     * @throws Bongo.BongoException if the command is missing necessary arguments.
     */
    public String getArgs() throws Bongo.BongoException {
        if (args.isBlank()) {
            throw new Bongo.BongoException("I'm missing a few vital details here...");
        }
        return args;
    }
}