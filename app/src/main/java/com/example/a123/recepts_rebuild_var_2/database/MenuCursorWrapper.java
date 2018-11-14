package com.example.a123.recepts_rebuild_var_2.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.a123.recepts_rebuild_var_2.model.Menu;

import static com.example.a123.recepts_rebuild_var_2.database.MenuDbSchemka.MenuTable;

public class MenuCursorWrapper extends CursorWrapper {
    public MenuCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Menu getMenu(){
        int uuid = getInt(getColumnIndex(MenuTable.ColsTableReceipts.id));
        String title = getString(getColumnIndex(MenuTable.ColsTableReceipts.TITLE));
        String description = getString(getColumnIndex(MenuTable.ColsTableReceipts.DESCRIPTION));
        String ingredients = getString(getColumnIndex(MenuTable.ColsTableReceipts.INGREDIENTS));
        byte[] photoPreview = getBlob(getColumnIndex(MenuTable.ColsTableReceipts.IMAGE_PREVIEW));
        byte[] photoDescription = getBlob(getColumnIndex(MenuTable.ColsTableReceipts.IMAGE_DESCRIPTION));

        Menu menu = new Menu();
        menu.setName(title);
        menu.setPhoto(photoPreview);
        menu.setPhotoDescription(photoDescription);
        menu.setId(uuid);
        menu.setDescription(description);
        menu.setIngredients(ingredients);

        return menu;
    }
}
