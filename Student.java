package sisairis7;


import javax.xml.bind.annotation.*;


@XmlRootElement
@XmlAccessorType (XmlAccessType.FIELD)
public class Student {

    private int id;
    private String name;
    private double mark;

    public Student() {
        this.id = 0;
        this.name = "NoName";
        this.mark = 0.0;
    }

    public Student(int id, String name, double mark) {
        this.id = id;
        this.name = name;
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Student N" + id + ". Name: "
                + name + ". Mark: " + mark + ".";
    }

}