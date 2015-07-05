/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.android.imagemanagement;

import com.imagelake.control.CategoriesDAOImp;
import com.imagelake.control.DBFactory;
import com.imagelake.control.ImagesDAOImp;
import com.imagelake.control.UserDAOImp;
import com.imagelake.model.Categories;
import com.imagelake.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class Servlet_ImageManagement extends HttpServlet {

    ImagesDAOImp idi;
    UserDAOImp udi;
    CategoriesDAOImp cdi;
    JSONArray ja;
    Categories catt;
    User us;

    public Servlet_ImageManagement() {
        idi = new ImagesDAOImp();
        udi = new UserDAOImp();
        cdi = new CategoriesDAOImp();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        try {
            String type = request.getParameter("type");
            System.out.println(type);

            if (type != null && !type.equals("")) {

                if (type.equals("sort")) {

                    ja = new JSONArray();

                    String date = request.getParameter("date");
                    String date2 = request.getParameter("date2");
                    String cat = request.getParameter("cat");
                    String usnm = request.getParameter("usnm");
                    String key = request.getParameter("key");

                    System.out.println("date: "+date);
                    System.out.println("date2: "+date2);
                    System.out.println("cat: "+cat);
                    System.out.println("usnm: "+usnm);
                    System.out.println("key: "+key);
                    
                    String dt = "dt" + date + "dt";
                    String ct = "ct" + cat + "ct";
                    String un = "un" + usnm + "un";
                    String ky = "ky" + key + "ky";

                    System.out.println("date: "+dt);
                    //System.out.println("date2: "+ct);
                    System.out.println("cat: "+ct);
                    System.out.println("usnm: "+un);
                    System.out.println("key: "+ky);
                    
                    String sql = "SELECT * FROM images ";

                    sql += "WHERE ";

                    if (!date.equals("")  || !dt.equals("dtdt") && !dt.equals("dtnulldt") && !date2.equals("") ) {
                        try {
                            String[] dtt = date.split("-");
                            String orDate = dtt[2] + "-" + dtt[1] + "-" + dtt[0];
                            String[] dtt2 = date2.split("-");
                            String orDate2 = dtt2[2] + "-" + dtt2[1] + "-" + dtt2[0];
                            System.out.println("date=" + orDate);
                            sql += "date BETWEEN '" + orDate + "' AND '" + orDate2 + "' AND ";

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    if (!cat.equals("") || !ct.equals("ctct") && !ct.equals("ctnullct")) {
                        if(!ct.equals("ct0ct")){
                        sql += "categories_category_id='" + cat + "' AND ";
                        }

                    }
                    if (!usnm.equals("") || !un.equals("unun") && !un.equals("unnullun")) {
                        if(!un.equals("un0un")){
                        sql += "user_user_id='" + usnm + "' AND ";
                        }

                    }
                    if (!key.equals("")|| !ky.equals("kyky") && !ky.equals("kynullky")) {
                        sql += "images_id IN(SELECT images_images_id FROM key_words WHERE key_word LIKE '%" + key + "%') AND ";
                    }

                    sql += "image_state_image_state_id='1' OR ";

                    if (!date.equals("")  || !dt.equals("dtdt") && !dt.equals("dtnulldt") && !date2.equals("") ) {
                        try {
                            String[] dtt = date.split("-");
                            String orDate = dtt[2] + "-" + dtt[1] + "-" + dtt[0];
                            String[] dtt2 = date2.split("-");
                            String orDate2 = dtt2[2] + "-" + dtt2[1] + "-" + dtt2[0];
                            System.out.println("date=" + orDate);
                            sql += "date BETWEEN '" + orDate + "' AND '" + orDate2 + "' AND ";

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    if (!cat.equals("") || !ct.equals("ctct") && !ct.equals("ctnullct")) {
                        if(!ct.equals("ct0ct")){
                        sql += "categories_category_id='" + cat + "' AND ";
                        }

                    }
                    if (!usnm.equals("") || !un.equals("unun") && !un.equals("unnullun")) {
                        if(!un.equals("un0un")){
                        sql += "user_user_id='" + usnm + "' AND ";
                        }

                    }
                    if (!key.equals("")|| !ky.equals("kyky") && !ky.equals("kynullky")) {
                        sql += "images_id IN(SELECT images_images_id FROM key_words WHERE key_word LIKE '%" + key + "%') AND ";
                    }
                    
                    sql +="image_state_image_state_id='5' ORDER BY images_id DESC";
                    
                    System.out.println(sql);

                    try {

                        PreparedStatement ps = DBFactory.getConnection().prepareStatement(sql);
                        ResultSet rs = ps.executeQuery();

                        while (rs.next()) {
                            System.out.println(""+rs.getInt(1));
                            if (rs.getInt(20) != 3) {

                                if (rs.getInt(20) == 1 || rs.getInt(20) == 5) {
                                    JSONObject jo = new JSONObject();
                                    jo.put("id", rs.getInt(1));
                                    jo.put("url", rs.getString(15));
                                    jo.put("title", rs.getString(2));
                                    jo.put("date", rs.getString(16));
                                    catt = cdi.getCategory(rs.getInt(19));
                                    jo.put("cat", catt.getCategory());
                                    us = udi.getUser(rs.getInt(18));
                                    jo.put("sel", us.getUser_name());
                                    jo.put("state", rs.getInt(20));
                                    ja.add(jo);
                                }
                            }
                        }

                        System.out.println("-------" + ja.toJSONString());
                        out.write("json=" + ja.toJSONString());

                    } catch (Exception e) {
                        e.printStackTrace();
                        out.write("msg=Internal server error,Please try again later.");
                    }

                } else {
                     out.write("msg=Internal server error,Please try again later.");
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
