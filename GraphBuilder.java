import java.util.*;

public class GraphBuilder {

    public Map<String, Set<String>> buildConflictGraph(List<Exam> exams) {
        Map<String, Set<String>> graph = new HashMap<>();

        for (int i = 0; i < exams.size(); i++) {
            Exam a = exams.get(i);
            for (int j = i + 1; j < exams.size(); j++) {
                Exam b = exams.get(j);

                if (hasConflict(a, b)) {
                    graph.computeIfAbsent(a.id, k -> new HashSet<>()).add(b.id);
                    graph.computeIfAbsent(b.id, k -> new HashSet<>()).add(a.id);
                }
            }
        }

        return graph;
    }

    private boolean hasConflict(Exam a, Exam b) {
        Set<String> studentsA = new HashSet<>(a.students);
        studentsA.retainAll(b.students);
        return !studentsA.isEmpty() || a.teacher.equals(b.teacher);
    }
}
