package com.trax.mapelement;

import android.location.Location;
import com.google.android.gms.location.LocationListener;

/**
 * Created by unautre on 25/11/14.
 */
public abstract class MovableMarker extends Marker implements LocationListener {
    public abstract void onMovement();

    @Override
    public void onLocationChanged(Location location) {
        if(position.distanceTo(location) < Marker.delta){
            position = location;
            onMovement();
        }
    }
}
