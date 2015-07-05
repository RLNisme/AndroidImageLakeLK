/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.model;

/**
 *
 * @author Lakmal
 */
public class SubscriptionPackage {
    private int subscription_id;
    private int duration;
    private int subscription_unit_price_id;
    private int subscription_type_id;
    private int state;
    private int count_id;
    private double per_image;
    private double old_per_images;
    private int discount;
    public int getSubscription_id() {
        return subscription_id;
    }

    public void setSubscription_id(int subscription_id) {
        this.subscription_id = subscription_id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    

    public int getSubscription_type_id() {
        return subscription_type_id;
    }

    public void setSubscription_type_id(int subscription_type_id) {
        this.subscription_type_id = subscription_type_id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCount_id() {
        return count_id;
    }

    public void setCount_id(int count_id) {
        this.count_id = count_id;
    }

    public int getSubscription_unit_price_id() {
        return subscription_unit_price_id;
    }

    public void setSubscription_unit_price_id(int subscription_unit_price_id) {
        this.subscription_unit_price_id = subscription_unit_price_id;
    }

    public double getPer_image() {
        return per_image;
    }

    public void setPer_image(double per_image) {
        this.per_image = per_image;
    }

    public double getOld_per_images() {
        return old_per_images;
    }

    public void setOld_per_images(double old_per_images) {
        this.old_per_images = old_per_images;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
