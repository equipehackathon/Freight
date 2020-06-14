package com.example.freight.Freight.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import com.example.freight.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONAdapter extends BaseAdapter implements ListAdapter {
    private final Activity activity;
    private final JSONArray jsonArray;
    public JSONAdapter(Activity activity, JSONArray jsonArray) {
        assert activity != null;
        assert jsonArray != null;

        this.jsonArray = jsonArray;
        this.activity = activity;
    }


    @Override public int getCount() {

        return jsonArray.length();
    }

    @Override public JSONObject getItem(int position) {

        return jsonArray.optJSONObject(position);
    }

    @Override public long getItemId(int position) {
        JSONObject jsonObject = getItem(position);

        return jsonObject.optLong("id");
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = activity.getLayoutInflater().inflate(R.layout.driver_row, null);

        JSONObject jsonObject = getItem(position);

        return convertView;
    }
}
