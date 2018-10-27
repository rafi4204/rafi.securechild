package com.example.rafiw.securechild;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Menu extends AppCompatActivity implements View.OnClickListener {
Button gps;
    Button sms;
    Button call;
    Button home;
    private Button logoutBtn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        gps=(Button) findViewById(R.id.gps);
        sms=(Button) findViewById(R.id.sms);
        call=(Button) findViewById(R.id.call);
        //home=(Button) findViewById(R.id.home);
        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Menu.this,TestLoc.class);
                startActivity(intent);
            }
        });

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Menu.this,Sms_get.class);
                startActivity(intent);

            }
        });
       /* home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Menu.this,selectDevice.class);
                startActivity(intent);

            }
        });*/
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Menu.this,Call_logs_get.class);
                startActivity(intent);

            }
        });
        firebaseAuth = FirebaseAuth.getInstance();

        logoutBtn = (Button) findViewById(R.id.logout);

        logoutBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == logoutBtn){
            firebaseAuth.signOut();
            startActivity(new Intent(Menu.this, Parentlogin.class));
        }

    }
}
