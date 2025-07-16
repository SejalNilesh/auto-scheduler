import java.io.*;
import java.util.*;

public class CSVLoader {

    public static List<Exam> loadExams(String filename) {
        List<Exam> exams = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String id = parts[0].trim();
                List<String> students = Arrays.asList(parts[1].split("\\|"));
                String teacher = parts[2].trim();
                exams.add(new Exam(id, students, teacher));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exams;
    }

    public static List<Room> loadRooms(String filename) {
        List<Room> rooms = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String id = parts[0].trim();
                int cap = Integer.parseInt(parts[1].trim());
                rooms.add(new Room(id, cap));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rooms;
    }
}
