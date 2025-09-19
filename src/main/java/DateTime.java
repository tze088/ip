import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTime {

    private LocalDate date;

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