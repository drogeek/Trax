package com.trax.tools;

import android.support.annotation.NonNull;

import java.util.*;

/**
 * Created by unautre on 03/12/14.
 */
public class ObservableTable<K, V> extends Observable implements Map<K, V> {
    private Map<K, V> self;

    public ObservableTable(){
        self = new HashMap<K, V>();
    }

    public ObservableTable(Map<K, V> self){
        this.self = self;
    }

    @Override
    public void clear() {
        self.clear();
        notifyObservers();
    }

    @Override
    public boolean containsKey(Object key) {
        return self.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return self.containsValue(value);
    }

    @NonNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        return self.entrySet();
    }

    @Override
    public boolean equals(Object object) {
        return self.equals(object);
    }

    @Override
    public V get(Object key) {
        return self.get(key);
    }

    @Override
    public int hashCode() {
        return self.hashCode();
    }

    @Override
    public boolean isEmpty() {
        return self.isEmpty();
    }

    @NonNull
    @Override
    public Set<K> keySet() {
        return self.keySet();
    }

    public V put(K key, V value) {
        notifyObservers();
        return self.put(key, value);
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        notifyObservers();
        self.putAll(map);
    }

    @Override
    public V remove(Object key) {
        notifyObservers();
        return self.remove(key);
    }

    @Override
    public int size() {
        return self.size();
    }

    @NonNull
    @Override
    public Collection<V> values() {
        return self.values();
    }
}
