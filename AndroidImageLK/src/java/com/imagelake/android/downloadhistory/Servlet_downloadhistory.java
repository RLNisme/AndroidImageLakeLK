/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.android.downloadhistory;

import com.imagelake.control.CartDAOImp;
import com.imagelake.control.ImagesDAOImp;
import com.imagelake.model.Cart;
import com.imagelake.model.CartHasImages;
import com.imagelake.model.Images;
import com.imagelake.model.ImagesSub;
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
public class Servlet_downloadhistory extends HttpServlet {

    private static final int LOAD_DOWNLOAD_HISTORY = 1;
    private static final int LOAD_DATE = 2;
    private static final int LOAD_SIZE = 3;
    private static final int DOWNLOAD_IMG = 4;

    int user_id;
    int type;
    Images im = null;
    ImagesSub ims = null;
    Cart c;
    CartDAOImp cdi;
    ImagesDAOImp idi;

    public Servlet_downloadhistory() {
        cdi = new CartDAOImp();
        idi = new ImagesDAOImp();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        PrintWriter out =response.getWriter();
        
        String type_id = request.getParameter("type");
         JSONArray box_list = new JSONArray();
        if (type_id != null) {
            type = Integer.parseInt(type_id);
            String userId = request.getParameter("user_id");

            if (userId != null) {

                user_id = Integer.parseInt(userId);
                c = cdi.getCart(user_id);

                if (c != null) {

                    if (type == LOAD_DOWNLOAD_HISTORY) {

                        System.out.println("cart :" + c.getCart_id());
                        List<CartHasImages> list = new CartDAOImp().listUserCartHasImages(c.getCart_id(), 1);
                        
                        if(!list.isEmpty()){
                            
                            for (CartHasImages ca : list) {
                                
                                im=idi.getImageDetail(ca.getImg_id());
                               // ims=idi.getSubImage(ca.getSubimg_id());
                                
                                 if(im.getImage_state_image_state_id()==1 && im.getImage_state_image_state_id()!=3){
                                 
                                     JSONObject jo = new JSONObject();
                                     jo.put("cart_id", ca.getCart_has_images_id());
                                     jo.put("title", im.getTitle());
                                     jo.put("sub_id", ca.getSubimg_id());
                                     jo.put("date", ca.getDate());
                                     jo.put("credits", ca.getCredits());
                                     jo.put("state", im.getImage_state_image_state_id());
                                     box_list.add(jo);
                                     
                                 }else if(im.getImage_state_image_state_id()==5 && im.getImage_state_image_state_id()!=3){
                                 
                                    JSONObject jo = new JSONObject();
                                     jo.put("cart_id", ca.getCart_has_images_id());
                                     jo.put("title", im.getTitle());
                                     jo.put("sub_id", ca.getSubimg_id());
                                     jo.put("date", ca.getDate());
                                     jo.put("credits", ca.getCredits());
                                     jo.put("state", im.getImage_state_image_state_id());
                                     box_list.add(jo);
                                     
                                 }
                                
                            }
                            System.out.println(box_list.toJSONString());
                                out.write("json="+box_list.toJSONString());
                        }else{
                            out.write("msg=No item found.");
                        }

                    } else if (type == LOAD_DATE) {

                        System.out.println("cart :"+c.getCart_id());   
                         
                         List<CartHasImages> list=new CartDAOImp().sortByDate(1, "DESC", c.getCart_id(), "date");
                         
                         if(!list.isEmpty()){
                            
                            for (CartHasImages ca : list) {
                                
                                im=idi.getImageDetail(ca.getImg_id());
                               // ims=idi.getSubImage(ca.getSubimg_id());
                                
                                 if(im.getImage_state_image_state_id()==1 && im.getImage_state_image_state_id()!=3){
                                 
                                     JSONObject jo = new JSONObject();
                                     jo.put("cart_id", ca.getCart_has_images_id());
                                     jo.put("title", im.getTitle());
                                     jo.put("sub_id", ca.getSubimg_id());
                                     jo.put("date", ca.getDate());
                                     jo.put("credits", ca.getCredits());
                                     jo.put("state", im.getImage_state_image_state_id());
                                     box_list.add(jo);
                                     
                                 }else if(im.getImage_state_image_state_id()==5 && im.getImage_state_image_state_id()!=3){
                                 
                                    JSONObject jo = new JSONObject();
                                     jo.put("cart_id", ca.getCart_has_images_id());
                                     jo.put("title", im.getTitle());
                                     jo.put("sub_id", ca.getSubimg_id());
                                     jo.put("date", ca.getDate());
                                     jo.put("credits", ca.getCredits());
                                     jo.put("state", im.getImage_state_image_state_id());
                                     box_list.add(jo);
                                     
                                 }
                                
                            }
                            System.out.println(box_list.toJSONString());
                                out.write("json="+box_list.toJSONString());
                        }else{
                            out.write("msg=No item found.");
                        }
                        
                    } else if (type == LOAD_SIZE) {

                        System.out.println("cart :"+c.getCart_id());   
                         
                         List<CartHasImages> list=new CartDAOImp().sortByDate(1, "ASC", c.getCart_id(), "credits");
                         
                         if(!list.isEmpty()){
                            
                            for (CartHasImages ca : list) {
                                
                                im=idi.getImageDetail(ca.getImg_id());
                               // ims=idi.getSubImage(ca.getSubimg_id());
                                
                                 if(im.getImage_state_image_state_id()==1 && im.getImage_state_image_state_id()!=3){
                                 
                                     JSONObject jo = new JSONObject();
                                     jo.put("cart_id", ca.getCart_has_images_id());
                                     jo.put("title", im.getTitle());
                                     jo.put("sub_id", ca.getSubimg_id());
                                     jo.put("date", ca.getDate());
                                     jo.put("credits", ca.getCredits());
                                     jo.put("state", im.getImage_state_image_state_id());
                                     box_list.add(jo);
                                     
                                 }else if(im.getImage_state_image_state_id()==5 && im.getImage_state_image_state_id()!=3){
                                 
                                    JSONObject jo = new JSONObject();
                                     jo.put("cart_id", ca.getCart_has_images_id());
                                     jo.put("title", im.getTitle());
                                     jo.put("sub_id", ca.getSubimg_id());
                                     jo.put("date", ca.getDate());
                                     jo.put("credits", ca.getCredits());
                                     jo.put("state", im.getImage_state_image_state_id());
                                     box_list.add(jo);
                                     
                                 }
                                
                            }
                            System.out.println(box_list.toJSONString());
                                out.write("json="+box_list.toJSONString());
                        }else{
                            out.write("msg=No item found.");
                        }
                        
                    }

                }
            }else{
                    out.write("msg=Internal server error,please try agin later.");
                }
        }else{
            out.write("msg=Internal server error,please try agin later.");
        }
    }
}
