package com.trax.modes;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import com.trax.Trax;
import com.trax.errors.AlreadyLaunchedSessionException;
import com.trax.networking.BeaconReceiver;
import com.trax.networking.BeaconTransmitter;
import com.trax.networking.Follower;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by unautre on 23/11/14.
 */
public abstract class Session {
    protected Session() throws AlreadyLaunchedSessionException {
        setInstance(this);
        Trax.getApplication().startService(new Intent(Trax.getContext(), BeaconTransmitter.class));
    }

    private List<Follower> pendingFollowerList = new ArrayList<Follower>();
    private List<Follower> followerList = new ArrayList<Follower>();

    //Getters
    public List<Follower> getFollowerList() {
        return followerList;
    }

    public void addFollower(Follower f){
        pendingFollowerList.add(f);
        f.sendSMS(Trax.MSG_INVITATION);
    }
    public void confirm(String num, String answer){
        for(Follower f: pendingFollowerList){
            if(f.getNum().equals(num)){
                pendingFollowerList.remove(f);
                if(answer.equals("yes"))
                    followerList.add(f);
                /* TODO: prévenir la vue/l'user */
                return;
            }
        }
        /* tiens, on a recu une confirmation bizarre ? TODO: error handling */
    }

    public void removeFollower(Follower f){
        followerList.remove(f);
        pendingFollowerList.remove(f);
    }

    public void moveFollower(String num, Location location){
        for(Follower f: followerList) {
            if(f.getNum().equals(num)){
                f.setLocation(location);
                return;
            }
        }
        /* TODO: error handling */
    }

    /* C'est une classe singleton. */
    static private Session instance;
    static public Session getInstance(){ return instance; }
    static public void endInstance(){ instance = null; }
    static public void setInstance(Session instance) throws AlreadyLaunchedSessionException {
        if(Session.instance != null) throw new AlreadyLaunchedSessionException();
        Session.instance = instance;
    }
}
