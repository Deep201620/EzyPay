package com.example.ezypay;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewAllCustomer extends AppCompatActivity {
    DBHelper db;
    SQLiteDatabase sqLiteDatabase;
    ListView details_list;
    ArrayList<HashMap<String, String>> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_customer);

        details_list = findViewById(R.id.detailsview);

        //initializing DBHelper and SQLiteDtabase objects
        db = new DBHelper(this);
        sqLiteDatabase = db.getWritableDatabase();


        userList = db.fetchdata();

        //Populating list using adapter
        ListAdapter listAdapter = new SimpleAdapter(this, userList, R.layout.datarows, new String[]{"Acc_No", "Customer_name", "Balance"}, new int[]{R.id.AccNo, R.id.Customer_name, R.id.Balance});
        details_list.setAdapter(listAdapter);


        //Handling ListItem click action
        details_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = view.findViewById(R.id.AccNo);
                String Acno = tv.getText().toString().substring(16, 19);
                Intent intent = new Intent(ViewAllCustomer.this, MoneyTransfer.class);
                intent.putExtra("Acc_No", Acno);
                startActivity(intent);
            }
        });


    }
}