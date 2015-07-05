/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.Percent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Lakmal
 */
public class PercentDAOImp {
    public Percent getPercentage(){
        Percent p=null;
        try {
            String sql="SELECT * FROM percent";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            
            ResultSet rs=ps.executeQuery();
            while (rs.next()) { 
                p=new Percent();
                p.setPercent_id(rs.getInt(1));
                p.setPercent(rs.getInt(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
        
    }
    
    public boolean updatePercentage(int id,int percent){
       boolean ok=false;
        try {
            String sql="UPDATE percent SET percent=? WHERE percent_id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, percent);
            ps.setInt(2, id);
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
