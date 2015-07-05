/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.CreditUnitPrice;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Lakmal
 */
public class CreditUnitPriceDAOImp implements CreditUnitPriceDAO{

    @Override
    public CreditUnitPrice getUnitPriceForCredit(int credit_unit_price_id) {
        CreditUnitPrice cp=new CreditUnitPrice();
        try {
            String sql="SELECT * FROM credit_unit_price WHERE credit_unit_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, credit_unit_price_id);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                cp.setCredit_unit_price_id(rs.getInt(1));
                cp.setOld_per_image(rs.getDouble(3));
                cp.setPer_image(rs.getDouble(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cp;
    }
    
}
