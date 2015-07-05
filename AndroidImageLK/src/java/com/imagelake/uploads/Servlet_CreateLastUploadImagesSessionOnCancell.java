/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.uploads;

import com.imagelake.control.ImagesDAOImp;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lakmal
 */
public class Servlet_CreateLastUploadImagesSessionOnCancell extends HttpServlet {

    protected void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
        PrintWriter out=response.getWriter();
        
        String uid=request.getParameter("uid");
        int images_id=new ImagesDAOImp().getLastUplodedImagesId(Integer.parseInt(uid));
        int subimagesCount=new ImagesDAOImp().getLastUploadImageSize(images_id);
        
        if(subimagesCount>=3){
            out.write(">=3");
        }else{
            out.write("<3");
        }
        
    }
}
