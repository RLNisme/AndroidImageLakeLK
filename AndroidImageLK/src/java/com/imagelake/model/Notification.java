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
public class Notification {
    
    private int notification_id;
    private String notification;
    private String date;
    private String time;
    private int user_user_id;
    private int state_state_id;
    private int noti_type_noti_type_id;

    public int getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(int notification_id) {
        this.notification_id = notification_id;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUser_user_id() {
        return user_user_id;
    }

    public void setUser_user_id(int user_user_id) {
        this.user_user_id = user_user_id;
    }

    public int getState_state_id() {
        return state_state_id;
    }

    public void setState_state_id(int state_state_id) {
        this.state_state_id = state_state_id;
    }

    public int getNoti_type_noti_type_id() {
        return noti_type_noti_type_id;
    }

    public void setNoti_type_noti_type_id(int noti_type_noti_type_id) {
        this.noti_type_noti_type_id = noti_type_noti_type_id;
    }
    
}
