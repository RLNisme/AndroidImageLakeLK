/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.model;

import com.mysql.jdbc.Blob;
import java.io.InputStream;

/**
 *
 * @author Lakmal
 */
public class ImagesSub {
   private int sub_images_id;
   private InputStream img_blob;
   private String img_url=null;
   private String dimention=null;
   private double price;
   private int credits;
   private long size_long;
   private String size_string=null;
   private int images_image_id;

    public int getSub_images_id() {
        return sub_images_id;
    }

    public void setSub_images_id(int sub_images_id) {
        this.sub_images_id = sub_images_id;
    }

    public InputStream getImg_blob() {
        return img_blob;
    }

    public void setImg_blob(InputStream img_blob) {
        this.img_blob = img_blob;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getDimention() {
        return dimention;
    }

    public void setDimention(String dimention) {
        this.dimention = dimention;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public long getSize_long() {
        return size_long;
    }

    public void setSize_long(long size_long) {
        this.size_long = size_long;
    }

    public String getSize_string() {
        return size_string;
    }

    public void setSize_string(String size_string) {
        this.size_string = size_string;
    }

    public int getImages_image_id() {
        return images_image_id;
    }

    public void setImages_image_id(int images_image_id) {
        this.images_image_id = images_image_id;
    }
   
}
