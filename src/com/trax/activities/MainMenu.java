package com.trax.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.trax.R;
import com.trax.errors.AlreadyLaunchedSessionException;
import com.trax.modes.Session;
import com.trax.modes.SessionEnregistrement;
import com.trax.modes.SessionItinerant;
import com.trax.networking.BeaconReceiver;

public class MainMenu extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        startService(new Intent(this, BeaconReceiver.class));
        Session.endInstance();
    }

    public void launch_record(View v){
        /* TODO ! */
        try {
            new SessionEnregistrement();
            startActivity(new Intent(this, SelectionContacts.class));
        } catch (AlreadyLaunchedSessionException e) {
            Log.e("DTRAX", e.toString());
        }
    }

    public void launch_pathway(View v){
        /* TODO ! */
        try {
            new SessionItinerant();
            startActivity(new Intent(this, SelectionItineraire.class));
        } catch (AlreadyLaunchedSessionException e) {
            Log.e("DTRAX", e.toString());
        }
    }
}
