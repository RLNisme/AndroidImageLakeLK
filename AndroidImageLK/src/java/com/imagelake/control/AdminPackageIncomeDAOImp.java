/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.AdminPackageIncome;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public class AdminPackageIncomeDAOImp implements AdminPackageIncomeDAO{

    @Override
    public boolean setAdminIncome(int uhi_id, double total) {
        boolean ok=false;
        try {
            String sql="INSERT INTO admin_package_income(uhp_id,total) VALUES(?,?)";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, uhi_id);
            ps.setDouble(2, total);
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
    public AdminPackageIncome getAdminPackageIncome(int uhp_id) {
        AdminPackageIncome apinc=null;
        try {
            String sql="SELECT * FROM admin_package_income WHERE uhp_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, uhp_id);
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()) {                
                apinc=new AdminPackageIncome();
                apinc.setAdmin_pck_id(rs.getInt(1));
                apinc.setUhp_id(rs.getInt(2));
                apinc.setTotal(rs.getDouble(3));
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apinc;
    }

    @Override
    public boolean updateAdminincomeTotal(AdminPackageIncome ainc) {
        boolean ok=false;
        System.out.println("++++++++++++++pckid "+ainc.getAdmin_pck_id());
        System.out.println("++++++++++++++uhp_id "+ainc.getUhp_id());
        System.out.println("++++++++++++++total "+ainc.getTotal());
        try {
            String sql="UPDATE admin_package_income SET total=? WHERE uhp_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setDouble(1, ainc.getTotal());
            ps.setInt(2, ainc.getUhp_id());
            
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
    public String getIncome() {
        
        StringBuffer sb=null;
        sb=new StringBuffer("{'incexpyear':{"); 
        sb.append("'name':'Income & Expence year wise',");
        sb.append("'rate':[");
        try {
            String sql="SELECT ";
            sql+="YEAR(purchase_date),";
            sql+="SUM(total),";
            sql+="user_has_packages.user_id ";
            sql+="FROM ";
            sql+="user_has_packages,";
            sql+="admin_package_income ";
            
            sql+="WHERE ";
            sql+="user_has_packages.uhp_id=admin_package_income.uhp_id ";
            sql+="GROUP BY YEAR(purchase_date)";
            
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
               
                if(rs.isLast()){
                    sb.append("{'year':'"+rs.getString(1)+"','income':'"+rs.getDouble(2)+"','expenses':'"+getExpense(rs.getString(1))+"'}");
                }else{
                    sb.append("{'year':'"+rs.getString(1)+"','income':'"+rs.getDouble(2)+"','expenses':'"+getExpense(rs.getString(1))+"'},");
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

    @Override
    public String getMonth() {
         StringBuffer sb=null;
        sb=new StringBuffer("{'incexpmon':{"); 
        sb.append("'name':'Income & Expence year wise',");
        sb.append("'rate':[");
        try {
            String sql="SELECT ";
            sql+="YEAR(purchase_date),";
            sql+="MONTH(purchase_date),";
            sql+="SUM(total),";
            sql+="user_has_packages.user_id ";
            sql+="FROM ";
            sql+="user_has_packages,";
            sql+="admin_package_income ";
            
            sql+="WHERE ";
            sql+="user_has_packages.uhp_id=admin_package_income.uhp_id ";
            sql+="GROUP BY MONTH(purchase_date) ORDER BY MONTH(purchase_date)";
            
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
               
                if(rs.isLast()){
                    sb.append("{'month':'"+rs.getString(1)+"-"+rs.getString(2)+"','income':'"+rs.getDouble(3)+"','expenses':'"+getExpense(rs.getString(2))+"'}");
                }else{
                    sb.append("{'month':'"+rs.getString(1)+"-"+rs.getString(2)+"','income':'"+rs.getDouble(3)+"','expenses':'"+getExpense(rs.getString(2))+"'},");
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
    
    
    public double getExpense(String year){
        double d=0.0;
        try {
            String sql="SELECT SUM(amount) FROM payment_preferences WHERE YEAR(date)=? AND state='2' GROUP BY YEAR(date)";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setString(1, year);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                d=rs.getDouble(1);
                System.out.println("Expence:"+d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }
   
    public double getExpenseMonth(String month){
        double d=0.0;
        try {
            String sql="SELECT SUM(amount) FROM payment_preferences WHERE MONTH(date)=? AND state='2' GROUP BY MONTH(date)";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setString(1, month);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                d=rs.getDouble(1);
                System.out.println("Expence:"+d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }
}
