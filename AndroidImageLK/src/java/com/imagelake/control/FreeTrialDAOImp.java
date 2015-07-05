/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.FreeTrial;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public class FreeTrialDAOImp {
    public List<FreeTrial> getFreeTrail(){
        List<FreeTrial> fr=new ArrayList<FreeTrial>();
        try {
            String sql="SELECT * FROM free_trial";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) { 
                FreeTrial free=new FreeTrial();
                free.setFreeId(rs.getInt(1));
                free.setDays(rs.getInt(2));
                free.setDownload_count(rs.getInt(3));
                free.setPackage_type_id(rs.getInt(4));
                fr.add(free);
                System.gc();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fr;
    }
    
    public FreeTrial getAFreeTrail(int package_type_id){
        FreeTrial free=null;
        try {
            String sql="SELECT * FROM free_trial WHERE package_type_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, package_type_id);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) { 
                free=new FreeTrial();
                free.setFreeId(rs.getInt(1));
                free.setDays(rs.getInt(2));
                free.setDownload_count(rs.getInt(3));
                free.setPackage_type_id(rs.getInt(4));
                
                System.gc();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return free;
    }
    
    public String getPackageName(int pck_id){
        String name=null;
        try {
            String sql="SELECT package_type FROM packages WHERE pck_id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, pck_id);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                name=rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }
    
    public boolean updatePackage(FreeTrial ft){
        boolean ok=false;
        try {
            String sql="UPDATE free_trial SET days=?,download_count=? WHERE id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, ft.getDays());
            ps.setInt(2, ft.getDownload_count());
            ps.setInt(3, ft.getFreeId());
            
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
