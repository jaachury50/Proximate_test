package com.example.proximate_test.Models;

public class ModelProducts {
    String title;
    String image;
    String longDescription;
    String shortDescription;


    public ModelProducts(String title, String image, String longDescription, String shortDescription) {
        this.title = title;
        this.image = image;
        this.longDescription = longDescription;
        this.shortDescription = shortDescription;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
