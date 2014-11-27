package com.trax.networking;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import com.google.android.gms.location.LocationListener;
import com.trax.Trax;
import com.trax.modes.Session;

/**
 * Created by unautre on 23/11/14.
 * Cette classe transmet les informations GPS aux followers
 */
public class BeaconTransmitter extends Service implements LocationListener {
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
}
