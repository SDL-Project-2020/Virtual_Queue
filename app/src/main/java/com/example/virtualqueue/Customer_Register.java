package com.example.virtualqueue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Customer_Register extends AppCompatActivity {

    private EditText cname;
    private EditText cmail;
    private EditText cnumber;
    private EditText cpassword;
    private Button cregister;
    private FirebaseAuth auth;
    int customer_count;
    //FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__register);

        cmail = findViewById(R.id.cmail);
        cnumber = findViewById(R.id.cnumber);
        cname = findViewById(R.id.cname);
        cpassword = findViewById(R.id.cpassword);
        cregister = findViewById(R.id.cregister);
        auth = FirebaseAuth.getInstance();

        cregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = cmail.getText().toString();
                String txt_name = cname.getText().toString();
                String txt_number = cnumber.getText().toString();
                String txt_pwd = cpassword.getText().toString();
               
                if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_pwd)){
                    Toast.makeText(Customer_Register.this, "Empty Credentials",Toast.LENGTH_SHORT).show();
                }
                else if(txt_pwd.length()<6){
                    cpassword.setError("Password too short");
                }
                else if(TextUtils.isEmpty(txt_name)){
                    cname.setError("Name is required");
                }
                else if(TextUtils.isEmpty(txt_number)){
                    cnumber.setError("Number is required");
                }
                else{

                    registerUser(txt_email,txt_pwd,txt_name,txt_number);
                }

            }
        });

    }

    private void registerUser(String txt_email, String txt_pwd,String txt_name,String txt_number) {

        auth.createUserWithEmailAndPassword(txt_email, txt_pwd).addOnCompleteListener(Customer_Register.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Customer_Register.this, "Registering user successful",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Customer_Register.this, Customer_Home.class));
                    finish();
                }else{
                    Toast.makeText(Customer_Register.this, "Registerion failed. Email-id exists",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Retrive customer count
        DatabaseReference count_refernce = FirebaseDatabase.getInstance().getReference().child("Customers").child("Count");
        count_refernce.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                customer_count = snapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        customer_count++;

        FirebaseDatabase.getInstance().getReference().child("Customers").child("Customer " + customer_count).child("name").setValue(txt_name);
        FirebaseDatabase.getInstance().getReference().child("Customers").child("Customer " + customer_count).child("mail").setValue(txt_email);
        FirebaseDatabase.getInstance().getReference().child("Customers").child("Customer " + customer_count).child("number").setValue(txt_number);

        //update customer count
        FirebaseDatabase.getInstance().getReference().child("Customers").child("Count").setValue(customer_count);

    }
}