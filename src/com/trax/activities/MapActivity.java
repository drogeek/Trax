package com.trax.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.trax.R;

/**
 * Created by unautre on 23/11/14.
 */
public class MapActivity extends FragmentActivity implements GoogleMap.OnMapLongClickListener {
    private GoogleMap map;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);
        map = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        map.setMyLocationEnabled(true);
        map.setOnMapLongClickListener(this);

        // nice and easy
        Polyline line = map.addPolyline(new PolylineOptions()
                .add(new LatLng(51.5, -0.1), new LatLng(40.7, -74.0))
                .width(5).color(Color.RED));

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        // Ajoutons un marker là où on longClick
        map.addMarker(new MarkerOptions().position(latLng).title("Hello World").draggable(true));
    }


}