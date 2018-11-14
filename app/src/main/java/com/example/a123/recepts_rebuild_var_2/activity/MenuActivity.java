package com.example.a123.recepts_rebuild_var_2.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.a123.recepts_rebuild_var_2.fragment.Menu_item_fragment;

import java.util.UUID;

public class MenuActivity extends AbstractFragManager {
    private static final String EXTRA_MENU_ID = "com.example.a123.recepts_rebuild_var_2.menu_id";



    public static Intent newIntent(Context packageContext, int menuId){
        Intent intent = new Intent(packageContext, MenuActivity.class);
        intent.putExtra(EXTRA_MENU_ID, menuId);
        return intent;
    }

    @Override
    protected Fragment createFragment(){
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_MENU_ID);
        return Menu_item_fragment.newInstance(crimeId);
    }
}
