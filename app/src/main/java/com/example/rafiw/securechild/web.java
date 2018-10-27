package com.example.rafiw.securechild;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Browser;
import android.provider.CallLog;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class web extends AppCompatActivity {
    DatabaseReference mDatabaseLocationDetails;
    ArrayList<String> smsMessagesList = new ArrayList<>();
    ListView messages;
    ArrayAdapter arrayAdapter;
    EditText input;
    private static final String TAG = web.class.getSimpleName();

    SmsManager smsManager = SmsManager.getDefault();
    private static web inst;

    private static final int READ_SMS_PERMISSIONS_REQUEST = 1;

    public static web instance() {
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
        setContentView(R.layout.activity_web);

        mDatabaseLocationDetails = FirebaseDatabase.getInstance().getReference().child("browsing_history");

        // messages = (ListView) findViewById(R.id.messages);
        // input = (EditText) findViewById(R.id.input);
       /* arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, smsMessagesList);
        messages.setAdapter(arrayAdapter);*/

            refreshSmsInbox();



    }

    public void updateInbox(final String smsMessage) {
        //  mDatabaseLocationDetails.child("sms").setValue(smsMessage);
        storeInDatabase(smsMessage);
        /*arrayAdapter.insert(smsMessage, 0);

        arrayAdapter.notifyDataSetChanged();*/
    }

    public void onSendClick(View view) {
        //mDatabaseLocationDetails = FirebaseDatabase.getInstance().getReference().child("sms_Details").push();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            getPermissionToReadSMS();
        } else {
            smsManager.sendTextMessage("07701056337", null, input.getText().toString(), null, null);
            Toast.makeText(this, "Message sent!", Toast.LENGTH_SHORT).show();
        }
    }

    public void getPermissionToReadSMS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_CALL_LOG)) {
                Toast.makeText(this, "Please allow permission!", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG},
                    READ_SMS_PERMISSIONS_REQUEST);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == READ_SMS_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read SMS permission granted", Toast.LENGTH_SHORT).show();
                refreshSmsInbox();
            } else {
                Toast.makeText(this, "Read SMS permission denied", Toast.LENGTH_SHORT).show();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }



    }

    public void refreshSmsInbox() {
       /* ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        //arrayAdapter.clear();
        do {
            String str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
                    "\n" + smsInboxCursor.getString(indexBody) + "\n";
            //arrayAdapter.add(str);
            storeInDatabase(str);
            //    mDatabaseLocationDetails.child("sms").setValue(str);
        } while (smsInboxCursor.moveToNext());*/
//messages.setSelection(arrayAdapter.getCount() - 1);


        //   String sb ;
       /* String[] proj = new String[] { Browser.BookmarkColumns.TITLE, Browser.BookmarkColumns.URL };
        String sel = Browser.BookmarkColumns.BOOKMARK + " = 0"; // 0 = history, 1 = bookmark
        Cursor mCur = getContentResolver().query(Browser.BOOKMARKS_URI, proj, sel, null, null);
        mCur.moveToFirst();
        @SuppressWarnings("unused")
        String title = "";
        @SuppressWarnings("unused")
        String url = "";
        if (mCur.moveToFirst() && mCur.getCount() > 0) {
            boolean cont = true;
            while (mCur.isAfterLast() == false && cont) {
                title = mCur.getString(mCur.getColumnIndex(Browser.BookmarkColumns.TITLE));
                url = mCur.getString(mCur.getColumnIndex(Browser.BookmarkColumns.URL));
                // Do something with title and url
                mCur.moveToNext();
            }
        }*/


        final Uri BOOKMARKS_URI = Uri.parse("content://browser/bookmarks");
        final String[] HISTORY_PROJECTION = new String[]
                {
                        "_id", // 0
                        "url", // 1
                        "visits", // 2
                        "date", // 3
                        "bookmark", // 4
                        "title", // 5
                        "favicon", // 6
                        "thumbnail", // 7
                        "touch_icon", // 8
                        "user_entered", // 9
                };


        Cursor mCur = this.managedQuery(BOOKMARKS_URI, HISTORY_PROJECTION, null,   null,null);
        this.startManagingCursor(mCur );
        mCur .moveToFirst();

        String title = "";
        String url = "";
        String date="";

        if (mCur.moveToFirst() && mCur.getCount() > 0) {
            while (mCur.isAfterLast() == false ) {

                title= mCur.getString(mCur .getColumnIndex("title"));
                url = mCur.getString(mCur .getColumnIndex("url"));
                date= mCur.getString(mCur .getColumnIndex("date"));


                Log.d(TAG, "Value issssssssssssss: " + title);
                mCur .moveToNext();
            }
        }





    }

    private void storeInDatabase(String string) {


        mDatabaseLocationDetails.push(). child("call logs").setValue(string);
        //mDatabaseLocationDetails.child("latitude").setValue(latitude);
    }



}

