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
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;

public class ClimbListAdapter extends FirebaseListAdapter<Climb> {

    public ClimbListAdapter(Query ref, int layout, Activity activity) {
        super(ref, Climb.class, layout, activity);
    }

    /**
     * Bind an instance of the <code>Climb</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>Chat</code> instance that represents the current data to bind.
     *
     * @param view A view instance corresponding to the layout we passed to the constructor.
     * @param climb An instance representing the current state of a climb
     */
    @Override
    protected void populateView(View view, Climb climb) {
        // Map a climb object to an entry in our listview
        String grade = climb.getGrade();
        TextView gradeText = (TextView) view.findViewById(R.id.grade);
        gradeText.setText(grade);
    }
}
