/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.Packages;
import com.imagelake.model.SubscriptionPackage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public class SubscriptionPackageDAOImp implements SubscriptionPackageDAO{

    @Override
    public List<SubscriptionPackage> listAll() {
        List<SubscriptionPackage> list=new ArrayList<SubscriptionPackage>();
        try {
            String sql="SELECT * FROM subscriptions_package ORDER BY subscription_id DESC";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                SubscriptionPackage sp=new SubscriptionPackage();
                sp.setCount_id(rs.getInt(6));
                sp.setDuration(rs.getInt(2));
                
                sp.setSubscription_unit_price_id(rs.getInt(3));
                sp.setState(rs.getInt(5));
                sp.setSubscription_id(rs.getInt(1));
                sp.setSubscription_type_id(rs.getInt(4));
                sp.setPer_image(rs.getDouble(7));
                sp.setOld_per_images(rs.getDouble(8));
                sp.setDiscount(rs.getInt(9));
                list.add(sp);
                System.gc();
            }
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    
    
    
    @Override
    public List<SubscriptionPackage> listByDownCount(int coid) {
        List<SubscriptionPackage> list=new ArrayList<SubscriptionPackage>();
        try {
            String sql="SELECT * FROM subscriptions_package WHERE count_id=? ORDER BY count_id";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, coid);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                SubscriptionPackage sp=new SubscriptionPackage();
                sp.setCount_id(rs.getInt(6));
                sp.setDuration(rs.getInt(2));
                
                sp.setSubscription_unit_price_id(rs.getInt(3));
                sp.setState(rs.getInt(5));
                sp.setSubscription_id(rs.getInt(1));
                sp.setSubscription_type_id(rs.getInt(4));
                sp.setPer_image(rs.getDouble(7));
                sp.setOld_per_images(rs.getDouble(8));
                sp.setDiscount(rs.getInt(9));
                list.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public SubscriptionPackage getSubscriptionPackage(int coid) {
       SubscriptionPackage sp=new SubscriptionPackage();
        try {
            String sql="SELECT * FROM subscriptions_package WHERE subscription_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, coid);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                sp.setCount_id(rs.getInt(6));
                sp.setDuration(rs.getInt(2));
                
                sp.setSubscription_unit_price_id(rs.getInt(3));
                sp.setState(rs.getInt(5));
                sp.setSubscription_id(rs.getInt(1));
                sp.setSubscription_type_id(rs.getInt(4));
                sp.setPer_image(rs.getDouble(7));
                sp.setOld_per_images(rs.getDouble(8));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sp;
    }

    @Override
    public SubscriptionPackage getSubscription(int sub_id) {
       
        SubscriptionPackage sp=null;
        try {
            String sql="SELECT * FROM subscriptions_package WHERE subscription_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, sub_id);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) { 
                sp=new SubscriptionPackage();
                sp.setCount_id(rs.getInt(6));
                sp.setDuration(rs.getInt(2));
                
                sp.setSubscription_unit_price_id(rs.getInt(3));
                sp.setState(rs.getInt(5));
                sp.setSubscription_id(rs.getInt(1));
                sp.setSubscription_type_id(rs.getInt(4));
                sp.setPer_image(rs.getDouble(7));
                sp.setOld_per_images(rs.getDouble(8));
                sp.setDiscount(rs.getInt(9));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sp;
    }

    @Override
    public String getSubscriptionType(int type_id) {
        String a="";
        try {
           String sql="SELECT subscriptions_type FROM subscription_type WHERE id=?";
        Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, type_id); 
            
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                System.gc();
                a=rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public List<Packages> getPackageList() {
       List<Packages> li=new ArrayList<Packages>();
        try {
            String sql="SELECT * FROM packages";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                Packages pk=new Packages();
                pk.setPid(rs.getInt(1));
                pk.setPackageType(rs.getString(2));
                li.add(pk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return li;
    }

    @Override
    public boolean updatePackage(SubscriptionPackage sp) {
        boolean ok=false;
        try {
           String sql="UPDATE subscriptions_package SET duration=?,state=?,count_id=?,per_image=?,old_per_image=?,discount=? WHERE subscription_id=?";
           PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
           ps.setInt(1, sp.getDuration());
           ps.setInt(2, sp.getState());
           ps.setInt(3, sp.getCount_id());
           ps.setDouble(4, sp.getPer_image());
           ps.setDouble(5, sp.getOld_per_images());
           ps.setInt(6, sp.getDiscount());
           ps.setInt(7, sp.getSubscription_id());
           
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
    public boolean insertPackage(SubscriptionPackage sp) {
        boolean ok=false;
        try {
            String sql="INSERT INTO subscriptions_package(duration,subscript_unit_price_id,subscription_type_id,state,count_id,per_image,old_per_image,discount) VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1,sp.getDuration());
            ps.setInt(2,1);
            ps.setInt(3,sp.getSubscription_type_id());
            ps.setInt(4,sp.getState());
            ps.setInt(5,sp.getCount_id());
            ps.setDouble(6,sp.getPer_image());
            ps.setDouble(7,sp.getOld_per_images());
            ps.setInt(8, sp.getDiscount());
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
    public boolean duplicatePackage(SubscriptionPackage sp) {
        boolean ok=false;
        try {
            String sql="SELECT subscription_id FROM subscriptions_package WHERE duration=? AND subscript_unit_price_id=? AND subscription_type_id=? AND count_id=? AND per_image=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, sp.getDuration());
            ps.setInt(2, 1);
            ps.setInt(3,sp.getSubscription_type_id());
           
            ps.setInt(4,sp.getCount_id());
            ps.setDouble(5,sp.getPer_image());
            
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
