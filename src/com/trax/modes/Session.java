package com.trax.modes;

import android.content.Intent;
import android.location.Location;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import com.trax.Trax;
import com.trax.errors.AlreadyLaunchedSessionException;
import com.trax.networking.BeaconTransmitter;
import com.trax.networking.Follower;
import com.trax.networking.PhoneNumber;
import com.trax.tools.ObservableTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by unautre on 23/11/14.
 */
public abstract class Session {
    protected Session() throws AlreadyLaunchedSessionException {
        setInstance(this);
        // Trax.getApplication().startService(new Intent(Trax.getContext(), BeaconTransmitter.class));
    }

    private ObservableTable<PhoneNumber, Follower> pendingFollowers = new ObservableTable<PhoneNumber, Follower>();
    private ObservableTable<PhoneNumber, Follower> followers = new ObservableTable<PhoneNumber, Follower>();
    private ObservableTable<PhoneNumber, Follower> invitations = new ObservableTable<PhoneNumber, Follower>();

    //Getters
    public Collection<Follower> getFollowerList() {
        return followers.values();
    }
    public Collection<Follower> getPendingFollowerList() {
        return pendingFollowers.values();
    }
    public Collection<Follower> getInvitationsList(){ return invitations.values(); }

    public ObservableTable<PhoneNumber, Follower> getPendingFollowers() {
        return pendingFollowers;
    }
    public ObservableTable<PhoneNumber, Follower> getFollowers() {
        return followers;
    }
    public ObservableTable<PhoneNumber, Follower> getInvitations() {
        return invitations;
    }

    public void addFollower(Follower f){
        pendingFollowers.put(f.getNum(), f);
        f.sendSMS(Trax.MSG_INVITATION);
    }

    public void confirm(PhoneNumber num, String answer){
        Follower f = pendingFollowers.get(num);
        pendingFollowers.remove(num);

        Log.d("DTRAX", "Confirmation de " + num + " -> " + f);
        for(PhoneNumber key: pendingFollowers.keySet())
            Log.d("DTRAX", "     " + key + " -> " + pendingFollowers.get(key));

        if(answer.equals("yes") && f != null)
            followers.put(num, f);
        /* TODO: error handling */
    }

    public void moveFollower(String num, Location location){
        followers.get(num).setLocation(location);
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

    public void setOwnPosition(Location l){}

    public void removeFollower(Follower f){
        removeFollower(f.getNum());
    }

    public void removeFollower(String num) {
        removeFollower(new PhoneNumber(num));
    }

    public void removeFollower(PhoneNumber num){
        followers.remove(num);
        pendingFollowers.remove(num);
        invitations.remove(num);
    }
}
