package com.example.a123.recepts_rebuild_var_2.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.a123.recepts_rebuild_var_2.R;
import com.example.a123.recepts_rebuild_var_2.database.MenuBaseHelper;
import com.example.a123.recepts_rebuild_var_2.database.MenuCursorWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.example.a123.recepts_rebuild_var_2.database.MenuDbSchemka.MenuTable;

public class MenuLab {
    private static final String TAG = "ImagePath";
    private static final String ICON_FOLDER = "icon";
    private static final String ICON_FOLDER_FULL = "icon_full";
    private static final String DESCRIPTION = "opisanie";
    private static final String INGREDIENTS = "ingredienti";

    private AssetManager mAssetManager;
    private List<Menu> mMenus;

    private static MenuLab sMenuLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private String[] nazvanie;
    private String[] icon_name;

    public List<Menu> getMenus() {
        return mMenus;
    }


    public List<Menu> getSavedMenu() {
        List<Menu> savedMenu = new ArrayList<>();

        MenuCursorWrapper cursorWrapper = queryMenu(null, null);

        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                savedMenu.add(cursorWrapper.getMenu());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }

        return savedMenu;
    }

    public Menu getSavedMenu(int id) {
        MenuCursorWrapper cursor = queryMenu(MenuTable.ColsTableReceipts.id + " =?", new String[]{String.valueOf(id)});
        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getMenu();
        } finally {
            cursor.close();
        }
    }

    public Menu getMenu(int position) {
        return mMenus.get(position);
    }

    public static MenuLab get(Context context) {
        if (sMenuLab == null) {
            sMenuLab = new MenuLab(context);
        }
        return sMenuLab;
    }

    private MenuLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new MenuBaseHelper(mContext).getWritableDatabase();
        mMenus = new ArrayList<>();
        mAssetManager = context.getAssets();
        nazvanie = context.getResources().getStringArray(R.array.spisok_nazvaniy);
        icon_name = context.getResources().getStringArray(R.array.icon_name);
        Menu menu = new Menu();
        for(int i=0;i<nazvanie.length;i++){
            menu.setId(i);
            menu.setTitle(nazvanie[i]);
            menu.setPhoto(loadImage(ICON_FOLDER,icon_name[i]));
            menu.setPhotoDescription(loadImage(ICON_FOLDER_FULL,icon_name[i]));
        }
//        loadImage();
    }

    private static ContentValues getContentValues(Menu menu) {
        ContentValues values = new ContentValues();
        values.put(MenuTable.ColsTableReceipts.id, menu.getId());
        //values.put(MenuTable.ColsTableReceipts.TITLE, menu.getTitle());
        values.put(MenuTable.ColsTableReceipts.DESCRIPTION, menu.getDescription());
        //values.put(MenuTable.ColsTableReceipts.IMAGE_DESCRIPTION, menu.getPhotoDescription());
        //values.put(MenuTable.ColsTableReceipts.IMAGE_PREVIEW, menu.getPhoto());

        ContentValues valuesSecondTable = new ContentValues();
        values.put(MenuTable.ColsTableReceiptsByStep.id, menu.getId());
        //values.put(MenuTable.ColsTableReceiptsByStep.STEPS_IMAGE, menu.());
        //values.put(MenuTable.ColsTableReceiptsByStep.DESCRIPTION, menu.getDescription());
        // values.put(MenuTable.ColsTableReceiptsByStep.STEPS_IMAGE,menu.getGetPhotoDescriptionByStep(position));

        return values;
    }

//    private void loadImage() {
//        String[] ImageNames = null;
//        try {
//            ImageNames = mAssetManager.list(ICON_FOLDER);
//            Log.i(TAG, "В файле " + ImageNames.length);
//            for (int i = 0; i < icon_name.length; i++) {
//                Menu menu = new Menu();
//                //menu.setImage(icon_name[i]);
//                menu.setTitle(nazvanie[i]);
//                //menu.setNumber(i);
//                mMenus.add(menu);
//            }
//        } catch (IOException ioe) {
//            Log.e(TAG, "Не найдено", ioe);
//        }
//    }

    private void loadInformation() {
        String[] ImageNames;
        InputStream inputStream = null;
        try {
            ImageNames = mAssetManager.list(ICON_FOLDER);
            Log.i(TAG, "В файле " + ImageNames.length);
            for (int i = 0; i < icon_name.length; i++) {
                Menu menu = new Menu();
                inputStream = mContext.getApplicationContext().getAssets().open(ICON_FOLDER + "/" + icon_name[i]);
                Drawable drawable = Drawable.createFromStream(inputStream, null);
                //Drawable drawable1 = Drawable.createFromPath("assets/"+ICON_FOLDER +"/"+ icon_name[i]);
                //menu.setPhoto(icon_name[i]);
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                menu.setTitle(nazvanie[i]);
                menu.setPhoto(bitmap);
                menu.setId(i);
                mMenus.add(menu);
                inputStream.close();
            }
        } catch (IOException ioe) {
            Log.e(TAG, "Не найдено", ioe);
        }
    }

    private Bitmap loadImage(String folder, String name) {
        String[] ImageNames;
        InputStream inputStream = null;
        Bitmap bitmap = null;
        try {
            ImageNames = mAssetManager.list(folder);
            Log.i(TAG, "В файле " + ImageNames.length);
            inputStream = mContext.getApplicationContext().getAssets().open(folder + "/" + name);
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            bitmap = ((BitmapDrawable) drawable).getBitmap();
            inputStream.close();
        } catch (IOException ioe) {
            Log.e(TAG, "Не найдено", ioe);
        }

        return bitmap;
    }

    public void addToBase(Menu menu) {
        ContentValues values = getContentValues(menu);
        mDatabase.insert(MenuTable.NAME, null, values);
    }

    public void updateMenu(Menu menu) {
        int uuidString = menu.getId();
        ContentValues values = getContentValues(menu);

        mDatabase.update(MenuTable.NAME, values, MenuTable.ColsTableReceipts.TITLE + " =?", new String[]{String.valueOf(uuidString)});
    }

    private MenuCursorWrapper queryMenu(String whereMenu, String[] whereArgs) {
        Cursor cursor = mDatabase.query(MenuTable.NAME, null, whereMenu, whereArgs, null, null, null);

        return new MenuCursorWrapper(cursor);
    }

    public void deleteToBase(Menu menu) {
        int uuidString = menu.getId();
        mDatabase.delete(MenuTable.NAME, " =?", new String[]{String.valueOf(uuidString)});
    }
}
