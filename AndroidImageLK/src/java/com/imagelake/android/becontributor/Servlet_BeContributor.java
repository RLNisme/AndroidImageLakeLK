/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.android.becontributor;

import com.imagelake.control.SellerIncomeDAOImp;
import com.imagelake.control.UserDAOImp;
import com.imagelake.model.User;
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
public class Servlet_BeContributor extends HttpServlet {

   protected void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
       
       PrintWriter out = response.getWriter();
       try{
       String uid,fn,ln;
       uid=request.getParameter("uid");
       fn=request.getParameter("fn");
       ln=request.getParameter("ln");
       
       if(uid.equals("")||uid==null||fn.equals("")||ln.equals("")){
           out.write("msg=Please check user details.");
        }else{
            User u=new User();
            u.setUser_id(Integer.parseInt(uid));
            u.setFirst_name(fn);
            u.setLast_name(ln);
            u.setUser_type(2);
            System.gc();
            boolean b=new UserDAOImp().updatePrivateData(u);
            boolean a=new SellerIncomeDAOImp().setIncome(Integer.parseInt(uid));
            if(b && a){
                User user=new UserDAOImp().getUser(Integer.parseInt(uid));
               
                System.out.println("ok");
                out.write("msg=Successful...");
                
            }else{
                out.write("msg=Unable to complete the action please try again later.");
            }
        }
        
       
       
       }catch(Exception e){
           e.printStackTrace();
       }
   }
}
