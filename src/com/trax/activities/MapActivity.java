package com.trax.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.trax.R;
import com.trax.Trax;
import com.trax.modes.Session;
import com.trax.networking.Follower;
import com.trax.tools.ContactAdapter;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by toor on 10/12/14.
 */
public class MapActivity extends ActionBarActivity implements Observer {

    GoogleMap map;
    Map<Follower,Marker> markers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("DTRAX","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);
        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);
        markers = new ArrayMap<Follower,Marker>();
        for(Follower f : Session.getInstance().getFollowerList())
            addObserver(f);
    }

//Plus besoin, on garde la même instance à chaque fois youpie
//    protected void onResume(){
////        Log.d("DTRAX","onResume appelé");
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(Trax.getLatitude(),Trax.getLongitude()),Trax.getZoom());
//        map.moveCamera(cameraUpdate);
//        super.onResume();
//    }


//    @Override
//    protected void onStop() {
//        Log.d("DTRAX","onStop appelé");
//        Trax.setLatitude(map.getCameraPosition().target.latitude);
//        Trax.setLongitude(map.getCameraPosition().target.longitude);
//        Trax.setZoom(map.getCameraPosition().zoom);
//        super.onStop();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_layout_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_contact:
                openContacts();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openContacts(){
        startActivity(new Intent(this, SelectionContacts.class));
    }

    @Override
    public void onBackPressed() {
        confirmQuit();
    }

    public void confirmQuit(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(R.string.askQuit);

        alertDialog.setNegativeButton("Non merci", null);
        alertDialog.setPositiveButton("Oui, fini de traxer!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Session.endInstance();
                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(intent);
            }
        });

            AlertDialog dialog = alertDialog.create();
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            dialog.show();
    }


    //Ajout d'un marker sur la map correspondant au follower
    public void addObserver(Follower f){
        f.addObserver(this);

    }

    public void moveMarker(Follower f, LatLng loc){
        markers.get(f).setPosition(loc);
    }

    public void rmMarker(Follower f){
        if(markers.containsKey(f))
            markers.remove(f).remove();
    }

    @Override
    public void update(Observable observable, Object data) {
        if(data instanceof Trax.OBS_ACTIONS){
            switch((Trax.OBS_ACTIONS)data){
                case MOVE:
                    if(markers.containsKey((Follower)observable))
                        moveMarker((Follower)observable,((Follower) observable).getLatLng());
                    else
                        markers.put((Follower)observable,map.addMarker(new MarkerOptions()
                                .position(((Follower)observable).getLatLng())
                                .title(((Follower) observable).getName()))
                        );
                    //TODO ajouter la couleur
                break;

                case DELETE:
                    rmMarker((Follower)observable);
                break;

                default:
                    Log.e("DTRAX","wtf?oO");
            }
        }
    }
}
