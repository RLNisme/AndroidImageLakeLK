/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.android.settings;

import com.imagelake.control.CategoriesDAOImp;
import com.imagelake.control.CreditsDAOImp;
import com.imagelake.model.Categories;
import com.imagelake.model.Credits;
import com.imagelake.model.SliceImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
public class Servlet_ImageSetting extends HttpServlet {

    CreditsDAOImp cdoi;
    CategoriesDAOImp cdi;
    JSONArray ja;
    public Servlet_ImageSetting() {
        cdoi = new CreditsDAOImp();
        cdi = new CategoriesDAOImp();
        
    }

    
    
    protected void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
        PrintWriter out = response.getWriter();
        try {
            String type = request.getParameter("type");
            System.out.println("type :"+type);
            if(type!= null && !type.equals("")){
                
                if(type.equals("SliceTypes")){
                
                    String sliceid = request.getParameter("sliceType");
                    if (sliceid != null && !sliceid.trim().equals("") && Integer.parseInt(sliceid) != 0) {
                        List<SliceImage> sl = cdoi.getTypeSliceList(Integer.parseInt(sliceid), 1);
                        
                        if (!sl.isEmpty()) {
                            ja = new JSONArray();
                            for (SliceImage sliceImage : sl) {
                                Credits cr = cdoi.getCreditDetails(sliceImage.getCredit_id());
                                
                                JSONObject jo = new JSONObject();
                                jo.put("cid", cr.getCredit_id());
                                jo.put("csize", cr.getSize());
                                jo.put("sid", sliceImage.getId());
                                ja.add(jo);
                                
                            }
                            System.out.println(ja.toJSONString());
                            out.write("json="+ja.toJSONString());
                        } else {
                            
                            out.write("msg=No item found.");
                        }
                    } else {
                        String li = "";
                        out.write(li);
                    }

                    
                }else if (type.equals("allimageSlices")) {

                    String k = cdoi.getFullSliceDetails();
                    System.out.println("KKKKK" + k);
                    
                    response.getWriter().write(k);

                }else if (type.equals("addsliceimage")) {
                    String sliceid = request.getParameter("creditId");
                    String sliceType = request.getParameter("sliceType");
                    System.out.println(sliceid);
                    System.out.println(sliceType);
                    if (sliceid != null && !sliceid.trim().equals("") && Integer.parseInt(sliceid) != 0 && sliceType!=null && !sliceType.trim().equals("") && Integer.parseInt(sliceType)!=0) {
                        
                        boolean ok=cdoi.insertSliceImage(Integer.parseInt(sliceid),Integer.parseInt(sliceType),1);
                        if (ok) {
                            out.write("msg=Add_Slice_Successful...");
                        } else {
                            out.write("msg=Unable to complete the action.");
                        }
                        
                    }else{
                        out.write("msg=Internal server error,Please try again later.");
                    }
                }else if(type.equals("removesliceimage")){
                    String sliceid = request.getParameter("sliceId");
                     if (sliceid != null && !sliceid.trim().equals("") && Integer.parseInt(sliceid) != 0 ){
                         boolean ok=cdoi.removeSliceImage(Integer.parseInt(sliceid));
                         if (ok) {
                            out.write("msg=Remove_Slice_Successful...");
                        } else {
                            out.write("msg=Unable to complete the action.");
                        }
                     }else{
                     out.write("msg=Internal server error,Please try again later.");
                     }
                }else if (type.equals("AllCategories")) {
                    List<Categories> li = new CategoriesDAOImp().listAllCategories();
                    ja = new JSONArray();
                    for (Categories categories : li) {
                        JSONObject jo = new JSONObject();
                        jo.put("id",categories.getCategory_id());
                        jo.put("cat",categories.getCategory());
                        ja.add(jo);
                    }
                    out.write("json="+ja.toJSONString());
                    
                } else if (type.equals("newCategories")) {
                    String category = request.getParameter("category");
                    System.out.println("categ: " + category);
                    if (category != null && !category.trim().equals("")) {
                        boolean ok = new CategoriesDAOImp().insertCategory(category);
                        if (ok) {
                            out.write("msg=Add_Category_Successful...");
                        } else {
                            out.write("msg=Unable to complete the action.");
                        }
                    } else {
                       out.write("msg=Internal server error,Please try again later.");
                    }
                } 
                
            }else{
                out.write("msg=Internal server error,Please try again later.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
