package fr.steren.climbtracker;

import com.firebase.client.Firebase;

/**
 * Initialize Firebase with the application context. This must happen before the client is used.
 */
public class ClimbTrackerApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}