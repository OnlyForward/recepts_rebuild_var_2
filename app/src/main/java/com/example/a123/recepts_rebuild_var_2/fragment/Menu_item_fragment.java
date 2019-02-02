package com.example.a123.recepts_rebuild_var_2.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a123.recepts_rebuild_var_2.R;
import com.example.a123.recepts_rebuild_var_2.model.MenuLab;
import com.example.a123.recepts_rebuild_var_2.model.MenuReceipt;
import com.example.a123.recepts_rebuild_var_2.model.MenuSteps;
import com.example.a123.recepts_rebuild_var_2.database.DbManager;

import java.util.ArrayList;
import java.util.List;

public class Menu_item_fragment extends Fragment {
    private static final String ARG_MENU_ID = "menu_id";
    private int dotscount;
    private ImageView[] dots;
    List<String> images1;


    private MenuReceipt mMenu;
    private TextView mDescription;
    private TextView mStep;
    LinearLayout sliderDotspanel;
    private ViewPager mViewPager;
    private TextView mTextView;
    private TextView mIngredients;
    private ImageView mImageView;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public static Menu_item_fragment newInstance(int menuId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_MENU_ID, menuId);

        Menu_item_fragment fragment = new Menu_item_fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        int menuId = (int) getArguments().getSerializable(ARG_MENU_ID);
        mMenu = MenuLab.get(getContext()).getMenu(menuId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== R.id.set_izb) {
            DbManager dbManager = new DbManager(getContext());

            dbManager.SaveReceipt(mMenu);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(android.view.Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_item_fragment,menu);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.menu_item_fragment,container,false);
        images1 = new ArrayList<>();
        mViewPager = (ViewPager) v.findViewById(R.id.pager_for_images1);
        sliderDotspanel = (LinearLayout) v.findViewById(R.id.SliderDots);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext());
        mViewPager.setAdapter(viewPagerAdapter);
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];
        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        mImageView = (ImageView)v.findViewById(R.id.image_detaled);
        mImageView.setImageBitmap(mMenu.getPhotoFull());
        mTextView = (TextView)v.findViewById(R.id.text_detaled);
        mTextView.setText(mMenu.getDescription());
        mIngredients = (TextView)v.findViewById(R.id.ingredients);
        mIngredients.setText(mMenu.getIngredients());

        return v;
    }

    private class ViewPagerAdapter extends PagerAdapter {

        private Context context;
        private LayoutInflater layoutInflater;
        private List<MenuSteps> menus;


        public ViewPagerAdapter(Context context) {
            this.context = context;
            menus = mMenu.getMenuSteps();
        }

        @Override
        public int getCount() {
            return menus.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.for_second_view_pager, null);
            MenuSteps mMenuReceipt = menus.get(position);
            ImageView imageView = (ImageView) view.findViewById(R.id.image_for_recepts);
            imageView.setImageBitmap(mMenuReceipt.getImage_step());
            mDescription = (TextView)view.findViewById(R.id.description);
            mDescription.setText(mMenuReceipt.getDescription());
            mStep = (TextView)view.findViewById(R.id.step);
            ViewPager vp = (ViewPager) container;

            vp.addView(view, 0);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            ViewPager vp = (ViewPager) container;
            View view = (View) object;
            vp.removeView(view);

        }
    }

}

