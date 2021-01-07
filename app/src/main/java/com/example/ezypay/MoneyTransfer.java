package com.example.ezypay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class MoneyTransfer extends AppCompatActivity {
    DBHelper db;
    SQLiteDatabase sqldb;
    EditText tL1,tL2,tL3;
    TextView t1,t2;
    Button b1,b2;
    String fromacc,toacc,amt,bal;
    String Acno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_transfer);


        tL1 = findViewById(R.id.Accno);
        tL2 = findViewById(R.id.toAcc);
        tL3 = findViewById(R.id.amt);
        b1 = findViewById(R.id.pay);
        b2 = findViewById(R.id.back);


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
                        amt = tL3.getText().toString();
                        int oldbal1 = db.getbal(toacc);
                        db.update(toacc, Integer.parseInt(amt), oldbal1);
                        int oldbal2 = db.getbal(fromacc);
                        db.update2(fromacc, Integer.parseInt(amt), oldbal2);
                        Toast.makeText(MoneyTransfer.this, "Transfer Successfull", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(MoneyTransfer.this, ViewAllCustomer.class);
                        startActivity(i);
                    }
                }
            });
        }
    }
}