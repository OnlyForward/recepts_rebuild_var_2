package com.example.a123.recepts_rebuild_var_2.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a123.recepts_rebuild_var_2.R;
import com.example.a123.recepts_rebuild_var_2.activity.MenuActivity;
import com.example.a123.recepts_rebuild_var_2.model.MenuLab;
import com.example.a123.recepts_rebuild_var_2.model.MenuReceipt;

import java.util.List;

public class Menu_Fragment extends Fragment {

    private static String favorites = "favourites";
    private static final int favorites_code = 1;
    private boolean izb = false;

    private RecyclerView mMenuRecyclerView;
    private MenuAdapter mAdapter;

    @Override
    public void onResume() {
        super.onResume();
        UpdateUI();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(android.view.Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.list_fragment,menu);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(Activity.RESULT_OK == resultCode){
            return;
        }
        if(requestCode == favorites_code){
            izb = data.getBooleanExtra(favorites,false);
        }else{
            izb = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_fragment, container, false);
        mMenuRecyclerView = (RecyclerView)v.findViewById(R.id.reycler_view);
        mMenuRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        UpdateUI();

        return v;
    }

    private void UpdateUI(){

        MenuLab menuLab = MenuLab.get(getActivity());
        List<MenuReceipt> menus = menuLab.getMenus();
        if(mAdapter==null) {
            mAdapter = new MenuAdapter(menus);
            mMenuRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.notifyDataSetChanged();
        }
    }

    private class MenuAdapter extends RecyclerView.Adapter<MenuHolder>{

        private List<MenuReceipt> mMenus;

        public MenuAdapter(List<MenuReceipt> menu){
            mMenus = menu;
        }

        @NonNull
        @Override
        public MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new MenuHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull MenuHolder holder, int position) {
            MenuReceipt menus = mMenus.get(position);
            holder.bind(menus);
        }

        @Override
        public int getItemCount() {
            return mMenus.size();
        }
    }

    private class MenuHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private MenuReceipt mMenu;
        private TextView mTextView;
        private ImageView mImageView;
        private ImageView mIzbImage;

        public MenuHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_fragment1,parent,false));
            itemView.setOnClickListener(this);

            mTextView = (TextView)itemView.findViewById(R.id.text1);
            mImageView = (ImageView)itemView.findViewById(R.id.image1);
            mIzbImage = (ImageView)itemView.findViewById(R.id.red_flag1);
        }

        public void bind(MenuReceipt menu){
            mMenu = menu;
            mTextView.setText(mMenu.getTitle());
            mImageView.setImageBitmap(menu.getPhoto());
            //mIzbImage.setVisibility(mMenu.getVisibleornot());
        }

        @Override
        public void onClick(View v) {
            Intent intent = MenuActivity.newIntent(getActivity(),mMenu.getId());
            startActivity(intent);
        }


    }
}
