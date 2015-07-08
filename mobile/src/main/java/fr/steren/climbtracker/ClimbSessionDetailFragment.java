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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

import java.util.Date;

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
    public static final String ARG_FIRST_CLIMB_KEY = "first_climb_key";
    public static final String ARG_LAST_CLIMB_KEY = "last_climb_key";
    public static final String ARG_CLIMB_TIME = "climb_time";

    /**
     * The dummy content this fragment is presenting.
     */
    private ClimbSession mSession;

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
        AuthData authData = mFirebaseRef.getAuth();
        mFirebaseRef = mFirebaseRef.child("users")
                .child(authData.getUid())
                .child("climbs");



        // For faster title diplay, get the date of the first climb
        if (getArguments().containsKey(ARG_CLIMB_TIME)) {
            long time = getArguments().getLong(ARG_CLIMB_TIME);
            mDay = new Date(time);
        }

        String climbKey = getArguments().getString(ARG_FIRST_CLIMB_KEY);

        // TODO see if we could use endAt(), passed as argument.
        ClimbListAdapter listAdapter = new ClimbListAdapter(mFirebaseRef.limitToFirst(50).startAt(climbKey).orderByChild("date"), R.layout.climb_item, getActivity());
        //setListAdapter(listAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_climbsession_detail, container, false);

        if (mDay != null) {
            ((TextView) rootView.findViewById(R.id.climbsession_date)).setText(mDay.toString());
        }

        if (mSession != null) {
            //((TextView) rootView.findViewById(R.id.climbsession_detail)).setText(mSession.toString());
        }

        return rootView;
    }
}
