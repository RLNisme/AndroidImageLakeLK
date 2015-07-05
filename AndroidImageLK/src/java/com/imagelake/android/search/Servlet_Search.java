/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.android.search;

import com.imagelake.control.CartDAOImp;
import com.imagelake.control.CategoriesDAOImp;
import com.imagelake.control.DBFactory;
import com.imagelake.control.ImagesDAOImp;
import com.imagelake.control.UserDAOImp;
import com.imagelake.model.CartHasImages;
import com.imagelake.model.Categories;
import com.imagelake.model.Images;
import com.imagelake.model.ImagesSub;
import com.imagelake.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author RLN
 */
public class Servlet_Search extends HttpServlet {

    private int noOfRecords;
    JSONArray ja;
    JSONArray jja;
    ImagesDAOImp idi;
    UserDAOImp udi;
    ImagesSub isb;
    Categories c;
    CategoriesDAOImp ctdi;
    User us;
    CartDAOImp cdi;
    double rate = 0.0;
    double userCount = 0.0;
    double totalRate = 0.0;

    public Servlet_Search() {
        idi = new ImagesDAOImp();
        udi = new UserDAOImp();
        cdi = new CartDAOImp();
        ctdi = new CategoriesDAOImp();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        try {

            String type = request.getParameter("type");
            System.out.println(type);
            
            //----------------------
            int page = 1;
            int recordsPerPage = 10;

            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
        //---------------------

            //get the session 
            SearchResults sr = new SearchResults();
//        //check that the session has already have a SearchResult object
//        if (ses.getAttribute("results") == null) {
//            System.out.println("no previous session");
//            sr = new SearchResults();
//
//        } else {
//
//            sr = (SearchResults) ses.getAttribute("results");
//        }
//        sr.setImList(new ArrayList<Images>());
//        
            if (type.equals("SearchImages")) {
                jja = new JSONArray();
                //int noOfRecords = 0;
                String ca = request.getParameter("cat");
                String ke = request.getParameter("key");
                //String colo=request.getParameter("colo");
                String credFrom = request.getParameter("from");
                String credTo = request.getParameter("to");
                String seller = request.getParameter("sell");
                String size = request.getParameter("size");

                System.out.println(ca);
                System.out.println(ke);
                System.out.println(credFrom);
                System.out.println(credTo);
                System.out.println(seller);
                System.out.println(size);
                
                
                String A = "A" + ca + "A";
                String B = "B" + ke+ "B";
                //String C = "C" + colo + "C";
                String D = "D" + credFrom + "D";
                String G = "G" + credTo + "G";
                String E = "E" + seller + "E";
                String F = "F" + size + "F";

                System.out.println("cat:" + ca + "-------------" + A);
                System.out.println("Key:" + ke + "-------------" + B);
                //System.out.println("colo:" + colo + "-------------" + C);
                System.out.println("cred:" + credFrom + "-------------" + D);
                System.out.println("cred:" + credTo + "-------------" + G);
                System.out.println("seller:" + seller + "-------------" + E);
                System.out.println("size:" + size + "-------------" + F);
                
                
                String key[] = ke.split("-");
                String ky = "";
                for(int i =0;i<key.length;i++){
                    if(i == (key.length - 1)){
                        ky += key[i]; 
                    }else{
                        ky += key[i]+ " "; 
                    }
                }

                if (ca != null && ca != "" && !A.equals("AA")&& !A.equals("A0A")) {
                    System.out.println("cat is not null" + ca);
                    sr.setCategory(ca);
                    System.out.println("cat is not null" + sr.getCategory());
                } else if (A.equals("AcloseA")) {
                    sr.setCategory(null);
                }
                if (ke != null && ke != "" && !B.equals("BB") && !B.equals("BnullB")) {
                    System.out.println("key is not null" + ky);
                    sr.setKeyWord(ky);
                    System.out.println("key is not null" + sr.getKeyWord());
                } else if (B.equals("BcloseB")) {
                    sr.setKeyWord(null);
                }
//        if(colo!=null && colo!="" && !C.equals("CnullC") &&!C.equals("CC")&&!C.equals("CcloseC")){
//            System.out.println("color is not null"+colo);
//            HexaToRBG hrg=new HexaToRBG();
//            String rgbColo=hrg.convertHexToRGB("0x00"+colo);
//            sr.setColor(rgbColo);
//            System.out.println("color is not null"+sr.getColor());
//        }else if(C.equals("CcloseC")){
//            sr.setColor(null);
//        }
                if (credFrom != null && credFrom != "" && !D.equals("DD") && !D.equals("DnullD") && !D.equals("D0D") && credTo != null && credTo != "" && !G.equals("GG") && !G.equals("GnullG") && !G.equals("G0G")) {
                    System.out.println("credits is not null" + credFrom);
                    sr.setCreditFrom(Integer.parseInt(credFrom));
                    System.out.println("key is not null" + sr.getCreditFrom());

                    sr.setCreditTo(Integer.parseInt(credTo));
                    System.out.println("key is not null" + sr.getCreditTo());

                } else if (D.equals("D0D") && G.equals("G0G")) {
                    sr.setCreditFrom(0);
                    sr.setCreditTo(0);
                }
                if (seller != null && seller != "" && !E.equals("EE") && !E.equals("EnullE") && !E.equals("E0E")) {
                    System.out.println("color is not null" + seller);
                    sr.setSellerId(Integer.parseInt(seller));
                    System.out.println("color is not null" + sr.getSellerId());
                } else if (E.equals("E0E")) {
                    sr.setSellerId(0);
                }
                if (size != null && size != "" && !F.equals("FF") && !F.equals("FnullF") && !F.equals("F0F")) {
                    System.out.println("credits is not null" + size);
                    String c[]= size.split("x");
                    sr.setSize(c[0]+" x "+c[1]);
                    System.out.println("key is not null" + sr.getSize());
                } else if (F.equals("FcloseF")) {
                    sr.setSize(null);
                }

                String sql = "SELECT SQL_CALC_FOUND_ROWS * FROM images WHERE image_state_image_state_id='1' AND ";
                if (sr.getCategory() != null) {
                    sql += "categories_category_id='" + sr.getCategory() + "'";
                    if (sr.getColor() != null || sr.getCreditFrom() != 0 && sr.getCreditTo() != 0 || sr.getKeyWord() != null || sr.getSellerId() != 0 || sr.getSize() != null) {
                        sql += " AND ";
                    }
                }
                if (sr.getSellerId() != 0) {
                    sql += "user_user_id='" + sr.getSellerId() + "'";
                    if (sr.getColor() != null || sr.getCreditFrom() != 0 && sr.getCreditTo() != 0 || sr.getKeyWord() != null || sr.getSize() != null) {
                        sql += " AND ";
                    }
                }
//                if (sr.getColor() != null) {
//                    sql += "images_id IN(SELECT images_id FROM images WHERE dominate_color='" + sr.getColor() + "' OR color_1='" + sr.getColor() + "' OR color_2='" + sr.getColor() + "' OR color_3='" + sr.getColor() + "' OR color_4='" + sr.getColor() + "' OR color_5='" + sr.getColor() + "' OR color_6='" + sr.getColor() + "' OR color_7='" + sr.getColor() + "' OR color_8='" + sr.getColor() + "' OR color_9='" + sr.getColor() + "')";
//                    if (sr.getCreditFrom() != 0 && sr.getCreditTo() != 0 || sr.getKeyWord() != null || sr.getSize() != null) {
//                        sql += " AND ";
//                    }
//                }
                if (sr.getCreditFrom() != 0 && sr.getCreditTo() != 0) {
                    sql += "images_id IN(SELECT images_images_id FROM sub_images WHERE credits IN(SELECT credit_id FROM credits WHERE credits BETWEEN '" + sr.getCreditTo() + "' AND '" + sr.getCreditFrom() + "'))";
                    if (sr.getKeyWord() != null || sr.getSize() != null) {
                        sql += " AND ";
                    }
                }

                if (sr.getKeyWord() != null) {
                    sql += "images_id IN(SELECT images_images_id FROM key_words WHERE key_word LIKE '%" + sr.getKeyWord() + "%')";
                    if (sr.getSize() != null) {
                        sql += " AND ";
                    }
                }
                if (sr.getSize() != null) {
                    sql += "images_id IN(SELECT images_images_id FROM sub_images WHERE dimention='" + sr.getSize() + "')";
                }

                if (sr.getCategory() == null && sr.getColor() == null && sr.getCreditFrom() == 0 && sr.getCreditTo() == 0 && sr.getKeyWord() == null && sr.getSellerId() == 0 && sr.getSize() == null) {
                    sql += "image_state_image_state_id='1' ";
                }
                sql += " ORDER BY date DESC LIMIT " + (page - 1) * recordsPerPage + ", " + recordsPerPage;
                System.out.println(sql);
                try {
                    PreparedStatement ps = DBFactory.getConnection().prepareStatement(sql);                   
                    ResultSet rs = ps.executeQuery();
                    ja = new JSONArray();
                    jja = new JSONArray();
                    while (rs.next()) {
                        System.out.println("Image id from category:" + rs.getInt(1));

                        c = ctdi.getCategory(rs.getInt(19));    
                        us = udi.getUser(rs.getInt(18));
                        //isb = idi.getSubImage(rs.getInt(1));
                        JSONObject jo = new JSONObject();
                        jo.put("id", rs.getInt(1));                       
                        jo.put("cat", c.getCategory());
                        jo.put("title", rs.getString(2));                        
                        jo.put("seller", us.getUser_name());
                        jo.put("url", rs.getString(15));                       
                        jo.put("dimention", rs.getString(16));
                        
                        List<CartHasImages> chiList = cdi.getRattingList(rs.getInt(1));
                        rate = 0.0;
                        userCount = 0.0;
                        totalRate = 0.0;

                        if (!chiList.isEmpty()) {
                            for (CartHasImages chi : chiList) {
                                totalRate += chi.getRatting();
                                userCount++;
                                rate = totalRate / userCount;
                            }
                        }
                        jo.put("rate", rate);
                        ja.add(jo);
                    }
                    jja.add(ja);
                    
                    rs.close();
                    
                    PreparedStatement ps2 = DBFactory.getConnection().prepareStatement(sql);
                    ResultSet rs2 = ps.executeQuery();
                    
                    rs2 = ps2.executeQuery("SELECT FOUND_ROWS()");
                    if (rs2.next()) {
                        this.noOfRecords = rs2.getInt(1);
                        System.out.println(rs2.getInt(1));
                    }
                    //jja.add(ja);
                } catch (Exception e) {
                    e.printStackTrace();

                }
                int noOfRecords = getNoOfRecords();
                int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

                JSONObject jo = new JSONObject();
                jo.put("noOfPages", noOfPages);
                jo.put("currentPage", page);
                jja.add(jo);
                // System.out.println("list empty: " + sr.getImList().isEmpty());
//        ses.setAttribute("results", sr);
//        ses.setAttribute("noOfPages", noOfPages);
//        ses.setAttribute("currentPage", page);
                System.out.println(jja.toJSONString());
                out.write("json="+jja.toJSONString());
            } else if (type.equals("AllImages")) {
                //int noOfRecords = 0;
                String sql = "SELECT SQL_CALC_FOUND_ROWS * FROM images WHERE image_state_image_state_id='1'";
                sql += " ORDER BY date DESC LIMIT " + (page - 1) * recordsPerPage + ", " + recordsPerPage;
                System.out.println(sql);
                try {
                    PreparedStatement ps = DBFactory.getConnection().prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();
                    ja = new JSONArray();
                    jja = new JSONArray();
                    while (rs.next()) {
                        System.out.println("Image id from category:" + rs.getInt(1));
                        c = ctdi.getCategory(rs.getInt(19));    
                        us = udi.getUser(rs.getInt(18));
                        //isb = idi.getSubImage(rs.getInt(1));
                        JSONObject jo = new JSONObject();
                        jo.put("id", rs.getInt(1));                       
                        jo.put("cat", c.getCategory());
                        jo.put("title", rs.getString(2));                        
                        jo.put("seller", us.getUser_name());
                        jo.put("url", rs.getString(15));                       
                        jo.put("dimention", rs.getString(16));
                        
                        List<CartHasImages> chiList = cdi.getRattingList(rs.getInt(1));
                        rate = 0.0;
                        userCount = 0.0;
                        totalRate = 0.0;

                        if (!chiList.isEmpty()) {
                            for (CartHasImages chi : chiList) {
                                totalRate += chi.getRatting();
                                userCount++;
                                rate = totalRate / userCount;
                            }
                        }
                        jo.put("rate", rate);
                        ja.add(jo);
                    }
                    jja.add(ja);
                    
                    rs.close();
                    
                    PreparedStatement ps2 = DBFactory.getConnection().prepareStatement(sql);
                    ResultSet rs2 = ps.executeQuery();
                    
                    rs2 = ps2.executeQuery("SELECT FOUND_ROWS()");
                    if (rs2.next()) {
                        this.noOfRecords = rs2.getInt(1);
                        System.out.println(rs2.getInt(1));
                    }
                   
                } catch (Exception e) {
                    e.printStackTrace();

                }
                
                int noOfRecords = getNoOfRecords();
                int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
                System.out.println("No of records"+noOfRecords);
                System.out.println("No of pages"+noOfPages);
                
                JSONObject jo = new JSONObject();
                jo.put("noOfPages", noOfPages);
                jo.put("currentPage", page);
                jja.add(jo);
                // System.out.println("list empty: " + sr.getImList().isEmpty());
//        ses.setAttribute("results", sr);
//        ses.setAttribute("noOfPages", noOfPages);
//        ses.setAttribute("currentPage", page);
                System.out.println(jja.toJSONString());
                out.write("json="+jja.toJSONString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getNoOfRecords() {
        return noOfRecords;
    }

}
