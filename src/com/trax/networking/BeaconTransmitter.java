package com.trax.networking;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by unautre on 23/11/14.
 * Cette classe transmet les informations GPS aux followers
 */
public class BeaconTransmitter extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
