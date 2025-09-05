package bongo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandTest {

    @Test
    void testFromInput_ValidCommandType() {
        // Test for known command types
        Command command = Command.fromInput("list");
        assertEquals(Command.CommandType.LIST, command.getType(), "Command type should be LIST");

        command = Command.fromInput("todo Buy groceries");
        assertEquals(Command.CommandType.TODO, command.getType(), "Command type should be TODO");

        command = Command.fromInput("deadline Submit report by tomorrow");
        assertEquals(Command.CommandType.DEADLINE, command.getType(), "Command type should be DEADLINE");

        command = Command.fromInput("event Meeting with team");
        assertEquals(Command.CommandType.EVENT, command.getType(), "Command type should be EVENT");

        command = Command.fromInput("mark 1");
        assertEquals(Command.CommandType.MARK, command.getType(), "Command type should be MARK");

        command = Command.fromInput("unmark 2");
        assertEquals(Command.CommandType.UNMARK, command.getType(), "Command type should be UNMARK");

        command = Command.fromInput("delete 3");
        assertEquals(Command.CommandType.DELETE, command.getType(), "Command type should be DELETE");

        command = Command.fromInput("bye");
        assertEquals(Command.CommandType.BYE, command.getType(), "Command type should be BYE");
    }

    @Test
    void testFromInput_UnknownCommandType() {
        // Test for an unknown command
        Command command = Command.fromInput("hello");
        assertEquals(Command.CommandType.UNKNOWN, command.getType(), "Command type should be UNKNOWN for an unknown command");
    }

    @Test
    void testGetArgs_ValidArguments() throws Bongo.BongoException {
        // Test for commands that have valid arguments
        Command command = Command.fromInput("todo Buy groceries");
        assertEquals("Buy groceries", command.getArgs(), "Command should return correct arguments");

        command = Command.fromInput("deadline Submit report by tomorrow");
        assertEquals("Submit report by tomorrow", command.getArgs(), "Command should return correct arguments");

        command = Command.fromInput("event Meeting with team");
        assertEquals("Meeting with team", command.getArgs(), "Command should return correct arguments");
    }

    @Test
    void testGetArgs_MissingArguments() {
        // Test for commands missing arguments (empty command string)
        Command command = Command.fromInput("todo");
        assertThrows(Bongo.BongoException.class, command::getArgs, "Should throw BongoException when arguments are missing");

        command = Command.fromInput("deadline");
        assertThrows(Bongo.BongoException.class, command::getArgs, "Should throw BongoException when arguments are missing");

        command = Command.fromInput("event");
        assertThrows(Bongo.BongoException.class, command::getArgs, "Should throw BongoException when arguments are missing");
    }

    @Test
    void testFromInput_EmptyInput() {
        // Test for empty input
        Command command = Command.fromInput("");
        assertEquals(Command.CommandType.UNKNOWN, command.getType(), "Command type should be UNKNOWN for empty input");
    }
}
