package com.trax.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.*;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import com.trax.R;
import com.trax.modes.Session;
import com.trax.networking.Follower;
import com.trax.tools.ContactAdapter;

/**
 * Created by unautre on 25/11/14.
 */
public class SelectionContacts extends Activity {
    int PICK_CONTACT = 1;
    ListView listView;

//    private static SelectionContacts instance = null;
//
//    private SelectionContacts(){
//        super();
//    }
//
//    public static SelectionContacts getInstance(){
//            if(instance == null) instance = new SelectionContacts();
//            return instance;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection_contacts);

        listView = (ListView)findViewById(R.id.lv_Contact);
        listView.setAdapter(new ContactAdapter(this,Session.getInstance().getPendingFollowers()));
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
            Session.getInstance().addFollower(Follower.fromUri(data.getData(), getContentResolver()));
        //Juste pour tester si la liste grandie
        Log.d("DTRAX",Integer.valueOf(Session.getInstance().getPendingFollowerList().size()).toString());
        Log.d("DTRAX",Integer.valueOf(listView.getAdapter().getCount()).toString());
    }

    public Adapter getAdapterPendingFollower(){
        return listView.getAdapter();
    }
}