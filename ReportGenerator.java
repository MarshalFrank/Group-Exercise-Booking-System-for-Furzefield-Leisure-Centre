import java.util.*;
import java.util.stream.Collectors;

public class ReportGenerator {

    private List<Lesson> lessons;
    private List<Booking> bookings;

    public ReportGenerator(List<Lesson> lessons, List<Booking> bookings) {
        this.lessons = lessons;
        this.bookings = bookings;
    }

    // Report 1: Monthly Lesson Report
    public void generateMonthlyLessonReport() {
        System.out.println("\n========== MONTHLY LESSON REPORT ==========");
        System.out.printf("%-5s %-12s %-10s %-10s %-12s %-12s%n",
                "ID", "Type", "Day", "Time", "Attendance", "Avg Rating");
        System.out.println("-".repeat(65));

        for (Lesson lesson : lessons) {
            List<Booking> attended = lesson.getBookings().stream()
                    .filter(b -> b.getStatus() == BookingStatus.ATTENDED)
                    .collect(Collectors.toList());

            int count = attended.size();
            double avgRating = attended.stream()
                    .filter(b -> b.getReview() != null)
                    .mapToInt(b -> b.getReview().getRating())
                    .average()
                    .orElse(0.0);

            System.out.printf("%-5d %-12s %-10s %-10s %-12d %-12s%n",
                    lesson.getId(),
                    lesson.getType(),
                    lesson.getDay(),
                    lesson.getTime(),
                    count,
                    count > 0 && avgRating > 0
                        ? String.format("%.1f", avgRating)
                        : "N/A");
        }
        System.out.println("=".repeat(65));
    }

    // Report 2: Monthly Champion Exercise (highest income)
    public void generateMonthlyChampionReport() {
        System.out.println("\n========== MONTHLY CHAMPION EXERCISE REPORT ==========");

        Map<ExerciseType, Double> incomeByType = new EnumMap<>(ExerciseType.class);
        Map<ExerciseType, Integer> attendanceByType = new EnumMap<>(ExerciseType.class);

        for (ExerciseType type : ExerciseType.values()) {
            incomeByType.put(type, 0.0);
            attendanceByType.put(type, 0);
        }

        for (Lesson lesson : lessons) {
            long attendedCount = lesson.getBookings().stream()
                    .filter(b -> b.getStatus() == BookingStatus.ATTENDED)
                    .count();
            double income = attendedCount * lesson.getPrice();
            incomeByType.merge(lesson.getType(), income, Double::sum);
            attendanceByType.merge(lesson.getType(), (int) attendedCount, Integer::sum);
        }

        System.out.printf("%-15s %-15s %-15s%n", "Exercise", "Total Income", "Total Attendance");
        System.out.println("-".repeat(47));

        incomeByType.entrySet().stream()
                .sorted(Map.Entry.<ExerciseType, Double>comparingByValue().reversed())
                .forEach(e -> System.out.printf("%-15s £%-14.2f %-15d%n",
                        e.getKey(),
                        e.getValue(),
                        attendanceByType.get(e.getKey())));

        ExerciseType champion = incomeByType.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        System.out.println("\n🏆 CHAMPION EXERCISE: " + champion
                + " with £" + String.format("%.2f", incomeByType.get(champion)));
        System.out.println("=".repeat(47));
    }
}