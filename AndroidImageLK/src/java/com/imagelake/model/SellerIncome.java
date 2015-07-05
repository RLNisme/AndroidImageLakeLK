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
public class SellerIncome {
    private int sell_inc_id;
    private int user_id;
    private double total;
    private double net_earning;

    public int getSell_inc_id() {
        return sell_inc_id;
    }

    public void setSell_inc_id(int sell_inc_id) {
        this.sell_inc_id = sell_inc_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getNet_earning() {
        return net_earning;
    }

    public void setNet_earning(double net_earning) {
        this.net_earning = net_earning;
    }
}
