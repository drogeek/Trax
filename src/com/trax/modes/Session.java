package com.trax.modes;

import android.content.Intent;
import android.location.Location;
import android.util.Log;
import com.trax.Trax;
import com.trax.errors.AlreadyLaunchedSessionException;
import com.trax.networking.BeaconTransmitter;
import com.trax.networking.Follower;
import com.trax.networking.PhoneNumber;
import com.trax.tools.ObservableTable;

import java.util.Collection;

/**
 * Created by unautre on 23/11/14.
 */
public abstract class Session {
    protected Session() throws AlreadyLaunchedSessionException {
        setInstance(this);
        Trax.getApplication().startService(new Intent(Trax.getContext(), BeaconTransmitter.class));
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

    public void addPendingFollower(Follower f){
        pendingFollowers.put(f.getPhoneNum(), f);
        f.sendSMS(Trax.MSG_INVITATION);
    }

    public void addFollower(Follower f){
        followers.put(f.getPhoneNum(),f);
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

        followers.get(new PhoneNumber(num)).setLocation(location); //ici le follower est pas trouvé si on cherche avec num
        /* TODO: error handling */
    }

    /* C'est une classe singleton. */
    static private Session instance;
    static public Session getInstance(){ return instance; }
    static public void endInstance(){
        Trax.getApplication().stopService(new Intent(Trax.getContext(), BeaconTransmitter.class));
        instance = null;
    }
    static public void setInstance(Session instance) throws AlreadyLaunchedSessionException {
        if(Session.instance != null) throw new AlreadyLaunchedSessionException();
        Session.instance = instance;
    }

    public void setOwnPosition(Location l){}

    public void removeFollower(Follower f){
        removeFollower(f.getPhoneNum());
    }

    public void removeFollower(String num) {
        removeFollower(new PhoneNumber(num));
    }

    public void removeFollower(PhoneNumber num){
        Follower f;

        f = pendingFollowers.remove(num);
        if(f != null){
            f.sendSMS(Trax.MSG_DELETE);
            f.deleteFollower(); // pour les observeurs du follower

            return;
        }

        f = invitations.remove(num);
        if(f != null){
            f.sendSMS(String.format(Trax.MSG_ANSWER, "no"));
            f.deleteFollower(); // pour les observeurs du follower

            return;
        }

        f = followers.remove(num);
        if(f != null){
            f.sendSMS(Trax.MSG_DELETE);
            f.deleteFollower(); // pour les observeurs du follower
            return;
        }
    }
}
