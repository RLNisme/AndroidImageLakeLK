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
public class CreditUnitPrice {
    private int credit_unit_price_id;
    private double per_image;
    private double old_per_image;

    public int getCredit_unit_price_id() {
        return credit_unit_price_id;
    }

    public void setCredit_unit_price_id(int credit_unit_price_id) {
        this.credit_unit_price_id = credit_unit_price_id;
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
}
