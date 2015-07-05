/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.SellerIncome;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Lakmal
 */
public class SellerIncomeDAOImp implements SellerIncomeDAO{

    @Override
    public boolean setIncome(int user_id) {
        boolean ok=false;
        String sql="INSERT INTO seller_income(user_id,total,net_earning) VALUES(?,?,?)";
        try {
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, user_id);
            ps.setDouble(2,0);
            ps.setDouble(3,0);
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
    public SellerIncome getSellerIncome(int user_id) {
        SellerIncome si=null;
        try {
            String sql="SELECT * FROM seller_income WHERE user_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, user_id);
            ResultSet rs=ps.executeQuery();
            while ( rs.next()) {   
                si=new SellerIncome();
                si.setSell_inc_id(rs.getInt(1));
                si.setUser_id(rs.getInt(2));
                si.setTotal(rs.getDouble(3));
                si.setNet_earning(rs.getDouble(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return si;
    }

    @Override
    public boolean updateSellerIncome(SellerIncome sinc) {
        boolean ok=false;
        System.out.println("==============sell_id "+sinc.getSell_inc_id());
        System.out.println("==============user_id "+sinc.getUser_id());
        System.out.println("==============total "+sinc.getTotal());
        try {
            String sql="UPDATE seller_income SET total=? WHERE user_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setDouble(1, sinc.getTotal());
            ps.setInt(2, sinc.getUser_id());
            
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
