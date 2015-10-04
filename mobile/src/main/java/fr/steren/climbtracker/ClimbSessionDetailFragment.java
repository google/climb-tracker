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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.Date;

/**
 * A fragment representing a single ClimbSession detail screen.
 * A ClimbSession is, for the moment, all the climbs done during the same day
 * Takes as argument a Date that will be used to fetch the climbs.
 * If no date is provided as argument, another argument is expected to exist:
 * the ID of a climb from which the date will be extracted
 * This fragment is either contained in a {@link ClimbTrackerActivity}
 * in two-pane mode (on tablets) or a {@link ClimbSessionDetailActivity}
 * on handsets.
 */
public class ClimbSessionDetailFragment extends Fragment {
    private static final String LOG_TAG = ClimbSessionDetailFragment.class.getSimpleName();

    /**
     * The fragment argument representing the climb ID that this fragment
     * represents.
     */
    public static final String ARG_FIRST_CLIMB_TIME = "first_climb_time";
    public static final String ARG_LAST_CLIMB_TIME = "last_climb_time";

    /** Climb session, does not contain the climbs */
    private ClimbSession mSession;
    private Firebase mFirebaseRef;
    private ListView mList;
    private ClimbListAdapter mListAdapter;

    /** The selected Climb, if any */
    private Climb mSelectedClimb;

    /** Analytics tracker */
    private Tracker mTracker;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ClimbSessionDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Analytics.
        ClimbTrackerApplication application = (ClimbTrackerApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();

        // track pageview
        mTracker.setScreenName(LOG_TAG);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        mFirebaseRef = new Firebase(getResources().getString(R.string.firebase_url));
        AuthData authData = mFirebaseRef.getAuth();
        mFirebaseRef = mFirebaseRef.child("users")
                .child(authData.getUid())
                .child("climbs");


        long time = getArguments().getLong(ARG_FIRST_CLIMB_TIME);
        mSession = new ClimbSession( new Date(time) );

        long lastTime = getArguments().getLong(ARG_LAST_CLIMB_TIME);

        mListAdapter = new ClimbListAdapter(mFirebaseRef.limitToFirst(50).orderByChild("date").startAt(time).endAt(lastTime), R.layout.climb_item, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_climbsession_detail, container, false);

        mList = (ListView) rootView.findViewById(R.id.climbList);
        mList.setAdapter(mListAdapter);
        ((TextView) rootView.findViewById(R.id.climbsession_date)).setText(mSession.getReadableDate(getActivity()));

        mList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long id) {
                mSelectedClimb = (Climb) mList.getItemAtPosition(position);

                // Display a confirmation dialog
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.action_delete_climb_title)
                        .setMessage(R.string.action_delete_climb_message)
                        .setPositiveButton(R.string.action_delete_climb_confirm, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // delete this climb
                                String selectedKey = mSelectedClimb.getKey();
                                mFirebaseRef.child(selectedKey).removeValue();

                                mTracker.send(new HitBuilders.EventBuilder()
                                        .setCategory("action")
                                        .setAction("delete-climb")
                                        .build());
                            }
                        })
                        .setNegativeButton(R.string.action_delete_climb_cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Do nothing
                            }
                        })
                        .show();

                return true;
            }
        });

        return rootView;
    }

    // TODO add onListItemClick to the listView.
}
