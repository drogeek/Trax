package com.trax.networking;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.location.LocationListener;
import com.trax.Trax;
import com.trax.modes.Session;

/**
 * Created by unautre on 23/11/14.
 * Cette classe transmet les informations GPS aux followers
 */
public class BeaconTransmitter extends Service implements LocationListener {
    @Override
    public void onCreate() {
        super.onCreate();
        LocationManager lm = (LocationManager)Trax.getApplication().getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, Trax.time_delta, Trax.distance_delta, this);
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
        for(Follower f: Session.getInstance().getFollowerList()){
            f.sendSMS(String.format(Trax.MSG_POSITION,
                    Location.convert(location.getLatitude(), Trax.COORDS_FORMAT),
                    Location.convert(location.getLongitude(), Trax.COORDS_FORMAT),
                    Location.convert(location.getAltitude(), Trax.COORDS_FORMAT)
            ));
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
