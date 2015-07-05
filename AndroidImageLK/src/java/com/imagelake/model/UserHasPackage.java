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
public class UserHasPackage {
    private int uhp_id;
    private int package_id;
    private int user_id;
    private String purchase_date;
    private String expire_date;
    private int package_type;
    private int download_count;
    private int credit_count;
    private int state;
    private String last_date;
    private int org_downloads;
    private double unit_price;
    private int duration;

    public int getUhp_id() {
        return uhp_id;
    }

    public void setUhp_id(int uhp_id) {
        this.uhp_id = uhp_id;
    }

    public int getPackage_id() {
        return package_id;
    }

    public void setPackage_id(int package_id) {
        this.package_id = package_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    public String getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(String expire_date) {
        this.expire_date = expire_date;
    }

    public int getPackage_type() {
        return package_type;
    }

    public void setPackage_type(int package_type) {
        this.package_type = package_type;
    }

    public int getDownload_count() {
        return download_count;
    }

    public void setDownload_count(int download_count) {
        this.download_count = download_count;
    }

    public int getCredit_count() {
        return credit_count;
    }

    public void setCredit_count(int credit_count) {
        this.credit_count = credit_count;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getLast_date() {
        return last_date;
    }

    public void setLast_date(String last_date) {
        this.last_date = last_date;
    }

    public int getOrg_downloads() {
        return org_downloads;
    }

    public void setOrg_downloads(int org_downloads) {
        this.org_downloads = org_downloads;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
