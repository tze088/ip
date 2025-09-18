package bongo.task;

import bongo.Bongo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * The DateTime class represents a date and time.
 * It provides functionality to parse a string into a LocalDateTime and format it for display.
 */
public class DateTime implements Serializable {

    private LocalDateTime dateTime;

    private static final String DATE_FORMATS =
            //"[E]" +         // Mon
            //"[EEEE]" +      // Monday
            "[d/M]" +       // 1/1
            "[d/M/yy]" +    // 1/1/25
            "[d/M/yyyy]";   // 1/1/2025

    private static final String TIME_FORMATS =
            //"[hhmma]" +   // 1230am
            //"[HHmm]" +    // 0030
            "[h[:m]a]";     // 12am, 12:30am
            //"[H[:m]]";    // 0, 0:30

    /**
     * Constructs a DateTime object by parsing the provided string.
     *
     * @param input The string to be parsed.
     * @throws Bongo.BongoException If the string does not match one of the required formats.
     */
    public DateTime(String input) throws Bongo.BongoException {
        try {
            dateTime = LocalDateTime.parse(input, getInputFormatter());
        } catch (Exception e) {
            throw new Bongo.BongoException(e.getMessage());
        }
    }

    private DateTimeFormatter getInputFormatter() {
        final LocalDate NOW = LocalDate.now();

        return new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                // Match Date formats and/or Time formats, separated by a space
                .appendPattern(DATE_FORMATS)
                .appendPattern("[ ]")
                .appendPattern(TIME_FORMATS)
                // Set date fields to current date, and time fields to 0.
                .parseDefaulting(ChronoField.YEAR, NOW.getYear())
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, NOW.getMonthValue())
                .parseDefaulting(ChronoField.DAY_OF_MONTH, NOW.getDayOfMonth())
                .parseDefaulting(ChronoField.CLOCK_HOUR_OF_AMPM, 12)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .toFormatter();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm, dd LLL");
        return dateTime.format(formatter);
    }
}
