/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.SubscriptionUnitPrice;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Lakmal
 */
public class SubscriptionUnitPriceDAOImp implements SubscriptionUnitPriceDAO{

    @Override
    public SubscriptionUnitPrice getSubscriptionUnitPrice(int subscription_unit_price_id) {
        SubscriptionUnitPrice cp=new SubscriptionUnitPrice();
        try {
            String sql="SELECT * FROM subscription_unit_price WHERE subscript_unit_price_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, subscription_unit_price_id);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                cp.setSubscription_unit_price_id(rs.getInt(1));
                cp.setOld_per_image(rs.getDouble(3));
                cp.setPer_image(rs.getDouble(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cp;
    }
    
}
