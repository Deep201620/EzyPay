package com.example.ezypay;

import android.provider.BaseColumns;

public final class Customer_schema {

    private Customer_schema() {
    }

    //Declared and defined some constant to be used frequently in our project
    public static class customer_coln implements BaseColumns {
        public static final String TABLE_NAME = "Customer";
        public static final String COL_Acc_No = "Acc_No";
        public static final String COL_Customer_name = "Customer_name";
        public static final String COL_Balance = "Balance";
        public static final String COL_Email = "Email";

        public static final String TABLE2_NAME = "Transactions";
        public static final String Col_Transaction_id = "Transaction_Id";
        public static final String Col_Transaction_amt = "Amount";
        public static final String Col_Transaction_type = "Transaction_type";
        public static final String Col_Transaction_Ano1 = "From_Acc";
        public static final String Col_Transaction_Ano2 = "To_Acc";
        public static final String Col_Transaction_time = "Transaction_time";

    }

}
