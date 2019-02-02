package com.example.a123.recepts_rebuild_var_2.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MenuReceipt {

    private int id;
    private String description;
    private String ingredients;
    private Bitmap photoPreview;
    private Bitmap photoFull;
    private String mTitle;
    private String photoFullName;
    private String photoPreviewName;
    private List<MenuSteps> mMenuSteps;
    private int visibleornot = View.INVISIBLE;


    public String getPhotoFullName() {
        return photoFullName;
    }

    public void setPhotoFullName(String photoFullName) {
        this.photoFullName = photoFullName;
    }

    public String getPhotoPreviewName() {
        return photoPreviewName;
    }

    public void setPhotoPreviewName(String photoPreviewName) {
        this.photoPreviewName = photoPreviewName;
    }

    public MenuReceipt() {
        mMenuSteps = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public Bitmap getPhoto() {
        return photoPreview;
    }

    public void setPhoto(byte[] photo) {
        this.photoPreview = BitmapFactory.decodeByteArray(photo, 0, photo.length);
    }

    public void setPhoto(Bitmap photo) {
        this.photoPreview = photo;
    }


    public List<MenuSteps> getMenuSteps() {
        return mMenuSteps;
    }

    public void setMenuSteps(List<MenuSteps> menuSteps) {
        mMenuSteps = menuSteps;
    }

    public Bitmap getPhotoFull() {
        return photoFull;
    }

    public void setPhotoFull(Bitmap photoFull) {
        this.photoFull = photoFull;
    }
}
