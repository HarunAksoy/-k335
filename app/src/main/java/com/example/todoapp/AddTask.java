package com.example.todoapp;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class AddTask extends AppCompatActivity {

    SQLite mSQLite = new SQLite(this);
    EditText title;
    EditText description;
    TextView fulllocation;
    TextView id;
    RadioGroup rg;
    RadioButton rb;
    RadioButton rbRed;
    RadioButton rbOrange;
    RadioButton rbGreen;

    private FusedLocationProviderClient client;

    Double latitude;
    Double longitude;

    Geocoder geocoder;
    List<Address> addresses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);
        geocoder = new Geocoder(this, Locale.getDefault());
        mSQLite = new SQLite(this);

    }


    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.picklocation:
                if (ActivityCompat.checkSelfPermission(AddTask.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                client.getLastLocation().addOnSuccessListener(AddTask.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            TextView fullocation = findViewById(R.id.location);
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
            case R.id.buttonAdd:
                title = findViewById(R.id.editTaskname);
                description = findViewById(R.id.editTaskdescription);
                fulllocation = findViewById(R.id.location);
                id = findViewById(R.id.textId);
                rg = findViewById(R.id.rgroup);
                rb = findViewById(rg.getCheckedRadioButtonId());
                if (Validate()) {
                    addData(title.getText().toString(), description.getText().toString(), rb.getText().toString(), fulllocation.getText().toString());
                    Intent intent = new Intent(AddTask.this, MainActivity.class);
                    startActivity(intent);
                    break;
                }

        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }


    public void addData(String title, String description, String priority, String address)
    {
        mSQLite.insert(title, description, priority, address);
    }

    private boolean Validate() {

        title = findViewById(R.id.editTaskname);
        description = findViewById(R.id.editTaskdescription);

        rbRed = findViewById(R.id.high);
        rbOrange = findViewById(R.id.medium);
        rbGreen = findViewById(R.id.low);

        if (!hasText(title)) return false;
        if (!hasText(description)) return false;
        if (!rbRed.isChecked() && !rbOrange.isChecked() && !rbGreen.isChecked()) {
            openDialog();
            return false;
        }


        return true;
    }

    public boolean hasText(EditText editText) {

        String text = editText.getText().toString().trim();

        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError("required field");
            return false;
        }

        return true;
    }

    public void openDialog() {
        DialogAlert exampleDialog = new DialogAlert();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }



}
