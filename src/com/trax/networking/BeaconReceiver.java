package com.trax.networking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

/**
 * Created by unautre on 23/11/14.
 * Cette classe capte les messages des autres usagers et les traite.
 */
public class BeaconReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data = intent.getExtras();
        if (data == null) return;

        List PDUs = Arrays.asList((Object[]) data.get("pdus"));
        for (Object m : PDUs) {
            SmsMessage message = SmsMessage.createFromPdu((byte[]) m);
            Log.d("TRAX", message.getMessageBody());
        }
    }
}
