import java.util.List;

public class Exam {
    public String id;
    public List<String> students;
    public String teacher;

    public Exam(String id, List<String> students, String teacher) {
        this.id = id;
        this.students = students;
        this.teacher = teacher;
    }
}

