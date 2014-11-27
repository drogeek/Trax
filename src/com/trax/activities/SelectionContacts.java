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
    static String[] proj = new String[]{
            Profile._ID,
            Profile.DISPLAY_NAME,
            Profile.PHOTO_THUMBNAIL_URI,
            Profile.HAS_PHONE_NUMBER,
    };
    int PICK_CONTACT = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection_contacts);

    }

    public void openContactList(View v){
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }

    //On appelle la google map
    public void openMap(View v){
        new MapActivity();
        startActivity(new Intent(this, MapActivity.class));
    }

    //on récupère les info des contacts
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_CONTACT && resultCode == RESULT_OK){
            Cursor cursor = getContentResolver().query(data.getData(), proj, null, null, null);
            if(cursor.moveToFirst()){
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Session.getInstance().addFollower(Follower.fromName(name));
            }else{
                /* wtf ? */
            }
        }
        else {
            Log.d("ErrorOnActivityResult", "requestCode isn't equal PICK_CONTACT");
        }
    }


}