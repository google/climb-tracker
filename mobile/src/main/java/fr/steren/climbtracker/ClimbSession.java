package fr.steren.climbtracker;

import java.util.ArrayList;

public class ClimbSession {
    public String id;

    public ArrayList<Climb> climbs;

    public ClimbSession(String id, ArrayList<Climb> climbs) {
        this.id = id;
        this.climbs = climbs;
    }

    @Override
    public String toString() {
        String result = "";
        for(Climb climb : climbs) {
            result += climb.getGrade() + " ";
        }
        return result;
    }
}
