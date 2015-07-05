/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;


import com.imagelake.model.InterfacesSub;
import com.imagelake.model.Privilages;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public class InterfaceSubDAOImp implements InterfacesSubDAO{

    @Override
    public List<InterfacesSub> listAll(int interfaces_interfaces_id) {
        List<InterfacesSub> interfacessublist=new ArrayList<InterfacesSub>();
       
            try {
            String sql="SELECT * FROM interfaces_sub WHERE interfaces_interfaces_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, interfaces_interfaces_id);
            ResultSet rs=ps.executeQuery();
                while (rs.next()) {                    
                    InterfacesSub intSub=new InterfacesSub();
                    intSub.setInterfaces_sub_id(rs.getInt(1));
                    intSub.setInterfaces_interfaces_id(rs.getInt(2));
                    intSub.setSub_interfaces_sub_interfaces_id(rs.getInt(3));
                    
                    System.out.println(rs.getInt(3)+"");
                    interfacessublist.add(intSub);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
           
        System.out.println("Interface Sub class listAll done");
        return interfacessublist;
    }

}
