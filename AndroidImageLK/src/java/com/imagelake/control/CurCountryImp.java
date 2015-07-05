/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.CurrentCountry;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public class CurCountryImp implements CurCountryDAO{

    @Override
    public List<CurrentCountry> listAll() {
       List<CurrentCountry> currentcountrylist=new ArrayList<CurrentCountry>();
        try {
            String sql="SELECT * FROM current_country ";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                CurrentCountry cc=new CurrentCountry();
                cc.setCurrent_country_current_country_id(rs.getInt(1));
                cc.setCountry(rs.getString(2));
                currentcountrylist.add(cc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentcountrylist;
    }

    @Override
    public String countryName(int id) {
        System.out.println("country name");
        String curcon=null;
        try {
            String sql="SELECT * FROM current_country WHERE current_country_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                curcon=rs.getString(2);
                System.out.println(curcon);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return curcon;
        }
        
        return curcon;
    }
    
}
