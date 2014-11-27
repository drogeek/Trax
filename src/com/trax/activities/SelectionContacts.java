package com.trax.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.*;
import android.util.Log;
import android.view.View;
import com.trax.R;
import com.trax.modes.Session;
import com.trax.networking.Follower;

/**
 * Created by unautre on 25/11/14.
 */
public class SelectionContacts extends Activity {
    int PICK_CONTACT = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection_contacts);
    }

    public void openContactList(View v){
        Intent intent = new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);
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
    }
}