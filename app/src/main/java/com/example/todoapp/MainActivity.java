package com.example.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import android.widget.AdapterView;
import android.widget.ListView;


import com.example.todoapp.Adapters.MyAdapter;
import com.example.todoapp.Models.Todos;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {


    SQLite mSQLite;
    ListView mListView;
    ArrayList<Todos> arrayList;
    MyAdapter myAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = findViewById(R.id.listView);
        mSQLite = new SQLite(this);
        arrayList = new ArrayList<>();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Todos todos = arrayList.get(position);
                Gson gson = new Gson();
                String myItem = gson.toJson(todos);
                Intent intent2 = new Intent(MainActivity.this, Details.class);
                intent2.putExtra("myItem", myItem);
                startActivity(intent2);
                //id.setText(todos.getId());
                Log.d("myTodo", todos.getTitle() + " " + todos.getId());
            }
        });
        populateListView();

    }

    public void onClick(View v) {
        Intent intent = new Intent(this, AddTask.class);
        startActivity(intent);
    }


    private void populateListView() {

        arrayList = mSQLite.getAllData();
        myAdapter = new MyAdapter(this, arrayList);
        mListView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

    }
}





