/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.android.download;

import com.imagelake.control.CartDAOImp;
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
public class Servlet_Rating extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        try {
             String chimID = request.getParameter("chid"); 
            String rate = request.getParameter("x");
            System.out.println("****** " + rate + " **** " + chimID);

            boolean ok = new CartDAOImp().updateRatting(Integer.parseInt(chimID), Double.parseDouble(rate));
            System.out.println(ok);
            if (ok) {
                out.write("msg=Rating added!");
            } else {
                out.write("msg=Unable to complete the action.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
