public class Deadline extends Task {

    private DateTime by;

    public Deadline(String description, String by) throws Bongo.BongoException {
        super(description);
        this.by = new DateTime(by);
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + by + ")";
    }
}