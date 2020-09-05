package com.example.virtualqueue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.lang.reflect.Array;

public class Vendor_Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText vname;
    private EditText vmail;
    private EditText vnumber;
    private EditText vpassword;
    private EditText vaddress;
    private Button vregister;
    private FirebaseAuth auth;
    int vendor_count;
    String txt_type;
    //FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        txt_type = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(Vendor_Register.this, "Select Type",Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor__register);

        vmail = findViewById(R.id.vmail);
        vnumber = findViewById(R.id.vnumber);
        vname = findViewById(R.id.vname);
        vpassword = findViewById(R.id.vpassword);
        vregister = findViewById(R.id.vregister);
        vaddress = findViewById(R.id.vaddress);
        final Spinner spinner1 = findViewById(R.id.spinner1);
        auth = FirebaseAuth.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);

        vregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txt_email = vmail.getText().toString();
                String txt_add = vaddress.getText().toString();
                String txt_name = vname.getText().toString();
                String txt_number = vnumber.getText().toString();
                String txt_pwd = vpassword.getText().toString();

                if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_pwd)){
                    Toast.makeText(Vendor_Register.this, "Empty Credentials",Toast.LENGTH_SHORT).show();
                }
                else if(txt_pwd.length()<6){
                    vpassword.setError("Password too short");
                }
                else if(TextUtils.isEmpty(txt_name)){
                    vname.setError("Name is required");
                }
                else if(TextUtils.isEmpty(txt_add)){
                    vname.setError("Address is required");
                }
                else if(TextUtils.isEmpty(txt_type)){
                    vname.setError("Type is required");
                }
                else if(TextUtils.isEmpty(txt_number)){
                    vnumber.setError("Number is required");
                }
                else{

                    registerUser(txt_email,txt_pwd,txt_name,txt_number,txt_type,txt_add);
                }

            }
        });

    }


    private void registerUser(String txt_email, String txt_pwd,String txt_name,String txt_number, String txt_type, String txt_add) {

        auth.createUserWithEmailAndPassword(txt_email, txt_pwd).addOnCompleteListener(Vendor_Register.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Vendor_Register.this, "Registering user successful",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Vendor_Register.this, Vendor_Home.class));
                    finish();
                }else{
                    Toast.makeText(Vendor_Register.this, "Registerion failed. Email-id exists",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Retrive vendor count
        DatabaseReference count_refernce = FirebaseDatabase.getInstance().getReference().child("Vendors").child("Count");
        count_refernce.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vendor_count = snapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        vendor_count++;
        FirebaseDatabase.getInstance().getReference().child("Vendors").child(txt_type).child("Vendor " + vendor_count).child("name").setValue(txt_name);
        FirebaseDatabase.getInstance().getReference().child("Vendors").child(txt_type).child("Vendor " + vendor_count).child("mail").setValue(txt_email);
        FirebaseDatabase.getInstance().getReference().child("Vendors").child(txt_type).child("Vendor " + vendor_count).child("number").setValue(txt_number);
        FirebaseDatabase.getInstance().getReference().child("Vendors").child(txt_type).child("Vendor " + vendor_count).child("address").setValue(txt_add);

        //update vendor count
          FirebaseDatabase.getInstance().getReference().child("Vendors").child("Count").setValue(vendor_count);


    }
}