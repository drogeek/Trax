package com.trax.networking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
import com.trax.Trax;
import com.trax.errors.ParseException;

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
        /* for (Object m : PDUs) {
            SmsMessage message = SmsMessage.createFromPdu((byte[]) m);
        } */
        SmsMessage message = SmsMessage.createFromPdu((byte[])PDUs.get(0));
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

            String verb = sc.next();
            /* h(verb){
                case "RESPONSE"
            } */
        }catch(ParseException e){
            return;
        }
    }
}
