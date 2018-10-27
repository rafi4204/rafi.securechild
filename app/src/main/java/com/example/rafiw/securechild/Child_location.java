package com.example.rafiw.securechild;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Child_location extends AppCompatActivity {

    //String timer[]={"Select time","5 sec","10 sec","15 sec","20 sec","30 sec"};
    //String tim;
    //Button mLocationBtn;
    TextView mText;
    GPS_Service gps;

    //Firebase Work
    DatabaseReference mDatabaseLocationDetails;
    public static final int REQUEST_LOCATION_CODE = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_location);



        mText = (TextView)findViewById(R.id.location_tv);
       // Spinner mSpinTime= (Spinner) findViewById(R.id.spinner_time);
        //mLocationBtn= (Button) findViewById(R.id.location_btn);
        mDatabaseLocationDetails = FirebaseDatabase.getInstance().getReference().child("Location_Details");

//      permission check
       if(!runtime_permission())
            enable_button();
        runtime_permission();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
       {
            CheckLocationPermission();
        }



       // enable_button();
       /* mSpinTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                tim= adapterView.getItemAtPosition(i).toString();
                if(tim.equals("Select time")){
                    Toast.makeText(Child_location.this, "Please Select time!", Toast.LENGTH_SHORT).show();
                }
                if(tim=="5 sec"){
                    tim= String.valueOf(tim.charAt(0));
                    Toast.makeText(Child_location.this, tim+"", Toast.LENGTH_SHORT).show();
                }
                if(tim=="10 sec"){
                    tim= tim.substring(0,2);
                    Toast.makeText(Child_location.this, tim+"", Toast.LENGTH_SHORT).show();
                }if(tim=="15 sec"){
                    tim= tim.substring(0,2);
                    Toast.makeText(Child_location.this, tim+"", Toast.LENGTH_SHORT).show();
                }if(tim=="20 sec"){
                    tim= tim.substring(0,2);
                    Toast.makeText(Child_location.this, tim+"", Toast.LENGTH_SHORT).show();
                }if(tim=="30 sec"){
                    tim= tim.substring(0,2);
                    Toast.makeText(Child_location.this, tim+"", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                tim= String.valueOf(0);
            }
        });

        ArrayAdapter arrayAdapterCity = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,timer);
        arrayAdapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinTime.setAdapter(arrayAdapterCity);*/
    }

    private void enable_button() {

       /* mLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gps = new GPS_Service(Child_location.this,tim);
                startService(new Intent(Child_location.this,GPS_Service.class));

                if(gps.canGetLocation()){
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    storeInDatabase(latitude,longitude);
                    mText.setText(latitude+" ::: "+longitude);
                    Toast.makeText(Child_location.this, latitude+" ::: "+ longitude, Toast.LENGTH_SHORT).show();
                }else{
                    gps.showSettingsAlert();
                }
            }
        });*/
        gps = new GPS_Service(Child_location.this,"5");
        startService(new Intent(Child_location.this,GPS_Service.class));


        if(gps.canGetLocation()){
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            storeInDatabase(latitude,longitude);
            mText.setText(latitude+" ::: "+longitude);
            Toast.makeText(Child_location.this, latitude+" ::: "+ longitude, Toast.LENGTH_SHORT).show();
        }else{
            gps.showSettingsAlert();
        }


    }

    private void storeInDatabase(double latitude, double longitude) {


        mDatabaseLocationDetails. child("longitude").setValue(longitude);
        mDatabaseLocationDetails.child("latitude").setValue(latitude);
    }

    private boolean runtime_permission() {
        if(Build.VERSION.SDK_INT>=23 && ContextCompat.checkSelfPermission(Child_location.this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED&& ContextCompat.checkSelfPermission(Child_location.this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},123);
            return true;
        }
        return false;
    }

    public boolean CheckLocationPermission(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_CODE);

            }
            // LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
            return false;

        }
        else
            return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if(grantResults.length >0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                    {
                        enable_button();
                    }

                }
                else
                {
                    Toast.makeText(this,"permission Denied!", Toast.LENGTH_LONG).show();
                }
                return;
        }
    }
}