package com.trax.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
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
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }
}