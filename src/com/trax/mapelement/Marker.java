package com.trax.mapelement;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by unautre on 25/11/14.
 */
public class Marker {
    LatLng position;

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }
}
