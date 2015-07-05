/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.model;

import java.util.List;

/**
 *
 * @author Lakmal
 */
public class AdminResults {
    private List<Images> imgList=null;
    private List<User> userList=null;
    private List<CartHasImages> carthasimgList=null;

    public List<Images> getImgList() {
        return imgList;
    }

    public void setImgList(List<Images> imgList) {
        this.imgList = imgList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<CartHasImages> getCarthasimgList() {
        return carthasimgList;
    }

    public void setCarthasimgList(List<CartHasImages> carthasimgList) {
        this.carthasimgList = carthasimgList;
    }
}
