import java.util.Scanner;

public class Main {
    private static BookingSystem system = new BookingSystem();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Furzefield Leisure Centre Booking System");

        // TEMP DATA (until Phase 4)
        system.addMember(new Member("John", "123"));
        system.addMember(new Member("Mary", "456"));

        system.addLesson(new Lesson(ExerciseType.YOGA, BookingDay.SATURDAY, SessionTime.MORNING, "Alice", 10));
        system.addLesson(new Lesson(ExerciseType.PILATES, BookingDay.SUNDAY, SessionTime.AFTERNOON, "Bob", 12));

        boolean running = true;

        while (running) {
            printMainMenu();
            int choice = readInt();

            switch (choice) {
                case 1 -> viewTimetableMenu();
                case 2 -> bookLessonMenu();
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

    private static int readInt() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}