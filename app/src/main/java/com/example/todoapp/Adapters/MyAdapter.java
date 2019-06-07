package com.example.todoapp.Adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.todoapp.Models.Todos;
import com.example.todoapp.R;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {


    Context context;
    ArrayList<Todos> arrayList;

    public MyAdapter(Context context, ArrayList<Todos> arrayList) {

        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.listdesign, null);
        TextView title = convertView.findViewById(R.id.textviewTitle);
        ImageView background = convertView.findViewById(R.id.background);


        Todos todos = arrayList.get(position);

        title.setText(todos.getTitle());

        Log.d("myAdapter", todos.getPriorty());


        if (todos.getPriorty().equals("high")) {

            background.setColorFilter(ContextCompat.getColor(context, R.color.colorRed), PorterDuff.Mode.MULTIPLY);
        }

        if (todos.getPriorty().equals("medium")) {


            background.setColorFilter(ContextCompat.getColor(context, R.color.colorOrange), PorterDuff.Mode.MULTIPLY);
        }

        if (todos.getPriorty().equals("low")) {

            background.setColorFilter(ContextCompat.getColor(context, R.color.colorGreen), PorterDuff.Mode.MULTIPLY);
        }



        return convertView;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }
}
