package com.trax.mapElement;

import android.location.Location;

/**
 * Created by unautre on 25/11/14.
 */
public abstract class Point {
    Location position;

    public Location getPosition() {
        return position;
    }
    public void setPosition(Location position) {
        this.position = position;
    }
}
