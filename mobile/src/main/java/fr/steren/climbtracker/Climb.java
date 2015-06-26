package fr.steren.climbtracker;

import java.util.Date;

public class Climb {
    private Date date;
    private String grade;
    private String system;

    /** to replace by Firebase ID */
    public String id;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private Climb() {
    }

    public Climb(Date date, String grade, String system) {
        this.date = date;
        this.grade = grade;
        this.system = system;
    }

    public Climb(String id, Date date, String grade, String system) {
        this.id = id;
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

    @Override
    public String toString() {
        return grade;
    }
}
