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

public class Customer_Login extends AppCompatActivity {

    private EditText cmail_log;
    private EditText cpassword_log;
    private Button login;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__login);

        cmail_log = findViewById(R.id.cmail_log);
        cpassword_log = findViewById(R.id.cpassword_log);
        login = findViewById(R.id.login);
        auth = FirebaseAuth.getInstance();

    login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String txt_email = cmail_log.getText().toString();
            String txt_password = cpassword_log.getText().toString();
            loginUser(txt_email,txt_password);
        }
    });
    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(Customer_Login.this, "Login successful",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Customer_Login.this, Customer_Home.class));
                finish();
            }
        });
    }
}