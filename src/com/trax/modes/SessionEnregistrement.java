package com.trax.modes;

import android.location.Location;
import android.util.Log;
import com.trax.errors.AlreadyLaunchedSessionException;

/**
 * Created by unautre on 24/11/14.
 */
public class SessionEnregistrement extends Session {
    public SessionEnregistrement() throws AlreadyLaunchedSessionException {
        super();
    }

    @Override
    public void setOwnPosition(Location l) {
        getItineraire().add(l);
        super.setOwnPosition(l);
        Log.d("DTRAX", "Position changée, devrait changer l'itineraire.");
    }
}
