package com.ketomelts.main.domain;

/**
 * Created by Chaitanya on 2/26/17.
 */
public class Item {

    private String item;
    private String category;
    private Double price;

    public Item(String item, String category, Double price){
        this.item = item;
        this.category = category;
        this.price = price;
    }
}
