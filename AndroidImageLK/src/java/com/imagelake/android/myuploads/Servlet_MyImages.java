/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.android.myuploads;

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
public class Servlet_MyImages extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            String uid=request.getParameter("uid");
        System.out.println("///mmm"+uid);
        String imglist=new CreateMyUploadthumbnail().createThumb(Integer.parseInt(uid));
        System.gc();
        if(imglist !=null || !imglist.equals("")){
                out.write(imglist);
                System.out.println(imglist);
        }else{
            out.write("msg=Internal server error,please try agin later.");
            System.out.println("error");
        }
        System.gc();
         
    
        } catch (Exception e) {
            out.write("msg=Internal server error,please try agin later.");
            e.printStackTrace();
        }
    }

    
}
