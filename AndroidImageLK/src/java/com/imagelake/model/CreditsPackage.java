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
public class CreditsPackage {
    private int creditpack_id;
    private int credits;
    private int credit_unit_price_id;
    
    private int duration;
    
    private int state;
    private double per_image;
    private double old_per_image;
    private int discount;
    
    public int getCreditpack_id() {
        return creditpack_id;
    }

    public void setCreditpack_id(int creditpack_id) {
        this.creditpack_id = creditpack_id;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    

    
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCredit_unit_price_id() {
        return credit_unit_price_id;
    }

    public void setCredit_unit_price_id(int credit_unit_price_id) {
        this.credit_unit_price_id = credit_unit_price_id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getPer_image() {
        return per_image;
    }

    public void setPer_image(double per_image) {
        this.per_image = per_image;
    }

    public double getOld_per_image() {
        return old_per_image;
    }

    public void setOld_per_image(double old_per_image) {
        this.old_per_image = old_per_image;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
    
}
