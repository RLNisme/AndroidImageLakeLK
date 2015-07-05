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
public class Privilages {
    private int privilages_id;
    private int user_type_user_type_id;
    private int interface_interface_id;
    private int state;
    
    public int getPrivilages_id() {
        return privilages_id;
    }

    public void setPrivilages_id(int privilages_id) {
        this.privilages_id = privilages_id;
    }

    public int getUser_type_user_type_id() {
        return user_type_user_type_id;
    }

    public void setUser_type_user_type_id(int user_type_user_type_id) {
        this.user_type_user_type_id = user_type_user_type_id;
    }

    public int getInterface_interface_id() {
        return interface_interface_id;
    }

    public void setInterface_interface_id(int interface_interface_id) {
        this.interface_interface_id = interface_interface_id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
