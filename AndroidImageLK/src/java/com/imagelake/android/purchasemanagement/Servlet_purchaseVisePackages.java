/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.android.purchasemanagement;

import com.imagelake.control.DBFactory;
import com.imagelake.control.UserDAOImp;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author RLN
 */
public class Servlet_purchaseVisePackages extends HttpServlet {

    JSONArray ja;
    UserDAOImp udi;

    public Servlet_purchaseVisePackages() {
        udi = new UserDAOImp();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        try {

            String type = request.getParameter("type");

            String sql = "SELECT SQL_CALC_FOUND_ROWS user_has_packages.purchase_date,user_has_packages.expire_date,user_has_packages.user_id,user_has_packages.state,admin_package_income.total"
                    + " FROM user_has_packages,admin_package_income WHERE user_has_packages.uhp_id=admin_package_income.uhp_id ";
            if (type != null && !type.equals("")) {
                if (type.equals("all")) {

                    sql += "ORDER BY user_has_packages.purchase_date DESC ";
                    ja = new JSONArray();
                    System.out.println("sql=" + sql);
                    PreparedStatement ps = DBFactory.getConnection().prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        rs.close();
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            JSONObject jo = new JSONObject();
                            DecimalFormat df = new DecimalFormat();
                            jo.put("pur_date", rs.getString(1));
                            jo.put("exp_date", rs.getString(2));
                            jo.put("un", udi.getUn(rs.getInt(3)));
                            if (rs.getInt(4) == 1) {
                                jo.put("state", 1);
                            } else if (rs.getInt(4) == 2) {
                                jo.put("state", 2);
                            }
                            jo.put("income", df.format(rs.getDouble(5)));
                            ja.add(jo);

                        }
                        System.out.println(ja.toJSONString());
                        out.write("json=" + ja.toJSONString());
                    } else {
                        out.write("msg=No item found.");
                    }

                } else if (type.equals("sort")) {
                    ja = new JSONArray();
                    String from = request.getParameter("date");
                    String to = request.getParameter("date2");
                    String cat = request.getParameter("cat");
                    String buy = request.getParameter("buy");

                    System.out.println("date_pur:"+from);
                    System.out.println("date_exp:"+to);
                    System.out.println("cat:"+cat);
                    System.out.println("buy:"+buy);
                    
                    if(cat.equals("0")){
                        cat = "";
                    }
                    
                    if (from != null && !from.trim().equals("") && to != null && !to.trim().equals("")) {
                        String[] dt = from.split("-");
                        String[] dt2 = to.split("-");
                        String orDate = dt[2] + "-" + dt[1] + "-" + dt[0];
                        String orDate2 = dt2[2] + "-" + dt2[1] + "-" + dt2[0];
                        System.out.println("date=" + orDate);

                        sql += " AND user_has_packages.purchase_date BETWEEN '" + orDate + "' AND '" + orDate2 + "' ";

                    }
                    if (cat != null && !cat.trim().equals("")) {
                        sql += " AND user_has_packages.package_type='" + cat + "' ";
                    }
                    if (buy != null && !buy.trim().equals("")) {
                        sql += " AND user_has_packages.user_id='" + buy + "' ";
                    }

                    sql += "ORDER BY user_has_packages.purchase_date DESC ";

                    System.out.println("sql: " + sql);

                    PreparedStatement ps = DBFactory.getConnection().prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();
                    DecimalFormat df = new DecimalFormat();
                    if (rs.next()) {
                        rs.close();
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            JSONObject jo = new JSONObject();
                            
                            jo.put("pur_date", rs.getString(1));
                            jo.put("exp_date", rs.getString(2));
                            jo.put("un", udi.getUn(rs.getInt(3)));
                            if (rs.getInt(4) == 1) {
                                jo.put("state", 1);
                            } else if (rs.getInt(4) == 2) {
                                jo.put("state", 2);
                            }
                            jo.put("income", df.format(rs.getDouble(5)));
                            ja.add(jo);
                        }
                        System.out.println(ja.toJSONString());
                         out.write("json="+ja.toJSONString());   
                    } else {
                        out.write("msg=No item found.");
                    }

                }
            } else {
                out.write("msg=Internal server error,Please try again later.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.write("msg=Internal server error,Please try again later.");
        }

    }

}
