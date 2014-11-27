package com.trax;

import android.app.Application;
import android.content.Context;

/**
 * Created by unautre on 27/11/14.
 */
public class Trax extends Application {
    /* Protocol */
    public static String base_name = "TRAX";
    public static int protocol_version = 1;
    public static String MSG_START = String.format("%s:%d:", base_name, protocol_version);
    public static String BASE_INVITATION = MSG_START + "INVITATION recu de l'application Trax (url) !";
    public static String ITIN_INVITATION = BASE_INVITATION + " Cliquez ici une fois l'application installée: %s";
    public static String POSITION = MSG_START + "POSITION %d %d %d";
    public static String POINTOFINTEREST = MSG_START + "POI %d %d %d";
    public static String MSG_CHAT = "CHAT %s";

    private static Trax instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getContext(){
        return instance;
    }
}
