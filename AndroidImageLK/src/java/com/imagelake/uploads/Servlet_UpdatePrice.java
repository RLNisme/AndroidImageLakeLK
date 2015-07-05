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
public class Servlet_UpdatePrice extends HttpServlet {

    protected void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
        PrintWriter out=response.getWriter();
        String subimgid=request.getParameter("subid");
        String udprice=request.getParameter("udprice");
        String dimention=request.getParameter("updimention");
        
        System.out.println(subimgid+"--"+udprice+"---"+dimention);
        boolean update=new ImagesDAOImp().updatePriceUpload(Integer.parseInt(subimgid),Double.valueOf(udprice),dimention);
        System.out.println("update:-"+update);
        if(update){
            out.write("Updated");
        }else{
            out.write("Error");
        }
    }
}
