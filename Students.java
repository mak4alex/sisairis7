package sisairis7;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;


@XmlRootElement
@XmlAccessorType (XmlAccessType.FIELD)
public class Students {


    @XmlElement(name = "student")
    private List<Student> students;

    public Students() {
        students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        int index = students.indexOf(student);
        if (index != -1) students.remove(index);
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}