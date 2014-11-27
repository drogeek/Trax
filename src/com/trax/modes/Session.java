package com.trax.modes;

import com.trax.Trax;
import com.trax.errors.AlreadyLaunchedSessionException;
import com.trax.networking.Follower;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by unautre on 23/11/14.
 */
public abstract class Session {
    protected Session() throws AlreadyLaunchedSessionException {
        setInstance(this);
    }

    private List<Follower> pendingFollowerList = new ArrayList<Follower>();
    private List<Follower> followerList = new ArrayList<Follower>();

    public void addFollower(Follower f){
        pendingFollowerList.add(f);
        f.sendSMS(Trax.MSG_INVITATION);
    }
    public void confirm(String num){
        for(Follower f: pendingFollowerList){
            if(f.getNum().equals(num)){
                followerList.add(f);
                pendingFollowerList.remove(f);
                /* TODO: prévenir la vue */
                return;
            }
        }
        /* tiens, on a recu une confirmation bizarre ? TODO: error handling */
    }

    public void removeFollower(Follower f){
        followerList.remove(f);
        pendingFollowerList.remove(f);
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
