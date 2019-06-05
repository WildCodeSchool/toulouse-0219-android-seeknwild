package fr.wildcodeschool.seeknwild;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        Boolean getting_closer = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, false);

    }
}
