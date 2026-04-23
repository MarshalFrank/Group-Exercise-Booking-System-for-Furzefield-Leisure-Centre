import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class BookingSystem {
    private List<Member> members;
    private List<Lesson> lessons;
    private List<Booking> bookings;

    private String getMonthName(int month) {
        return switch (month) {
            case 1 -> "January";
            case 2 -> "February";
            case 3 -> "March";
            case 4 -> "April";
            case 5 -> "May";
            case 6 -> "June";
            case 7 -> "July";
            case 8 -> "August";
            case 9 -> "September";
            case 10 -> "October";
            case 11 -> "November";
            case 12 -> "December";
            default -> "Invalid Month";
        };
    }

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
        System.out.println("\n===== Timetable — " + day + " =====");
        System.out.printf("%-5s %-12s %-10s %-10s %-18s %-8s %-6s%n",
                "ID", "Type", "Day", "Month", "Time", "Price", "Spots");
        System.out.println("-".repeat(75));

        lessons.stream()
                .filter(l -> l.getDay() == day)
                .sorted(Comparator.comparing(Lesson::getMonth)
                        .thenComparing(Lesson::getTime))
                .forEach(l -> System.out.printf("%-5d %-12s %-10s %-10s %-18s £%-7.2f %-6d%n",
                        l.getId(),
                        l.getType(),
                        l.getDay(),
                        l.getMonthName(),   // ✅ NEW
                        l.getTime(),
                        l.getPrice(),
                        l.getAvailableSpots()));
    }

    public void viewTimetableByType(ExerciseType type) {
        System.out.println("\n===== Timetable — " + type + " =====");
        System.out.printf("%-5s %-10s %-10s %-12s %-18s %-8s %-6s%n",
                "ID", "Day", "Month", "Time", "Instructor", "Price", "Spots");
        System.out.println("-".repeat(80));

        lessons.stream()
                .filter(l -> l.getType() == type)
                .sorted(Comparator.comparing(Lesson::getMonth)
                        .thenComparing(Lesson::getDay)
                        .thenComparing(Lesson::getTime))
                .forEach(l -> System.out.printf("%-5d %-10s %-10s %-12s %-18s £%-7.2f %-6d%n",
                        l.getId(),
                        l.getDay(),
                        l.getMonthName(),   // ✅ NEW
                        l.getTime(),
                        l.getInstructor(),
                        l.getPrice(),
                        l.getAvailableSpots()));
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
        String validationError = validateMemberLesson(memberId, lessonId);
        if (validationError != null) return validationError;
        
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

    public Booking findBookingById(int id) {
        return bookings.stream()
            .filter(b -> b.getId() == id)
            .findFirst()
            .orElse(null);
    }

    public void viewMemberBookings(int memberId) {
        Member member = findMemberById(memberId);
        if (member == null) { 
            System.out.println("Member not found."); 
            return; 
        }

        System.out.println("\n===== Bookings for " + member.getName() + " =====");

        bookings.stream()
            .filter(b -> b.getMember().getId() == memberId)
            .forEach(b -> {
                System.out.printf("  [%d] %-12s %-10s %-10s Status: %-10s",
                    b.getId(), b.getLesson().getType(),
                    b.getLesson().getDay(), b.getLesson().getTime(),
                    b.getStatus());

                if (b.getReview() != null)
                    System.out.printf(" | Rating: %d | \"%s\"",
                        b.getReview().getRating(),
                        b.getReview().getComment());

                System.out.println();
            });
    }

    public String cancelBooking(int memberId, int bookingId) {
        Booking booking = findBookingById(bookingId);
        if (booking == null) return "ERROR: Booking not found.";
        if (booking.getMember().getId() != memberId)
            return "ERROR: This booking does not belong to this member.";
        if (booking.getStatus() == BookingStatus.CANCELLED)
            return "ERROR: Booking is already cancelled.";
        if (booking.getStatus() == BookingStatus.ATTENDED)
            return "ERROR: Cannot cancel a lesson already attended.";

        booking.setStatus(BookingStatus.CANCELLED);
        return "SUCCESS: Booking " + bookingId + " has been cancelled.";
    }

    public String changeBooking(int memberId, int oldBookingId, int newLessonId) {
        Booking oldBooking = findBookingById(oldBookingId);
        if (oldBooking == null) return "ERROR: Original booking not found.";
        if (oldBooking.getMember().getId() != memberId)
            return "ERROR: Booking does not belong to this member.";
        if (oldBooking.getStatus() != BookingStatus.BOOKED
                && oldBooking.getStatus() != BookingStatus.CHANGED)
            return "ERROR: Only active bookings can be changed.";

        Member member = findMemberById(memberId);
        Lesson newLesson = findLessonById(newLessonId);

        if (newLesson == null) return "ERROR: New lesson not found.";
        if (newLesson.isFull()) return "ERROR: New lesson is fully booked.";
        if (alreadyBooked(member, newLesson))
            return "ERROR: Already booked on this lesson.";

        oldBooking.setStatus(BookingStatus.CANCELLED);

        if (hasTimeConflict(member, newLesson)) {
            oldBooking.setStatus(BookingStatus.BOOKED);
            return "ERROR: New lesson conflicts with another booking.";
        }

        Booking newBooking = new Booking(member, newLesson);
        newBooking.setStatus(BookingStatus.CHANGED);

        newLesson.getBookings().add(newBooking);
        bookings.add(newBooking);

        return "SUCCESS: Booking changed. New Booking ID: " + newBooking.getId();
    }

    public String attendLesson(int memberId, int bookingId) {
        Booking booking = findBookingById(bookingId);
        if (booking == null) return "ERROR: Booking not found.";
        if (booking.getMember().getId() != memberId)
            return "ERROR: Booking does not belong to this member.";
        if (booking.getStatus() == BookingStatus.CANCELLED)
            return "ERROR: Cannot attend a cancelled booking.";
        if (booking.getStatus() == BookingStatus.ATTENDED)
            return "ERROR: Already marked as attended.";

        booking.setStatus(BookingStatus.ATTENDED);
        return "SUCCESS: Attendance recorded for booking " + bookingId + ".";
    }

    public String leaveReview(int memberId, int bookingId, String comment, int rating) {
        Booking booking = findBookingById(bookingId);
        if (booking == null) return "ERROR: Booking not found.";
        if (booking.getMember().getId() != memberId)
            return "ERROR: Booking does not belong to this member.";
        if (booking.getStatus() != BookingStatus.ATTENDED)
            return "ERROR: You can only review lessons you have attended.";
        if (booking.getReview() != null)
            return "ERROR: You have already reviewed this booking.";
        if (rating < Review.MIN_RATING || rating > Review.MAX_RATING)
            return "ERROR: Rating must be between " + Review.MIN_RATING + " and " + Review.MAX_RATING + ".";

        booking.setReview(new Review(comment, rating));
        return "SUCCESS: Review submitted. Thank you!";
    }

    public void generateMonthlyLessonReport(int month) {
        new ReportGenerator(lessons, bookings).generateMonthlyLessonReport(month);
    }

    public void generateMonthlyChampionReport(int month) {
        new ReportGenerator(lessons, bookings).generateMonthlyChampionReport(month);
    }

    private String validateMemberLesson(int memberId, int lessonId) {
        if (findMemberById(memberId) == null) return "ERROR: Member not found.";
        if (findLessonById(lessonId) == null) return "ERROR: Lesson not found.";
        return null;
    }
    
    // Methods to be implemented in later phases
    public List<Member> getMembers() { return members; }
    public List<Lesson> getLessons() { return lessons; }
    public List<Booking> getBookings() { return bookings; }
}
