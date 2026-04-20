import java.util.ArrayList;
import java.util.List;

public class BookingSystem {
    private List<Member> members;
    private List<Lesson> lessons;
    private List<Booking> bookings;

    public BookingSystem() {
        this.members = new ArrayList<>();
        this.lessons = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    // Methods to be implemented in later phases
    public List<Member> getMembers() { return members; }
    public List<Lesson> getLessons() { return lessons; }
    public List<Booking> getBookings() { return bookings; }
}
