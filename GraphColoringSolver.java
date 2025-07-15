import java.util.*;

public class GraphColoringSolver {

    public Map<String, Integer> colorGraph(
        Map<String, Set<String>> graph,
        List<Exam> exams,
        int maxSlotCount
    ) {
        Map<String, Integer> colorAssignment = new HashMap<>();
        Map<String, Map<Integer, Integer>> studentSlotCount = new HashMap<>();

        Map<String, Exam> examMap = new HashMap<>();
        for (Exam e : exams) examMap.put(e.id, e);

        List<String> nodes = new ArrayList<>(graph.keySet());
        // Optional: sort by degree (high → low)
        nodes.sort((a, b) -> graph.get(b).size() - graph.get(a).size());

        for (String examId : nodes) {
            Set<String> neighbors = graph.getOrDefault(examId, new HashSet<>());

            Set<Integer> usedColors = new HashSet<>();
            for (String neighbor : neighbors) {
                if (colorAssignment.containsKey(neighbor)) {
                    usedColors.add(colorAssignment.get(neighbor));
                }
            }

            boolean assigned = false;

            for (int color = 0; color < maxSlotCount; color++) {
                if (usedColors.contains(color)) continue;

                if (!violatesStudentLimit(examMap.get(examId), studentSlotCount, color)) {
                    colorAssignment.put(examId, color);
                    updateStudentSlotCount(examMap.get(examId), studentSlotCount, color);
                    assigned = true;
                    break;
                }
            }

            if (!assigned) {
                colorAssignment.put(examId, -1); // Mark as UNSCHEDULABLE
            }
        }

        return colorAssignment;
    }

    private boolean violatesStudentLimit(Exam exam, Map<String, Map<Integer, Integer>> countMap, int color) {
        for (String student : exam.students) {
            Map<Integer, Integer> slotMap = countMap.getOrDefault(student, new HashMap<>());
            if (slotMap.getOrDefault(color, 0) >= 2) {
                return true;
            }
        }
        return false;
    }

    private void updateStudentSlotCount(Exam exam, Map<String, Map<Integer, Integer>> countMap, int color) {
        for (String student : exam.students) {
            Map<Integer, Integer> slotMap = countMap.getOrDefault(student, new HashMap<>());
            slotMap.put(color, slotMap.getOrDefault(color, 0) + 1);
            countMap.put(student, slotMap);
        }
    }
}
