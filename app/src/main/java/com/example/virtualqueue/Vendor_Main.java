package com.example.virtualqueue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Vendor_Main extends AppCompatActivity {

    private Button reg_vendor;
    private Button log_vendor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_main);

        reg_vendor = findViewById(R.id.reg_vednor);
        log_vendor = findViewById(R.id.log_vendor);

        reg_vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Vendor_Main.this, Vendor_Register.class));
            }
        });

        log_vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Vendor_Main.this, Vendor_Login.class));
            }
        });


    }
}