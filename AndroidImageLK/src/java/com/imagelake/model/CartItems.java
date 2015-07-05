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
public class CartItems {
    private int imgId;
    private int subimg_Id;
    private int credit; 
    private String date;

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getSubimg_Id() {
        return subimg_Id;
    }

    public void setSubimg_Id(int subimg_Id) {
        this.subimg_Id = subimg_Id;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    
}
