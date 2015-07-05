/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.AdminNotification;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Lakmal
 */
public class AdminNotificationDAOImp {
    public int insertNotificaation(AdminNotification an){
        int ok=0;
        try {
            String sql="INSERT INTO notification(notification,type,user_id,dis,date) VALUES(?,?,?,?,?)";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setString(1, an.getNotification());
            ps.setInt(2, an.getType());
            ps.setInt(3, an.getUser_id());
            ps.setInt(4, an.getShow());
            ps.setString(5, an.getDate());
            
            
            int i=ps.executeUpdate();
            if(i>0){
                String sql1="SELECT LAST_INSERT_ID()";
                PreparedStatement ps1=DBFactory.getConnection().prepareStatement(sql1);
                ResultSet rs=ps1.executeQuery();
                if(rs.next()){
                    ok=rs.getInt(1);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }
    
}
