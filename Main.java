import java.time.LocalDate;
import java.util.Iterator;
import java.util.NoSuchElementException;

// Διεπαφή Event με δύο μεθόδους
interface Event {
    String getDescription();
    Iterator<LocalDate> getEventDate();
}

// Κλάση RecurringEvent που υλοποιεί τη διεπαφή Event
class RecurringEvent implements Event {
    private String description;
    private LocalDate start;
    private LocalDate end;
    private int interval;

    // Πρώτος κατασκευαστής: Χωρίς ημερομηνία λήξης
    public RecurringEvent(String description, LocalDate start, int interval) {
        this.description = description;
        this.start = start;
        this.interval = interval;
        this.end = null; // Ατέρμονη επανάληψη
    }

    // Δεύτερος κατασκευαστής: Με ημερομηνία λήξης
    public RecurringEvent(String description, LocalDate start, LocalDate end, int interval) {
        this.description = description;
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    // Υλοποίηση της getDescription()
    @Override
    public String getDescription() {
        return description;
    }

    // Υλοποίηση της getEventDate()
    @Override
    public Iterator<LocalDate> getEventDate() {
        return new Iterator<LocalDate>() {
            private LocalDate nextDate = start;

            @Override
            public boolean hasNext() {
                return end == null || nextDate.isBefore(end) || nextDate.isEqual(end);
            }

            @Override
            public LocalDate next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more dates available");
                }
                LocalDate currentDate = nextDate;
                nextDate = nextDate.plusDays(interval);
                return currentDate;
            }
        };
    }
}

// Κύρια κλάση για τη δοκιμή της RecurringEvent
public class Main {
    public static void main(String[] args) {
        // Δημιουργία ενός RecurringEvent χωρίς ημερομηνία λήξης
        RecurringEvent event = new RecurringEvent("Go to gym", LocalDate.of(2020, 1, 1), 7);
        
        // Λήψη των ημερομηνιών του γεγονότος μέσω iterator
        Iterator<LocalDate> dates = event.getEventDate();
        
        // Εκτύπωση των ημερομηνιών (ο βρόχος θα είναι ατέρμονος αν δεν οριστεί end date)
        while (dates.hasNext()) {
            System.out.println(dates.next());
        }
    }
}

