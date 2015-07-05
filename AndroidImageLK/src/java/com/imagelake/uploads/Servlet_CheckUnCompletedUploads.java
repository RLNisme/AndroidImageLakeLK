/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.uploads;

import com.imagelake.control.ImagesDAOImp;
import com.imagelake.model.Images;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lakmal
 */
public class Servlet_CheckUnCompletedUploads extends HttpServlet {

    protected void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
        PrintWriter out=response.getWriter();
        String uid=request.getParameter("uid");
        System.out.println(uid+"/uid");
        List<Images> unfinish=new ImagesDAOImp().getLastUplodedImagesDetails(Integer.parseInt(uid));
        
        if(!unfinish.isEmpty()){
            String alert="";
            for (Images images : unfinish) {
                            alert+="" +
"                                         <div class='alert alert-danger' onclick='unFinishModalOk("+images.getImages_id()+")' style='cursor: pointer;'>" +
"                                            <strong style='font-size:17px;'>"+images.getTitle()+"</strong> upload hasn't finished on <small style='font-size:12px;'>"+images.getDate()+"</small>." +
"                                         </div>";

            }

            
            out.write(alert);
        }else{
            out.write("finish");
        }
    }
}
