/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.PaymentAccount;
import com.imagelake.model.PaymentPreferences;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public class PaymentAccountDAOImp implements PaymentAccountDAO{

    @Override
    public String getPaymetAccountName(int acid) {
        String acc=null;
        try {
            String sql="SELECT account FROM payment_account WHERE paid=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, acid);
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()) {                
                acc=rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return acc;
    }

    @Override
    public List<PaymentAccount> getpaymentAccounts() {
        List<PaymentAccount> li=new ArrayList<PaymentAccount>();
        try {
            String sql="SELECT * FROM payment_account";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
               PaymentAccount pa=new PaymentAccount();
               pa.setPaid(rs.getInt(1));
               pa.setAcc(rs.getString(2));
               
               li.add(pa);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return li;
    }
    
}
