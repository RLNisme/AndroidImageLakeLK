/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.HomeSlider;
import com.imagelake.model.IndexLook;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public class HomeSliderDAOImp {
    public List<HomeSlider> listAll(){
        List<HomeSlider> li=new ArrayList<HomeSlider>();
        try {
            String sql="SELECT * FROM home_slider";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                HomeSlider hs=new HomeSlider();
                hs.setId(rs.getInt(1));
                hs.setImg_id(rs.getInt(2));
                li.add(hs);
                System.gc();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return li;
    }
    
    
    public List<IndexLook> listIt(){
        List<IndexLook> li=new ArrayList<IndexLook>();
        try {
            String sql="SELECT * FROM index_look";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                IndexLook il=new IndexLook();
                il.setId(rs.getInt(1));
                il.setImgId(rs.getInt(2));
                il.setPosition(rs.getInt(3));
                li.add(il);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return li;
    }
   
    public boolean checkLook(int imgId){
        boolean ok=true;
        try {
            String sql="SELECT id FROM index_look WHERE img_id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, imgId);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                ok=false;
            }
        } catch (Exception e) {
            e.printStackTrace();
                    
        }
        return ok;
    }
    public int getLook(int imgId){
        int ok=0;
        try {
            String sql="SELECT position FROM index_look WHERE img_id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, imgId);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                ok=rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
                    
        }
        return ok;
    }
    
    
    public boolean updateLook(int posid,int imgid){
        boolean ok=false;
        try {
            String sql="UPDATE index_look SET img_id=? WHERE position=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, imgid);
            ps.setInt(2, posid);
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
