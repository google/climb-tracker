package fr.steren.climbtracker.dummy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.steren.climbtracker.Climb;


public class ContentStore {

    /**
     * An array of sample (dummy) items.
     */
    public static List<Climb> ITEMS = new ArrayList<Climb>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, Climb> ITEM_MAP = new HashMap<String, Climb>();

    static {
        addItem(new Climb("1", new Date(), "6"));
        addItem(new Climb("2", new Date(), "6+"));
    }

    private static void addItem(Climb item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

}
