public class DataSeeder {

    public static void seed(BookingSystem system) {
        // --- 10 Members ---
        Member[] members = {
            new Member("Alice Johnson",   "07700100001"),
            new Member("Bob Smith",       "07700100002"),
            new Member("Carol White",     "07700100003"),
            new Member("David Brown",     "07700100004"),
            new Member("Emma Davis",      "07700100005"),
            new Member("Frank Wilson",    "07700100006"),
            new Member("Grace Moore",     "07700100007"),
            new Member("Henry Taylor",    "07700100008"),
            new Member("Isla Anderson",   "07700100009"),
            new Member("Jack Thomas",     "07700100010")
        };
        for (Member m : members) system.addMember(m);

        // --- 48 Lessons: 8 weekends × 2 days × 3 sessions ---
        // Instructors cycle through 6 types; price varies by type
        ExerciseType[] types = ExerciseType.values();
        String[][] instructors = {
            {"Sarah Bloom", "Mike Pace", "Lucy Rivers", "Tom Hart", "Zoe Stone", "Chelsea Green"},
            {"Sarah Bloom", "Mike Pace", "Lucy Rivers", "Tom Hart", "Zoe Stone", "Chelsea Green"}
        };
        double[] prices = {12.00, 10.00, 11.00, 13.00, 12.50, 12.30};

        BookingDay[] days = {BookingDay.SATURDAY, BookingDay.SUNDAY};
        SessionTime[] times = {SessionTime.MORNING, SessionTime.AFTERNOON, SessionTime.EVENING};

        // 8 weekends, each weekend: Sat + Sun, each day: 3 sessions
        // Assign exercise type in round-robin per slot
        int typeIndex = 0;
        for (int week = 0; week < 8; week++) {

            int month = (week < 4) ? 5 : 6; // First 4 weekends in May, next 4 in June

            for (BookingDay day : days) {
                for (SessionTime time : times) {
                    ExerciseType type = types[typeIndex % types.length];
                    String instructor = instructors[0][typeIndex % types.length];
                    double price = prices[typeIndex % types.length];
                    system.addLesson(new Lesson(type, day, time, instructor, price, month));
                    typeIndex++;
                }
            }
        }

        // --- Pre-seed bookings and attendance with 20+ reviews ---
        // Book several members into specific lessons and mark some attended
        int[][] bookingPairs = {
            {1,1},{2,1},{3,1},{4,1},   // Lesson 1 full
            {1,4},{2,5},{3,6},
            {5,2},{6,2},{7,3},
            {8,7},{9,8},{10,9},
            {1,10},{2,11},{3,12},
            {4,13},{5,14},{6,15},
            {7,16},{8,17},{9,18},
            {10,19},{1,20},{2,21},
            {3,22},{4,23},{5,24}
        };
        for (int[] pair : bookingPairs) {
            system.bookLesson(pair[0], pair[1]);
        }

        // Mark some as attended
        int[] attendedBookingIds = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
        for (int bid : attendedBookingIds) {
            // Find the booking and mark attended
            Booking b = system.findBookingById(bid);
            if (b != null) b.setStatus(BookingStatus.ATTENDED);
        }

        // 20 reviews
        String[][] reviews = {
            {"Absolutely loved it, very energising!",       "5"},
            {"Great session, will come again.",             "4"},
            {"Good class but a bit crowded.",               "3"},
            {"Instructor was fantastic!",                   "5"},
            {"Enjoyed it, nice pace.",                      "4"},
            {"Average session, nothing special.",           "3"},
            {"Brilliant! Best class I've attended.",        "5"},
            {"A bit too intense for me.",                   "2"},
            {"Very relaxing and well-structured.",          "5"},
            {"Good workout, felt great after.",             "4"},
            {"Instructor knew their stuff.",                "4"},
            {"Not my favourite but decent.",                "3"},
            {"Incredible atmosphere in class.",             "5"},
            {"Would have preferred more variety.",          "3"},
            {"Perfect for beginners.",                      "4"},
            {"Very professional instructor.",               "5"},
            {"Enjoyed every minute!",                       "5"},
            {"Slightly disappointed, expected more.",       "2"},
            {"Fun class, will bring a friend next time.",   "4"},
            {"Excellent value for the price.",              "5"}
        };
        for (int i = 0; i < reviews.length; i++) {
            int bookingId = i + 1;
            int rating = Integer.parseInt(reviews[i][1]);
            system.leaveReview(
                system.findBookingById(bookingId).getMember().getId(),
                bookingId, reviews[i][0], rating);
        }
    }
}