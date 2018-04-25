package com.example.a0lambj41.budgetplannerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

/**
 * Created by 0lambj41 on 25/04/2018.
 */

public class MyHelper extends SQLiteOpenHelper{
    public MyHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "budgetplanner.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL("CREATE TABLE TRANSACTIONS( ID INTEGER PRIMARY KEY AUTOINCREMENT, TRANSACTION TEXT, DESCRIPTION TEXT UNIQUE, AMOUNT INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TRANSACTIONS;");
        onCreate(sqLiteDatabase);
    }

    public void insert_transaction(String transaction, String description, String amount){
        ContentValues contentValues = new ContentValues();
        contentValues.put("TRANSACTION",transaction);
        contentValues.put("DESCRIPTION",description);
        contentValues.put("AMOUNT",amount);
        this.getWritableDatabase().insertOrThrow("TRANSACTIONS","",contentValues);
    }

    public void delete_transaction(String description) {
        this.getWritableDatabase().delete("TRANSACTIONS", "DESCRIPTION='" + description + "'", null);
    }

    public void update_transaction(String desc, String new_amount){
        this.getWritableDatabase().execSQL("UPDATE TRANSACTIONS SET AMOUNT='" + new_amount + "' WHERE DESCRIPTION='" + desc + "'");
    }

    public void list_transactions(TextView textView){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM TRANSACTIONS",null);
        while (cursor.moveToNext()){
            textView.append(cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getInt(3));
        }
    }
}
