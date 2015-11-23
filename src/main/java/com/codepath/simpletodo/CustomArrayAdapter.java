package com.codepath.simpletodo;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geapen on 11/20/15.
 */
public class CustomArrayAdapter extends ArrayAdapter<TodoItem> {

    private final Context context;
    private final ArrayList<TodoItem> values;

    public CustomArrayAdapter(Context context, @NonNull ArrayList<TodoItem> todoItems) {
        super(context, android.R.layout.simple_list_item_1, todoItems);
        this.context = context;
        this.values = todoItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(android.R.layout.simple_list_item_1, null);
        }

        TodoItem item = (TodoItem) getItem(position);
        if (item != null) {
            TextView itemView = (TextView) view.findViewById(android.R.id.text1);
            if (itemView != null) {
                itemView.setText(item.name);
            }
        }
        return view;
    }
}
