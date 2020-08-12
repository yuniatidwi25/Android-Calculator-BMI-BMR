package com.example.androidhw1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater li;
    private JSONArray array;

    public ListViewAdapter(Context context, JSONArray array) {
        this.context = context;
        this.array = array;
        this.li = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {

        return array.length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    public String getItem2(int position, String key) throws JSONException {
        JSONObject jsonObject = array.getJSONObject(position);
        String value = jsonObject.optString(key);
        return value;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    public static class ViewHolder
    {
        TextView id;
        TextView name;
        TextView age;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = li.inflate(R.layout.listviewadapter, parent, false);
            holder = new ViewHolder();

            holder.id = (TextView) convertView.findViewById(R.id.id_txt);
            holder.name = (TextView) convertView.findViewById(R.id.name_txt);
            holder.age  = (TextView) convertView.findViewById(R.id.age_txt);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        PersonalInfo personalInfo = null;
        try {
            personalInfo = new PersonalInfo(getItem2(position,"id"),getItem2(position,"name"),getItem2(position,"age"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.id.setText(personalInfo.getId());
        holder.name.setText(personalInfo.getName());
        holder.age.setText(personalInfo.getAge());


        final String finalId = personalInfo.getId();
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PersonalData.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", finalId);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

}
