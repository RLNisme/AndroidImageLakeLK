/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.android.changepassword;

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
public class Servlet_ChangePassword extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        try {

            String uid = request.getParameter("uid");
            String oldPw = request.getParameter("old-psw");
            String newPw = request.getParameter("new-psw");
            String rtypw = request.getParameter("rty-psw");
            String type = request.getParameter("type");

           // System.out.println("uid:" + uid + " oldpw:" + oldPw + " newPw:" + newPw + " rtypw:" + rtypw);
            if (!oldPw.equals("")) {
                
                User user = new UserDAOImp().searchPassword(oldPw, Integer.parseInt(uid));
                System.out.println(user != null);
                
                if (user != null) {

                    if (!user.getPassword().equals(oldPw)) {

                       
                        out.write("msg=Unable to change password");
                    } else {

                        if (newPw.equals("") && rtypw.equals("")) {
                            
                            out.write("msg=Unable to change password");
                        } else {

                            if (newPw.equals(rtypw)) {
                                
                                System.out.println("newpw==rtypw");
                                boolean pwup = new UserDAOImp().updatePassword(newPw, user.getUser_id());
                                
                                if (pwup) {
                                    out.write("msg=Successful...");
                                } else {
                                   
                                    out.write("msg=Unable to change password please try again later.");
                                }

                            } else {
                                
                                out.write("msg=Password mismatch");
                            }

                        }
                    }
                } else {
                   
                    out.write("msg=Please check the password you have entered");
                }
            } else {
                
                 out.write("msg=Please check the password you have entered");
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
