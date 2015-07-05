/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.android.lightbox;

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
public class Servlet_LightBox extends HttpServlet {

    private final int LOAD_LIGHTBOX = 1;
    private final int LOAD_DATE = 2;
    

    int type_id;
    int user_id;
    CartDAOImp cdi;
    ImagesDAOImp idi;
    Images im=null;
    ImagesSub ims=null;

    public Servlet_LightBox() {

        cdi = new CartDAOImp();
        idi = new ImagesDAOImp();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        String type = request.getParameter("type");
        System.out.println("type :"+type);    
        JSONArray box_list = new JSONArray();
        
        if (type != null) {
            type_id = Integer.parseInt(type);
            if (type_id == LOAD_LIGHTBOX) {

                String userId = request.getParameter("user_id");
                System.out.println("user id :"+userId);    
                if(userId != null){
                    
                    user_id = Integer.parseInt(userId);
                    
                    Cart c=cdi.getCart(user_id);
                    
                    if(c != null){
                        System.out.println("cart :"+c.getCart_id());    
                        List<CartHasImages> list=new CartDAOImp().listUserCartHasImages(c.getCart_id(),2);
                        if(!list.isEmpty()){
                            
                            for (CartHasImages ca : list) {
                                
                                im=idi.getImageDetail(ca.getImg_id());
                                //ims=idi.getSubImage(ca.getSubimg_id());
                                    
                                 if(im.getImage_state_image_state_id()==1 && im.getImage_state_image_state_id()!=3){
                                 
                                     JSONObject jo = new JSONObject();
                                     jo.put("cart_id", ca.getCart_has_images_id());
                                     jo.put("title", im.getTitle());
                                     jo.put("date", ca.getDate());
                                     jo.put("state", im.getImage_state_image_state_id());
                                     box_list.add(jo);
                                     
                                 }else if(im.getImage_state_image_state_id()==5 && im.getImage_state_image_state_id()!=3){
                                 
                                    JSONObject jo = new JSONObject();
                                     jo.put("cart_id", ca.getCart_has_images_id());
                                     jo.put("title", im.getTitle());
                                     jo.put("date", ca.getDate());
                                     jo.put("state", im.getImage_state_image_state_id());
                                     box_list.add(jo);
                                     
                                 }
                                
                            }
                            System.out.println(box_list.toJSONString());
                            out.write("json="+box_list.toJSONString());
                        }else{
                            out.write("msg=No item found.");
                        }
                    }else{
                    
                    }
                    
                }else{
                    out.write("msg=Internal server error,please try agin later.");
                }
            //List<CartHasImages> list=new CartDAOImp().listUserCartHasImages(c.getCart_id(),1);
            }else if(type_id == LOAD_DATE){
                
                String userId = request.getParameter("user_id");
                
                if(userId != null){
                    
                    user_id = Integer.parseInt(userId);
                    
                    Cart c=new CartDAOImp().getCart(user_id);
                        
                    if(c != null){
                        
                         System.out.println("cart :"+c.getCart_id());   
                         
                         List<CartHasImages> list=new CartDAOImp().sortByDate(2, "DESC", c.getCart_id(), "date");
                         
                         if(!list.isEmpty()){
                         
                             for (CartHasImages ca : list) {
                                
                                im=idi.getImageDetail(ca.getImg_id());
                                ims=idi.getSubImage(ca.getSubimg_id());
                                    
                                 if(im.getImage_state_image_state_id()==1 && im.getImage_state_image_state_id()!=3){
                                 
                                     JSONObject jo = new JSONObject();
                                     jo.put("cart_id", ca.getCart_has_images_id());
                                     jo.put("title", im.getTitle());
                                     jo.put("date", ca.getDate());
                                     jo.put("state", im.getImage_state_image_state_id());
                                     box_list.add(jo);
                                     
                                 }else if(im.getImage_state_image_state_id()==5 && im.getImage_state_image_state_id()!=3){
                                 
                                    JSONObject jo = new JSONObject();
                                     jo.put("cart_id", ca.getCart_has_images_id());
                                     jo.put("title", im.getTitle());
                                     jo.put("date", ca.getDate());
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
                    
                }else{
                    out.write("msg=Internal server error,please try agin later.");
                }
                
            }

        }else{
            out.write("msg=Internal server error,please try agin later.");
        }
    }
}
