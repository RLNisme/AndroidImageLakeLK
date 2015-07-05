/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.Packages;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public class PackageDAOImp {
    
    public List<Packages> getPackages(){
        List<Packages> li=new ArrayList<Packages>();
        try {
            String sql="SELECT * FROM packages";
            Statement st=DBFactory.getConnection().createStatement();
            ResultSet rs=st.executeQuery(sql);
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
    
}
