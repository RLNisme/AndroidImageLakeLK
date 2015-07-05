/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.android.privilages.Servlet_privilages;
import com.imagelake.model.Interfaces;
import com.imagelake.model.InterfacesSub;
import com.imagelake.model.Privilages;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/** 
 *
 * @author Lakmal
 */
public class InterfaceDAOImp implements InterfacesDAO{

    @Override
    public List<Interfaces> listAll(ArrayList<Privilages> privilagelist) {
        List<Interfaces> interfacelist=new ArrayList<Interfaces>();
        
       
            try {
                String sql="SELECT * FROM interfaces WHERE interfaces_id=?";
                Connection con=DBFactory.getConnection();
                PreparedStatement ps=con.prepareStatement(sql);
                
                 
                for (int i = 0; i < privilagelist.size(); i++) {
                    Privilages privilages1 = privilagelist.get(i);
                    ps.setInt(1, privilages1.getInterface_interface_id());
                    
                    ResultSet rs=ps.executeQuery();
                    if (rs.next()) {                        
                        Interfaces interfaces=new Interfaces();
                        interfaces.setInterface_id(rs.getInt(1));
                        interfaces.setUrl(rs.getString(2));
                        interfaces.setDisplay_name(rs.getString(3));
                        System.out.println("Interfaces class listing");
                        System.out.println(rs.getString(3));
                        interfacelist.add(interfaces);
                    }
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        System.out.println("Interfcae class listAll done");
        return interfacelist;
    }

    
    public Interfaces getInterface(String name){
        Interfaces inf=null;
        try {
            String ql="SELECT * FROM interfaces WHERE url=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(ql);
            ps.setString(1, name);
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()) {                
                inf=new Interfaces();
                inf.setInterface_id(rs.getInt(1));
                inf.setDisplay_name(rs.getString(3));
                inf.setUrl(rs.getString(2));
                inf.setState(rs.getInt(4));
                System.gc();
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return inf;
    }

    @Override
    public boolean updateInteface(Interfaces inf) {
        boolean ok=false;
        try {
            String sql="UPDATE interfaces SET state=? WHERE url=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, inf.getState());
            ps.setString(2, inf.getUrl());
            int i=ps.executeUpdate();
            if(i>0){
                ok=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }
   
    public String listAll(){
        StringBuffer sb=null;
        try {
            String sql="SELECT * FROM interfaces";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            
                 sb=new StringBuffer("{'privilegesetting':{"); 
                 sb.append("'name':'all',");
                 sb.append("'allinf':[");
    
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                  if(rs.isLast()){
                  sb.append("{'id':'"+rs.getInt(1)+"','url':'"+rs.getString(2)+"','name':'"+rs.getString(3)+"','state':'"+rs.getInt(4)+"'}");
                  }else{
                  sb.append("{'id':'"+rs.getInt(1)+"','url':'"+rs.getString(2)+"','name':'"+rs.getString(3)+"','state':'"+rs.getInt(4)+"'},");
                  }
            }
            sb.append("],");
            sb.append("'types':[");
                String sql3="SELECT * FROM user_type WHERE user_type_id!=4";
                    PreparedStatement ps3=DBFactory.getConnection().prepareStatement(sql3);
                    ResultSet rs3=ps3.executeQuery();
                    while (rs3.next()) {
                        if(rs3.isLast()){
                             sb.append("{'value':'"+rs3.getString(1)+"','type':'"+rs3.getString(2)+"'}");
                        }else{
                             sb.append("{'value':'"+rs3.getString(1)+"','type':'"+rs3.getString(2)+"'},");
                        }
                       
                    }
            sb.append("]");
                sb.append("}");
                sb.append("}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    
    public boolean insertInterface(Interfaces inf){
        boolean ok=false;
        try {
            String sql="INSERT INTO interfaces(url,display_name,state) VALUES(?,?,?)";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setString(1, inf.getUrl());
            ps.setString(2, inf.getDisplay_name());
            ps.setInt(3, inf.getState());
            
            int i=ps.executeUpdate();
            if(i>0){
                ok=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }
    
    
    public Interfaces getInterfaceName(int infid){
        Interfaces inf=null;
        System.out.println("IIIIIIIIii "+infid);
        try {
            String sql="SELECT * FROM interfaces WHERE interfaces_id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, infid);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) { 
              
                System.out.println("url:"+rs.getString(2));
                inf=new Interfaces();
                inf.setInterface_id(rs.getInt(1));
                inf.setUrl(rs.getString(2));
                inf.setDisplay_name(rs.getString(3));
                inf.setState(rs.getInt(4));
                inf.setImg_id(rs.getInt(5));
                
               
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inf;
    }

    public String listPrivilages() {
        String sb ="";
        JSONArray ja = new JSONArray();
        try {
            String sql3="SELECT * FROM user_type WHERE user_type_id!=4";
                    PreparedStatement ps3=DBFactory.getConnection().prepareStatement(sql3);
                    ResultSet rs3=ps3.executeQuery();
                    while (rs3.next()) {
                        JSONObject jo = new JSONObject();
                        jo.put("id",rs3.getString(1));     
                        jo.put("type",rs3.getString(2));     
                        ja.add(jo);
                    }
                    sb = "json="+ja.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            sb = "msg=Internal server error,Please try again later.";
        }
        return  sb;
    }

    public String listInterfaces() {
        String sb ="";
        JSONArray ja = new JSONArray();
        try {
            String sql="SELECT * FROM interfaces";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                 JSONObject jo = new JSONObject();
                 jo.put("id", rs.getInt(1));
                 jo.put("name", rs.getString(3));
                 jo.put("state", rs.getInt(4));
                 ja.add(jo);
                  
            }
            sb ="json="+ja.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            sb = "msg=Internal server error,Please try again later.";
        }
        return  sb;
    }
    
    
    
}
