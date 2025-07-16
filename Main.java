import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Exam> exams = CSVLoader.loadExams("exams.csv");
        List<Room> rooms = CSVLoader.loadRooms("rooms.csv");


        GraphBuilder graphBuilder = new GraphBuilder();
        Map<String, Set<String>> graph = graphBuilder.buildConflictGraph(exams);

        System.out.println("Conflict Graph:");
        for (Map.Entry<String, Set<String>> entry : graph.entrySet()) {
            System.out.println("  " + entry.getKey() + " → " + entry.getValue());
        }

        // GraphColoringSolver solver = new GraphColoringSolver();
        // int maxSlots = 5; // Can be adjusted
        // Map<String, Integer> schedule = solver.colorGraph(graph, exams, maxSlots);

        BacktrackingScheduler scheduler = new BacktrackingScheduler();
        int maxSlots = 5;
        BacktrackingScheduler.ScheduleResult result = scheduler.scheduleExams(graph, exams, rooms, maxSlots);
        Map<String, Integer> schedule = result.examToSlot;
        Map<String, String> examToRoom = result.examToRoom;

        // Map<String, String> examToRoom = RoomAssigner.assignRooms(exams, schedule, rooms);

        Map<Integer, String> slotToTime = Map.of(
            0, "09:00 AM - 10:00 AM",
            1, "10:00 AM - 11:00 AM",
            2, "11:00 AM - 12:00 PM",
            3, "12:00 PM - 01:00 PM",
            4, "01:00 PM - 02:00 PM"
        );
        
        System.out.println("\nFinal Schedule:");
        System.out.println("-----------------------------------------------------------");
        System.out.println("| Exam | Slot No. | Time Slot             | Room Assigned |");
        System.out.println("-----------------------------------------------------------");

        for (Map.Entry<String, Integer> entry : schedule.entrySet()) {
            String examId = entry.getKey();
            int slot = entry.getValue();
            String time = (slot == -1) ? "UNSCHEDULABLE" : slotToTime.getOrDefault(slot, "UNKNOWN");
            String room = (slot == -1) ? "-" : examToRoom.getOrDefault(examId, "N/A");
            String status = (slot == -1) ? "❌ Cannot Schedule" : "✅ OK";

            System.out.printf("| %-5s| %-9d| %-22s| %-14s|%-14s|\n", examId, slot, time, room,status);
        }
       System.out.println("-----------------------------------------------------------");
       ScheduleExporter.saveScheduleToCSV(schedule, examToRoom, slotToTime, "schedule.csv");

    }
    
}