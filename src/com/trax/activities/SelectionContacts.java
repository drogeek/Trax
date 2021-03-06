package com.trax.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.*;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.trax.R;
import com.trax.Trax;
import com.trax.modes.Session;
import com.trax.networking.Follower;
import com.trax.networking.PhoneNumber;
import com.trax.tools.ContactAdapter;
import com.trax.tools.ObservableTable;

/**
 * Created by unautre on 25/11/14.
 */
public class SelectionContacts extends ActionBarActivity {
    int PICK_CONTACT = 1;
    ListView lvPendingFollower, lvFollower, lvToAccept;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection_contacts);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);

        Log.d("DTRAX", "Session: " + Session.getInstance());

        lvPendingFollower = (ListView)findViewById(R.id.lv_PendingFollower);
        Log.d("DTRAX", "Session:" + Session.getInstance());
        ObservableTable<PhoneNumber, Follower> pending = Session.getInstance().getPendingFollowers();
        ContactAdapter adapterPendingFollower = new ContactAdapter(this, pending);
        lvPendingFollower.setAdapter(adapterPendingFollower);

        lvFollower = (ListView)findViewById(R.id.lv_Follower);
        ObservableTable<PhoneNumber, Follower> follower = Session.getInstance().getFollowers();
        ContactAdapter adapterFollower = new ContactAdapter(this, follower);
        lvFollower.setAdapter(adapterFollower);


    }

    public void openContactList(View v){
        Intent intent = new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);
        intent.setType(CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, PICK_CONTACT);
    }

    //On appelle la google map
    public void openMap(View v){
        startActivity(new Intent(this, MapActivity.class));
    }

    //on récupère les info des contacts
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_CONTACT && resultCode == RESULT_OK)
            Session.getInstance().addPendingFollower(Follower.fromUri(data.getData(), getContentResolver()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_contactList:
                openContactList(getWindow().getDecorView().findViewById(android.R.id.content));
                return true;
            case R.id.action_map:
                openMap(getWindow().getDecorView().findViewById(android.R.id.content));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contact_layout_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

}