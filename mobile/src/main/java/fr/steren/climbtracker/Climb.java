package fr.steren.climbtracker;

import java.util.Date;

public class Climb {
    private Date date;
    private String grade;

    /** to replace by Firebase ID */
    public String id;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private Climb() {
    }

    public Climb(String id, Date date, String grade) {
        this.id = id;
        this.date = date;
        this.grade = grade;
    }

    public Date getDate() {
        return date;
    }

    public String getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return grade;
    }
}
