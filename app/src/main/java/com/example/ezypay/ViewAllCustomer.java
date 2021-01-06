package com.example.ezypay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
        db = new DBHelper(this);
        sqLiteDatabase = db.getWritableDatabase();

        userList = db.fetchdata();
        ListAdapter listAdapter = new SimpleAdapter(this,userList,R.layout.datarows,new String[]{"Acc_No","Customer_name","Balance"},new int[]{R.id.AccNo,R.id.Customer_name,R.id.Balance});
        details_list.setAdapter(listAdapter);


    }
}