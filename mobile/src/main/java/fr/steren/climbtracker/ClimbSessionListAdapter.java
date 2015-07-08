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

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClimbSessionListAdapter extends BaseAdapter {

    private Query mRef;
    private int mLayout;
    private LayoutInflater mInflater;
    private List<Climb> mModels;
    private Map<String, Climb> mModelKeys;

    private List<ClimbSession> mClimbSessions;

    private ChildEventListener mListener;


    /**
     * @param mRef        The Firebase location to watch for data changes. Can also be a slice of a location, using some
     *                    combination of <code>limit()</code>, <code>startAt()</code>, and <code>endAt()</code>,
     * @param mLayout     This is the mLayout used to represent a single list item. You will be responsible for populating an
     *                    instance of the corresponding view with the data from an instance of mModelClass.
     * @param activity    The activity containing the ListView
     */
    public ClimbSessionListAdapter(Query mRef, int mLayout, Activity activity) {
        this.mRef = mRef;
        this.mLayout = mLayout;
        mInflater = activity.getLayoutInflater();
        mModels = new ArrayList<Climb>();
        mModelKeys = new HashMap<String, Climb>();
        mClimbSessions = new ArrayList<ClimbSession>();

        // Look for all child events. We will then map them to our own internal ArrayList, which backs ListView
        mListener = this.mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

                Climb model = dataSnapshot.getValue(Climb.class);
                model.setKey(dataSnapshot.getKey());
                mModelKeys.put(dataSnapshot.getKey(), model);

                // Insert into the correct location, based on previousChildName
                if (previousChildName == null) {
                    mModels.add(0, model);
                } else {
                    Climb previousModel = mModelKeys.get(previousChildName);
                    int previousIndex = mModels.indexOf(previousModel);
                    int nextIndex = previousIndex + 1;
                    if (nextIndex == mModels.size()) {
                        mModels.add(model);
                    } else {
                        mModels.add(nextIndex, model);
                    }
                }

                mClimbSessions = ClimbSession.climbsToSessions(mModels);
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                // One of the mModels changed. Replace it in our list and name mapping
                String modelName = dataSnapshot.getKey();
                Climb oldModel = mModelKeys.get(modelName);
                Climb newModel = dataSnapshot.getValue(Climb.class);
                newModel.setKey(modelName);
                int index = mModels.indexOf(oldModel);

                mModels.set(index, newModel);
                mModelKeys.put(modelName, newModel);

                mClimbSessions = ClimbSession.climbsToSessions(mModels);
                notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                // A model was removed from the list. Remove it from our list and the name mapping
                String modelName = dataSnapshot.getKey();
                Climb oldModel = mModelKeys.get(modelName);
                mModels.remove(oldModel);
                mModelKeys.remove(modelName);

                mClimbSessions = ClimbSession.climbsToSessions(mModels);
                notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {

                // A model changed position in the list. Update our list accordingly
                String modelName = dataSnapshot.getKey();
                Climb oldModel = mModelKeys.get(modelName);
                Climb newModel = dataSnapshot.getValue(Climb.class);
                newModel.setKey(modelName);
                int index = mModels.indexOf(oldModel);
                mModels.remove(index);
                if (previousChildName == null) {
                    mModels.add(0, newModel);
                } else {
                    Climb previousModel = mModelKeys.get(previousChildName);
                    int previousIndex = mModels.indexOf(previousModel);
                    int nextIndex = previousIndex + 1;
                    if (nextIndex == mModels.size()) {
                        mModels.add(newModel);
                    } else {
                        mModels.add(nextIndex, newModel);
                    }
                }
                mClimbSessions = ClimbSession.climbsToSessions(mModels);
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("FirebaseListAdapter", "Listen was cancelled, no more updates will occur");
            }

        });
    }

    public void cleanup() {
        // We're being destroyed, let go of our mListener and forget about all of the mModels
        mRef.removeEventListener(mListener);
        mModels.clear();
        mModelKeys.clear();
        mClimbSessions.clear();
    }

    @Override
    public int getCount() {
        return mClimbSessions.size();
    }

    @Override
    public ClimbSession getItem(int i) {
        return mClimbSessions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = mInflater.inflate(mLayout, viewGroup, false);
        }

        ClimbSession model = mClimbSessions.get(i);
        // Call out to subclass to marshall this model into the provided view
        populateView(view, model);
        return view;
    }

    /**
     * Each time the data at the given Firebase location changes, this method will be called for each item that needs
     * to be displayed. The arguments correspond to the mLayout and mModelClass given to the constructor of this class.
     * <p/>
     * Your implementation should populate the view using the data contained in the model.
     *
     * @param view     The view to populate
     * @param session The object containing the data used to populate the view
     */
    public void populateView(View view, ClimbSession session) {
        // Map a climbsession object to an entry in our listview
        PrettyTime p = new PrettyTime();
        String date = p.format(session.getDate());
        TextView dateText = (TextView) view.findViewById(R.id.date);
        dateText.setText(date);

        String grades = session.toGradesString();
        TextView gradeText = (TextView) view.findViewById(R.id.grades);
        gradeText.setText(grades);
    }
}