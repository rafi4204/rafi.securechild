package com.example.rafiw.securechild;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class Parentsignup extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    private TextView textViewSignin;

    private ProgressDialog progressDialog;

    // defining FirebaseAuth
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parentsignup);

        //getting the firebae object
        firebaseAuth = FirebaseAuth.getInstance();

        //initializing all the views
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        buttonSignup = (Button) findViewById(R.id.signup);
        textViewSignin = (TextView) findViewById(R.id.login);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button sign up
        buttonSignup.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignup){
            registerUser();
        }
        if (view == textViewSignin){
            startActivity(new Intent(Parentsignup.this, Parentlogin.class));
        }
    }

    private void registerUser() {

        //getting all the inputs from the user
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //validation
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() <= 5){
            Toast.makeText(this, "Password must be more than 5 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering, Please Wait...");
        progressDialog.show();

        //now we can create the user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Parentsignup.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                            progressDialog.hide();

                            startActivity(new Intent(getApplicationContext(), selectDevice.class));
                            return;

                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(Parentsignup.this, "Email already exists!", Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(Parentsignup.this, "Something went terrible wrong!", Toast.LENGTH_SHORT).show();
                            progressDialog.hide();
                            return;
                        }
                    }
                });
    }
}

