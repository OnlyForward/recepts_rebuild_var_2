package com.example.a123.recepts_rebuild_var_2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.a123.recepts_rebuild_var_2.database.MenuDbSchemka.*;

public class MenuBaseHelper extends SQLiteOpenHelper {
    private static final int version = 1;
    private static final String DATABASE_NAME = "menuBase.db";

    public MenuBaseHelper(Context context){
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ MenuTable.NAME +"("+
                MenuTable.ColsTableReceipts.id+" integer primary key autoincrement, "+
                MenuTable.ColsTableReceipts.TITLE+", "+
                MenuTable.ColsTableReceipts.DESCRIPTION+", "+
                MenuTable.ColsTableReceipts.IMAGE_DESCRIPTION+" BLOB"+","+
                MenuTable.ColsTableReceipts.IMAGE_PREVIEW+" BLOB"+","+
                MenuTable.ColsTableReceipts.INGREDIENTS+")");

        db.execSQL("create table "+ MenuTable.NAMESTEPS +"("+
                MenuTable.ColsTableReceiptsByStep.DESCRIPTION+", "+
                MenuTable.ColsTableReceiptsByStep.STEPS+" BLOB"+","+
                MenuTable.ColsTableReceiptsByStep.STEPS_IMAGE+" BLOB"+", "+
                MenuTable.ColsTableReceiptsByStep.id+" integer,foreign key ("+MenuTable.ColsTableReceiptsByStep.id+") references "+ MenuTable.NAME +"("+MenuTable.ColsTableReceipts.id+")"+");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
