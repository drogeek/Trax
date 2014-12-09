package com.trax.tools;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import java.util.*;

/**
 * Created by unautre on 03/12/14.
 */
public abstract class TableAdapter<K, V> extends BaseAdapter implements Observer {
    private List<K> lookup;
    private ObservableTable<K, V> table;

    protected Context context;

    public TableAdapter(ObservableTable<K, V> table){
        this.table = table;
        this.lookup = Arrays.asList((K[])table.keySet().toArray());
        table.addObserver(this);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return lookup.size();
    }

    @Override
    public Object getItem(int position) {
        return getNativeItem(position);
    }

    public V getNativeItem(int position){
        V ret = table.get(lookup.get(position));
        assert ret != null;
        return ret;
    }

    @Override
    public void update(Observable observable, Object data){
        /* on rafraichit la table de correspondance */
        this.lookup = Arrays.asList((K[])table.keySet().toArray());
        notifyDataSetChanged();
    }
}
