public class Review {
    private String comment;
    private int rating; // 1 to 5

    public Review(String comment, int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
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
