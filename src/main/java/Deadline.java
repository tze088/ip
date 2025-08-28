import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    private LocalDateTime by;

    public Deadline(String description, String by) throws Bongo.BongoException {
        super(description);
        try {
            this.by = LocalDateTime.parse(by, DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
        } catch (Exception e) {
            throw new Bongo.BongoException("Invalid date format. Please use dd/MM/yyyy HHmm.");
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLL yyyy, HH:mm");
        return super.toString() + " (by: " + by.format(formatter) + ")";
    }
}