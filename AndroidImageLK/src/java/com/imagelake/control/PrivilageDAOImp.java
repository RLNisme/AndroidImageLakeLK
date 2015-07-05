/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.Privilages;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public class PrivilageDAOImp implements PrivilageDAO{

    @Override
    public List<Privilages> listAll(int user_type_user_type_id) {
        List<Privilages> privilagelist=new ArrayList<Privilages>();
        try {
            String sql="SELECT * FROM privilages WHERE user_type_user_type_id=? && state='1'";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, user_type_user_type_id);
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()) {     
                if(!rs.isFirst()){
                Privilages privilages=new Privilages();
                privilages.setPrivilages_id(rs.getInt(1));
                privilages.setInterface_interface_id(rs.getInt(3));
                privilages.setUser_type_user_type_id(rs.getInt(2));
                privilages.setState(rs.getInt(4));
                System.out.println(rs.getInt(3)+"");
                privilagelist.add(privilages);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("privilage class listAll done");
        return privilagelist;
    }
    
    

    @Override
    public boolean addPrivilage(int uid,int ifid,int state) {
        boolean ok=false;
        try {
            String sql="INSERT INTO privilages(user_type_user_type_id,interfaces_interfaces_id,state) VALUES(?,?,?)";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, uid);
            ps.setInt(2, ifid);
            ps.setInt(3, state);
            int i=ps.executeUpdate();
            if(i>0){
                ok=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public boolean updatePrivilage(int uid, int ifid, int state) {
        boolean ok=false;
        try {
            String sql="UPDATE privilages SET state=? WHERE user_type_user_type_id=? AND interfaces_interfaces_id=?";
            
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, state);
            ps.setInt(2, uid);
            ps.setInt(3, ifid);
            int i=ps.executeUpdate();
            if(i>0){
                ok=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public Privilages getFirstPrivilage(int uid) {
        Privilages pi=null;
        try {
            String sql="SELECT * FROM privilages WHERE user_type_user_type_id=? AND state='1' LIMIT 1";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, uid);
            
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                System.out.println(rs.getInt(3));
                pi=new Privilages();
                pi.setPrivilages_id(rs.getInt(1));
                pi.setUser_type_user_type_id(rs.getInt(2));
                pi.setInterface_interface_id(rs.getInt(3));
                pi.setState(rs.getInt(4));
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }

    @Override
    public boolean checkPrivilages(int uid, int infid) {
       boolean ok=false;
        try {
            String sql="SELECT * FROM privilages WHERE user_type_user_type_id=? AND interfaces_interfaces_id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, uid);
            ps.setInt(2, infid);
            ResultSet rs=ps.executeQuery();
            
            if (rs.next()) {
                ok=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    
    
    
}
