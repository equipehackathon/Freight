package com.example.freight.Freight.Adapter;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.freight.Freight.Model.Person;
import com.example.freight.R;

import java.util.List;

public class PersonListAdapter extends ArrayAdapter<Person> {

    private int resourceLayout;
    private Context mContext;

    public PersonListAdapter(Context context, int resource, List<Person> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        Person p = getItem(position);

        if (p != null) {
            TextView tt1 = v.findViewById(R.id.tv_driver_name);

            if (tt1 != null) {
                tt1.setText(p.getName());
            }
        }

        return v;
    }

}
