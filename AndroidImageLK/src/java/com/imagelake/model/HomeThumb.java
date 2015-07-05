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
public class HomeThumb {
    private int id;
    private int img_id;
    private int sub_id;
    private String div_thumb=null;
    private String div_cover=null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImg_id() {
        return img_id;
    }

    public void setImg_id(int img_id) {
        this.img_id = img_id;
    }

    public int getSub_id() {
        return sub_id;
    }

    public void setSub_id(int sub_id) {
        this.sub_id = sub_id;
    }

    public String getDiv_thumb() {
        return div_thumb;
    }

    public void setDiv_thumb(String div_thumb) {
        this.div_thumb = div_thumb;
    }

    public String getDiv_cover() {
        return div_cover;
    }

    public void setDiv_cover(String div_cover) {
        this.div_cover = div_cover;
    }
}
