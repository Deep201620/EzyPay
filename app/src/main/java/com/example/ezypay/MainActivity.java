package com.example.ezypay;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button b1, b2, b3, b4;
    TextInputLayout t1;
    DBHelper db;
    TextView detailview;
    SQLiteDatabase sqldb;
    long rowid;
    ArrayList<String> Customer_name, AccountNo, Balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.viewallcustomer);
        b2 = findViewById(R.id.transfermoney);
        b3 = findViewById(R.id.search_cust);
        b4 = findViewById(R.id.chkbal);
        t1 = findViewById(R.id.textInputLayout);
        detailview = findViewById(R.id.alldetails);
        detailview.setVisibility(View.GONE);

        //initializing DBHelper and SQLiteDtabase objects
        db = new DBHelper(this);
        sqldb = db.getWritableDatabase();

        //Creating Table and Drop if already exists
        db.onUpgrade(sqldb, 2, 3);
     /* ContentValues contentValues = new ContentValues();
        contentValues.put(Customer_schema.customer_coln.COL_Acc_No,"101");
        contentValues.put(Customer_schema.customer_coln.COL_Customer_name,"john");
        contentValues.put(Customer_schema.customer_coln.COL_Balance,"15,000");
        contentValues.put(Customer_schema.customer_coln.COL_Email,"john@gmail.com");

        rowid = sqldb.insert(Customer_schema.customer_coln.TABLE_NAME,null,contentValues);
            if(rowid==0){
                Log.d("Data not inserted"," problem");
            }else{
                Log.d("Operation Successfull","Data inserted successfully");
            }*/
        // db.update("101","45000");


        //Handling Button Click events
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailview.setText("");
                t1.getEditText().setText("");
                Intent intent = new Intent(MainActivity.this, ViewAllCustomer.class);
                startActivity(intent);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MoneyTransfer.class);
                startActivity(intent);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (t1.getEditText().getText().toString().isEmpty()) {
                    t1.getEditText().setError("Enter Account Number");
                } else {
                    detailview.setVisibility(View.VISIBLE);
                    t1.getEditText().onEditorAction(EditorInfo.IME_ACTION_DONE);
                    ArrayList<String> Alldetails = new ArrayList<>();
                    Alldetails = db.getDetails(t1.getEditText().getText().toString());
                    if (Alldetails.isEmpty()) {
                        detailview.setText("No Record Found");
                    } else {
                        detailview.setText(Alldetails.get(0) + "\n" + Alldetails.get(1) + "\n" + Alldetails.get(2) + "\n" + Alldetails.get(3));
                    }
                }
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.getEditText().onEditorAction(EditorInfo.IME_ACTION_DONE);
                if (t1.getEditText().getText().toString().isEmpty()) {
                    t1.getEditText().setError("Enter Account Number");
                } else {
                    int Balance = db.getbal(t1.getEditText().getText().toString());
                    if (Balance != 0) {
                        detailview.setVisibility(View.VISIBLE);
                        detailview.setText("Account Balance: " + Balance);
                    } else {
                        detailview.setVisibility(View.VISIBLE);
                        detailview.setText("Account not existed");
                    }
                }
            }
        });

    }



}
