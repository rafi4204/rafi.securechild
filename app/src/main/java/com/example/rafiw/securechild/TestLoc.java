package com.example.rafiw.securechild;

import android.nfc.Tag;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TestLoc extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FirebaseDatabase database;
    DatabaseReference myRef;
    double longitude;
    double latitude;
    private static final String TAG = TestLoc.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_loc);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

         database = FirebaseDatabase.getInstance();
         myRef = database.getReference().child("Location_Details");



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera


       // Firebase locationRef = mRootRef.child("your reference");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                latitude = dataSnapshot.child("latitude").getValue(Double.class);
                longitude = dataSnapshot.child("longitude").getValue(Double.class);
                Log.d(TAG, "Value is: " + latitude);
               // mMap.addMarker(new MarkerOptions() .position(new LatLng(latitude, longitude)).title("MY LOCATION") .snippet("kochi").draggable(true));

                LatLng sydney = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(sydney).title("current location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


               /* for (DataSnapshot snapm: dataSnapshot.getChildren()) {

                    latitude = snapm.child("latitude").getValue(Double.class);
                    longitude = snapm.child("longitude").getValue(Double.class);
                    Log.d(TAG, "Value issssssssssssssssssssssssss: " + longitude);
                   // mMap.addMarker(new MarkerOptions() .position(new LatLng(23.843327, 90.3776309)).title("MY LOCATION").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)) .snippet("kochi").draggable(true));



                    LatLng sydney = new LatLng(latitude, longitude);
                    mMap.addMarker(new MarkerOptions().position(sydney).title("current location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    //mMap.setMyLocationEnabled(true);

                   /* LatLng newLocation = new LatLng(
                            snapm.child("latitude").getValue(Double.class),
                            snapm.child("longitude").getValue(Double.class)
                    );
                    mMap.addMarker(new MarkerOptions()
                            .position(newLocation)
                            .title("current location"));*/

               // }*/
              //  latitude = dataSnapshot.child("latitude").getValue(Double.class);
                // longitude = snapm.child("longitud").getValue(Double.class);
                //Log.d(TAG, "Value is: " + latitude);

            }
            @Override
            public void onCancelled(DatabaseError error) {
                //throw error.toException();
                Log.w(TAG, "Failed to read value.", error.toException());

            }
        });





    }
}
