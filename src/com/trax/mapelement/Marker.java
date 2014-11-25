package com.trax.mapelement;

import android.location.Location;

/**
 * Created by unautre on 25/11/14.
 */
public class Marker {
    static float delta = 5;

    Location position;

    public Location getPosition() {
        return position;
    }
    public void setPosition(Location position) {
        this.position = position;
    }
}
