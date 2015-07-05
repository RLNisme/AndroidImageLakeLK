/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.Credits;
import com.imagelake.model.CreditsPackage;
import com.imagelake.model.SubscriptionPackage;
import com.imagelake.model.SubscriptionUnitPrice;
import com.imagelake.model.UserHasPackage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public class UserHasPackageDAOImp implements UserHasPackageDAO{

    @Override
    public List<UserHasPackage> getUserPackages(int uid) {
        List<UserHasPackage> uhpList=new ArrayList<UserHasPackage>();
        try {
            String sql="SELECT * FROM user_has_packages WHERE user_id=?";
        Connection con=DBFactory.getConnection();
        PreparedStatement ps=con.prepareStatement(sql);
        ps.setInt(1, uid);
        ResultSet rs=ps.executeQuery();
        while (rs.next()) {            
            UserHasPackage us=new UserHasPackage();
            us.setCredit_count(rs.getInt(8));
            us.setDownload_count(rs.getInt(7));
            us.setExpire_date(rs.getString(5));
            us.setPackage_id(rs.getInt(2));
            us.setPackage_type(rs.getInt(6));
            us.setPurchase_date(rs.getString(4));
            us.setState(rs.getInt(9));
            us.setUhp_id(rs.getInt(1));
            us.setUser_id(rs.getInt(3));
            us.setLast_date(rs.getString(10));
            us.setOrg_downloads(rs.getInt(11));
            us.setUnit_price(rs.getDouble(12));
            us.setDuration(rs.getInt(13));
            
            uhpList.add(us);
            System.gc();
        }
            System.out.println("list is empty:"+uhpList.isEmpty());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uhpList;
    }

    @Override
    public boolean addAPackage(UserHasPackage u) {
        boolean ok=false;
        try {
            String sql="INSERT INTO user_has_packages(package_id,user_id,purchase_date,expire_date,package_type,download_count,credit_count,state,last_date,org_downloads,unit_price,duration)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,u.getPackage_id());
            ps.setInt(2,u.getUser_id());
            ps.setString(3, u.getPurchase_date());
            ps.setString(4, u.getExpire_date());
            ps.setInt(5, u.getPackage_type());
            ps.setInt(6, u.getDownload_count());
            ps.setInt(7, u.getCredit_count());
            ps.setInt(8, u.getState());
            ps.setString(9, u.getLast_date());
            ps.setInt(10, u.getOrg_downloads());
            ps.setDouble(11, u.getUnit_price());
            ps.setInt(12, u.getDuration());
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
    public boolean updateState(int uid) {
        System.out.println("user ID:----------------------"+uid);
        boolean ok=false;
        try {
            String sql="UPDATE user_has_packages SET state=? WHERE uhp_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,2);
            ps.setInt(2, uid);
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
    public boolean updatePackage(UserHasPackage uhp) {
        boolean ok=false;
        try {
            String sql="UPDATE user_has_packages SET download_count=?,credit_count=?,last_date=? WHERE uhp_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, uhp.getDownload_count());
            ps.setInt(2, uhp.getCredit_count());
            ps.setString(3, uhp.getLast_date());
            ps.setInt(4, uhp.getUhp_id());
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
    public UserHasPackage getUserPackage(int uid, int state,int type) {
        UserHasPackage us=null;
        try {
            String sql="SELECT * FROM user_has_packages WHERE user_id=? AND state=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, uid);
            ps.setInt(2, state);
            
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {     
                
             if(rs.getInt(6)==type){   
            us=new UserHasPackage();
            us.setCredit_count(rs.getInt(8));
            us.setDownload_count(rs.getInt(7));
            us.setExpire_date(rs.getString(5));
            us.setPackage_id(rs.getInt(2));
            us.setPackage_type(rs.getInt(6));
            us.setPurchase_date(rs.getString(4));
            us.setState(rs.getInt(9));
            us.setUhp_id(rs.getInt(1));
            us.setUser_id(rs.getInt(3));
            us.setLast_date(rs.getString(10));
            us.setOrg_downloads(rs.getInt(11));
            us.setUnit_price(rs.getDouble(12));
            us.setDuration(rs.getInt(13));
             }
            System.gc();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return us;
    }

    @Override
    public String getPackageType(int pckID) {
        String a="";
        try {
            String sql="SELECT package_type FROM packages WHERE pck_id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, pckID);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                a=rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public UserHasPackage getUserActivePackage(int uid, int state) {
        UserHasPackage us=null;
        try {
            String sql="SELECT * FROM user_has_packages WHERE user_id=? AND state=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, uid);
            ps.setInt(2,state);
            
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                us=new UserHasPackage();
            us.setCredit_count(rs.getInt(8));
            us.setDownload_count(rs.getInt(7));
            us.setExpire_date(rs.getString(5));
            us.setPackage_id(rs.getInt(2));
            us.setPackage_type(rs.getInt(6));
            us.setPurchase_date(rs.getString(4));
            us.setState(rs.getInt(9));
            us.setUhp_id(rs.getInt(1));
            us.setUser_id(rs.getInt(3));
            us.setLast_date(rs.getString(10));
            us.setOrg_downloads(rs.getInt(11));
            us.setUnit_price(rs.getDouble(12));
            us.setDuration(rs.getInt(13));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return us;
    }

    @Override
    public boolean updateExpireDate(UserHasPackage up) {
        boolean ok=false;
        try {
            String sql="UPDATE user_has_packages SET expire_date=?,download_count=?,last_date=? WHERE uhp_id=? ";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setString(1, up.getExpire_date());
            ps.setInt(2, up.getDownload_count());
            ps.setString(3,up.getLast_date());
            ps.setInt(4, up.getUhp_id());
            int i=ps.executeUpdate();
            if(i>0){
                ok=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String getPackageSellingRate() {
        StringBuffer sb=null;
        try {
            
         sb=new StringBuffer("{'pack':{"); 
         sb.append("'name':'Packages',");
         sb.append("'subchart':[");
        
         String sql="SELECT COUNT(package_id),package_id FROM user_has_packages WHERE package_type='3' GROUP BY package_id";
                 PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
                 ResultSet rs=ps.executeQuery();
                 SubscriptionPackageDAOImp spdi=new SubscriptionPackageDAOImp();
                 
                 SubscriptionPackage sp=null;
                 while (rs.next()) { 
                     String du=getpackageDura(rs.getInt(2));
                     sp=spdi.getSubscription(rs.getInt(2));
                     String type=getPacType(sp.getSubscription_type_id());
                     String dwn=getDwnCount(sp.getCount_id());
                     if(rs.isLast()){
                     sb.append("{'package':'"+dwn+" a "+du+" Month ("+type+")','value':'"+rs.getString(1)+"'}");
                     }else{
                     sb.append("{'package':'"+dwn+" a "+du+" Month ("+type+")','value':'"+rs.getString(1)+"'},");    
                     }
                }
                
         sb.append("],");
         sb.append("'crechart':[");
         
         String sql2="SELECT COUNT(package_id),package_id FROM user_has_packages WHERE package_type='4' GROUP BY package_id";
         PreparedStatement ps2=DBFactory.getConnection().prepareStatement(sql2);
         ResultSet rs2=ps2.executeQuery();
            CreditPackageDAOImp cdi=new CreditPackageDAOImp();
            CreditsPackage cp=null;
            while (rs2.next()) {   
                cp=cdi.getCreditPackage(rs2.getInt(2));
                if(rs2.isLast()){
                    sb.append("{'package':'"+cp.getCredits()+" credits for "+cp.getDuration()+" Months','value':'"+rs2.getString(1)+"'}");
                }else{
                    sb.append("{'package':'"+cp.getCredits()+" credits for "+cp.getDuration()+" Months','value':'"+rs2.getString(1)+"'},");
                }
                
                
            }
         sb.append("],");
         sb.append("'subtable':[");
         
         List<SubscriptionPackage> li=spdi.listAll();
         //SubscriptionUnitPriceDAOImp supi=new SubscriptionUnitPriceDAOImp();
            //SubscriptionUnitPrice sup=null;
            for (int i = 0; i < li.size(); i++) {
                SubscriptionPackage subscriptionPackage = li.get(i);
                String dw=getDwnCount(subscriptionPackage.getCount_id());
                String ty=getPacType(subscriptionPackage.getSubscription_type_id());
                //sup=supi.getSubscriptionUnitPrice(subscriptionPackage.getSubscription_unit_price_id());
                if(i==li.size()-1){
                sb.append("{'id':'"+subscriptionPackage.getSubscription_id()+"','downloads':'"+dw+"','duration':'"+subscriptionPackage.getDuration()+"','unitprice':'"+subscriptionPackage.getPer_image()+"','type':'"+ty+"','countid':'"+subscriptionPackage.getCount_id()+"','state':'"+subscriptionPackage.getState()+"','disco':'"+subscriptionPackage.getDiscount()+"'}");
                }else{
                sb.append("{'id':'"+subscriptionPackage.getSubscription_id()+"','downloads':'"+dw+"','duration':'"+subscriptionPackage.getDuration()+"','unitprice':'"+subscriptionPackage.getPer_image()+"','type':'"+ty+"','countid':'"+subscriptionPackage.getCount_id()+"','state':'"+subscriptionPackage.getState()+"','disco':'"+subscriptionPackage.getDiscount()+"'},");
                }
            }
            
         sb.append("],");
         sb.append("'crdtable':[");
         List<CreditsPackage> lis=cdi.listAll();
            for (int i = 0; i < lis.size(); i++) {
                CreditsPackage creditsPackage = lis.get(i);
                if(i==lis.size()-1){
                sb.append("{'id':'"+creditsPackage.getCreditpack_id()+"','credits':'"+creditsPackage.getCredits()+"','duration':'"+creditsPackage.getDuration()+"','unitprice':'"+creditsPackage.getPer_image()+"','state':'"+creditsPackage.getState()+"','disco':'"+creditsPackage.getDiscount()+"'}");
                }else{
                sb.append("{'id':'"+creditsPackage.getCreditpack_id()+"','credits':'"+creditsPackage.getCredits()+"','duration':'"+creditsPackage.getDuration()+"','unitprice':'"+creditsPackage.getPer_image()+"','state':'"+creditsPackage.getState()+"','disco':'"+creditsPackage.getDiscount()+"'},");
                }
            }
         
         
         sb.append("],");
         sb.append("'creditlist':[");
         CreditsDAOImp crdi=new CreditsDAOImp();
         List<Credits> clis=crdi.getCreditList();
            for (int i = 0; i < clis.size(); i++) {
                Credits credits = clis.get(i);
                if(i==clis.size()-1){
                    sb.append("{'id':'"+credits.getCredit_id()+"','credits':'"+credits.getCredits()+"','size':'"+credits.getSize()+"','width':'"+credits.getWidth()+"','height':'"+credits.getHeight()+"','state':'"+credits.getState()+"'}");
                }else{
                    sb.append("{'id':'"+credits.getCredit_id()+"','credits':'"+credits.getCredits()+"','size':'"+credits.getSize()+"','width':'"+credits.getWidth()+"','height':'"+credits.getHeight()+"','state':'"+credits.getState()+"'},");
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

    
    public String getpackageDura(int pcid){
        String du=null;
        try {
            String sql="SELECT duration FROM subscriptions_package WHERE subscription_id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, pcid);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                du=rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return du;
    }
    
    public String getPacType(int pktype){
        String type=null;
        try {
            String sql="SELECT subscriptions_type FROM subscription_type WHERE id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, pktype);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                type=rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return type;
    }
 
    public String getDwnCount(int dwid){
        String swn=null;
        try {
            String sql="SELECT count FROM download_count WHERE id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, dwid);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                swn=rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return swn;
    }
}
