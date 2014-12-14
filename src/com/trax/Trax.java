package com.trax;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.WindowManager;
import com.trax.activities.MainMenu;
import com.trax.activities.MapActivity;
import com.trax.modes.Session;
import com.trax.networking.BeaconReceiver;
import com.trax.networking.Follower;

/**
 * Created by unautre on 27/11/14.
 */
public class Trax extends Application {
    /* Protocol */
    public static String base_name = "TRAX";
    public static int protocol_version = 1;
    public static String MSG_START = String.format("%s:%d:", base_name, protocol_version);
    public static enum VERB { INVITATION, ANSWER, POSITION, POINTOFINTEREST, CHAT, DELETE };
    public static enum OBS_ACTIONS { MOVE, DELETE };
    public static String MSG_INVITATION = String.format("%s%s recue de l'application Trax (url) !", MSG_START, VERB.INVITATION);
    public static String MSG_ANSWER = MSG_START + VERB.ANSWER + " %s";
    public static String MSG_ITIN_INVITATION = MSG_INVITATION + " Cliquez ici une fois l'application installée: %s";
    public static String MSG_POSITION = MSG_START + VERB.POSITION + " %s %s %s";
    public static String MSG_POINTOFINTEREST = MSG_START + VERB.POINTOFINTEREST + " %d %d %d";
    public static String MSG_CHAT = MSG_START + VERB.CHAT + "%s";
    public static String MSG_DELETE = MSG_START + VERB.DELETE;
    public static int COORDS_FORMAT = Location.FORMAT_SECONDS;

    public static long time_delta = 1000;//*60*5; // 5 minutes
    public static long distance_delta = 0; // 20 metres

    //Map
    public static double latitude, longitude;
    public static float zoom;

    public static float getZoom() {
        return zoom;
    }

    public static void setZoom(float zoom) {
        Trax.zoom = zoom;
    }

    public static double getLongitude() {
        return longitude;
    }

    public static void setLongitude(double longitude) {
        Trax.longitude = longitude;
    }

    public static double getLatitude() {
        return latitude;
    }

    public static void setLatitude(double latitude) {
        Trax.latitude = latitude;
    }

    private static Trax instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        startService(new Intent(this, BeaconReceiver.class));
    }

    public static Context getContext(){
        return instance;
    }
    public static Trax getApplication(){ return instance; }

    public static enum INVITATION_CHOICE_ENUM {PopupDialog, Notification};
    public static INVITATION_CHOICE_ENUM INVITATION_CHOICE = INVITATION_CHOICE_ENUM.PopupDialog;

    public void show_invitation(final Follower f, @Nullable String url){
        switch(INVITATION_CHOICE) {
            case PopupDialog:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(BeaconReceiver.getContext());
                dialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
                dialogBuilder.setMessage(String.format("Invitation Trax recue de %s (%s)", f.getName(), f.getPhoneNum().getNum()));
                dialogBuilder.setPositiveButton(R.string.accept_invitation, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setClass(BeaconReceiver.getContext(), MainMenu.class);
                                intent.putExtra("num", f.getPhoneNum().toString());
                                /* TODO: si l'invitation contient un itineraire, l'ajouter dans l'intent. */
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Session.endInstance();
                                BeaconReceiver.getContext().startActivity(intent);


                                //on envoie une réponse oui
                                Log.d("DTRAX", "Réponse envoyée " + String.format(Trax.MSG_ANSWER,"yes"));
                                f.sendSMS(String.format(Trax.MSG_ANSWER,"yes"));

                            }
                        }
                );
                dialogBuilder.setNegativeButton(R.string.refuse_invitation, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //envoie réponse non
                        f.sendSMS(String.format(Trax.MSG_ANSWER, "no"));
                        Log.d("DTRAX", "Réponse envoyée " + String.format(Trax.MSG_ANSWER,"no"));
                    }
                });
                AlertDialog dialog = dialogBuilder.create();
                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                dialog.show();
                break;
            case Notification:
                /* TODO */
                break;
        }
    }


}
