package com.trax.networking;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.location.LocationListener;
import android.util.Log;
import com.trax.Trax;
import com.trax.modes.Session;

/**
 * Created by unautre on 23/11/14.
 * Cette classe transmet les informations GPS aux followers
 */
public class BeaconTransmitter extends Service implements LocationListener {
    LocationManager lm;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("DTRAX", "Service BeaconTransmitter lancé.");
        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, Trax.time_delta, Trax.distance_delta, this);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, Trax.time_delta, Trax.distance_delta, this);

        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location == null)
            location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Session.getInstance().setOwnPosition(location);
        Log.d("DTRAX", "Providers possibles:");
        for(String provider: lm.getAllProviders())
            Log.d("DTRAX", "       " + provider);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("DTRAX","Position changée");
        for(Follower f: Session.getInstance().getFollowerList()){
            f.sendSMS(String.format(Trax.MSG_POSITION,
                    Location.convert(location.getLatitude(), Trax.COORDS_FORMAT),
                    Location.convert(location.getLongitude(), Trax.COORDS_FORMAT),
                    Location.convert(location.getAltitude(), Trax.COORDS_FORMAT)
            ));
        }
        Session.getInstance().setOwnPosition(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("DTRAX", "onStatusChanged called: " + provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("DTRAX", "onProviderEnabled called: " + provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("DTRAX", "onProviderDisabled called: " + provider);
    }
}