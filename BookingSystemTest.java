import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingSystemTest {

    private BookingSystem system;
    private Member alice;
    private Member bob;
    private Member carol;
    private Member dave;
    private Member eve;
    private Lesson yogaSatMorning;
    private Lesson zumbaSatMorning;   // same slot as yoga – conflict test
    private Lesson yogaSatAfternoon;

    @BeforeEach
    public void setUp() {
        system = new BookingSystem();
        alice  = new Member("Alice",  "07700000001");
        bob    = new Member("Bob",    "07700000002");
        carol  = new Member("Carol",  "07700000003");
        dave   = new Member("Dave",   "07700000004");
        eve    = new Member("Eve",    "07700000005");
        system.addMember(alice);
        system.addMember(bob);
        system.addMember(carol);
        system.addMember(dave);
        system.addMember(eve);

        yogaSatMorning    = new Lesson(ExerciseType.YOGA,   BookingDay.SATURDAY, SessionTime.MORNING,   "Inst A", 12.00);
        zumbaSatMorning   = new Lesson(ExerciseType.ZUMBA,  BookingDay.SATURDAY, SessionTime.MORNING,   "Inst B", 10.00);
        yogaSatAfternoon  = new Lesson(ExerciseType.YOGA,   BookingDay.SATURDAY, SessionTime.AFTERNOON, "Inst A", 12.00);
        system.addLesson(yogaSatMorning);
        system.addLesson(zumbaSatMorning);
        system.addLesson(yogaSatAfternoon);
    }

    // Test 1: Successful booking
    @Test
    @Order(1)
    public void testBookLessonSuccess() {
        String result = system.bookLesson(alice.getId(), yogaSatMorning.getId());
        assertTrue(result.startsWith("SUCCESS"),
                "Expected successful booking but got: " + result);
        assertEquals(1, yogaSatMorning.getBookings().size());
        assertEquals(BookingStatus.BOOKED,
                yogaSatMorning.getBookings().get(0).getStatus());
    }

    // Test 2: Lesson at full capacity
    @Test
    @Order(2)
    public void testBookLessonFullCapacity() {
        system.bookLesson(alice.getId(), yogaSatMorning.getId());
        system.bookLesson(bob.getId(),   yogaSatMorning.getId());
        system.bookLesson(carol.getId(), yogaSatMorning.getId());
        system.bookLesson(dave.getId(),  yogaSatMorning.getId());
        // Lesson is now full
        String result = system.bookLesson(eve.getId(), yogaSatMorning.getId());
        assertTrue(result.startsWith("ERROR"),
                "Should reject booking when lesson is full");
        assertTrue(result.contains("fully booked"),
                "Error message should mention fully booked");
    }

    // Test 3: Duplicate booking rejected
    @Test
    @Order(3)
    public void testBookLessonDuplicate() {
        system.bookLesson(alice.getId(), yogaSatMorning.getId());
        String result = system.bookLesson(alice.getId(), yogaSatMorning.getId());
        assertTrue(result.startsWith("ERROR"),
                "Should reject duplicate booking");
        assertTrue(result.contains("already booked"),
                "Error should indicate duplicate");
    }

    // Test 4: Time conflict rejected
    @Test
    @Order(4)
    public void testBookLessonTimeConflict() {
        system.bookLesson(alice.getId(), yogaSatMorning.getId());
        // zumbaSatMorning is also Saturday Morning – conflict
        String result = system.bookLesson(alice.getId(), zumbaSatMorning.getId());
        assertTrue(result.startsWith("ERROR"),
                "Should reject time-conflicting booking");
        assertTrue(result.contains("conflict"),
                "Error should mention conflict");
    }

    // Test 5: Cancel booking
    @Test
    @Order(5)
    public void testCancelBooking() {
        system.bookLesson(alice.getId(), yogaSatMorning.getId());
        Booking booking = system.findBookingById(
                system.getBookings().get(0).getId());
        assertNotNull(booking);
        String result = system.cancelBooking(alice.getId(), booking.getId());
        assertTrue(result.startsWith("SUCCESS"),
                "Expected successful cancellation");
        assertEquals(BookingStatus.CANCELLED, booking.getStatus(),
                "Booking status should be CANCELLED");
    }

    // Test 6: Change booking
    @Test
    @Order(6)
    public void testChangeBooking() {
        system.bookLesson(alice.getId(), yogaSatMorning.getId());
        Booking original = system.getBookings().get(0);
        int origId = original.getId();

        String result = system.changeBooking(
                alice.getId(), origId, yogaSatAfternoon.getId());
        assertTrue(result.startsWith("SUCCESS"),
                "Expected successful change but got: " + result);

        assertEquals(BookingStatus.CANCELLED, original.getStatus(),
                "Original booking should be CANCELLED after change");

        Booking newBooking = system.getBookings().stream()
                .filter(b -> b.getStatus() == BookingStatus.CHANGED)
                .findFirst()
                .orElse(null);
        assertNotNull(newBooking, "New booking with CHANGED status should exist");
        assertEquals(yogaSatAfternoon.getId(), newBooking.getLesson().getId());
    }

    // Test 7: Attend and leave review
    @Test
    @Order(7)
    public void testAttendAndReview() {
        system.bookLesson(alice.getId(), yogaSatMorning.getId());
        Booking booking = system.getBookings().get(0);

        String attendResult = system.attendLesson(alice.getId(), booking.getId());
        assertTrue(attendResult.startsWith("SUCCESS"));
        assertEquals(BookingStatus.ATTENDED, booking.getStatus());

        String reviewResult = system.leaveReview(
                alice.getId(), booking.getId(), "Wonderful class!", 5);
        assertTrue(reviewResult.startsWith("SUCCESS"),
                "Review should be accepted for attended booking");
        assertNotNull(booking.getReview());
        assertEquals(5, booking.getReview().getRating());
        assertEquals("Wonderful class!", booking.getReview().getComment());
    }

    // Test 8: Review blocked unless attended
    @Test
    @Order(8)
    public void testReviewBlockedWithoutAttendance() {
        system.bookLesson(alice.getId(), yogaSatMorning.getId());
        Booking booking = system.getBookings().get(0);
        // Do NOT call attendLesson first
        String result = system.leaveReview(
                alice.getId(), booking.getId(), "Sneaky review", 4);
        assertTrue(result.startsWith("ERROR"),
                "Review should be blocked if lesson not attended");
        assertNull(booking.getReview(),
                "No review should be attached");
    }
}
