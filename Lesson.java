import java.util.ArrayList;
import java.util.List;

public class Lesson {
    private static int counter = 1;
    private int id;
    private ExerciseType type;
    private BookingDay day;
    private SessionTime time;
    private String instructor;
    private double price;
    private int capacity;
    private final int month;
    private List<Booking> bookings;
    public static final int MAX_CAPACITY = 4;
    public static final int DEFAULT_CAPACITY = MAX_CAPACITY;

    public Lesson(ExerciseType type, BookingDay day, SessionTime time,
                  String instructor, double price, int
                   month) {
        this.id = counter++;
        this.type = type;
        this.day = day;
        this.time = time;
        this.instructor = instructor;
        this.month = month;
        this.price = price;
        this.capacity = DEFAULT_CAPACITY;
        this.bookings = new ArrayList<>();
    }

    public int getId() { return id; }
    public ExerciseType getType() { return type; }
    public BookingDay getDay() { return day; }
    public SessionTime getTime() { return time; }
    public String getInstructor() { return instructor; }
    public double getPrice() { return price; }
    public int getCapacity() { return capacity; }
    public int getMonth() { return month; }
    public String getMonthName() {
        String name = java.time.Month.of(month).name();
        return name.substring(0,1) + name.substring(1).toLowerCase();
    }
    public List<Booking> getBookings() { return bookings; }

    public boolean isFull() {
        long active = bookings.stream()
            .filter(b -> b.getStatus() == BookingStatus.BOOKED
                      || b.getStatus() == BookingStatus.CHANGED)
            .count();
        return active >= capacity;
    }

    public int getAvailableSpots() {
        long active = bookings.stream()
            .filter(b -> b.getStatus() == BookingStatus.BOOKED
                      || b.getStatus() == BookingStatus.CHANGED)
            .count();
        return capacity - (int) active;
    }

    @Override
    public String toString() {
        return String.format("Lesson{id=%d, type=%s, day=%s, time=%s, instructor='%s', price=%.2f, spots=%d, month=%d}",
                id, type, day, time, instructor, price, getAvailableSpots(), month);
    }
}
