package com.trax.networking;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;
import com.trax.R;
import com.trax.Trax;
import com.trax.activities.MainMenu;
import com.trax.errors.ParseException;
import com.trax.modes.Session;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by unautre on 23/11/14.
 * Cette classe capte les messages des autres usagers et les traite.
 */
public class BeaconReceiver extends BroadcastReceiver {
    private static BeaconReceiver instance;
    private static Context context;
    public BeaconReceiver() {
        super();
        Log.d("TRAX", "BeaconReceiver créé.");
        instance = this;
    }

    static public BeaconReceiver getInstance() {
        return instance;
    }
    static public Context getContext(){ return context; }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        Bundle data = intent.getExtras();
        if (data == null) return;

        /* TODO:
            lire le message si TRAX
            parser le message
            changer la location de l'user correspondant
         */

        List PDUs = Arrays.asList((Object[]) data.get("pdus"));
        for (Object m : PDUs) {
            parseMessage(SmsMessage.createFromPdu((byte[])m));
        }
    }

    public void parseMessage(SmsMessage message){
        String num = message.getOriginatingAddress();

        /* PRÉSENT POUR LE DEBUG */
        if(num.startsWith("1555")) {
            num = num.substring(7);
            Log.d("DTRAX", "" + message.getOriginatingAddress() + "changé en " + num);
        }

        String msg = message.getMessageBody();

        Log.d("TRAX", "Message recu de "+num);

        try {
            /* On parse le message. */
            Scanner sc = new Scanner(msg).useDelimiter(":");
            String head = sc.next();
            Log.d("TRAX", "Header: " + head);
            if (!head.equals(Trax.base_name)) return;
            Log.d("TRAX", "C'est un message TRAX !");

            /* le message est bien pour nous, on l'intercepte. */
            abortBroadcast();

            if (!sc.next().equals(Integer.toString(Trax.protocol_version)))
                throw new ParseException("protocol version mismatch.");

            //sc.reset(); // API level 9 only
            sc.useDelimiter(" ");
            try { sc.skip(":"); }
            catch (NoSuchElementException e){
                throw new ParseException("parse error: ':' excepted.");
            }

            Trax.VERB verb;
            String str_verb = sc.next();
            try{ verb = Trax.VERB.valueOf(str_verb); }
            catch(IllegalArgumentException e){ throw new ParseException("Unknown verb " + str_verb); }

            switch(verb){
                case INVITATION:
                    Log.d("DTRAX","Invitation reçue de "+num);
                    Follower f = Follower.fromNum(num, getContext().getContentResolver());
                    int beginURL = msg.indexOf("trax://");
                    String URL = null;
                    if(beginURL != -1){
                        int endURL = msg.indexOf(" ", beginURL);
                        URL = (endURL != -1) ?
                                msg.substring(beginURL, endURL) :
                                msg.substring(beginURL);
                    }
                    Log.d("TRAX", "Invitation de " + num + " avec itineraire: " + URL);
                    Trax.getApplication().show_invitation(f, URL);
                    break;
                case ANSWER:
                    String answer = sc.next();
                    Log.d("DTRAX", String.format("Réponse recue de %s: %s", num, answer));
                    Session.getInstance().confirm(new PhoneNumber(num), answer);
                    break;
                case POSITION:
                    Location location = new Location("unknown"); // provider
                    location.setLatitude(Location.convert(sc.next()));
                    location.setLongitude(Location.convert(sc.next()));
                    location.setAltitude(Location.convert(sc.next()));
                    Session.getInstance().moveFollower(num, location);
                    break;
                case POINTOFINTEREST:
                    /* TODO: rajouter un WayPoint/POI */
                    break;
                case CHAT:
                    /* TODO: afficher le message chat */
                    break;
                default:
                    throw new ParseException("Unknown verb " + str_verb);
            }
        }catch(ParseException e){
            Log.e("TRAX", "Parse Error: " + e.toString());
        }
    }
}
