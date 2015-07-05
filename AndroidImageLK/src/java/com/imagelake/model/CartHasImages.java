/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.model;

import java.util.Date;

/**
 *
 * @author Lakmal
 */
public class CartHasImages {
    private int cart_has_images_id;
    private int img_id;
    private int subimg_id;
    private int is_purchase;
    private int cart_id;
    private Date date;
    private int credits;
    private int ratting;
    
    public int getCart_has_images_id() {
        return cart_has_images_id;
    }

    public void setCart_has_images_id(int cart_has_images_id) {
        this.cart_has_images_id = cart_has_images_id;
    }

    public int getImg_id() {
        return img_id;
    }

    public void setImg_id(int img_id) {
        this.img_id = img_id;
    }

    public int getSubimg_id() {
        return subimg_id;
    }

    public void setSubimg_id(int subimg_id) {
        this.subimg_id = subimg_id;
    }

    public int getIs_purchase() {
        return is_purchase;
    }

    public void setIs_purchase(int is_purchase) {
        this.is_purchase = is_purchase;
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getRatting() {
        return ratting;
    }

    public void setRatting(int ratting) {
        this.ratting = ratting;
    }
}
