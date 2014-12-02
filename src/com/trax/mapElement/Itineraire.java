package com.trax.mapElement;

import android.location.Location;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by unautre on 02/12/14.
 */
public class Itineraire {
    List<Location> self = new ArrayList<Location>();

    public static Itineraire fromString(String s){
        /* TODO */
        return null;
    }

    public static String serialize(Itineraire itineraire){
        /* TODO */
        return null;
    }

    public PolylineOptions toMap(){
        PolylineOptions ret = new PolylineOptions();
        for(Location l: self){
            ret.add(new LatLng(l.getLatitude(), l.getLongitude()));
        }
        return ret;
    }
}
