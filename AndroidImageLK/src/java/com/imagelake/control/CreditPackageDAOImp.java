/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.CreditsPackage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public class CreditPackageDAOImp implements CreditPackageDAO{

    @Override
    public List<CreditsPackage> listAll() {
        List<CreditsPackage> list=new ArrayList<CreditsPackage>();
        try {
            String sql="SELECT * FROM credits_package";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                CreditsPackage cp=new CreditsPackage();
                cp.setCreditpack_id(rs.getInt(1));
                cp.setCredits(rs.getInt(2));
                
                cp.setCredit_unit_price_id(rs.getInt(3));
                cp.setState(rs.getInt(4));
                cp.setDuration(rs.getInt(5));
                cp.setPer_image(rs.getDouble(6));
                cp.setOld_per_image(rs.getDouble(7));
                cp.setDiscount(rs.getInt(8));
                list.add(cp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public CreditsPackage getCreditPackage(int cre_id) {
        CreditsPackage cp=null;
        try {
            String sql="SELECT * FROM credits_package WHERE creditpack_id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, cre_id);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {  
                cp=new CreditsPackage();
                cp.setCreditpack_id(rs.getInt(1));
                cp.setCredits(rs.getInt(2));
                
                cp.setCredit_unit_price_id(rs.getInt(3));
                cp.setState(rs.getInt(4));
                cp.setDuration(rs.getInt(5));
                 cp.setPer_image(rs.getDouble(6));
                cp.setOld_per_image(rs.getDouble(7));
                cp.setDiscount(rs.getInt(8));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cp;
    }

    @Override
    public boolean updatePackage(CreditsPackage cp) {
        boolean ok=false;
        try {
            String sql="UPDATE credits_package SET credits=?,credit_unit_price_id=?,state=?,duration=?,per_image=?,old_per_image=?,discount=? WHERE creditpack_id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, cp.getCredits());
            ps.setInt(2, 1);
            ps.setInt(3, cp.getState());
            ps.setInt(4, cp.getDuration());
            ps.setDouble(5, cp.getPer_image());
            ps.setDouble(6, cp.getOld_per_image());
            
            ps.setInt(7, cp.getDiscount());
            ps.setInt(8, cp.getCreditpack_id());
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
    public boolean duplicatePackage(CreditsPackage cp) {
        boolean ok=false;
        try {
            String sql="SELECT * FROM credits_package WHERE credits=? AND duration=? AND per_image=? AND old_per_image=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, cp.getCredits());
            ps.setInt(2, cp.getDuration());
            ps.setDouble(3, cp.getPer_image());
            ps.setDouble(4, cp.getOld_per_image());
            
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                ok=true;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public boolean insertPackage(CreditsPackage cp) {
        boolean ok=false;
        try {
           String sql="INSERT INTO credits_package(credits,credit_unit_price_id,state,duration,per_image,old_per_image,discount) VALUES(?,?,?,?,?,?,?)"; 
           PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
           ps.setInt(1, cp.getCredits());
           ps.setInt(2, cp.getCredit_unit_price_id());
           ps.setInt(3, cp.getState());
           ps.setInt(4, cp.getDuration());
           ps.setDouble(5, cp.getPer_image());
           ps.setDouble(6, cp.getOld_per_image());
           ps.setInt(7, cp.getDiscount());
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
