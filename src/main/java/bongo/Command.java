package bongo;

public class Command {
    public enum CommandType {
        LIST, TODO, DEADLINE, EVENT, MARK, UNMARK, DELETE, BYE, UNKNOWN;

        private static CommandType from(String input) {
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

    private final CommandType type;
    private final String args;

    private Command(CommandType type, String args) {
        this.type = type;
        this.args = args;
    }

    public static Command fromInput(String input) {
        String[] parts = input.split("\\s+", 2);
        CommandType type = CommandType.from(parts[0]);
        String args = parts.length > 1 ? parts[1] : "";
        return new Command(type, args);
    }

    public CommandType getType() {
        return type;
    }

    public String getArgs() throws Bongo.BongoException {
        if (args.isBlank()) {
            throw new Bongo.BongoException("I'm missing a few vital details here...");
        }
        return args;
    }
}