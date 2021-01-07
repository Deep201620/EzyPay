package com.example.ezypay;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "banking.db";

    private  static final String CREATE_TABLE = "CREATE TABLE " + Customer_schema.customer_coln.TABLE_NAME + " (" + Customer_schema.customer_coln.COL_Acc_No + " TEXT PRIMARY KEY,"
            + Customer_schema.customer_coln.COL_Customer_name + " TEXT,"+Customer_schema.customer_coln.COL_Balance + " INTEGER,"+Customer_schema.customer_coln.COL_Email + " TEXT );";

    private static final  String DROP_TAbLE = "DROP TABLE IF EXISTS "+ Customer_schema.customer_coln.TABLE_NAME;

    String[] Result= {Customer_schema.customer_coln.COL_Acc_No,Customer_schema.customer_coln.COL_Customer_name,Customer_schema.customer_coln.COL_Balance,Customer_schema.customer_coln.COL_Email};

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TAbLE);
        onCreate(db);
    }

    public ArrayList<HashMap<String, String>> fetchdata(){
        ArrayList<HashMap<String, String>> userList2 = new ArrayList<>();
        SQLiteDatabase sqldb = this.getReadableDatabase();
        //String selection = Customer_schema.customer_coln.COL_Acc_No + " = ?";
        //String[] selectionArgs = {"101"};
        Cursor cursor = sqldb.query(
                Customer_schema.customer_coln.TABLE_NAME,
                Result,
                null,
                null,
                null,
                null,
                null
        );
        ArrayList<HashMap<String,String>> itemId = new ArrayList<>();
        while(cursor.moveToNext()) {
            HashMap<String,String> user = new HashMap<>();
            user.put("Acc_No","Account number: "+cursor.getString(cursor.getColumnIndex("Acc_No")));
            //Log.d("Data: ",cursor.getString(cursor.getColumnIndex("Acc_No")));
            user.put("Customer_name","Account Holder: "+cursor.getString(cursor.getColumnIndex("Customer_name")));
            user.put("Balance","Account Balance: "+cursor.getString(cursor.getColumnIndex("Balance")));
            userList2.add(user);
            //System.out.println(cursor.getString(0));
        }
        return userList2;
    }

    public int getbal(String Ano){
        int bal2 = 0;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(Customer_schema.customer_coln.TABLE_NAME,new String[]{Customer_schema.customer_coln.COL_Balance},Customer_schema.customer_coln.COL_Acc_No+ "=?",new String[]{String.valueOf(Ano)},null,null,null);
    if (cursor.moveToFirst())
    {
        String s1;
        s1 = cursor.getString(cursor.getColumnIndex("Balance")).replace(",","");
        bal2 = Integer.parseInt(s1);
    }
    return bal2;
    }

    public boolean update(String ToAno, int amount,int oldbal){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int newbal = oldbal+amount;
        sqLiteDatabase.execSQL("UPDATE "+Customer_schema.customer_coln.TABLE_NAME+ " SET Balance = "+"'"+newbal+"' "+ "WHERE Acc_No = "+"'"+ToAno+"'");
        return true;
    }

    public boolean update2(String fromAno, int amount, int oldbal2) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int newbal2 = oldbal2 - amount;
        sqLiteDatabase.execSQL("UPDATE " + Customer_schema.customer_coln.TABLE_NAME + " SET Balance = " + "'" + newbal2 + "' " + "WHERE Acc_No = " + "'" + fromAno + "'");
        return true;
    }

    public ArrayList<String> getDetails(String Acno) {
        ArrayList<String> details = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(Customer_schema.customer_coln.TABLE_NAME, Result, Customer_schema.customer_coln.COL_Acc_No + "=?", new String[]{String.valueOf(Acno)}, null, null, null);
        if (cursor.moveToFirst()) {
            String s1, s2, s3, s4;
            s1 = cursor.getString(cursor.getColumnIndex("Balance"));
            s2 = cursor.getString(cursor.getColumnIndex("Acc_No"));
            s3 = cursor.getString(cursor.getColumnIndex("Customer_name"));
            s4 = cursor.getString(cursor.getColumnIndex("Email"));
            details.add("Account No: " + s2);
            details.add("Account Holder: " + s3);
            details.add("Account Balance: " + s1);
            details.add("Email: " + s4);
        }
        return details;
    }
}

