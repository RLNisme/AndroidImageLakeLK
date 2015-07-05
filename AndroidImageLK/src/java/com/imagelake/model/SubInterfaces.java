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
public class SubInterfaces {
    private int sub_interfaces_id;
    private String url=null;
    private String display_name=null;

    public int getSub_interfaces_id() {
        return sub_interfaces_id;
    }

    public void setSub_interfaces_id(int sub_interfaces_id) {
        this.sub_interfaces_id = sub_interfaces_id;
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
}
