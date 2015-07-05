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
public class Interfaces {
    private int interface_id;
    private String url;
    private String display_name;
    private int state;
    private int img_id;
    
    public int getInterface_id() {
        return interface_id;
    }

    public void setInterface_id(int interface_id) {
        this.interface_id = interface_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    /**
     * @return the img_id
     */
    public int getImg_id() {
        return img_id;
    }

    /**
     * @param img_id the img_id to set
     */
    public void setImg_id(int img_id) {
        this.img_id = img_id;
    }
}
