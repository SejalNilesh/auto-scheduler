import java.util.*;

public class BacktrackingScheduler {

    public Map<String, Integer> scheduleExams(
        Map<String, Set<String>> graph,
        List<Exam> exams,
        int maxSlots
    ) {
        Map<String, Integer> assignment = new HashMap<>();
        Map<String, Exam> examMap = new HashMap<>();
        for (Exam e : exams) examMap.put(e.id, e);

        Map<String, Map<Integer, Integer>> studentSlotCount = new HashMap<>();

        List<String> examOrder = new ArrayList<>(graph.keySet());
        examOrder.sort((a, b) -> graph.get(b).size() - graph.get(a).size()); // Optional: sort by degree

        boolean success = backtrack(0, examOrder, assignment, studentSlotCount, graph, examMap, maxSlots);

        return assignment;
    }

    private boolean backtrack(
        int index,
        List<String> examOrder,
        Map<String, Integer> assignment,
        Map<String, Map<Integer, Integer>> studentSlotCount,
        Map<String, Set<String>> graph,
        Map<String, Exam> examMap,
        int maxSlots
    ) {
        if (index == examOrder.size()) return true; // all exams assigned

        String examId = examOrder.get(index);
        Set<Integer> neighborSlots = new HashSet<>();
        for (String neighbor : graph.get(examId)) {
            if (assignment.containsKey(neighbor)) {
                neighborSlots.add(assignment.get(neighbor));
            }
        }

        for (int slot = 0; slot < maxSlots; slot++) {
            if (neighborSlots.contains(slot)) continue;

            if (!violatesStudentConstraint(examMap.get(examId), studentSlotCount, slot)) {
                // assign
                assignment.put(examId, slot);
                updateStudentSlotCount(examMap.get(examId), studentSlotCount, slot, +1);

                if (backtrack(index + 1, examOrder, assignment, studentSlotCount, graph, examMap, maxSlots)) {
                    return true;
                }

                // backtrack
                assignment.remove(examId);
                updateStudentSlotCount(examMap.get(examId), studentSlotCount, slot, -1);
            }
        }

        return false; // no valid slot found
    }

    private boolean violatesStudentConstraint(Exam exam, Map<String, Map<Integer, Integer>> countMap, int slot) {
        for (String student : exam.students) {
            int count = countMap.getOrDefault(student, new HashMap<>()).getOrDefault(slot, 0);
            if (count >= 2) return true;
        }
        return false;
    }

    private void updateStudentSlotCount(Exam exam, Map<String, Map<Integer, Integer>> countMap, int slot, int delta) {
        for (String student : exam.students) {
            Map<Integer, Integer> map = countMap.getOrDefault(student, new HashMap<>());
            map.put(slot, map.getOrDefault(slot, 0) + delta);
            if (map.get(slot) <= 0) map.remove(slot);
            countMap.put(student, map);
        }
    }
}
