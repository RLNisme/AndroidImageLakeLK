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
public class AdminPackageIncome {
    private int admin_pck_id;
    private int uhp_id;
    private double total;

    public int getAdmin_pck_id() {
        return admin_pck_id;
    }

    public void setAdmin_pck_id(int admin_pck_id) {
        this.admin_pck_id = admin_pck_id;
    }

    public int getUhp_id() {
        return uhp_id;
    }

    public void setUhp_id(int uhp_id) {
        this.uhp_id = uhp_id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
