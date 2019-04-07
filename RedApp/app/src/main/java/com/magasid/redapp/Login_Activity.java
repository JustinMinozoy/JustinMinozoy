package com.magasid.redapp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText Email;
    private EditText Password;

    private Button login , signup , reset;
    private ProgressBar progressBar;

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        initializeUI();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });

        Button signup = (Button) findViewById(R.id.sign_up);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Activity.this, Sign_Up_Activity.class));
            }
        });
        Button reset = (Button) findViewById(R.id.reset_password);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Activity.this, Reset_Password_Activity.class));
            }
        });
    }

    private void loginUserAccount(){
        progressBar.setVisibility(View.VISIBLE);

        String email, password;
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(), "Please Enter Your Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "Please Enter Your Password", Toast.LENGTH_SHORT).show();
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                            Intent intent = new Intent(Login_Activity.this, Main_App_Activity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Login failed! Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initializeUI() {
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);

        login = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar);
    }

}
