package com.example.ezypay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
Button b1,b2;
TextInputLayout t1;
DBHelper db;
SQLiteDatabase sqldb;
long rowid;
    ArrayList<String> Customer_name, AccountNo, Balance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.viewallcustomer);
        b2 = findViewById(R.id.transfermoney);

        db=new DBHelper(this);
        sqldb=db.getWritableDatabase();

       db.onUpgrade(sqldb,1,1);
      ContentValues contentValues = new ContentValues();
        contentValues.put(Customer_schema.customer_coln.COL_Acc_No,"410");
        contentValues.put(Customer_schema.customer_coln.COL_Customer_name,"Russel");
        contentValues.put(Customer_schema.customer_coln.COL_Balance,"82000");
        contentValues.put(Customer_schema.customer_coln.COL_Email,"Russelck@gmail.com");

        rowid = sqldb.insert(Customer_schema.customer_coln.TABLE_NAME,null,contentValues);
            if(rowid==0){
                Log.d("Data not inserted"," problem");
            }else{
                Log.d("Operation Successfull","Data inserted successfully");
            }
        // db.update("101","45000");

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,ViewAllCustomer.class);
                startActivity(intent);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MoneyTransfer.class);
                startActivity(intent);
            }
        });

    }



}
