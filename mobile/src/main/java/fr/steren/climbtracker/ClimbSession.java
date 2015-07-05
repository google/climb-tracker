package fr.steren.climbtracker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClimbSession {

    /** time, inms, between two climbs above which a new session is computed */
    private static long TIME_BETWEEN_2_SESSIONS = 1000 * 60 * 60 * 24; // one day

    private Date date;

    private List<Climb> climbs;

    private ClimbSession() {
    }

    public ClimbSession(Date date) {
        this.climbs = new ArrayList<Climb>();
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void addClimb(Climb climb) {
        climbs.add(climb);
    }

    public List<Climb> getClimbs() {
        return climbs;
    }

    public String getFirstClimbKey() {
        return climbs.get(0).getKey();
    }

    @Override
    public String toString() {
        return date.toString() + " " + toGradesString();
    }

    public String toGradesString() {
        String result = "";

        for(Climb climb : climbs) {
            result += climb.getGrade();
            result += " ";
        }
        return result;
    }



    /**
     * Transforms a sorted list of Climbs into a list of ClimSessions
     * @param climbs list of climbs sorted by date
     */
    public static List<ClimbSession> climbsToSessions(List<Climb> climbs) {
        List<ClimbSession> sessions = new ArrayList<ClimbSession>();
        ClimbSession session = null;

        for(Climb climb : climbs) {
            if(null == session) {
                session = new ClimbSession(climb.getDate());
                session.addClimb(climb);
            } else if (climb.getDate().getTime() - session.getDate().getTime() < TIME_BETWEEN_2_SESSIONS) {
                session.addClimb(climb);
            } else {
                sessions.add(session);
                session = new ClimbSession(climb.getDate());
                session.addClimb(climb);
            }
        }
        sessions.add(session);
        return sessions;
    }
}
