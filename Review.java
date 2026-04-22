public class Review {
    private String comment;
    private int rating; // 1 to 5
    public static final int MIN_RATING = 1;
    public static final int MAX_RATING = 5;

    public Review(String comment, int rating) {
        if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new IllegalArgumentException("Rating must be between " + MIN_RATING + " and " + MAX_RATING + ".");
        }
        this.comment = comment;
        this.rating = rating;
    }

    public String getComment() { return comment; }
    public int getRating() { return rating; }

    @Override
    public String toString() {
        return "Review{rating=" + rating + ", comment='" + comment + "'}";
    }
}
