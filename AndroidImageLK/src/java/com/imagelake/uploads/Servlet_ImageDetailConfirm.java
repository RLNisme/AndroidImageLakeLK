/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.uploads;

import com.imagelake.control.ImagesDAOImp;
import com.imagelake.model.Images;
import com.imagelake.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Lakmal
 */
public class Servlet_ImageDetailConfirm extends HttpServlet {

    protected void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
        PrintWriter out=response.getWriter();
        Date date=new Date();
        SimpleDateFormat sdt=new SimpleDateFormat("YYYY-MM-dd");
        String d=sdt.format(date);
        
        String title,category;
        title=request.getParameter("title");
        category=request.getParameter("category");
        
        HttpSession ses=request.getSession();
        User u=(User) ses.getAttribute("user");
        
        if(u != null){
            Images i=new Images();
        i.setTitle(title);
        i.setCategories_category_id(Integer.parseInt(category));
        i.setColor_1("");
        i.setColor_2("");
        i.setColor_3("");
        i.setColor_4("");
        i.setColor_5("");
        i.setColor_6("");
        i.setColor_7("");
        i.setColor_8("");
        i.setColor_9("");
        i.setDate(d);
        i.setDominate_color("");
        i.setImage_state_image_state_id(2);
        i.setImg_blob(null);
        i.setImg_url("");
        i.setKey_wrods("");
        i.setUser_user_id(u.getUser_id());
        i.setView(0);
        ImagesDAOImp imDAOImp=new ImagesDAOImp();
        boolean b=imDAOImp.insertImage(i);
        if(b){
            ImagesDAOImp imageDAOimg=new ImagesDAOImp();
            int k=imageDAOimg.getImageId(title);
            
            out.write(String.valueOf(k));
            
        }else{
            out.write("error");
        }
        }
        
        
        
    }
}
