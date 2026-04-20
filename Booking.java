public class Booking {
    private static int counter = 1;
    private int id;
    private Member member;
    private Lesson lesson;
    private BookingStatus status;
    private Review review;

    public Booking(Member member, Lesson lesson) {
        this.id = counter++;
        this.member = member;
        this.lesson = lesson;
        this.status = BookingStatus.BOOKED;
        this.review = null;
    }

    public int getId() { return id; }
    public Member getMember() { return member; }
    public Lesson getLesson() { return lesson; }
    public BookingStatus getStatus() { return status; }
    public Review getReview() { return review; }

    public void setStatus(BookingStatus status) { this.status = status; }
    public void setReview(Review review) { this.review = review; }

    @Override
    public String toString() {
        return String.format("Booking{id=%d, member='%s', lesson=%s %s, status=%s}",
                id, member.getName(), lesson.getType(), lesson.getTime(), status);
    }
}