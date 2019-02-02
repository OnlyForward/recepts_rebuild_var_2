package com.example.a123.recepts_rebuild_var_2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.example.a123.recepts_rebuild_var_2.model.MenuReceipt;

import java.io.IOException;
import java.io.InputStream;

public class DbManager {
    SQLiteDatabase sqLiteDatabase;
    Context mContext;
    private static final String ICON_FOLDER = "icon";
    private static final String ICON_FOLDER_FULL = "icon_full";

    public DbManager(Context context){
        mContext = context;
        MenuBaseHelper baseHelper = new MenuBaseHelper(context);
        sqLiteDatabase = baseHelper.getWritableDatabase();
    }


    private static class MenuBaseHelper extends SQLiteOpenHelper {
        private static final int version = 1;
        private static final String DATABASE_NAME = "menuBase.db";
        private static final String MENU_CREATE = "create table " + MenuDbSchemka.MenuTable.NAME + "(" +
                MenuDbSchemka.MenuTable.ColsTableReceipts.id + " integer primary key autoincrement, " +
                MenuDbSchemka.MenuTable.ColsTableReceipts.TITLE + ", " +
                MenuDbSchemka.MenuTable.ColsTableReceipts.DESCRIPTION + ", " +
                MenuDbSchemka.MenuTable.ColsTableReceipts.IMAGE_DESCRIPTION + " BLOB" + "," +
                MenuDbSchemka.MenuTable.ColsTableReceipts.IMAGE_PREVIEW + " BLOB" + "," +
                MenuDbSchemka.MenuTable.ColsTableReceipts.INGREDIENTS + ")";

        private static final String MENU_STEPS_CREATE = "create table " + MenuDbSchemka.MenuTable.NAMESTEPS + "(" +
                MenuDbSchemka.MenuTable.ColsTableReceiptsByStep.DESCRIPTION + ", " +
                MenuDbSchemka.MenuTable.ColsTableReceiptsByStep.STEPS + " BLOB" + "," +
                MenuDbSchemka.MenuTable.ColsTableReceiptsByStep.STEPS_IMAGE + " BLOB" + ", " +
                MenuDbSchemka.MenuTable.ColsTableReceiptsByStep.id + " integer,foreign key (" + MenuDbSchemka.MenuTable.ColsTableReceiptsByStep.id + ") references " + MenuDbSchemka.MenuTable.NAME + "(" + MenuDbSchemka.MenuTable.ColsTableReceipts.id + ")" + ");";

        public MenuBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(MENU_CREATE);

            db.execSQL(MENU_STEPS_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists " + MenuDbSchemka.MenuTable.NAME);
            db.execSQL("drop table if exists " + MenuDbSchemka.MenuTable.NAMESTEPS);

            db.execSQL(MENU_CREATE);

            db.execSQL(MENU_STEPS_CREATE);
        }

    }

    public long SaveReceipt(MenuReceipt menu){
        ContentValues values = forMenu(menu);
        long id = sqLiteDatabase.insert(MenuDbSchemka.MenuTable.NAME,"",values);
        return id;
    }

    private ContentValues forMenu(MenuReceipt menu){
        ContentValues values = new ContentValues();
        values.put(MenuDbSchemka.MenuTable.ColsTableReceipts.DESCRIPTION,menu.getDescription());
        values.put(MenuDbSchemka.MenuTable.ColsTableReceipts.IMAGE_DESCRIPTION,menu.getPhotoFullName());
        values.put(MenuDbSchemka.MenuTable.ColsTableReceipts.IMAGE_PREVIEW,menu.getPhotoPreviewName());
        values.put(MenuDbSchemka.MenuTable.ColsTableReceipts.INGREDIENTS,menu.getIngredients());
        values.put(MenuDbSchemka.MenuTable.ColsTableReceipts.TITLE,menu.getTitle());
        return values;
    }

//    private ContentValues forMenuStep(MenuSteps menuSteps){
//
//    }

    public int Delete(String Selection,String[] SelectionArgs){
        int count = sqLiteDatabase.delete(MenuDbSchemka.MenuTable.NAME,Selection,SelectionArgs);
        return count;
    }

    public MenuReceipt getMenu(String[] projection, String Selection, String[] SelectionArgs){
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(MenuDbSchemka.MenuTable.NAME);

        Cursor cursor = sqLiteQueryBuilder.query(sqLiteDatabase,projection,Selection,SelectionArgs,null,null,null);
        MenuReceipt menu = null;
        if(cursor.moveToFirst()){
            do {
                menu = new MenuReceipt();
                menu.setId(cursor.getInt(cursor.getColumnIndex(MenuDbSchemka.MenuTable.ColsTableReceipts.id)));
                menu.setDescription(cursor.getString(cursor.getColumnIndex(MenuDbSchemka.MenuTable.ColsTableReceipts.DESCRIPTION)));
                cursor.getString(cursor.getColumnIndex(MenuDbSchemka.MenuTable.ColsTableReceipts.IMAGE_PREVIEW));
                menu.setIngredients(cursor.getString(cursor.getColumnIndex(MenuDbSchemka.MenuTable.ColsTableReceipts.INGREDIENTS)));
                menu.setTitle(cursor.getString(cursor.getColumnIndex(MenuDbSchemka.MenuTable.ColsTableReceipts.TITLE)));
                menu.setPhoto(loadImage(ICON_FOLDER,cursor.getString(cursor.getColumnIndex(MenuDbSchemka.MenuTable.ColsTableReceipts.IMAGE_PREVIEW))));
                menu.setPhotoFull(loadImage(ICON_FOLDER_FULL,cursor.getString(cursor.getColumnIndex(MenuDbSchemka.MenuTable.ColsTableReceipts.IMAGE_DESCRIPTION))));
            }while(cursor.moveToNext());
        }

        return menu;
    }

    private Bitmap loadImage(String folder, String name) {
        InputStream inputStream = null;
        Bitmap bitmap = null;
        try {
            inputStream = mContext.getApplicationContext().getAssets().open(folder + "/" + name);
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            bitmap = ((BitmapDrawable) drawable).getBitmap();
            inputStream.close();
        } catch (IOException ioe) {
        }

        return bitmap;
    }

//    private MenuSteps getMenuSteps(long id){
//
//    }
}
