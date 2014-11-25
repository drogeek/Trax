package com.trax.networking;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

/**
 * Created by unautre on 24/11/14.
 */
public class Follower {
    private String num, name, couleur;

    public Follower(String num, String name) {
        this.num = num;
        this.name = name;
    }

    public String getCouleur() {
        return couleur;
    }

    /* TODO ! */
    static Follower fromNum(){
        return null;
    }

    /* TODO ! */
    static Follower fromName(){
        return null;
    }

    public String getNum() {
        return num;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void sendSMS(String msg, Context context){
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(Intent.ACTION_SENDTO), 0);
        SmsManager.getDefault().sendTextMessage(num, null, msg, pendingIntent, pendingIntent);
    }
}
