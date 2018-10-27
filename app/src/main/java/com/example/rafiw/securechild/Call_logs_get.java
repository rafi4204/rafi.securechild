package com.example.rafiw.securechild;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Call_logs_get extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    private static final String TAG = Call_logs_get.class.getSimpleName();

    //DatabaseReference mDatabaseLocationDetails;
    ArrayList<String> smsMessagesList = new ArrayList<>();
    ListView messages;
    ArrayAdapter arrayAdapter;
    EditText input;
    SmsManager smsManager = SmsManager.getDefault();
    private static Call_logs_get inst;

    private static final int READ_SMS_PERMISSIONS_REQUEST = 1;

    public static Call_logs_get instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_logs_get);

        // mDatabaseLocationDetails = FirebaseDatabase.getInstance().getReference().child("sms_Details").push();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("call_Details");

        messages = (ListView) findViewById(R.id.messages);
        // input = (EditText) findViewById(R.id.input);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, smsMessagesList);
        messages.setAdapter(arrayAdapter);

            refreshSmsInbox();



    }





    public void refreshSmsInbox() {
       /* ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        arrayAdapter.clear();
        do {
            String str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
                    "\n" + smsInboxCursor.getString(indexBody) + "\n";
            arrayAdapter.add(str);
            storeInDatabase(str);
            //    mDatabaseLocationDetails.child("sms").setValue(str);
        } while (smsInboxCursor.moveToNext());*/
//messages.setSelection(arrayAdapter.getCount() - 1);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                /*latitude = dataSnapshot.child("Location_Details").child("latitude").getValue(Double.class);
                longitude = dataSnapshot.child("Location_Details").child("longitude").getValue(Double.class);
                Log.d(TAG, "Value is: " + latitude);
                mMap.addMarker(new MarkerOptions() .position(new LatLng(latitude, longitude)).title("MY LOCATION") .snippet("kochi").draggable(true));*/

                for (DataSnapshot snapm: dataSnapshot.getChildren()) {

                    //latitude = snapm.child("latitude").getValue(Double.class);
                    //longitude = snapm.child("longitude").getValue(Double.class);
                    String str = snapm.child("call logs").getValue(String.class);
                    arrayAdapter.add(str);
                    //mMap.setMyLocationEnabled(true);

                   /* LatLng newLocation = new LatLng(
                            snapm.child("latitude").getValue(Double.class),
                            snapm.child("longitude").getValue(Double.class)
                    );
                    mMap.addMarker(new MarkerOptions()
                            .position(newLocation)
                            .title("current location"));*/

                }
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
