package fr.steren.climbtracker;

import android.net.Uri;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.data.FreezableUtils;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.List;

/**
 * Listens to DataItems
 */
public class DataLayerListenerService extends WearableListenerService {

    private static final String TAG = "DataLayerListener";

    private static final String CLIMB_PATH = "/climb";
    private static final String ROUTE_LABEL_KEY = "fr.steren.climbtracker.key.routelabel";

    GoogleApiClient mGoogleApiClient;

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

            if (CLIMB_PATH.equals(path)) {
                DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());
                String routeLabel = dataMapItem.getDataMap().getString(ROUTE_LABEL_KEY);
                if (routeLabel != null) {
                    Log.d(TAG, "New Climb, grade : " + routeLabel);
                }
            }
        }
    }

}
