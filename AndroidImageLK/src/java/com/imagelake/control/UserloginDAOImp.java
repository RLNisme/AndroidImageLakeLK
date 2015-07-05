/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.User;
import com.imagelake.model.Userlogin;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public class UserloginDAOImp implements UserloginDAO{

    @Override
    public boolean insert(Userlogin ul) {
                    System.out.println("insert");
                    boolean ok=false;
        try {
            String sql="INSERT INTO user_login(browser,ip_address,session_id,start_date,start_time,user_id,state,country,code) VALUES(?,?,?,?,?,?,?,?,?)";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,ul.getBrowser());
            ps.setString(2,ul.getIp_address());
            ps.setString(3,ul.getSession_id());
            ps.setString(4,ul.getStart_date());
            ps.setString(5,ul.getStart_time());
            ps.setInt(6,ul.getUser_user_id());
            ps.setInt(7,ul.getState());
            ps.setString(8,ul.getCountry());
            ps.setString(9,ul.getCode());
            
            int i=ps.executeUpdate();
            System.out.println(i+" update");
            if (i>0) {
                ok=true;
                String sql2="SELECT LAST_INSERT_ID() FROM user_login";
            Connection con1=DBFactory.getConnection();
            PreparedStatement ps2=con1.prepareStatement(sql2);
            ResultSet rs2=ps2.executeQuery();
                if (rs2.next()) {                    
                    System.out.println("IIIIIIIIIIIBBBBBBBBBBBBBBBB "+rs2.getInt(1));
                }
                
            } else {
                ok=false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }
    
    
    public int insertLogin(Userlogin ul) {
                    System.out.println("insert");
                    int ok=0;
        try {
            String sql="INSERT INTO user_login(browser,ip_address,session_id,start_date,start_time,user_id,state,country,code) VALUES(?,?,?,?,?,?,?,?,?)";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,ul.getBrowser());
            ps.setString(2,ul.getIp_address());
            ps.setString(3,ul.getSession_id());
            ps.setString(4,ul.getStart_date());
            ps.setString(5,ul.getStart_time());
            ps.setInt(6,ul.getUser_user_id());
            ps.setInt(7,ul.getState());
            ps.setString(8,ul.getCountry());
            ps.setString(9,ul.getCode());
            
             ok=ps.executeUpdate();
//            System.out.println(i+" update");
//            if (i>0) {
//                
//                String sql2="SELECT LAST_INSERT_ID() FROM user_login";
//            Connection con1=DBFactory.getConnection();
//            PreparedStatement ps2=con1.prepareStatement(sql2);
//            ResultSet rs2=ps2.executeQuery();
//                if (rs2.next()) {                    
//                    System.out.println("IIIIIIIIIIIBBBBBBBBBBBBBBBB "+rs2.getInt(1));
//                    ok=rs2.getInt(1);
//                }
//                
//            } 
            System.out.println("OK ="+ok);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }
    
                  
    @Override
    public boolean updateLogin(Userlogin ul) {
        boolean b=false;
        try {
            String sql="UPDATE user_login SET end_date=?,end_time=?,state=? WHERE user_login_id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setString(1,ul.getEnd_date());
            ps.setString(2,ul.getEnd_time());
            ps.setInt(3, ul.getState());
            ps.setInt(4, ul.getUser_login_id());
           
            
            int i=ps.executeUpdate();
            if(i>0){
                b=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    @Override
    public boolean checkInvalidSession(int uid, int state) {
         java.util.Date d=new java.util.Date();
        
        
        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm:ss");
        String loginTime=sdf.format(d);
        
        boolean ok=false;
   
        try {
            String sql="SELECT * FROM user_login WHERE user_id=? AND state=? AND end_date='0000-00-00'";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql); 
            ps.setInt(1, uid);
            ps.setInt(2, state);
            
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                System.out.println("there is a invalidate session");
                try {
                    String sql2="UPDATE user_login SET end_date='"+rs.getString(5)+"',end_time='"+loginTime+"',state='3' WHERE user_login_id='"+rs.getInt(1)+"'";
                    PreparedStatement ps2=DBFactory.getConnection().prepareStatement(sql2);
                    int up=ps2.executeUpdate();     
                    if(up>0){     
                        System.out.println("updating....");
                        ok=true; 
                    }
                } catch (Exception e) {
                    e.printStackTrace();    
                }
                
            }else{
            System.out.println("there is no invalide session");    
            ok=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public String listCurrentLogedUsers(int state) {
        StringBuffer sb=null;
        sb=new StringBuffer("{'userlogin':{"); 
           sb.append("'name':'loginrate',");
           sb.append("'rate':[");
        try {
            String sql="SELECT code,country,COUNT(user_login_id) FROM user_login WHERE state=? GROUP BY code";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, state);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                
                
                 if(rs.isLast()){
                    sb.append("{'code':'"+rs.getString(1)+"','name':'"+rs.getString(2)+"','value':'"+rs.getInt(3)+"','color':'#a7a737'}");
                }else{
                    sb.append("{'code':'"+rs.getString(1)+"','name':'"+rs.getString(2)+"','value':'"+rs.getInt(3)+"','color':'#a7a737'},");
                }
               
            }
            sb.append("]");
            sb.append("}");
            sb.append("}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    
    
    

}
