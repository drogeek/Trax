package com.trax.activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.trax.networking.Follower;

import java.util.List;

/**
 * Created by toor on 27/11/14.
 */
public class ContactAdapter extends ArrayAdapter<Follower> {
    //constructors
    public ContactAdapter(Context context, int resource) {
        super(context, resource);
    }

    public ContactAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public ContactAdapter(Context context, int resource, Follower[] objects) {
        super(context, resource, objects);
    }

    public ContactAdapter(Context context, int resource, int textViewResourceId, Follower[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public ContactAdapter(Context context, int resource, List<Follower> objects) {
        super(context, resource, objects);
    }

    public ContactAdapter(Context context, int resource, int textViewResourceId, List<Follower> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
