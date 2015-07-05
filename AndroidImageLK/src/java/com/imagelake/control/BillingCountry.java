/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.Country;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public class BillingCountry implements BillingCountryDAO{

    @Override
    public List<Country> listAll() {
        List<Country> countrylist=new ArrayList<Country>();
        
        try {
            String sql="SELECT * FROM country";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                Country country=new Country();
                country.setCountry_id(rs.getInt("country_id"));
                country.setCountry(rs.getString("country"));
                countrylist.add(country);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countrylist;
    }

    @Override
    public String getOneCountry(int country_id) {
        String country=null;
        try {
            String sql="SELECT * FROM country WHERE country_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, country_id);
            
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                country=rs.getString(2);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return country;
        }
        
        return country;
    }

   
    
}
