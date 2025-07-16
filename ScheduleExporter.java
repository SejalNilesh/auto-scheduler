import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ScheduleExporter {

    public static void saveScheduleToCSV(
        Map<String, Integer> examToSlot,
        Map<String, String> examToRoom,
        Map<Integer, String> slotToTime,
        String filename
    ) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("ExamID,Slot,TimeSlot,Room,Status\n");

            for (Map.Entry<String, Integer> entry : examToSlot.entrySet()) {
                String examId = entry.getKey();
                int slot = entry.getValue();

                String timeSlot = (slot == -1) ? "UNSCHEDULABLE" : slotToTime.getOrDefault(slot, "UNKNOWN");
                String room = (slot == -1) ? "-" : examToRoom.getOrDefault(examId, "N/A");
                String status = (slot == -1) ? "Constraint Violation" : "OK";

                writer.write(String.format("%s,%d,%s,%s,%s\n", examId, slot, timeSlot, room, status));
            }

            System.out.println("✅ Schedule saved to " + filename);
        } catch (IOException e) {
            System.out.println("❌ Error saving schedule: " + e.getMessage());
        }
    }
}
