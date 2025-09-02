package bongo.task;

import bongo.Bongo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The DateTime class represents a date in the dd/MM/yyyy format.
 * It provides functionality to parse a string into a LocalDate and format it for display.
 */
public class DateTime implements Serializable {

    private LocalDate date;

    /**
     * Constructs a DateTime object by parsing the provided date string.
     * The date string must be in the format "dd/MM/yyyy".
     *
     * @param date The date string to be parsed.
     * @throws Bongo.BongoException If the date string does not match the required format.
     */
    public DateTime(String date) throws Bongo.BongoException {
        try {
            this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            throw new Bongo.BongoException("I only recognise the dd/MM/yyyy format.");
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLL yyyy");
        return date.format(formatter);
    }
}