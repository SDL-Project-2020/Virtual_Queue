package com.example.virtualqueue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Customer_Main extends AppCompatActivity {

    private Button reg_customer;
    private Button log_customer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);

    reg_customer = findViewById(R.id.reg_customer);
    log_customer = findViewById(R.id.log_customer);

    reg_customer.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(Customer_Main.this, Customer_Register.class));
        }
    });

    log_customer.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(Customer_Main.this, Customer_Login.class));
        }
    });

    }
}