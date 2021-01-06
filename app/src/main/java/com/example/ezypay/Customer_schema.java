package com.example.ezypay;

import android.provider.BaseColumns;

public final class Customer_schema {

    private Customer_schema() {
    }
    public static class customer_coln implements BaseColumns{
        public static final String TABLE_NAME = "Customer";
        public static final String COL_Acc_No  = "Acc_No";
        public static final String COL_Customer_name = "Customer_name";
        public static final String COL_Balance = "Balance";
        public static final String COL_Email = "Email";

    }

}
