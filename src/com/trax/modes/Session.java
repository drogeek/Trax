package com.trax.modes;

import android.content.Intent;
import android.location.Location;
import com.trax.Trax;
import com.trax.errors.AlreadyLaunchedSessionException;
import com.trax.networking.BeaconTransmitter;
import com.trax.networking.Follower;
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
        Trax.getApplication().startService(new Intent(Trax.getContext(), BeaconTransmitter.class));
    }

    private ObservableTable<String, Follower> pendingFollowers;
    private ObservableTable<String, Follower> followers;
    private ObservableTable<String, Follower> invitations;

    //Getters
    public Collection<Follower> getFollowerList() {
        return followers.values();
    }
    public Collection<Follower> getPendingFollowerList() {
        return pendingFollowers.values();
    }
    public Collection<Follower> getInvitationsList(){ return invitations.values(); }

    public ObservableTable<String, Follower> getPendingFollowers() {
        return pendingFollowers;
    }
    public ObservableTable<String, Follower> getFollowers() {
        return followers;
    }
    public ObservableTable<String, Follower> getInvitations() {
        return invitations;
    }

    public void addFollower(Follower f){
        pendingFollowers.put(f.getNum(), f);
        f.sendSMS(Trax.MSG_INVITATION);
    }

    public void confirm(String num, String answer){
        Follower f = pendingFollowers.get(num);
        pendingFollowers.remove(num);
        if(answer.equals("yes"))
            followers.put(num, f);
        /* TODO: error handling */
    }

    public void removeFollower(Follower f){
        followers.remove(f.getNum());
        pendingFollowers.remove(f.getNum());
        invitations.remove(f.getNum());
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
}
