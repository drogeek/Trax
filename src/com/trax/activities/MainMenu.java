package com.trax.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.trax.R;
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
    }

    public void show_map(View v){
        startActivity(new Intent(this, MapActivity.class));
    }
}
