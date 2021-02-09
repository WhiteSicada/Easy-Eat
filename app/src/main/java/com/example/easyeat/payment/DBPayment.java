package com.example.easyeat.payment;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DBPayment  extends SQLiteOpenHelper {



    private static final String DATABASE_NAME = "Payment";
    private static final String TABLE_NAME = "PLACED_ORDER";
    private static final String COL_2 = "RESTAURANT";
    private static final String COL_3 = "ORDERTIME";
    private static final String COL_4 = "ESTIMATEDDELIVERYTIME";
    private static final String COL_5 = "ADRESSE";
    private static final String COL_6 = "CUSTOMER";
    private static final String COL_7 = "PRICE";
    private static final String COL_8 = "COMMENT";
    private static final String COL_9 = "STATUS";

    public DBPayment(@Nullable Context context) {
        super(context, DATABASE_NAME , null, 1);
    }





    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "RESTAURANT TEXT , ORDERTIME TEXT , ESTIMATEDDELIVERYTIME TEXT, ADRESSE TEXT , CUSTOMER TEXT , PRICE TEXT,COMMENT TEXT , STATUS TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean addPlacedOrder(PaymentModel paymentModel){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2 , paymentModel.getRestaurant());
        values.put(COL_3 , formatter.format(calendar.getTime()));
        values.put(COL_4 , "15 mins");
        values.put(COL_5 , paymentModel.getAdresse());
        values.put(COL_6 , paymentModel.getCustomer());
        values.put(COL_7 , paymentModel.getPrice());
        values.put(COL_8 , paymentModel.getComment());
        values.put(COL_9 , paymentModel.getStatus());
        long result = db.insert(TABLE_NAME , null , values);
        if (result == -1)return false;
        else return true;
    }

    public Boolean updatePlacedOrder(String id) {
        SQLiteDatabase myDB=this.getWritableDatabase();
        myDB.execSQL("UPDATE "+TABLE_NAME+" SET STATUS = "+"'"+"Finished"+"' "+ "WHERE ID = "+"'"+id+"'");
        return true;
    }
}
