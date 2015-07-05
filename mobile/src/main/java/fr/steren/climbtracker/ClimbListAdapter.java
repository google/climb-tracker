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
