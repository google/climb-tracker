package fr.steren.climbtracker;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ClimbTrackerWear extends Activity implements WearableListView.ClickListener {

    private GradeList gradeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gradeList = new GradeList();

        setContentView(R.layout.activity_climb_tracker_wear);

        WearableListView listView = (WearableListView) findViewById(R.id.list);
        listView.setAdapter(new Adapter(this, gradeList));
        listView.setClickListener(this);
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        String selectedGradeLabel = gradeList.get((int) viewHolder.itemView.getTag()).label;
        Toast.makeText(this, selectedGradeLabel, Toast.LENGTH_LONG);
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
