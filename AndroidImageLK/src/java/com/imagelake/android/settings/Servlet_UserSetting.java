/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.android.settings;

import com.imagelake.control.InterfaceDAOImp;
import com.imagelake.control.UserDAOImp;
import com.imagelake.model.Interfaces;
import com.imagelake.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author RLN
 */
public class Servlet_UserSetting extends HttpServlet {

    JSONArray ja;
    UserDAOImp udi;

    public Servlet_UserSetting() {
        udi = new UserDAOImp();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        try {

            String type = request.getParameter("type");

            if (type.equals("all")) {
                //ja = new JSONArray();
                String chart = udi.getSubadminList();
                System.out.println(chart);
                out.write(chart);
            } else if (type.equals("user_type_all")) {
                String list = udi.getJSONAllUserTypeList();
                out.write("json=" + list);
            } else if (type.equals("update")) {
                String adtype = request.getParameter("admintype");
                String state = request.getParameter("state");
                String id = request.getParameter("id");

                System.out.println("admin type:" + adtype);
                System.out.println("state:" + state);
                System.out.println("id:" + id);

                boolean A = udi.updateUserState(Integer.parseInt(id), Integer.parseInt(state));
                boolean B = udi.updateUserType(Integer.parseInt(id), Integer.parseInt(adtype));
                System.out.println(A + "|" + B);
                if (A && B) {
                    out.write("msg=Us_Successful...");
                } else {
                    out.write("msg=Unable to complete the action.");
                }

            }else if(type.equals("addnewsub")){
             InterfaceDAOImp idi=new InterfaceDAOImp();
             Interfaces in=new Interfaces();
             in.setState(1);
             in.setUrl("RegAdmin.jsp");
             boolean o=idi.updateInteface(in);
             if(o){
                 out.write("msg=addnewsub_Successful...");
             }else{
                 out.write("msg=Unable to complete the action.");
             }
        }
        else if(type.equals("closenewsub")){
             InterfaceDAOImp idi=new InterfaceDAOImp();
             Interfaces in=new Interfaces();
             in.setState(2);
             in.setUrl("RegAdmin.jsp");
             boolean o=idi.updateInteface(in);
             if(o){
                 out.write("msg=closenewsub_Successful...");
             }else{
                 out.write("msg=Unable to complete the action.");
             }
        }
        else if(type.equals("editsub")){
        InterfaceDAOImp idi=new InterfaceDAOImp();
             Interfaces in=new Interfaces();
             in.setState(1);
             in.setUrl("UpdateAdmin.jsp");
             boolean o=idi.updateInteface(in);
             if(o){
                 out.write("msg=editsub_Successful...");
             }else{
                 out.write("msg=Unable to complete the action.");
             }
        }
        else if(type.equals("closeeditsub")){
           InterfaceDAOImp idi=new InterfaceDAOImp();
             Interfaces in=new Interfaces();
             in.setState(2);
             in.setUrl("UpdateAdmin.jsp");
             boolean o=idi.updateInteface(in);
             if(o){
                 out.write("msg=closeeditsub_Successful...");
             }else{
                 out.write("msg=Unable to complete the action.");
             }
        }

        } catch (Exception e) {
            e.printStackTrace();
            out.write("msg=Internal server error,Please try again later.");
        }

    }
}
