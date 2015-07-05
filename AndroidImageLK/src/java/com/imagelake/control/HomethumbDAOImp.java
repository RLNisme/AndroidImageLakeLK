/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.HomeThumb;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public class HomethumbDAOImp {
    
    public List<HomeThumb> listAll(){
        List<HomeThumb> li=new ArrayList<HomeThumb>();
        try {
            String sql="SELECT * FROM home_thumb";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                HomeThumb t=new HomeThumb();
                t.setId(rs.getInt(1));
                t.setImg_id(rs.getInt(2));
                t.setSub_id(rs.getInt(3));
                t.setDiv_thumb(rs.getString(4));
                t.setDiv_cover(rs.getString(5));
                li.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return li;
    }
    
}
