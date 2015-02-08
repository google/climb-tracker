package fr.steren.climbtracker;

import java.util.Date;

public class Climb {
    public Date time;
    public String grade;

    public Climb(Date time, String grade) {
        this.time = time;
        this.grade = grade;
    }
}
