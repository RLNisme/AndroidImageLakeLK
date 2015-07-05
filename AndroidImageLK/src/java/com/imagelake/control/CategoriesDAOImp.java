/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.Categories;
import com.imagelake.model.Images;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public class CategoriesDAOImp implements CategoriesDAO{

    @Override
    public List<Categories> listAllCategories() {
        ArrayList<Categories> categoryList=new ArrayList<Categories>();
        try {
            String sql="SELECT * FROM categories";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            
            
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {            
                System.out.println("Category ID:"+rs.getInt(1));
                Categories c=new Categories();
                c.setCategory_id(rs.getInt(1));
                c.setCategory(rs.getString(2));
                
                categoryList.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    @Override
    public Categories getCategory(int cat_id) {
       Categories c=null;
        try {
            String sql="SELECT * FROM categories WHERE category_id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, cat_id);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {     
                c=new Categories();
                c.setCategory(rs.getString(2));
                c.setCategory_id(rs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }
    
    

    @Override
    public String getImageCategory(int catid) {
        
       String s=null;
       try {
           
           
           String sql="SELECT category FROM categories WHERE category_id=?";
           PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
           ps.setInt(1, catid);
           ResultSet rs=ps.executeQuery();
           while (rs.next()) {               
               s=rs.getString(1);
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return s;
    }

    @Override
    public boolean insertCategory(String cat) {
        boolean ok=false;
        try {
            String sql="INSERT INTO categories(category) VALUES(?)";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setString(1, cat);
            int i=ps.executeUpdate();
            if(i>0){
                ok=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }
    
    
    //--------------------------------
    
}
