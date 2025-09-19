package bongo.task;

import bongo.Bongo;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;

/**
 * The DateTime class represents a date and time.
 * It provides functionality to parse a string into a LocalDateTime and format it for display.
 */
public class DateTime implements Serializable {

    private LocalDateTime dateTime;

    private static final String[] DATE_FORMATS = {
            "[E]",          // Mon
            "[EEEE]",       // Monday
            "[LLL]",          // Jan
            "[LLLL]",       // January
            "[d/M]",        // 1/1
            "[d/M/yy]",     // 1/1/25
            "[d/M/yyyy]",   // 1/1/2025
    };

    private static final String[] TIME_FORMATS = {
            "hhmma",      // 1230am
            "HHmm",       // 0030
            "h[:mm]a",    // 12am, 12:30am
            "H[:mm]"      // 0, 0:30
    };


    /**
     * Constructs a DateTime object by parsing the provided string.
     *
     * @param input The string to be parsed.
     * @throws Bongo.BongoException If the string does not match one of the required formats.
     */
    public DateTime(String input) throws Bongo.BongoException {
        dateTime = LocalDateTime.of(parseDate(input), parseTime(input));
    }

    private TemporalAccessor parse(String input, String pattern) {
        return new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern(pattern)
                .toFormatter()
                .parse(input);
    }

    private LocalDate parseDate(String input) {
        LocalDate now = LocalDate.now();

        for (String format : DATE_FORMATS) {
            try {
                TemporalAccessor parsed = parse(input, format);

                // Try to extract full LocalDate if possible
                if (parsed.isSupported(ChronoField.YEAR) &&
                        parsed.isSupported(ChronoField.MONTH_OF_YEAR) &&
                        parsed.isSupported(ChronoField.DAY_OF_MONTH)) {
                    return LocalDate.from(parsed);
                }

                // Day of week → next or same day of week
                if (parsed.isSupported(ChronoField.DAY_OF_WEEK)) {
                    DayOfWeek dayOfWeek = DayOfWeek.of(parsed.get(ChronoField.DAY_OF_WEEK));
                    return now.with(TemporalAdjusters.nextOrSame(dayOfWeek));
                }

                // Handle: "Sep 12" → assume current year
                if (parsed.isSupported(ChronoField.MONTH_OF_YEAR) &&
                        parsed.isSupported(ChronoField.DAY_OF_MONTH)) {
                    int year = now.getYear();
                    return LocalDate.of(year,
                            parsed.get(ChronoField.MONTH_OF_YEAR),
                            parsed.get(ChronoField.DAY_OF_MONTH));
                }

                // Handle: "September" → next or same occurrence of the month, day = 1
                if (parsed.isSupported(ChronoField.MONTH_OF_YEAR)) {
                    int month = parsed.get(ChronoField.MONTH_OF_YEAR);
                    LocalDate thisMonth = LocalDate.of(now.getYear(), month, 1);
                    return (thisMonth.isBefore(now.withDayOfMonth(1))) ?
                            thisMonth.plusYears(1) : thisMonth;
                }

                // Handle: "2023" → Jan 1 of that year
                if (parsed.isSupported(ChronoField.YEAR)) {
                    return LocalDate.of(parsed.get(ChronoField.YEAR), 1, 1);
                }
            } catch (DateTimeParseException ignored) {
                // Move on to the next pattern
            }
        }
        return now;
    }

    private LocalTime parseTime(String input) {
        for (String format : TIME_FORMATS) {
            try {
                TemporalAccessor parsed = parse(input, format);
                return LocalTime.from(parsed);
            } catch (DateTimeParseException ignored) {
                // Move on to the next pattern
            }
        }
        return LocalTime.MIDNIGHT;
    }

    @Override
    public String toString() {
        LocalDate now = LocalDate.now();
        LocalDate date = dateTime.toLocalDate();

        String outputFormat = "HH:mm";

        if (!date.equals(now)) {
            // Print the date if it is not today
            outputFormat += ", dd LLL yyyy";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(outputFormat);
        return dateTime.format(formatter);
    }
}
