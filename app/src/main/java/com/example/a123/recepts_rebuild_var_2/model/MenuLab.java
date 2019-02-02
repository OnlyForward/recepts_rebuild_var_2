package com.example.a123.recepts_rebuild_var_2.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.a123.recepts_rebuild_var_2.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuLab {
    private static final String TAG = "ImagePath";
    private static final String ICON_FOLDER = "icon";
    private static final String ICON_FOLDER_FULL = "icon_full";
    private static final String DESCRIPTION = "opisanie";
    private static final String INGREDIENTS = "ingredienti";
    private static final String Sam_Recept = "sam_recept";

    private AssetManager mAssetManager;
    private List<MenuReceipt> mMenus;


    private static MenuLab sMenuLab;
    private Context mContext;
    private String[] nazvanie;
    private String[] icon_name;

    public List<MenuReceipt> getMenus() {
        return mMenus;
    }

    public MenuReceipt getMenu(int position) {
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
        mMenus = new ArrayList<>();
        mAssetManager = context.getAssets();
        nazvanie = context.getResources().getStringArray(R.array.spisok_nazvaniy);
        icon_name = context.getResources().getStringArray(R.array.icon_name);
        for(int i=0;i<nazvanie.length;i++){
            MenuReceipt menu = new MenuReceipt();
            menu.setId(i);
            menu.setTitle(nazvanie[i]);
            menu.setPhoto(loadImage(ICON_FOLDER,icon_name[i]));
            menu.setPhotoFullName(icon_name[i]);
            menu.setPhotoPreviewName(icon_name[i]);
            menu.setPhotoFull(loadImage(ICON_FOLDER_FULL,icon_name[i]));
            menu.setDescription(loadInformation(DESCRIPTION,"opis"+i+".txt"));
            menu.setIngredients(loadInformation(INGREDIENTS,"ing"+i+".txt"));
            String s = loadInformation(Sam_Recept,"recept"+i+".txt");
            menu.setMenuSteps(MyReceptsSteps(StrString(s)));
            mMenus.add(menu);
        }
    }

    private List<MenuSteps> MyReceptsSteps(List<String> Steps){
        List<String> mySteps = new ArrayList<>();
        List<MenuSteps> mStepToReceiptsList = new ArrayList<>();
        int step = 1;

        for (String s : Steps) {
            mySteps.addAll(Arrays.asList(s.split("``")));
        }

        for (int i = 0;i<mySteps.size();i++){
            if(mySteps.get(i).contains("Шаг")){
                mySteps.set(i+2,mySteps.get(i)+"\n"+mySteps.get(i+2));
                MenuSteps stepToReceipts = new MenuSteps();
                stepToReceipts.setDescription(mySteps.get(i+2));
                stepToReceipts.setImage_step_Name(mySteps.get(i+1));
                stepToReceipts.setImage_step(loadImage("image",mySteps.get(i+1)));

                stepToReceipts.setStep(step);
                mStepToReceiptsList.add(stepToReceipts);
                i+=2;
            }else{
                if(mySteps.size()-2>i){
                    MenuSteps stepToReceipts = new MenuSteps();
                    stepToReceipts.setDescription(mySteps.get(i+1));
                    stepToReceipts.setImage_step_Name(mySteps.get(i));
                    stepToReceipts.setImage_step(loadImage("image",mySteps.get(i)));
                    stepToReceipts.setStep(step);
                    mStepToReceiptsList.add(stepToReceipts);
                    i+=1;
                }
            }
        }

        return mStepToReceiptsList;
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

    private String loadInformation(String folder, String name){
        InputStreamReader inputStream = null;
        BufferedReader reader = null;
        StringBuilder txt = new StringBuilder("");
        try {
            inputStream = new InputStreamReader(mContext.getApplicationContext().getAssets().open(folder + "/" + name),"UTF-8");
            reader = new BufferedReader(
                    inputStream);

            String line;
            while ((line = reader.readLine()) != null) {
                txt.append(line);

            }
        } catch (IOException ioe) {
            Log.e(TAG, "Не найдено", ioe);
        }finally {
            if (reader!=null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return txt.toString();
    }

    private List<String> StrString(String str){
        int step = 0;
        List<String> map = new ArrayList<>();
        for (String s: str.split("Шаг \\d")) {
            map.add("Шаг "+step+" "+s);
            step++;
        }
        map.remove(0);
        return map;
    }

    //    public static String[] PostDataBase(String id, String[] colums, String table, String key){
//        Cursor cursor = CommonAction.mDb.query(table, colums, key + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//        String[] information = new String[colums.length];
//        if(cursor.moveToFirst()){
//            for(int j=0;j<colums.length;j++){
//                String dd="";
//                dd=cursor.getString(j);
//                information[j] = dd;
//            }
//        }
//        cursor.close();
//        return information;
//    }
//
//    public static List<String[]> PostDataBaseList(String id, String[] colums, String table, String key){
//        List<String[]> information1 = new ArrayList<>();
//        Cursor cursor = CommonAction.mDb.query(table, colums, key + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//        boolean c = cursor.moveToFirst();
//        while(c){
//            String[] information = new String[colums.length];
//            for(int j=0;j<colums.length;j++){
//                String dd="";
//                dd=cursor.getString(j);
//                information[j] = dd;
//            }
//            information1.add(information);
//            c = cursor.moveToNext();
//        }
//        cursor.close();
//        return information1;
//    }
}
