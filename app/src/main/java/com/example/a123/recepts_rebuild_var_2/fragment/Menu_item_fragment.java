package com.example.a123.recepts_rebuild_var_2.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
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
import android.widget.Toast;

import com.example.a123.recepts_rebuild_var_2.model.Menu;
import com.example.a123.recepts_rebuild_var_2.model.MenuLab;
import com.example.a123.recepts_rebuild_var_2.R;
import com.example.a123.recepts_rebuild_var_2.model.Str;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Menu_item_fragment extends Fragment {
    private static final String ARG_MENU_ID = "menu_id";
    private String s;
    private List<String> description;
    private List<String> step_for_desc;
    private String text_descriptiont;
    private String text_ingredients;
    private int dotscount;
    private ImageView[] dots;
    String[] sam_recept;
    List<String> images1;


    private Menu mMenu;
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
        MenuLab.get(getActivity()).updateMenu(mMenu);
    }

    public static Menu_item_fragment newInstance(UUID menuId){
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
        UUID menuId = (UUID) getArguments().getSerializable(ARG_MENU_ID);
        mMenu = MenuLab.get(getActivity()).getMenu(Integer.parseInt(menuId.toString()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== R.id.set_izb) {
            MenuLab.get(getActivity()).addToBase(mMenu);
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
        AssetManager manager = getActivity().getAssets();
        images1 = new ArrayList<>();
        mViewPager = (ViewPager) v.findViewById(R.id.pager_for_images1);
        sliderDotspanel = (LinearLayout) v.findViewById(R.id.SliderDots);
        s = write_description(getActivity().getAssets(),"sam_recept/recept" + mMenu.getId() + ".txt");
        s = s.replace("\n\n\n", "``");
        s = s.replace("\n\n", "``");
        sam_recept = Str.splitString(s, "``");
        images1 = find_images(sam_recept);
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
        Drawable drawable = Menu_Fragment.loadDrawable(getActivity().getAssets(), "icon_full/"+mMenu.getPhotoDescription());
        mImageView.setImageDrawable(drawable);
        mTextView = (TextView)v.findViewById(R.id.text_detaled);
        text_descriptiont = write_description(getActivity().getAssets(),"opisanie/opis"+mMenu.getId()+".txt");
        mTextView.setText(text_descriptiont);
        mIngredients = (TextView)v.findViewById(R.id.ingredients);
        text_ingredients = write_description(getActivity().getAssets(), "ingredienti/ing"+mMenu.getId()+".txt");
        mIngredients.setText(text_ingredients);
        mViewPager = (ViewPager)v.findViewById(R.id.pager_for_images1);
        for(int i=0;i<sam_recept.length;i++) {
            Toast.makeText(getActivity(), sam_recept[i], Toast.LENGTH_SHORT).show();
        }

        return v;
    }

    private List<String> find_images(String[] s){
        String timed = "";
        List<String> images = new ArrayList<>();
        description = new ArrayList<>();
        for(int i=0;i<s.length;i++){
            if(s[i].contains(".jpg")){
                images.add(s[i]);
            }else {
                if(s[i].contains("Шаг")) {
                    timed = s[i]+" ";
                }else{
                    description.add(timed+s[i]);
                    timed = "";
                }
            }
        }
        return images;
    }


    private String write_description(AssetManager manager, String fileName){
        try{
            InputStreamReader istream = new InputStreamReader(manager.open(fileName));
            BufferedReader in = new BufferedReader(istream);
            String lines = in.readLine();
            String lines1;
            while((lines1 = in.readLine())!=null){
                if (lines1 != "\n") {
                    lines+="\n"+lines1;
                }else{
                    lines+=lines1;
                }
            }

            istream.close();
            in.close();
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    private class ViewPagerAdapter extends PagerAdapter {

        private Context context;
        private LayoutInflater layoutInflater;


        public ViewPagerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return images1.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.for_second_view_pager, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image_for_recepts);
            Drawable drawable = Menu_Fragment.loadDrawable(getActivity().getAssets(), "image/"+images1.get(position));
            imageView.setImageDrawable(drawable);
            mDescription = (TextView)view.findViewById(R.id.description);
            mStep = (TextView)view.findViewById(R.id.step);
            if(position<images1.size()-1) {
                mDescription.setText(description.get(position));
            }else{
                mDescription.setText(description.get(position)+"\n"+description.get(position+1));
            }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }

    }
}

