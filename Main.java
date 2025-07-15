import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Exam> exams = List.of(
            new Exam("A", List.of("S1", "S2"), "T1"),
            new Exam("B", List.of("S2", "S3"), "T2"),
            new Exam("C", List.of("S1"), "T1"),
            new Exam("D", List.of("S3", "S4"), "T3")
        );

        GraphBuilder graphBuilder = new GraphBuilder();
        Map<String, Set<String>> graph = graphBuilder.buildConflictGraph(exams);

        System.out.println("Conflict Graph:");
        for (Map.Entry<String, Set<String>> entry : graph.entrySet()) {
            System.out.println("  " + entry.getKey() + " → " + entry.getValue());
        }

        GraphColoringSolver solver = new GraphColoringSolver();
        Map<String, Integer> schedule = solver.colorGraph(graph);
        Map<Integer, String> slotToTime = Map.of(
            0, "09:00 AM - 10:00 AM",
            1, "10:00 AM - 11:00 AM",
            2, "11:00 AM - 12:00 PM",
            3, "12:00 PM - 01:00 PM",
            4, "01:00 PM - 02:00 PM"
        );

        System.out.println("\nFinal Schedule:");
        System.out.println("-------------------------------------------");
        System.out.println("| Exam | Slot No. | Time Slot             |");
        System.out.println("-------------------------------------------");

        for (Map.Entry<String, Integer> entry : schedule.entrySet()) {
            String examId = entry.getKey();
            int slot = entry.getValue();
            String time = slotToTime.getOrDefault(slot, "UNKNOWN");
            
            System.out.printf("| %-5s| %-9d| %-22s|\n", examId, slot, time);
        }

        System.out.println("-------------------------------------------");
    }
            
}
