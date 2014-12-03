package com.trax.tools;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by unautre on 03/12/14.
 */
public class TableAdapter<K, V> extends ArrayAdapter<V> {
    private List<K> lookup;

    public TableAdapter(Context context, int resource, List<V> objects) {
        super(context, resource, objects);
        this.lookup = new ArrayList<K>();
    }
}
