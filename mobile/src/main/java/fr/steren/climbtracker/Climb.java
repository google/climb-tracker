package fr.steren.climbtracker;

import java.util.Date;

public class Climb {
    private Date time;
    private String grade;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private Climb() {
    }

    public Climb(Date time, String grade) {
        this.time = time;
        this.grade = grade;
    }

    public Date getTime() {
        return time;
    }

    public String getGrade() {
        return grade;
    }
}
