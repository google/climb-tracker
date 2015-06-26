package fr.steren.climbtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.view.DelayedConfirmationView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.UUID;

import fr.steren.climblib.Path;

public class ClimbConfirmation extends Activity implements
        DelayedConfirmationView.DelayedConfirmationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "ClimbConfirmation";

    private DelayedConfirmationView mDelayedView;

    private GoogleApiClient mGoogleApiClient;

    private String routeGradeLabelToSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();

        mDelayedView = (DelayedConfirmationView) findViewById(R.id.delayed_confirm);
        mDelayedView.setListener(this);

        routeGradeLabelToSave = getIntent().getStringExtra(ClimbTrackerWear.EXTRA_ROUTE_GRADE_LABEL);

        // change the recap text
        TextView climbRecapText = (TextView) findViewById(R.id.climb_recap);
        String recapString = getString(R.string.climb_recap, routeGradeLabelToSave);
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
            saveClimb();

            String savedString = getString(R.string.climb_saved, routeGradeLabelToSave);

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

    private void saveClimb() {
        // Create a unique identifier for this data item
        PutDataMapRequest putDataMapReq = PutDataMapRequest.create(Path.CLIMB + '/' + UUID.randomUUID());
        putDataMapReq.getDataMap().putString(Path.ROUTE_GRADE_LABEL_KEY, routeGradeLabelToSave);
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected(): Successfully connected to Google API client");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "Connection to Google API client has failed");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


}