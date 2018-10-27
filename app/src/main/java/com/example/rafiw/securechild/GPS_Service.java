package com.example.rafiw.securechild;

/**
 * Created by rafiw on 9/18/2017.
 */

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Binder;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.app.Service;
import android.content.Intent;
import android.os.*;
import android.os.Process;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;


public class GPS_Service extends Service implements LocationListener {

    private Context mContext;
    // flag for GPS status
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;

    Location location;//Location
    double latitude;//Latitude
    double longitude;//Longitude

    // The minimum time between updates in milliseconds
    static int tim;
    private static long MIN_TIME_BW_UPDATES;
    // private final IBinder mBinder = new LocalBinder();

    // Declaring a Location Manager
    protected LocationManager mlocationManager;


    public GPS_Service() {
    }

    public GPS_Service(Context mContext, String time) {
        this.mContext = mContext;
        this.tim = Integer.parseInt(time);
        MIN_TIME_BW_UPDATES = 1000 * tim;
        getLocation();
    }



    public int onStartCommand(Intent intent, int flags, int startId) {


        super.onStartCommand(intent, flags, startId);
       // mDatabaseLocationDetails = FirebaseDatabase.getInstance().getReference().child("sms_Details");



        startTimer();

        /*Message message = mServiceHandler.obtainMessage();
        message.arg1 = startId;
        mServiceHandler.sendMessage(message);*/


        return START_STICKY;
    }
    /*@Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        getLocation();
        Log.d("Working","Service Started");
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");
      //  Intent broadcastIntent = new Intent("com.example.rafiw.securechild.RestartSensor");
      //  sendBroadcast(broadcastIntent);
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

                getLocation();


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







    public void getLocation() {
        try {
            mlocationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = mlocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = mlocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                Log.d("network Enabled!!!!!!!!", "failed network!!!!!!");

            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    //noinspection MissingPermission
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.

                    }
                    mlocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, this);
                    Log.d("Network", "Network");
                    if (mlocationManager != null) {
                        //noinspection MissingPermission
                        location = mlocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Log.d("network Enabled!!!!!!!!", latitude+" "+longitude);

                        }
                    }
                }

                if (isGPSEnabled) {
                    if (location == null) {
                        //noinspection MissingPermission
                        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.

                        }
                        mlocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, 0, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (mlocationManager != null) {
                            //noinspection MissingPermission
                            location = mlocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                Log.d("GPS Enabled!!!!!!!!", latitude+" "+longitude);

                            }
                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
       // return location;
    }
    public void stopUsingGPS(){
        if(mlocationManager != null){
            mlocationManager.removeUpdates(GPS_Service.this);
        }
    }
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }


        return latitude;
    }
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        return longitude;
    }
    public boolean canGetLocation() {
        return this.canGetLocation;
    }
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        alertDialog.setTitle("GPS is settings");

        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private final class ServiceHandler extends Handler {

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            // Well calling mServiceHandler.sendMessage(message); from onStartCommand,
            // this method will be called.

            // Add your cpu-blocking activity here



        }
    }


}
