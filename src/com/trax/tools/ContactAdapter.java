package com.trax.tools;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.trax.R;
import com.trax.modes.Session;
import com.trax.networking.Follower;
import com.trax.networking.PhoneNumber;

/**
 * Created by toor on 27/11/14.
 */
public class ContactAdapter extends TableAdapter<PhoneNumber, Follower> {
    private Context context;

    public ContactAdapter(Context context, ObservableTable<PhoneNumber, Follower> table) {
        super(table);
        this.context = context;
        assert Session.getInstance() != null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final Follower currentFollower = getNativeItem(position);

        View view = inflater.inflate(R.layout.row_contact, parent, false);
        ImageView imageView = (ImageView)view.findViewById(R.id.img_Contact);
        TextView textView = (TextView)view.findViewById(R.id.t_Contact);
        ImageButton imageButton = (ImageButton)view.findViewById(R.id.b_Contact);

        //On ajoute une image de la liste des contacts, ou alors une image par default
        String imageUri = currentFollower.getPicture();
        if( imageUri != null)
            imageView.setImageURI(Uri.parse(currentFollower.getPicture()));
        else
            imageView.setImageResource(R.drawable.anonymous);

        textView.setText(currentFollower.getName());

        //bouton suppression
        imageButton.setImageResource(R.drawable.ic_close);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Session.getInstance().removeFollower(currentFollower);
            }
        });
        return view;
    }


}