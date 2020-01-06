package com.example.assignment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*Abhishek Kumar Yadava*/

public class StartActivity extends AppCompatActivity {

    Button mBtnCreateProduct, mBtnShowProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);



        mBtnCreateProduct = findViewById(R.id.idBtnCreateProduct);
        mBtnShowProduct = findViewById(R.id.idBtnShowProduct);


        mBtnCreateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start Main activity
                startActivity(new Intent(StartActivity.this, MainActivity.class));
            }
        });


        mBtnShowProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start ProductList Activity
                startActivity(new Intent(StartActivity.this, ProductListActivity.class));
            }
        });
    }
}
