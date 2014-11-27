package com.trax.networking;

import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.SmsManager;
import android.util.Log;
import com.trax.Trax;

/**
 * Created by unautre on 24/11/14.
 */
public class Follower {
    static String[] contact_projection = new String[]{
        Contacts.DISPLAY_NAME,
        Contacts.PHOTO_THUMBNAIL_URI,
        Phone.NUMBER
    };

    private String num, name, couleur, picture;

    private Follower(){}
    public Follower(String num, String name) {
        this.num = num;
        this.name = name;
        /* TODO: gestion des couleurs */
    }

    //Getters
    public String getCouleur() {
        return couleur;
    }
    public String getNum() {
        return num;
    }
    public String getName() {
        return name;
    }
    public String getPicture() {
        return picture;
    }

    public void sendSMS(String msg){
        Context context = Trax.getContext();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(Intent.ACTION_SENDTO), 0);
        SmsManager.getDefault().sendTextMessage(num, null, msg, pendingIntent, pendingIntent);
    }

    /* Factories */
    static public Follower fromNum(String num){
        return null;
    }

    static public Follower fromName(String name){
        return null;
    }

    public static Follower fromUri(Uri data, ContentResolver contentResolver){
        Follower follower = new Follower();

        Cursor cursor = contentResolver.query(data, contact_projection, null, null, null);

        if(!cursor.moveToFirst()) return null;

        follower.picture = cursor.getString(cursor.getColumnIndex(Contacts.PHOTO_THUMBNAIL_URI));
        follower.name = cursor.getString(cursor.getColumnIndex(Contacts.DISPLAY_NAME));
        follower.num = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
        /* TODO: chopper le numéro de téléphone portable uniquement */
        Log.d("DTRAX", String.format("Contact trouvé: %s, %s", follower.name, follower.num));

        return follower;
    }
}
