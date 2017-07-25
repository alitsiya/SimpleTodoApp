package com.simpletodo.simpletodo.customAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.simpletodo.simpletodo.R;

import java.util.ArrayList;

public class TodoAdapter extends ArrayAdapter<Todo> {
    public TodoAdapter(Context context, ArrayList<Todo> todoItems) {
        super(context, 0, todoItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Todo todoItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
        }
        // Lookup view for data population
        TextView itemName = (TextView) convertView.findViewById(R.id.item_name);
        // Populate the data into the template view using the data object
        itemName.setText(todoItem.name);
        // Return the completed view to render on screen
        return convertView;
    }
}

