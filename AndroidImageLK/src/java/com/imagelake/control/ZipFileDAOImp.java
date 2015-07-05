/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.UserZipFile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Lakmal
 */
public class ZipFileDAOImp {
    
    public boolean saveZipFile(UserZipFile zf){
        boolean ok=false;
        try {
            String sql="INSERT INTO zip_file(id,uid,path,state)VALUES(?,?,?,?)";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, zf.getId());
            ps.setInt(2, zf.getUid());
            ps.setString(3, zf.getPath());
            ps.setInt(4, zf.getState());
            int i=ps.executeUpdate();
            if(i>0){
                ok=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }
    
    public UserZipFile validateZipFile(int uid,String path,int state){
        UserZipFile z=null;
        try {
            String sql="SELECT * FROM zip_file WHERE uid=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, uid);
            
            
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
               if(rs.getString(3).equals(path)&& state==rs.getInt(4)){
                   z=new UserZipFile();
                   z.setId(rs.getInt(1));
                   z.setPath(rs.getString(3));
                   z.setUid(rs.getInt(2));
                   z.setState(rs.getInt(4));
                   break;
               }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return z;
    }
    
    public boolean updateZipFile(int state,int id){
        boolean ok=false;
        try {
            String sql="UPDATE zip_file SET state=? WHERE id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, state);
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
