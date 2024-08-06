package sample.models;

public class Course {
    private int id;
    private final String name;
    private final String section;
    private final String hours;

    public Course(int id, String name, String section, String hours) {
        this.id = id;
        this.name = name;
        this.section = section;
        this.hours = hours;
    }

    public Course(String name, String section, String hours) {
        this.name = name;
        this.section = section;
        this.hours = hours;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSection() {
        return section;
    }

    public String getHours() {
        return hours;
    }
}
