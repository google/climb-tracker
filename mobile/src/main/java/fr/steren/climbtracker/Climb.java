package fr.steren.climbtracker;

import java.util.Date;

public class Climb {
    private Date date;
    private String grade;
    private String system;

    private String key;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private Climb() {
    }

    public Climb(Date date, String grade, String system) {
        this.date = date;
        this.grade = grade;
        this.system = system;
    }

    public Climb(String key, Date date, String grade, String system) {
        this.key = key;
        this.date = date;
        this.grade = grade;
        this.system = system;
    }

    public Date getDate() {
        return date;
    }

    public String getGrade() {
        return grade;
    }

    public String getSystem() {
        return system;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return grade;
    }
}
