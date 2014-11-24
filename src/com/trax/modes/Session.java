package com.trax.modes;

import com.trax.errors.AlreadyLaunchedSessionException;
import com.trax.networking.Follower;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by unautre on 23/11/14.
 */
public abstract class Session {
    private List<Follower> followerList;

    protected Session(){
        followerList = new ArrayList<Follower>();
    }

    public List<Follower> getFollowerList() {
        return followerList;
    }

    /* C'est une classe singleton. */
    private Session instance;
    protected void endInstance(){ this.instance = null; }
    protected void setInstance(Session instance) throws AlreadyLaunchedSessionException {
        if(this.instance != null) throw new AlreadyLaunchedSessionException();
        this.instance = instance;
    }
}
