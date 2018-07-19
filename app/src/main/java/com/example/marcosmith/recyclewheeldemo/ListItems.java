package com.example.marcosmith.recyclewheeldemo;

public class ListItems {
    private String head;
    private String desc;
    private String imageUrl;

    public ListItems(String head, String desc, String image) {
        this.head = head;
        this.desc = desc;
        this.imageUrl = image;
    }

    public String getHead() {
        return head;
    }

    public String getDesc() {
        return desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
