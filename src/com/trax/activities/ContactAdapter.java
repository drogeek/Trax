package com.trax.activities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.trax.R;
import com.trax.networking.Follower;

import java.util.List;

/**
 * Created by toor on 27/11/14.
 */
public class ContactAdapter extends ArrayAdapter<Follower> {

    private Context context;
    private List<Follower> followers;

    public ContactAdapter(Context context, List<Follower> followers) {
        super(context, R.layout.row_contact);
        this.context = context;
        this.followers = followers;
        Log.d("DTRAX","Entered ContactAdapter");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Follower currentFollower = getItem(position);
        Log.d("DTRAX", "Entered getView from ContactAdapter");

        View view = inflater.inflate(R.layout.row_contact, parent, false);
        ImageView imageView = (ImageView)view.findViewById(R.id.img_Contact);
        TextView textView = (TextView)view.findViewById(R.id.t_Contact);
        ImageButton imageButton = (ImageButton)view.findViewById(R.id.b_Contact);
        imageView.setImageURI(Uri.parse(currentFollower.getPicture()));
        textView.setText(currentFollower.getName());
        //imageButton à ajouter
        return view;
    }

    @Override
    public int getCount() {
        return followers.size();
    }

    @Override
    public Follower getItem(int position) {
        return followers.get(position);
    }
}
