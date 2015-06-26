package fr.steren.climbtracker;

import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.data.FreezableUtils;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.Date;
import java.util.List;

import fr.steren.climblib.GradeList;
import fr.steren.climblib.Path;

/**
 * Listens to DataItems
 */
public class MobileDataLayerListenerService extends WearableListenerService {

    private static final String TAG = "MobileDataLayerListener";

    private GoogleApiClient mGoogleApiClient;

    private Firebase mFirebaseRef;

    @Override
    public void onCreate() {
        super.onCreate();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
        mGoogleApiClient.connect();

        mFirebaseRef = new Firebase(getResources().getString(R.string.firebase_url));
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.d(TAG, "onDataChanged: " + dataEvents);
        final List<DataEvent> events = FreezableUtils.freezeIterable(dataEvents);
        dataEvents.close();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String gradeSystemType = sharedPref.getString(Path.PREF_GRAD_SYSTEM_TYPE, GradeList.SYSTEM_DEFAULT);

        for (DataEvent event : events) {
            Log.d(TAG, "Event: " + event.getDataItem().toString());
            Uri uri = event.getDataItem().getUri();
            String path = uri.getPath();

            if (path.startsWith(Path.CLIMB)) {
                DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());
                String routeGradeLabel = dataMapItem.getDataMap().getString(Path.ROUTE_GRADE_LABEL_KEY);
                if (routeGradeLabel != null) {
                    Log.d(TAG, "New Climb, grade : " + routeGradeLabel);

                    AuthData authData = mFirebaseRef.getAuth();
                    if (authData != null) {
                        Climb newClimb = new Climb(new Date(), routeGradeLabel, gradeSystemType);
                        mFirebaseRef.child("users")
                                .child(authData.getUid())
                                .child("climbs")
                                .push().setValue(newClimb);
                    }
                }
            }
        }
    }

}
