import java.util.*;

public class RoomAssigner {

    public static Map<String, String> assignRooms(
        List<Exam> exams,
        Map<String, Integer> schedule,
        List<Room> rooms
    ) {
        Map<String, String> examToRoom = new HashMap<>(); // Exam ID → Room ID
        Map<Integer, Set<String>> usedRoomsInSlot = new HashMap<>(); // Slot → Room IDs used

        for (Map.Entry<String, Integer> entry : schedule.entrySet()) {
            String examId = entry.getKey();
            int slot = entry.getValue();
            Exam exam = exams.stream()
                .filter(e -> e.id.equals(examId))
                .findFirst()
                .orElse(null);

            if (exam == null) continue;

            int studentCount = exam.students.size();
            Set<String> usedInThisSlot = usedRoomsInSlot.getOrDefault(slot, new HashSet<>());

            boolean assigned = false;
            for (Room room : rooms) {
                if (room.capacity >= studentCount && !usedInThisSlot.contains(room.id)) {
                    examToRoom.put(examId, room.id);
                    usedInThisSlot.add(room.id);
                    usedRoomsInSlot.put(slot, usedInThisSlot);
                    assigned = true;
                    break;
                }
            }

            if (!assigned) {
                examToRoom.put(examId, "UNASSIGNED");
            }
        }

        return examToRoom;
    }
}
