package com.trax.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import com.trax.R;
import com.trax.Trax;
import com.trax.errors.AlreadyLaunchedSessionException;
import com.trax.modes.Session;
import com.trax.modes.SessionEnregistrement;
import com.trax.modes.SessionItinerant;
import com.trax.networking.Follower;

public class MainMenu extends ActionBarActivity {
    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        String num = null;

        if(data != null) num = data.getString("num");

        if(num != null){
            /* si on arrive ici par une invitation */
            String itineraire = data.getString("url");
            try{
                if(itineraire != null)
                    new SessionItinerant(itineraire);
                else
                    new SessionEnregistrement();
            }catch(AlreadyLaunchedSessionException e){
                Log.e("TRAX", "WTF ? Session déjà lancée.");
            }
            Session.getInstance().addFollower(Follower.fromNum(num, getContentResolver()));
            startActivity(new Intent(this, MapActivity.class));
        }else {
            setContentView(R.layout.main);
            Log.d("TRAX", "demande de lancement du BeaconReceiver");
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        Session.endInstance();
        Log.w("DTRAX", "Session supprimée dans MainMenu.onResume()");
    }

}
