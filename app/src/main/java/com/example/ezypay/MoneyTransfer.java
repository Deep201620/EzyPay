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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_transfer);

        tL1 = findViewById(R.id.Accno);
        tL2 = findViewById(R.id.toAcc);
        tL3 = findViewById(R.id.amt);
        b1 = findViewById(R.id.pay);
        b2 = findViewById(R.id.back);





        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DBHelper(MoneyTransfer.this);
                sqldb = db.getWritableDatabase();
                fromacc =tL1.getText().toString().replace(",","");
                toacc = tL2.getText().toString().replace(",","");
                amt = tL3.getText().toString();
                int oldbal1 = db.getbal(toacc);
               db.update(toacc,Integer.parseInt(amt),oldbal1);
               int oldbal2 = db.getbal(fromacc);
               db.update2(fromacc,Integer.parseInt(amt),oldbal2);
                Toast.makeText(MoneyTransfer.this,"Transfer Successfull",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MoneyTransfer.this,ViewAllCustomer.class);
                startActivity(i);

            }
        });
    }
}