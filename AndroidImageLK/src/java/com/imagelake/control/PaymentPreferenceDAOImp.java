/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.control;

import com.imagelake.model.MinEarning;
import com.imagelake.model.PaymentPreferences;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Lakmal
 */
public class PaymentPreferenceDAOImp implements PaymentPreferenceDAO {

    Date d=new Date();
        SimpleDateFormat sd1=new SimpleDateFormat("yyyy-MM-dd");
        String mdate=sd1.format(d);
    
    public double getMinEarnings() {
        double d = 0;
        try {
            String sql = "SELECT * FROM min_earning";
            PreparedStatement ps = DBFactory.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d = rs.getDouble(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    public MinEarning getMin(int id){
        MinEarning me=new MinEarning();
        try {
            String sql="SELECT * FROM min_earning WHERE id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                me.setId(rs.getInt(1));
                me.setMinearning(rs.getDouble(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return me;
    }
    
    
    public boolean updateMinEarning(double earn,int id){
        boolean b=false;
        try {
            String sql="UPDATE min_earning SET minearning=? WHERE id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setDouble(1, earn);
            ps.setInt(2, id);
            int i=ps.executeUpdate();
            if(i>0){
                b=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }
    
    @Override
    public List<PaymentPreferences> getUserEarningHistory(int user_id) {
        List<PaymentPreferences> li = new ArrayList<PaymentPreferences>();
        try {
            String sql = "SELECT * FROM payment_preferences WHERE user_id=?";
            PreparedStatement ps = DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PaymentPreferences pp = new PaymentPreferences();
                pp.setAcc_type(rs.getInt(5));
                pp.setAdm_id(rs.getInt(6));
                pp.setAmount(rs.getDouble(7));
                pp.setConf_date(rs.getString(4));
                pp.setDate(rs.getString(3));
                pp.setPpid(rs.getInt(1));
                pp.setState(rs.getInt(8));
                pp.setUser_id(rs.getInt(2));
                System.out.println("getting...");
                li.add(pp);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return li;
    }

    @Override
    public PaymentPreferences getPendingEarning(int uid, int state) {
        PaymentPreferences pp = null;
        try {
            String sql = "SELECT * FROM payment_preferences WHERE user_id=? AND state=?";
            PreparedStatement ps = DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, uid);
            ps.setInt(2, state);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pp = new PaymentPreferences();
                pp.setAcc_type(rs.getInt(5));
                pp.setAdm_id(rs.getInt(6));
                pp.setAmount(rs.getDouble(7));
                pp.setConf_date(rs.getString(4));
                pp.setDate(rs.getString(3));
                pp.setPpid(rs.getInt(1));
                pp.setState(rs.getInt(8));
                pp.setUser_id(rs.getInt(2));
                System.out.println("getting...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pp;
    }

    @Override
    public boolean AddPaymentPreference(PaymentPreferences pp) {
        boolean ok = false;
        try {
            String sql = "INSERT INTO payment_preferences(user_id,date,conf_date,acc_type,adm_id,amount,state,email) VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement ps = DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, pp.getUser_id());
            ps.setString(2, pp.getDate());
            ps.setString(3, pp.getConf_date());
            ps.setInt(4, pp.getAcc_type());
            ps.setInt(5, pp.getAdm_id());
            ps.setDouble(6, pp.getAmount());
            ps.setInt(7, pp.getState());
            ps.setString(8, pp.getEmail());

            int i = ps.executeUpdate();
            if (i > 0) {
                ok = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public List<PaymentPreferences> getUserEarnedHistory(int uid, int state) {
        ArrayList<PaymentPreferences> li = new ArrayList<PaymentPreferences>();
        try {
            String sql = "SELECT * FROM payment_preferences WHERE user_id=? AND state=?";
            PreparedStatement ps = DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, uid);
            ps.setInt(2, state);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PaymentPreferences pp = new PaymentPreferences();
                pp.setAcc_type(rs.getInt(5));
                pp.setAdm_id(rs.getInt(6));
                pp.setAmount(rs.getDouble(7));
                pp.setConf_date(rs.getString(4));
                pp.setDate(rs.getString(3));
                pp.setPpid(rs.getInt(1));
                pp.setState(rs.getInt(8));
                pp.setUser_id(rs.getInt(2));
                System.out.println("getting...");
                li.add(pp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return li;
    }

    @Override
    public String getJSONPaymentPreferances(int state) {
        StringBuffer sb = null;
        int dataCount = 0;
        
        int pages = 0;
        PaymentAccountDAOImp pad = new PaymentAccountDAOImp();
        try {
            sb = new StringBuffer("{'prefarence':{");
            sb.append("'pages':[");

            String sql = "SELECT * FROM payment_preferences WHERE state=?";
            PreparedStatement ps = DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, state);
            ResultSet rs = ps.executeQuery();
            System.out.println("total:"+getResultSetCount(rs));
            
            while (rs.next()) {
               
                
                if (dataCount % 10 == 0) {

                    pages++;
                    
                    if (rs.isLast()) {
                        sb.append("{'id':'" + rs.getString(1) + "','userid':'" + rs.getString(2) + "','reqdate':'" + rs.getString(3) + "','acctype':'" + pad.getPaymetAccountName(rs.getInt(5)) + "','amount':'" + rs.getDouble(7) + "','state':'" + rs.getInt(8) + "','email':'" + rs.getString(9) + "','no':'"+pages+"'}");
                    }
                    else {
                        
                       sb.append("{'id':'" + rs.getString(1) + "','userid':'" + rs.getString(2) + "','reqdate':'" + rs.getString(3) + "','acctype':'" + pad.getPaymetAccountName(rs.getInt(5)) + "','amount':'" + rs.getDouble(7) + "','state':'" + rs.getInt(8) + "','email':'" + rs.getString(9) + "','no':'"+pages+"'},");
                        
                    }
                }
               
                if (dataCount % 10 != 0) {

                    if(rs.isLast()){
                    sb.append("{'id':'" + rs.getString(1) + "','userid':'" + rs.getString(2) + "','reqdate':'" + rs.getString(3) + "','acctype':'" + pad.getPaymetAccountName(rs.getInt(5)) + "','amount':'" + rs.getDouble(7) + "','state':'" + rs.getInt(8) + "','email':'" + rs.getString(9) + "','no':'"+pages+"'}");
                    }else{
                    sb.append("{'id':'" + rs.getString(1) + "','userid':'" + rs.getString(2) + "','reqdate':'" + rs.getString(3) + "','acctype':'" + pad.getPaymetAccountName(rs.getInt(5)) + "','amount':'" + rs.getDouble(7) + "','state':'" + rs.getInt(8) + "','email':'" + rs.getString(9) + "','no':'"+pages+"'},");
                    }
                }
                System.out.println("id:" + rs.getInt(1));
                System.out.println(dataCount + "||" + dataCount % 10);
                System.out.println("pages" + pages);
                 dataCount++;
            }
            sb.append("],");
            sb.append("'leng':'"+pages+"',");
            sb.append("}");
            sb.append("}");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public JSONArray getPayments(int state) {
        JSONArray ja = new JSONArray();
        PaymentAccountDAOImp pad = new PaymentAccountDAOImp();
        try {
            String sql = "SELECT * FROM payment_preferences WHERE state=?";
            PreparedStatement ps = DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, state);
            ResultSet rs = ps.executeQuery();
            int no = 0;
            while (rs.next()) {
                no++;
                JSONObject jo = new JSONObject();
                jo.put("id", rs.getString(1));
                jo.put("userid", rs.getString(2));
                jo.put("reqdate", rs.getString(3));
                jo.put("acctype", pad.getPaymetAccountName(rs.getInt(5)));
                jo.put("amount", rs.getDouble(7));
                jo.put("state", rs.getInt(8));
                jo.put("email", rs.getString(9));
                jo.put("no", no);
                ja.add(jo);
            }
            return ja;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ja;
    }
    
    
    
    
    
    
    
    
    
    public int getResultSetCount(ResultSet rs){
        int con=0;
        try {
             rs.last();
         con = rs.getRow();
          rs.beforeFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
          return con;
    }

    @Override
    public boolean requestSettle(int prid, int state, int admid, String date) {
        boolean ok=false;
        try {
            String sql="UPDATE payment_preferences SET state=?,adm_id=?,"+date+"=? WHERE ppid=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, state);
            ps.setInt(2, admid);
            ps.setString(3,mdate);
            ps.setInt(4, prid);
            
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
