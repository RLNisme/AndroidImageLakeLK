/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.InterfacesSub;
import com.imagelake.model.SubInterfaces;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public class SubInterfacesDAOImp implements SubInterfacesDAO{

    @Override
    public List<SubInterfaces> listSub(ArrayList<InterfacesSub> interfacelist) {
        List<SubInterfaces> interfacesublist=new ArrayList<SubInterfaces>();
        
       
            try {
                String sql="SELECT * FROM sub_interfaces WHERE sub_interfaces_id=?";
                Connection con=DBFactory.getConnection();
                PreparedStatement ps=con.prepareStatement(sql);
                
                 
                for (int i = 0; i < interfacelist.size(); i++) {
                    InterfacesSub inter = interfacelist.get(i);
                    ps.setInt(1, inter.getSub_interfaces_sub_interfaces_id());
                    
                    ResultSet rs=ps.executeQuery();
                    if (rs.next()) {                        
                        SubInterfaces interfaces=new SubInterfaces();
                        interfaces.setSub_interfaces_id(rs.getInt(1));
                        interfaces.setUrl(rs.getString(2));
                        interfaces.setDisplay_name(rs.getString(3));
                        
                        System.out.println("Sub Interfaces class listing");
                        System.out.println(rs.getString(3));
                        interfacesublist.add(interfaces);
                    }
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        System.out.println("Sub Interfcae class listAll done");
        return interfacesublist;
    }
    
}
