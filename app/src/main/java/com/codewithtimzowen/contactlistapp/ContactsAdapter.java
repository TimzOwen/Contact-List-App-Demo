package com.codewithtimzowen.contactlistapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;


import java.util.ArrayList;

public class ContactsAdapter extends ArrayAdapter<Contacts> {

    // variable to hold the container colors
    private final int mColorResourceID;

    // constructor
    public ContactsAdapter(Activity context, ArrayList<Contacts> contacts, int colorResourceID) {
        super(context, 0, contacts);
        this.mColorResourceID = colorResourceID;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // get the current contact
        Contacts currentContact = getItem(position);

        //check if view is inflated or not
        View listItemview = convertView;

        if (listItemview == null) {
            listItemview = LayoutInflater.from(getContext()).inflate(R.layout.contact_list, parent, false);
        }
        TextView tvName = listItemview.findViewById(R.id.tv_name);
        tvName.setText(currentContact.getName());

        TextView tvContact = listItemview.findViewById(R.id.tv_phone);
        tvContact.setText(currentContact.getContact());

        ImageView imageView = listItemview.findViewById(R.id.iv_icon);

        if (currentContact.hasImage()) {
            imageView.setImageResource(currentContact.getmImageResoureID());
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }

        //First find the ID
        View linear_container = listItemview.findViewById(R.id.linear_container);

        // parse in the integer
        int color = ContextCompat.getColor(getContext(), mColorResourceID);

        //set the backgroung color
        linear_container.setBackgroundColor(color);

        return listItemview;
    }
}
