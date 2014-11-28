package com.trax.networking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
import com.trax.Trax;
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
    @Override
    public void onReceive(Context context, Intent intent) {
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
        String msg = message.getMessageBody();

        try {
            /* On parse le message. */
            Scanner sc = new Scanner(msg).useDelimiter(":");

            if (!sc.next().equals(Trax.base_name)) return;

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
                    /* TODO: gérer l'invitation. Afficher une popup ? */
                    Log.d("DTRAX","Invitation reçue de "+num);
                    break;
                case ANSWER:
                    String answer = sc.next();
                    Session.getInstance().confirm(num, answer); break;
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
            return;
        }
    }
}
