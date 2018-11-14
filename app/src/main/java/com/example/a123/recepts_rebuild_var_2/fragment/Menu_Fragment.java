package com.example.a123.recepts_rebuild_var_2.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
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

import com.example.a123.recepts_rebuild_var_2.model.Menu;
import com.example.a123.recepts_rebuild_var_2.activity.MenuActivity;
import com.example.a123.recepts_rebuild_var_2.model.MenuLab;
import com.example.a123.recepts_rebuild_var_2.R;

import java.io.IOException;
import java.io.InputStream;
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
//        if(item.getItemId()==R.id.go_to_izb){
//            Intent intent = new Intent(Menu_Fragment.this.getActivity(),IzbActivity.class);
//            intent.putExtra(favorites,true);
//            startActivityForResult(intent,favorites_code);
//            return true;
//        }
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
        List<Menu> menus = menuLab.getMenus();
        if(mAdapter==null) {
            mAdapter = new MenuAdapter(menus);
            mMenuRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.notifyDataSetChanged();
        }
    }

    public static Drawable loadDrawable(AssetManager manager, String fileName) {
        try{
            InputStream in = manager.open(fileName);
            Drawable drawable = Drawable.createFromStream(in, null);
            in.close();
            return drawable;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private class MenuAdapter extends RecyclerView.Adapter<MenuHolder>{

        private List<Menu> mMenus;

        public MenuAdapter(List<Menu> menu){
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
            Menu menus = mMenus.get(position);
            holder.bind(menus);
        }

        @Override
        public int getItemCount() {
            return mMenus.size();
        }
    }

    private class MenuHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Menu mMenu;
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

        public void bind(Menu menu){
            mMenu = menu;
            mTextView.setText(mMenu.getTitle());
            AssetManager manager = getActivity().getAssets();
            Drawable drawable = loadDrawable(getActivity().getAssets(), "icon/"+mMenu.getPhoto());
            mImageView.setImageDrawable(drawable);
            //mIzbImage.setVisibility(mMenu.getVisibleornot());
        }

        @Override
        public void onClick(View v) {
            Intent intent = MenuActivity.newIntent(getActivity(),mMenu.getId());
            startActivity(intent);
        }


    }
}
