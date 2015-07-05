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
public class FreeTrial {
    private int freeId;
    private int days;
    private int download_count;
    private int package_type_id;

    public int getFreeId() {
        return freeId;
    }

    public void setFreeId(int freeId) {
        this.freeId = freeId;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getDownload_count() {
        return download_count;
    }

    public void setDownload_count(int download_count) {
        this.download_count = download_count;
    }

    public int getPackage_type_id() {
        return package_type_id;
    }

    public void setPackage_type_id(int package_type_id) {
        this.package_type_id = package_type_id;
    }

}
