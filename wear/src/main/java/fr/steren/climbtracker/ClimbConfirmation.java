package fr.steren.climbtracker;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.view.DelayedConfirmationView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class ClimbConfirmation extends Activity implements
        DelayedConfirmationView.DelayedConfirmationListener {

    private DelayedConfirmationView mDelayedView;

    private String routeLabelToSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        mDelayedView = (DelayedConfirmationView) findViewById(R.id.delayed_confirm);
        mDelayedView.setListener(this);

        routeLabelToSave = getIntent().getStringExtra(ClimbTrackerWear.EXTRA_ROUTE_LABEL);

        // change the recap text
        TextView climbRecapText = (TextView) findViewById(R.id.climb_recap);
        String recapString = getString(R.string.climb_recap, routeLabelToSave);
        climbRecapText.setText(recapString);

        // Four seconds to cancel the action
        mDelayedView.setTotalTimeMs(4000);
        // Start the timer
        mDelayedView.start();
    }

    @Override
    public void onTimerFinished(View view) {
        // save only if user hasn't canceled
        if(!this.isFinishing()) {
            String savedString = getString(R.string.climb_saved, routeLabelToSave);

            Intent intent = new Intent(this, ConfirmationActivity.class);
            intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.SUCCESS_ANIMATION);
            intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, savedString);
            startActivity(intent);
        }
        finish();
    }

    /** User canceled by clicking the "X" button */
    @Override
    public void onTimerSelected(View view) {
        Toast.makeText(this, R.string.climb_canceled, Toast.LENGTH_SHORT).show();
        finish();
    }
}