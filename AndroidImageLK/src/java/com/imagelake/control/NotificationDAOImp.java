/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author Lakmal
 */
public class NotificationDAOImp implements NotificationDAO{

    @Override
    public boolean addNotification(String noti, String date, String time, int user_user_id, int state_state_id, int noti_type_noti_type_id) {
        boolean ok=false;
        try {
            String sql="INSERT INTO upload_notification(notification,date,time,user_user_id,state_state_id,noti_type_noti_type_id)"
                    + "VALUE(?,?,?,?,?,?)";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, noti);
            ps.setString(2, date);
            ps.setString(3, time);
            ps.setInt(4, user_user_id);
            ps.setInt(5, state_state_id);
            ps.setInt(6, noti_type_noti_type_id);
            
            int i=ps.executeUpdate();
            if(i>0){
            ok=true;
            }else{
            
            ok=false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }
    
    
}
