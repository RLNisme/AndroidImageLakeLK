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
public class Cart {
    private  int cart_id;
    private int image_count;
    private int credit_count;
    private int subscription_unit_price_id;
    private int credit_unit_price_id;
    
    private int user_id;

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getImage_count() {
        return image_count;
    }

    public void setImage_count(int image_count) {
        this.image_count = image_count;
    }

    public int getCredit_count() {
        return credit_count;
    }

    public void setCredit_count(int credit_count) {
        this.credit_count = credit_count;
    }

    public int getSubscription_unit_price_id() {
        return subscription_unit_price_id;
    }

    public void setSubscription_unit_price_id(int subscription_unit_price_id) {
        this.subscription_unit_price_id = subscription_unit_price_id;
    }

    public int getCredit_unit_price_id() {
        return credit_unit_price_id;
    }

    public void setCredit_unit_price_id(int credit_unit_price_id) {
        this.credit_unit_price_id = credit_unit_price_id;
    }

   
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
