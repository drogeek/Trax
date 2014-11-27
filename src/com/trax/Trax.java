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
    public static enum VERB { INVITATION, ANSWER, POSITION, POINTOFINTEREST, CHAT };
    public static String MSG_INVITATION = String.format("%s%s recue de l'application Trax (url) !", MSG_START, VERB.INVITATION);
    public static String MSG_ANSWER = MSG_START + VERB.ANSWER + " %s";
    public static String MSG_ITIN_INVITATION = MSG_INVITATION + " Cliquez ici une fois l'application installée: %s";
    public static String MSG_POSITION = MSG_START + VERB.POSITION + " %d %d %d";
    public static String MSG_POINTOFINTEREST = MSG_START + VERB.POINTOFINTEREST + " %d %d %d";
    public static String MSG_CHAT = MSG_START + VERB.CHAT + "%s";

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
