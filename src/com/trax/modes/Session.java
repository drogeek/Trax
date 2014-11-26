package com.trax.modes;

import com.trax.errors.AlreadyLaunchedSessionException;
import com.trax.networking.Follower;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by unautre on 23/11/14.
 */
public abstract class Session {
    protected Session() throws AlreadyLaunchedSessionException {
        followerList = new ArrayList<Follower>();
        setInstance(this);
    }

    private List<Follower> followerList;
    public List<Follower> getFollowerList() {
        return followerList;
    }
    public void addFollower(Follower f){ followerList.add(f); }
    public void removeFollower(Follower f){ followerList.remove(f); }

    /* C'est une classe singleton. */
    static private Session instance;
    static public Session getInstance(){ return instance; }
    static public void endInstance(){ instance = null; }
    static public void setInstance(Session instance) throws AlreadyLaunchedSessionException {
        if(Session.instance != null) throw new AlreadyLaunchedSessionException();
        Session.instance = instance;
    }
}
