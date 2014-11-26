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
    private Session instance;
    public void endInstance(){ this.instance = null; }
    public void setInstance(Session instance) throws AlreadyLaunchedSessionException {
        if(this.instance != null) throw new AlreadyLaunchedSessionException();
        this.instance = instance;
    }
}
