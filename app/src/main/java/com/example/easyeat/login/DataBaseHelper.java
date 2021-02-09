package com.example.easyeat.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.easyeat.model.User;

import org.jetbrains.annotations.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "USER_RECORD";
    private static final String TABLE_NAME = "USER_DATA";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "USERNAME";
    private static final String COL_3 = "EMAIL";
    private static final String COL_4 = "PASSWORD";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , USERNAME TEXT , EMAIL TEXT , PASSWORD TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean registerUser(String username , String email , String password){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2 , username);
        values.put(COL_3 , email);
        values.put(COL_4 , password);
        Boolean checkuser = checkUsername(username);
        if(checkuser==true) {
            return false;
        }
        else{
            long result = db.insert(TABLE_NAME , null , values);
            if(result == -1)
                return false;
            else
                return true;
        }


    }

    public User checkUser(String username , String password){
        User user = new User();
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("Select * from USER_DATA where USERNAME = ? and PASSWORD = ?",new String[] {username,password});
            if (cursor.moveToFirst())
                user = new User();
            user.setId(cursor.getInt(0));
            user.setUsernname(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setPassword(cursor.getString(3));
        }catch (Exception e){
            user = null;
        }
        return user;
    }


    public Boolean updateUser(String email,String id) {
        SQLiteDatabase myDB=this.getWritableDatabase();
        myDB.execSQL("UPDATE "+TABLE_NAME+" SET EMAIL = "+"'"+email+"' "+ "WHERE ID = "+"'"+id+"'");
        return true;
    }



    // check if user exists
    public boolean checkUsername(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * from USER_DATA where USERNAME = ? ",new String[] {username});
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }


    public Boolean updatePassword(String username,String password){
        SQLiteDatabase myDB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("password",password);
        long result=myDB.update(TABLE_NAME,contentValues,"USERNAME=?",new String[]{username});
        if(result == -1)
            return false;
        else
            return true;
    }

}