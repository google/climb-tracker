/*
Copyright 2014 Google Inc. All rights reserved.
        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0
        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/
package fr.steren.climbtracker;

import android.content.Context;
import android.text.format.DateUtils;

import org.ocpsoft.prettytime.PrettyTime;

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

    public String getLastClimbKey() {
        return climbs.get(climbs.size() - 1).getKey();
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

    public String getPrettyDate() {
        PrettyTime p = new PrettyTime();
        return p.format(getDate());
    }

    public String getReadableDate(Context context) {
        return DateUtils.formatDateTime(context, getDate().getTime(), DateUtils.FORMAT_SHOW_DATE);
    }

    /**
     * Transforms a list of Climbs into a list of ClimSessions
     * @param climbs list of climbs sorted by date
     * @return List of sessions, sorted by newest first
     */
    public static List<ClimbSession> climbsToSessions(List<Climb> climbs) {
        // TODO: only works when the received list is sorted from oldest to latest. Re-write to be order agnostic.
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

        // we built a list in the wrong order, reverse it
        List<ClimbSession> sessionsReversed = new ArrayList<ClimbSession>();
        for(ClimbSession session1 : sessions) {
            sessionsReversed.add(0, session1);
        }

        return sessionsReversed;
    }
}
