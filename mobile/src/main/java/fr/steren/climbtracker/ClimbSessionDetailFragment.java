package fr.steren.climbtracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.Date;

import fr.steren.climbtracker.dummy.ContentStore;

/**
 * A fragment representing a single ClimbSession detail screen.
 * A ClimbSession is, for the moment, all the climbs done during the same day
 * Takes as argument a Date that will be used to fetch the climbs.
 * If no date is provided as argument, another argument is expected to exist:
 * the ID of a climb from which the date will be extracted
 * This fragment is either contained in a {@link fr.steren.climbtracker.ClimbTracker}
 * in two-pane mode (on tablets) or a {@link ClimbSessionDetailActivity}
 * on handsets.
 */
public class ClimbSessionDetailFragment extends Fragment {
    /**
     * The fragment argument representing the climb ID that this fragment
     * represents.
     */
    public static final String ARG_CLIMB_ID = "climb_id";
    public static final String ARG_CLIMB_TIME = "climb_time";

    /**
     * The dummy content this fragment is presenting.
     */
    private Climb mItem;

    private Date mDay;

    private Firebase mFirebaseRef;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ClimbSessionDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseRef = new Firebase(getResources().getString(R.string.firebase_url));
        mFirebaseRef = mFirebaseRef.child("climbs");

        // retrieve the date of the selected item, and fetch all item of the same day.
        if (getArguments().containsKey(ARG_CLIMB_TIME)) {
            mDay = new Date(getArguments().getLong(ARG_CLIMB_TIME));

            // TODO fetch all items of this day
        }

        if (getArguments().containsKey(ARG_CLIMB_ID)) {
            mItem = ContentStore.ITEM_MAP.get(getArguments().getString(ARG_CLIMB_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_climbsession_detail, container, false);

        if (mDay != null) {
            ((TextView) rootView.findViewById(R.id.climbsession_date)).setText(mDay.toString());
        }

        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.climbsession_detail)).setText(mItem.toString());
        }

        return rootView;
    }
}
