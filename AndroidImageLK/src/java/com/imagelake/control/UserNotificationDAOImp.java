/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Lakmal
 */
public class UserNotificationDAOImp {
    
    public String getNotification(int uid,int status){
        StringBuffer sb=null;
        try {
            sb=new StringBuffer("{'notifications':{"); 
                sb.append("'name':'usnotification',");
                sb.append("'usnoti':[");    
            
                String sql3="SELECT * FROM upload_notification WHERE user_user_id=? AND state_state_id=? ORDER BY date DESC";
                    PreparedStatement ps3=DBFactory.getConnection().prepareStatement(sql3);
                    ps3.setInt(1, uid);
                    ps3.setInt(2, status);
                System.gc();
                    ResultSet rs3=ps3.executeQuery();
                    while (rs3.next()) {
                        if(rs3.isLast()){
                        sb.append("{'id':'"+rs3.getString(1)+"','notice':'"+rs3.getString(2)+"','date':'"+rs3.getString(3)+"','time':'"+rs3.getString(4)+"','usid':'"+rs3.getString(5)+"','status':'"+rs3.getString(6)+"','type':'"+rs3.getString(7)+"'}");
                        if(status==1){
                        updateUserNotofication(rs3.getInt(1),2);
                        }
                        }else{
                        sb.append("{'id':'"+rs3.getString(1)+"','notice':'"+rs3.getString(2)+"','date':'"+rs3.getString(3)+"','time':'"+rs3.getString(4)+"','usid':'"+rs3.getString(5)+"','status':'"+rs3.getString(6)+"','type':'"+rs3.getString(7)+"'},");
                        if(status==1){
                        updateUserNotofication(rs3.getInt(1),2);
                        }
                        }
                    System.gc();
                    }
                
                
                sb.append("],");
                sb.append("'count':'"+notificationCount(uid, 2)+"'");
                sb.append("}");
                sb.append("}");
                System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    
    
    public void updateUserNotofication(int notiid,int state){
         try {
            String sql="UPDATE upload_notification SET state_state_id=? WHERE noti_id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, state);
            ps.setInt(2, notiid);
            int i=ps.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public int notificationCount(int uid,int status){
        int i=0;
        try {
            String sql="SELECT * FROM upload_notification WHERE user_user_id=? AND state_state_id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, uid);
            ps.setInt(2, status);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
    
    public boolean updateAllNotifications(int uid,int state){
        boolean ok=false;
        
        try {
            String sql="UPDATE upload_notification SET state_state_id='3' WHERE user_user_id=? AND state_state_id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, uid);
            ps.setInt(2, state);
            int i=ps.executeUpdate();
            if(i>0){
                ok=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }
    
}
