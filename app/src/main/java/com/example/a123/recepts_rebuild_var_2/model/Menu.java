package com.example.a123.recepts_rebuild_var_2.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import java.util.List;

public class Menu {
    private int id;
    private String description;
    private String ingredients;
    private Bitmap photoPreview;
    private Bitmap photoDescription;
    private List<Bitmap> getPhotoDescriptionByStep;
    private String mTitle;
    private int visibleornot = View.INVISIBLE;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Bitmap getGetPhotoDescriptionByStep(int position) {
        return getPhotoDescriptionByStep.get(position);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getPhotoDescription() {
        return photoDescription;
    }

    public void setPhotoDescription(Bitmap photoDescription) {
        this.photoDescription = photoDescription;
    }

    public void setPhotoDescription(byte[] photo) {
        this.photoDescription = BitmapFactory.decodeByteArray(photo, 0, photo.length);
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

}
