/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.android.changephoneno;

import com.imagelake.control.UserDAOImp;
import com.imagelake.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author RLN
 */
public class Servlet_ChangePhoneno extends HttpServlet {

    UserDAOImp udi;

    public Servlet_ChangePhoneno() {
        udi = new UserDAOImp();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        try {
            String type = request.getParameter("type");
            if (type != null) {
                String uid = request.getParameter("uid");

                if (type.equals("load_phone")) {
                    if (uid != null) {
                        User u = udi.getUser(Integer.parseInt(uid));
                        if (u != null) {
                            JSONObject jo = new JSONObject();
                            jo.put("phn", u.getPhone());

                            System.out.println(jo.toJSONString());
                            out.write("json=" + jo.toJSONString());
                        } else {
                            out.write("msg=Internal server error,Please try again later.");
                        }
                    } else {
                        out.write("msg=Internal server error,Please try again later.");
                    }
                } else if(type.equals("update_phone")){
                    String phn = request.getParameter("phn");
                    if(phn != null){
                        boolean k = udi.updatePhoneNo(Integer.parseInt(uid), phn);
                        if(k){
                            out.write("msg=Successfully Completed.");
                        }else{
                            out.write("msg=Unable to complete the action,Please try again later.");
                        }
                    }else{
                        out.write("msg=Internal server error,Please try again later.");
                    }
                }
            } else {
                out.write("msg=Internal server error,Please try again later.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
