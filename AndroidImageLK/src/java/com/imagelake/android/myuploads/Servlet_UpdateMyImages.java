/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.android.myuploads;

import com.imagelake.control.ImagesDAOImp;
import com.imagelake.model.Images;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author RLN
 */
public class Servlet_UpdateMyImages extends HttpServlet {

    protected void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
         PrintWriter out=response.getWriter();
        try{
       
        String imgid ="",uid ="";
        imgid=request.getParameter("imgid");
        uid = request.getParameter("uid");
        
           
            
            if(uid!=null && !uid.equals("") && imgid != null && !imgid.equals("")){
                ImagesDAOImp idip=new ImagesDAOImp();
        System.out.println("image id in update image"+imgid);
        System.out.println("user id in update image"+Integer.parseInt(uid));
        
                Images im=idip.checkUserToImage(Integer.parseInt(uid),Integer.parseInt(imgid));
        
        
        
        if(im!=null){
               if(im.getImage_state_image_state_id() == 1){
                    boolean b=idip.deleteMyImages(im.getImages_id());
                    System.out.println(b);
                    if(b){
                        out.write("msg=Successful...");
                    }
                }else{
                    out.write("msg=Image is blocked by the admin");
                }
        }
        
            }else{
                out.write("msg=Internal error,please try again later.");
            }
         System.gc();
        }catch(Exception ex){
            ex.printStackTrace();
            System.gc();
            out.write("msg=Internal error,please try again later.");
        }
        
    }

}
