package com.trax.mapElement;

import android.graphics.Bitmap;
import android.location.Location;
import android.support.annotation.NonNull;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.*;

/**
 * Created by unautre on 02/12/14.
 */
public class Itineraire implements List<Location> {
    List<Location> self = new ArrayList<Location>();
    private String nom;
    private Bitmap image;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public static Itineraire fromString(String s){
        /* TODO */
        return null;
    }

    public static String serialize(Itineraire itineraire){
        /* TODO */
        return null;
    }

    public PolylineOptions toMap(){
        PolylineOptions ret = new PolylineOptions();
        for(Location l: self){
            ret.add(new LatLng(l.getLatitude(), l.getLongitude()));
        }
        return ret;
    }

    @Override
    public void add(int location, Location object) {
        self.add(location, object);
    }

    @Override
    public boolean add(Location object) {
        return self.add(object);
    }

    @Override
    public boolean addAll(int location, Collection<? extends Location> collection) {
        return self.addAll(location, collection);
    }

    @Override
    public boolean addAll(Collection<? extends Location> collection) {
        return self.addAll(collection);
    }

    @Override
    public void clear() {
        self.clear();
    }

    @Override
    public boolean contains(Object object) {
        return self.contains(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return self.containsAll(collection);
    }

    @Override
    public boolean equals(Object object) {
        return self.equals(object);
    }

    @Override
    public Location get(int location) {
        return self.get(location);
    }

    @Override
    public int hashCode() {
        return self.hashCode();
    }

    @Override
    public int indexOf(Object object) {
        return self.indexOf(object);
    }

    @Override
    public boolean isEmpty() {
        return self.isEmpty();
    }

    @NonNull
    @Override
    public Iterator<Location> iterator() {
        return self.iterator();
    }

    @Override
    public int lastIndexOf(Object object) {
        return self.lastIndexOf(object);
    }

    @NonNull
    @Override
    public ListIterator<Location> listIterator() {
        return self.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<Location> listIterator(int location) {
        return self.listIterator(location);
    }

    @Override
    public Location remove(int location) {
        return self.remove(location);
    }

    @Override
    public boolean remove(Object object) {
        return self.remove(object);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return self.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return self.retainAll(collection);
    }

    @Override
    public Location set(int location, Location object) {
        return self.set(location, object);
    }

    @Override
    public int size() {
        return self.size();
    }

    @NonNull
    @Override
    public List<Location> subList(int start, int end) {
        return self.subList(start, end);
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return self.toArray();
    }

    @NonNull
    @Override
    public <T> T[] toArray(T[] array) {
        return self.toArray(array);
    }
}
