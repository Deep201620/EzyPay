package com.example.ezypay;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    //Declared and defined some constant to be used in our project
    public static final int DB_VERSION = 2;
    public static final String DB_NAME = "banking.db";

    private static final String CREATE_TABLE = "CREATE TABLE " + Customer_schema.customer_coln.TABLE_NAME + " (" + Customer_schema.customer_coln.COL_Acc_No + " TEXT PRIMARY KEY,"
            + Customer_schema.customer_coln.COL_Customer_name + " TEXT," + Customer_schema.customer_coln.COL_Balance + " INTEGER," + Customer_schema.customer_coln.COL_Email + " TEXT );";

    private static final String CREATE_TABLE2 = "CREATE TABLE " + Customer_schema.customer_coln.TABLE2_NAME + " (" + Customer_schema.customer_coln.Col_Transaction_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Customer_schema.customer_coln.Col_Transaction_Ano1 + " TEXT NOT NULL," + Customer_schema.customer_coln.Col_Transaction_type + " TEXT," + Customer_schema.customer_coln.Col_Transaction_Ano2 + " TEXT NOT NULL," + Customer_schema.customer_coln.Col_Transaction_amt + " TEXT NOT NULL," + Customer_schema.customer_coln.Col_Transaction_time + " TEXT NOT NULL);";


    private static final String DROP_TAbLE = "DROP TABLE IF EXISTS " + Customer_schema.customer_coln.TABLE_NAME;

    private static final String DROP_TAbLE2 = "DROP TABLE IF EXISTS " + Customer_schema.customer_coln.TABLE2_NAME;


    String[] Result = {Customer_schema.customer_coln.COL_Acc_No, Customer_schema.customer_coln.COL_Customer_name, Customer_schema.customer_coln.COL_Balance, Customer_schema.customer_coln.COL_Email};


    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    //this method will create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE2);
    }

    //This method will drop the table if exists or create it
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // db.execSQL(DROP_TAbLE);
        db.execSQL(DROP_TAbLE2);
        onCreate(db);
    }

    //This method will fetch all data of customers which we have used in ViewAllCustomer Activity
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
            user.put("Acc_No", "Account number: " + cursor.getString(cursor.getColumnIndex("Acc_No")));
            //Log.d("Data: ",cursor.getString(cursor.getColumnIndex("Acc_No")));
            user.put("Customer_name", "Account Holder: " + cursor.getString(cursor.getColumnIndex("Customer_name")));
            user.put("Balance", "Account Balance: " + cursor.getString(cursor.getColumnIndex("Balance")));
            userList2.add(user);
            //System.out.println(cursor.getString(0));
        }
        return userList2;
    }


    //This method is used for fetching Balance of particular Account Number
    public int getbal(String Ano) {
        int bal2 = 0;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(Customer_schema.customer_coln.TABLE_NAME, new String[]{Customer_schema.customer_coln.COL_Balance}, Customer_schema.customer_coln.COL_Acc_No + "=?", new String[]{String.valueOf(Ano)}, null, null, null);
        if (cursor.moveToFirst()) {
            String s1;
            s1 = cursor.getString(cursor.getColumnIndex("Balance")).replace(",", "");
            bal2 = Integer.parseInt(s1);
        }
        return bal2;
    }

    //This method will update  data after transferring money in Customer table(Credited into Account)
    public boolean update(String ToAno, int amount, int oldbal){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int newbal = oldbal+amount;
        sqLiteDatabase.execSQL("UPDATE "+Customer_schema.customer_coln.TABLE_NAME+ " SET Balance = "+"'"+newbal+"' "+ "WHERE Acc_No = "+"'"+ToAno+"'");
        return true;
    }

    //This method will update  data after transferring money in Customer table(Debited from Account)
    public boolean update2(String fromAno, int amount, int oldbal2) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int newbal2 = oldbal2 - amount;
        sqLiteDatabase.execSQL("UPDATE " + Customer_schema.customer_coln.TABLE_NAME + " SET Balance = " + "'" + newbal2 + "' " + "WHERE Acc_No = " + "'" + fromAno + "'");
        return true;
    }

    //This method will details of particular Account number
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

