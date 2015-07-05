/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.AdminNotification;
import com.imagelake.model.Notification;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public class AdminHasNotificationDAOImp {
    
    public boolean insertNotification(List<String> li,int notiID,int status){
        boolean ok=false;
        try {
            String sql="";
            PreparedStatement ps;
            int i=0;
            for (String string : li) {
               sql="INSERT INTO admin_has_notification(admin_id,noti_id,status) VALUES(?,?,?)";
               ps=DBFactory.getConnection().prepareStatement(sql);
               ps.setInt(1,Integer.parseInt(string));
               ps.setInt(2,notiID);
               ps.setInt(3, status);
               
               i=ps.executeUpdate();
            }
            if(i>0){
                ok=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }
    
    
    public String getAllNotification(int uid,int status){
        StringBuffer sb=null;
        AdminNotification an=null;
        try {
                sb=new StringBuffer("{'notifications':{"); 
                sb.append("'name':'allnotification',");
                sb.append("'allnoti':[");    
            
                 String sql3="SELECT * FROM admin_has_notification WHERE admin_id=? AND status=? ORDER BY id DESC";
                    PreparedStatement ps3=DBFactory.getConnection().prepareStatement(sql3);
                    ps3.setInt(1, uid);
                    ps3.setInt(2, status);
                    System.gc();
                    ResultSet rs3=ps3.executeQuery();
                    while (rs3.next()) {
                        if(rs3.isLast()){
                             sb.append("{'id':'"+rs3.getString(1)+"','adminid':'"+rs3.getString(2)+"','notification':");
                                an=new AdminHasNotificationDAOImp().getNotification(rs3.getInt(3));
                                    sb.append("{'id':'"+an.getId()+"','notice':'"+an.getNotification()+"','type':'"+an.getType()+"','userid':'"+an.getUser_id()+"','show':'"+an.getShow()+"','date':'"+an.getDate()+"'}");
                             sb.append(",'status':'"+rs3.getInt(4)+"'}");
                             if(status==1){
                             updateAdminHasNotification(rs3.getInt(1),2);
                             }
                        }else{
                             sb.append("{'id':'"+rs3.getString(1)+"','adminid':'"+rs3.getString(2)+"','notification':");
                                an=new AdminHasNotificationDAOImp().getNotification(rs3.getInt(3));
                                    sb.append("{'id':'"+an.getId()+"','notice':'"+an.getNotification()+"','type':'"+an.getType()+"','userid':'"+an.getUser_id()+"','show':'"+an.getShow()+"','date':'"+an.getDate()+"'}");
                             sb.append(",'status':'"+rs3.getInt(4)+"'},");
                             if(status==1){
                             updateAdminHasNotification(rs3.getInt(1),2);
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
    
    public AdminNotification getNotification(int notid){
        AdminNotification no=null;
        try {
            String sql="SELECT * FROM notification WHERE id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, notid);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                no=new AdminNotification();
                no.setDate(rs.getString(6));
                no.setId(rs.getInt(1));
                no.setNotification(rs.getString(2));
                no.setShow(rs.getInt(5));
                no.setType(rs.getInt(3));
                no.setUser_id(rs.getInt(4));
                
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return no;
    }
    
    
    public void updateAdminHasNotification(int notid,int status){
        try {
            String sql="UPDATE admin_has_notification SET status=? WHERE id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, status);
            ps.setInt(2, notid);
            int i=ps.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public int notificationCount(int aid,int status){
        int i=0;
        try {
            String sql="SELECT * FROM admin_has_notification WHERE admin_id=? AND status=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, aid);
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
            String sql="UPDATE admin_has_notification SET status='3' WHERE admin_id=? AND status=?";
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
