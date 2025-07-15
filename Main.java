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

        System.out.println("\nSchedule:");
        for (Map.Entry<String, Integer> entry : schedule.entrySet()) {
            System.out.println("  Exam " + entry.getKey() + " → Slot " + entry.getValue());
        }
    }
}
