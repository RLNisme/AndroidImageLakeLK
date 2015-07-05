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
public class Servlet_FinishUploads extends HttpServlet {

    protected void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
        PrintWriter out=response.getWriter();
        String imgid=request.getParameter("imgid");
        System.out.println(imgid);
        String finish=new ImagesDAOImp().updateImageState(Integer.parseInt(imgid));
        if(finish.equals("done")){
            out.write("Finish");
            System.out.println("finish");
        }
        else if(finish.equals("nomindimention")){
            out.write("nomindimention");
            System.out.println("nomindimention");
         }
        else if(finish.equals("error")){
         out.write("Error");
         System.out.println("Error");
         
        }else if(finish.equals("<3")){
            out.write("<3");
            System.out.println("<3");
        }
        else if(finish.equals("<2")){
            out.write("<2");
            System.out.println("<2");
        }
        else if(finish.equals("<400")){
            out.write("<400");
            System.out.println("<400");
        }
   }
}
