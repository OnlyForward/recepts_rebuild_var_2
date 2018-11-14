package com.example.a123.recepts_rebuild_var_2.activity;

import android.support.v4.app.Fragment;

import com.example.a123.recepts_rebuild_var_2.fragment.Menu_Fragment;

public class MainActivity extends AbstractFragManager {

    @Override
    protected Fragment createFragment(){
        return new Menu_Fragment();
    }
}
