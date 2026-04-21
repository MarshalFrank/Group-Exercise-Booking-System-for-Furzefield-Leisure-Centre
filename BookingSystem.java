import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class BookingSystem {
    private List<Member> members;
    private List<Lesson> lessons;
    private List<Booking> bookings;

    public BookingSystem() {
        this.members = new ArrayList<>();
        this.lessons = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    public void addMember(Member member) {
    members.add(member);
    }

    public void addLesson(Lesson lesson) {
    lessons.add(lesson);
    }

    public Member findMemberById(int id) {
        return members.stream()
            .filter(m -> m.getId() == id)
            .findFirst()
            .orElse(null);
    }

    public Lesson findLessonById(int id) {
        return lessons.stream()
            .filter(l -> l.getId() == id)
            .findFirst()
            .orElse(null);
    }

    public void viewTimetableByDay(BookingDay day) {
        System.out.println("\n===== Timetable for " + day + " =====");
        lessons.stream()
            .filter(l -> l.getDay() == day)
            .sorted(Comparator.comparing(Lesson::getTime))
            .forEach(l -> System.out.printf(
                "  [%d] %-12s | %-10s | Instructor: %-15s | Price: £%.2f | Spots: %d%n",
                l.getId(), l.getType(), l.getTime(),
                l.getInstructor(), l.getPrice(), l.getAvailableSpots()));
    }

    public void viewTimetableByType(ExerciseType type) {
        System.out.println("\n===== Timetable for " + type + " =====");
        lessons.stream()
            .filter(l -> l.getType() == type)
            .sorted(Comparator.comparing(Lesson::getDay)
                    .thenComparing(Lesson::getTime))
            .forEach(l -> System.out.printf(
                "  [%d] %-10s | %-12s | Instructor: %-15s | Price: £%.2f | Spots: %d%n",
                l.getId(), l.getDay(), l.getTime(),
                l.getInstructor(), l.getPrice(), l.getAvailableSpots()));
    }

    private boolean hasTimeConflict(Member member, Lesson newLesson) {
        return bookings.stream()
            .filter(b -> b.getMember().getId() == member.getId())
            .filter(b -> b.getStatus() == BookingStatus.BOOKED
                      || b.getStatus() == BookingStatus.CHANGED)
            .anyMatch(b -> b.getLesson().getDay() == newLesson.getDay()
                        && b.getLesson().getTime() == newLesson.getTime());
    }

    private boolean alreadyBooked(Member member, Lesson lesson) {
        return bookings.stream()
            .filter(b -> b.getMember().getId() == member.getId())
            .filter(b -> b.getLesson().getId() == lesson.getId())
            .anyMatch(b -> b.getStatus() == BookingStatus.BOOKED
                        || b.getStatus() == BookingStatus.CHANGED);
    }

    public String bookLesson(int memberId, int lessonId) {
        Member member = findMemberById(memberId);
        if (member == null) return "ERROR: Member not found.";

        Lesson lesson = findLessonById(lessonId);
        if (lesson == null) return "ERROR: Lesson not found.";

        if (lesson.isFull()) return "ERROR: Lesson is fully booked.";

        if (alreadyBooked(member, lesson))
            return "ERROR: You have already booked this lesson.";

        if (hasTimeConflict(member, lesson))
            return "ERROR: You have a time conflict with another booking.";

        Booking booking = new Booking(member, lesson);
        lesson.getBookings().add(booking);
        bookings.add(booking);

        return "SUCCESS: Booking confirmed. Booking ID: " + booking.getId();
    }
    
    // Methods to be implemented in later phases
    public List<Member> getMembers() { return members; }
    public List<Lesson> getLessons() { return lessons; }
    public List<Booking> getBookings() { return bookings; }
}
