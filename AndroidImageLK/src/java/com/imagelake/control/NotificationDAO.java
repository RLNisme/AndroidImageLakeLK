/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

/**
 *
 * @author Lakmal
 */
public interface NotificationDAO {
    public boolean addNotification(String noti,String date,String time,int user_user_id,int state_state_id,int noti_type_noti_type_id);
     
}
