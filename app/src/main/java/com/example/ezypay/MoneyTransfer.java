package com.example.ezypay;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MoneyTransfer extends AppCompatActivity {
    DBHelper db;
    SQLiteDatabase sqldb;
    EditText tL1, tL2, tL3;
    Button b1, b2;
    String fromacc, toacc, amt, bal;
    String Acno;
    boolean track = false, track2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_transfer);


        tL1 = findViewById(R.id.Accno);
        tL2 = findViewById(R.id.toAcc);
        tL3 = findViewById(R.id.amt);
        b1 = findViewById(R.id.pay);
        b2 = findViewById(R.id.back);


        final AlertDialog alertDialog = new AlertDialog.Builder(MoneyTransfer.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Cannot perform transaction on same account");
        alertDialog.setButton(RESULT_OK, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Try with another account number", Toast.LENGTH_SHORT).show();
            }
        });

        final AlertDialog alertDialog2 = new AlertDialog.Builder(MoneyTransfer.this).create();
        alertDialog2.setTitle("Alert");
        alertDialog2.setMessage("Balance of Debit Account is Zero");
        alertDialog2.setButton(RESULT_OK, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Cannot perform Debit operation on this account", Toast.LENGTH_LONG).show();
            }
        });

        final AlertDialog alertDialog3 = new AlertDialog.Builder(MoneyTransfer.this).create();


        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Acno = intent.getStringExtra("Acc_No");
            tL2.setText(Acno);
            tL2.setEnabled(false);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tL1.getText().toString().isEmpty() || tL2.getText().toString().isEmpty() || tL3.getText().toString().isEmpty()) {
                        tL1.setError("Enter Account Number");
                        tL2.setError("Enter Account Number");
                        tL3.setError("Enter amount");
                    } else {
                        db = new DBHelper(MoneyTransfer.this);
                        sqldb = db.getWritableDatabase();
                        fromacc = tL1.getText().toString().replace(",", "");
                        toacc = tL2.getText().toString().replace(",", "");
                        if (fromacc.equals(toacc)) {
                            alertDialog.show();
                        } else {
                            amt = tL3.getText().toString();
                            int oldbal1 = db.getbal(toacc);
                            track = db.update(toacc, Integer.parseInt(amt), oldbal1);
                            int oldbal2 = db.getbal(fromacc);
                            if (oldbal2 == 0) {
                                alertDialog2.show();
                            } else {
                                track2 = db.update2(fromacc, Integer.parseInt(amt), oldbal2);
                                if (track && track2) {
                                    alertDialog3.setTitle("Success");
                                    alertDialog3.setMessage("Account " + toacc + " credited with: " + amt);
                                    alertDialog3.setButton(RESULT_OK, "OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(getApplicationContext(), "Transaction Successful", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    alertDialog3.show();
                                    //Toast.makeText(MoneyTransfer.this, "Transfer successfull", Toast.LENGTH_LONG).show();
                                    inserted();
                                }
                            }
                        }
                    }
                }
            });
        }

        //back button
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //Inserting int transaction table
    public void inserted() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Customer_schema.customer_coln.Col_Transaction_Ano1, fromacc);
        contentValues.put(Customer_schema.customer_coln.Col_Transaction_Ano2, toacc);
        contentValues.put(Customer_schema.customer_coln.Col_Transaction_amt, amt);
        contentValues.put(Customer_schema.customer_coln.Col_Transaction_time, getDateTime());
        long id = sqldb.insert(Customer_schema.customer_coln.TABLE2_NAME, null, contentValues);
        if (id == 0) {
            System.out.println("Failure, Data not Inserted");
        } else {
            System.out.println("Success, Data Inserted Successfully");
        }
    }

    //Function to get DateTime
    private String getDateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss",
                Locale.getDefault());

        Date date = new Date();
        return simpleDateFormat.format(date);
    }
}