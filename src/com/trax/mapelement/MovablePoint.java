package com.trax.mapelement;

import android.location.Location;
import com.google.android.gms.location.LocationListener;

/**
 * Created by unautre on 25/11/14.
 */
public abstract class MovablePoint extends Point implements LocationListener {
    static float delta = 5;

    public abstract void onMovement();

    @Override
    public void onLocationChanged(Location location) {
        if(position.distanceTo(location) < delta){
            position = location;
            onMovement();
        }
    }
}
