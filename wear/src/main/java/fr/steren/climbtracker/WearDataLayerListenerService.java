package fr.steren.climbtracker;

import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.data.FreezableUtils;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.List;

import fr.steren.climblib.Path;

/**
 * Listens to DataItems
 */
public class WearDataLayerListenerService extends WearableListenerService {

    private static final String TAG = "WearDataLayerListener";

    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.d(TAG, "onDataChanged: " + dataEvents);
        final List<DataEvent> events = FreezableUtils.freezeIterable(dataEvents);
        dataEvents.close();
        for (DataEvent event : events) {
            Log.d(TAG, "Event: " + event.getDataItem().toString());
            Uri uri = event.getDataItem().getUri();
            String path = uri.getPath();

            if (path.equals(Path.GRADE_SYSTEM)) {
                DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());
                String gradeSystem = dataMapItem.getDataMap().getString(Path.GRADE_SYSTEM_KEY);
                if (gradeSystem != null) {
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(Path.PREF_GRAD_SYSTEM_TYPE, gradeSystem);
                    editor.commit();
                }
            }
        }
    }

}
