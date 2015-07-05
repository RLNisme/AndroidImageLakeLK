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
public class Servlet_RemoveSubImages extends HttpServlet {

   protected void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
       PrintWriter out=response.getWriter();
       
       String subimgid=request.getParameter("subimgid");
       String imgid=request.getParameter("imgid");
       System.out.println("remove"+subimgid+"/"+imgid);
       String remove=new ImagesDAOImp().removeSubImage(Integer.parseInt(subimgid), Integer.parseInt(imgid));
       System.out.println(remove);
       out.write(remove);
   }
}
