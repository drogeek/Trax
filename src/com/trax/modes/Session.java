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
    protected static String BASE_INVITATION = "TRAX:1:INVITATION recu de l'application Trax (url) !";

    protected Session() throws AlreadyLaunchedSessionException {
        setInstance(this);
    }

    private List<Follower> pendingFollowerList = new ArrayList<Follower>();
    private List<Follower> followerList = new ArrayList<Follower>();

    public void addFollower(Follower f){
        pendingFollowerList.add(f);
        f.sendSMS(BASE_INVITATION);
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
