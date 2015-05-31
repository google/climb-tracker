package fr.steren.climbtracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.view.DelayedConfirmationView;
import android.support.wearable.view.WearableListView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ClimbTrackerWear extends Activity implements WearableListView.ClickListener {
    private static final int SAMPLE_NOTIFICATION_ID = 0;
    private GradeList gradeList;
    private DelayedConfirmationView mDelayedView;

    public static final String EXTRA_ROUTE_GRADE_LABEL = "routeGradeLabel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gradeList = new GradeList(this);

        setContentView(R.layout.activity_climb_tracker_wear);

        WearableListView listView = (WearableListView) findViewById(R.id.list);
        listView.setAdapter(new Adapter(this, gradeList));
        listView.setClickListener(this);
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        String selectedGradeLabel = gradeList.get((int) viewHolder.itemView.getTag()).label;

        Intent intent = new Intent(this, ClimbConfirmation.class);
        intent.putExtra(ClimbTrackerWear.EXTRA_ROUTE_GRADE_LABEL, selectedGradeLabel);

        startActivity(intent);
    }

    @Override
    public void onTopEmptyRegionClick() {

    }

    private static final class Adapter extends WearableListView.Adapter {
        private final Context mContext;
        private final LayoutInflater mInflater;
        private GradeList mGrades;

        private Adapter(Context context, GradeList grades) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
            mGrades = grades;
        }

        @Override
        public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new WearableListView.ViewHolder(
                    mInflater.inflate(R.layout.route_grade_list_item_layout, null));
        }

        @Override
        public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
            TextView view = (TextView) holder.itemView.findViewById(R.id.name);
            view.setText(mGrades.get(position).label);
            holder.itemView.setTag(position);
        }

        @Override
        public int getItemCount() {
            return mGrades.size();
        }
    }

}
