package fr.steren.climbtracker.dummy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.steren.climbtracker.Climb;
import fr.steren.climbtracker.ClimbSession;


public class ContentStore {

    /**
     * An array of sample (dummy) items.
     */
    public static List<ClimbSession> ITEMS = new ArrayList<ClimbSession>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, ClimbSession> ITEM_MAP = new HashMap<String, ClimbSession>();

    static {
        ArrayList<Climb> session1 = new ArrayList<Climb>();
        session1.add(new Climb(new Date(), "5"));
        session1.add(new Climb(new Date(), "5+"));
        session1.add(new Climb(new Date(), "6+"));

        ArrayList<Climb> session2 = new ArrayList<Climb>();
        session2.add(new Climb(new Date(), "6"));
        session2.add(new Climb(new Date(), "6+"));
        session2.add(new Climb(new Date(), "7-"));
        session2.add(new Climb(new Date(), "7"));

        addItem(new ClimbSession("1", session1));
        addItem(new ClimbSession("2", session2));
    }

    private static void addItem(ClimbSession item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

}
