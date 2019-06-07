package com.example.todoapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.todoapp.Models.Todos;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Details extends AppCompatActivity {

    private FusedLocationProviderClient client;

    TextView id;
    EditText title;
    EditText description;
    TextView fulllocation;
    RadioGroup rg;
    RadioButton rb;
    RadioButton rbRed;
    RadioButton rbOrange;
    RadioButton rbGreen;
    SQLite mSQLite;

    Double latitude;
    Double longitude;

    Geocoder geocoder;
    List<Address> addresses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Gson gson = new Gson();
        Todos todo = gson.fromJson(getIntent().getStringExtra("myItem"), Todos.class);
        mSQLite = new SQLite(this);

        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);
        geocoder = new Geocoder(this, Locale.getDefault());

        id = findViewById(R.id.textId);
        id.setText(Integer.toString(todo.getId()));
        title = findViewById(R.id.editTaskname2);
        title.setText(todo.getTitle());
        description = findViewById(R.id.editTaskdescription2);
        description.setText(todo.getDescription());
        fulllocation = findViewById(R.id.location2);
        fulllocation.setText(todo.getAddress());

        rg = findViewById(R.id.rgroup2);
        rbRed = findViewById(R.id.high2);
        rbOrange = findViewById(R.id.medium2);
        rbGreen = findViewById(R.id.low2);

        if (rbRed.getText().equals(todo.getPriorty())) {
            rbRed.setChecked(true);
        }
        if (rbOrange.getText().equals(todo.getPriorty())) {
            rbOrange.setChecked(true);
        }
        if (rbGreen.getText().equals(todo.getPriorty())) {
            rbGreen.setChecked(true);
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.picklocation2:
                if (ActivityCompat.checkSelfPermission(Details.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                client.getLastLocation().addOnSuccessListener(Details.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            TextView fullocation = findViewById(R.id.location2);
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            try {
                                addresses = geocoder.getFromLocation(latitude, longitude, 1);

                                String address = addresses.get(0).getAddressLine(0);

                                fullocation.setText(address);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
                break;
            case R.id.buttonDelete:
                deleteUser(Integer.parseInt(id.getText().toString()));
                Intent intent = new Intent(Details.this, MainActivity.class);
                startActivity(intent);
                break;
                case R.id.buttonUpgrade:
                    int radiobuttonid = rg.getCheckedRadioButtonId();
                    rb = findViewById(radiobuttonid);
                    updateData(title.getText().toString(), description.getText().toString(), rb.getText().toString(), fulllocation.getText().toString(), Integer.parseInt(id.getText().toString()));
                    Intent intent2 = new Intent(Details.this, MainActivity.class);
                    startActivity(intent2);
                    break;
        }
    }

    public void updateData(String title, String description, String priority, String address, int id) {

        mSQLite.updateUser(title, description, priority, address, id);

    }


    public void deleteUser(int id) {
        mSQLite.deleteUser(id);
    }
}
