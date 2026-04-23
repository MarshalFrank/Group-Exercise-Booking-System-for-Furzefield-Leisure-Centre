import java.util.Scanner;

public class Main {
    private static BookingSystem system = new BookingSystem();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Furzefield Leisure Centre Booking System");

        DataSeeder.seed(system);

        boolean running = true;

        while (running) {
            printMainMenu();
            int choice = readInt();

            switch (choice) {
                case 1 -> viewTimetableMenu();
                case 2 -> bookLessonMenu();
                case 3 -> changeBookingMenu();
                case 4 -> cancelBookingMenu();
                case 5 -> attendLessonMenu();
                case 6 -> leaveReviewMenu();
                case 7 -> viewBookingsMenu();
                case 8 -> {
                    System.out.print("Enter month (1–12): ");
                    int month = readInt();
                    system.generateMonthlyLessonReport(month);
                }
                case 9 -> {
                    System.out.print("Enter month (1–12): ");
                    int month = readInt();
                    system.generateMonthlyChampionReport(month);
                }
                case 0 -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }

        System.out.println("Goodbye!");
    }

    private static void printMainMenu() {
        System.out.println("\n===== MAIN MENU =====");
        System.out.println("1. View Timetable");
        System.out.println("2. Book a Lesson");
        System.out.println("3. Change a Booking");
        System.out.println("4. Cancel a Booking");
        System.out.println("5. Attend a Lesson");
        System.out.println("6. Leave a Review");
        System.out.println("7. View My Bookings");
        System.out.println("8. Monthly Lesson Report");
        System.out.println("9. Champion Exercise Report");
        System.out.println("0. Exit");
        System.out.print("Choice: ");
    }

    private static void viewTimetableMenu() {
        System.out.println("1. By Day  2. By Exercise Type");
        System.out.print("Choice: ");
        int c = readInt();

        if (c == 1) {
            System.out.println("1=SATURDAY  2=SUNDAY");
            System.out.print("Choice: ");
            int d = readInt();
            system.viewTimetableByDay(d == 1 ? BookingDay.SATURDAY : BookingDay.SUNDAY);
        } else {
            ExerciseType[] types = ExerciseType.values();
            for (int i = 0; i < types.length; i++) {
                System.out.printf("%d=%s ", i + 1, types[i]);
            }
            System.out.println();

            System.out.print("Choice: ");
            int t = readInt();

            if (t >= 1 && t <= types.length) {
                system.viewTimetableByType(types[t - 1]);
            }
        }
    }

    private static void bookLessonMenu() {
        System.out.print("Enter Member ID: ");
        int mid = readInt();

        system.viewTimetableByDay(BookingDay.SATURDAY);
        system.viewTimetableByDay(BookingDay.SUNDAY);

        System.out.print("Enter Lesson ID: ");
        int lid = readInt();

        System.out.println(system.bookLesson(mid, lid));
    }

    private static void changeBookingMenu() {
        System.out.print("Member ID: "); int mid = readInt();
        system.viewMemberBookings(mid);

        System.out.print("Booking ID to change: "); int bid = readInt();

        system.viewTimetableByDay(BookingDay.SATURDAY);
        system.viewTimetableByDay(BookingDay.SUNDAY);

        System.out.print("New Lesson ID: "); int lid = readInt();

        System.out.println(system.changeBooking(mid, bid, lid));
    }

    private static void cancelBookingMenu() {
        System.out.print("Member ID: "); int mid = readInt();
        system.viewMemberBookings(mid);

        System.out.print("Booking ID to cancel: "); int bid = readInt();

        System.out.println(system.cancelBooking(mid, bid));
    }

    private static void attendLessonMenu() {
        System.out.print("Member ID: "); int mid = readInt();
        system.viewMemberBookings(mid);

        System.out.print("Booking ID to mark as attended: "); int bid = readInt();

        System.out.println(system.attendLesson(mid, bid));
    }

    private static void leaveReviewMenu() {
        System.out.print("Member ID: "); int mid = readInt();
        system.viewMemberBookings(mid);

        System.out.print("Booking ID to review: "); int bid = readInt();

        System.out.print("Comment: ");
        String comment = scanner.nextLine();

        System.out.print("Rating (1-5): ");
        int rating = readInt();

        System.out.println(system.leaveReview(mid, bid, comment, rating));
    }

    private static void viewBookingsMenu() {
        System.out.print("Member ID: "); int mid = readInt();
        system.viewMemberBookings(mid);
    }

    private static int readInt() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}