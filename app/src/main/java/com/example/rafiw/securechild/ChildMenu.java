package com.example.rafiw.securechild;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ChildMenu extends AppCompatActivity  {

    Button gps;
    Button sms;
    Button call;
    Button web;
    private Button logoutBtn;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_menu);
        gps=(Button)findViewById(R.id.gps);
        sms=(Button)findViewById(R.id.sms);
        call=(Button)findViewById(R.id.call);
        //web=(Button)findViewById(R.id.web);
        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChildMenu.this,Child_location.class);
                startActivity(intent);

            }
        });

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChildMenu.this,Sms.class);
                startActivity(intent);

            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChildMenu.this,Call_logs.class);
                startActivity(intent);

            }
        });

      //  logoutBtn = (Button) findViewById(R.id.logout);

       // logoutBtn.setOnClickListener(this);

    }


    }

