package com.example.virtualqueue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Vendor_Login extends AppCompatActivity {

    private EditText vmail_log;
    private EditText vpassword_log;
    private Button vlogin;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor__login);

        vmail_log = findViewById(R.id.vmail_log);
        vpassword_log = findViewById(R.id.vpassword_log);
        vlogin = findViewById(R.id.vlogin);
        auth = FirebaseAuth.getInstance();

        vlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = vmail_log.getText().toString();
                String txt_password = vpassword_log.getText().toString();
                loginUser(txt_email,txt_password);
            }
        });

    }
    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(Vendor_Login.this, "Login successful",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Vendor_Login.this, Vendor_Home.class));
                finish();
            }
        });
    }

}