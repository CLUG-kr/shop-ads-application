package com.example.user.pyenhalean.model;

public class CardViewItemDTO {
    public int imageView;
    public int manageButton;
    public String title;
    public String subtitle;
    public String price;

    public CardViewItemDTO(int imageView, String title, String subtitle, String price) {
        this.imageView = imageView;
        this.title = title;
        this.subtitle = subtitle;
        this.price = price;
    }
    public CardViewItemDTO(int imageView, int manageButton, String title, String subtitle, String price ) {
        this(imageView, title, subtitle, price);
        this.manageButton = manageButton;

    }
}