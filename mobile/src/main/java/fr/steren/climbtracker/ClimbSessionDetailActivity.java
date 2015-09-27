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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.Date;


/**
 * An activity representing a single ClimbSession detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link fr.steren.climbtracker.ClimbTracker}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link ClimbSessionDetailFragment}.
 */
public class ClimbSessionDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_climbsession_detail);

        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ClimbSessionDetailFragment.ARG_FIRST_CLIMB_KEY,
                    getIntent().getStringExtra(ClimbSessionDetailFragment.ARG_FIRST_CLIMB_KEY));
            arguments.putString(ClimbSessionDetailFragment.ARG_LAST_CLIMB_KEY,
                    getIntent().getStringExtra(ClimbSessionDetailFragment.ARG_LAST_CLIMB_KEY));
            long time = getIntent().getLongExtra(ClimbSessionDetailFragment.ARG_CLIMB_TIME, 0);
            arguments.putLong(ClimbSessionDetailFragment.ARG_CLIMB_TIME, time);

            // set title with the pretty date of this session
            ClimbSession session = new ClimbSession(new Date(time));
            setTitle(session.getPrettyDate());

            ClimbSessionDetailFragment fragment = new ClimbSessionDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.climbsession_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, ClimbTracker.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
