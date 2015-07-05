/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.DownloadCount;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public class DownloadCountDAOImp implements DownloadCountDAO{

    @Override
    public List<DownloadCount> listAll() {
        List<DownloadCount> list=new ArrayList<DownloadCount>();
        try {
            String sql="SELECT * FROM download_count";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                DownloadCount co=new DownloadCount();
                co.setId(rs.getInt(1));
                co.setCount(rs.getInt(2));
                co.setSubscription_type(rs.getInt(3));
                list.add(co);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public DownloadCount getCount(int coid) {
       DownloadCount d=new DownloadCount();
        try {
            String sql="SELECT * FROM download_count WHERE id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, coid);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                d.setId(rs.getInt(1));
                d.setCount(rs.getInt(2));
                d.setSubscription_type(rs.getInt(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    @Override
    public boolean insertDownloadCount(DownloadCount dc) {
        boolean ok=false;
        try {
            String sql="INSERT INTO download_count(count,subscription_type) VALUES(?,?)";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, dc.getCount());
            ps.setInt(2, 1);
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
    public boolean duplication(DownloadCount dc) {
        boolean ok=false;
        try {
            String sql="SELECT * FROM download_count WHERE count=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, dc.getCount());
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                ok=true;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }
    
    
}
