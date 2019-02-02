package com.example.a123.recepts_rebuild_var_2.model;

import android.graphics.Bitmap;

public class MenuSteps {

    private int step;
    private Bitmap image_step;
    private String image_step_Name;
    private String description;

    public String getImage_step_Name() {
        return image_step_Name;
    }

    public void setImage_step_Name(String image_step_Name) {
        this.image_step_Name = image_step_Name;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public Bitmap getImage_step() {
        return image_step;
    }

    public void setImage_step(Bitmap image_step) {
        this.image_step = image_step;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
