/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.android.settings;

import com.imagelake.control.InterfaceDAOImp;
import com.imagelake.control.PrivilageDAOImp;
import com.imagelake.control.UserDAOImp;
import com.imagelake.model.Interfaces;
import com.imagelake.model.Privilages;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
public class Servlet_PrivilegeSetting extends HttpServlet {

    InterfaceDAOImp inf;
    PrivilageDAOImp pdi;
    UserDAOImp udi;
    JSONArray ja;

    public Servlet_PrivilegeSetting() {
        inf = new InterfaceDAOImp();
        pdi = new PrivilageDAOImp();
        udi = new UserDAOImp();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        try {
            String type = request.getParameter("type");

            if (type != null && !type.equals("")) {

                if (type.equals("AllUserTypes")) {
                    String k = inf.listPrivilages();
                    System.out.println(k);
                    out.write(k);
                } else if (type.equals("AddUserTypes")) {
                    String ustype = request.getParameter("ustype");
                    if (ustype != null && !ustype.trim().equals("")) {
                        boolean k = udi.addUsertType(ustype);
                        if (k) {
                            out.write("msg=Add_User_Type_Successfully...");
                        } else {
                            out.write("msg=Unable to complete the action.");
                        }
                    } else {
                        out.write("msg=Internal server error,Please try again later.");
                    }
                } else if (type.equals("TypePrivilege")) {
                    String id = request.getParameter("userType");
                    List<Privilages> li = pdi.listAll(Integer.parseInt(id));
                    List<Interfaces> lf = inf.listAll((ArrayList<Privilages>) li);
                    ja = new JSONArray();
                    for (int i = 0; i < lf.size(); i++) {
                        Interfaces in = lf.get(i);
                        JSONObject jo = new JSONObject();
                        jo.put("id", in.getInterface_id());
                        jo.put("name", in.getDisplay_name());
                        jo.put("state", in.getState());
                        ja.add(jo);
                    }
                    out.write("json=" + ja.toJSONString());
                } else if (type.equals("remvinf")) {
                    String id = request.getParameter("userType");
                    String infid = request.getParameter("infid");

                    boolean k = pdi.updatePrivilage(Integer.parseInt(id), Integer.parseInt(infid), 2);
                    if (k) {
                        out.write("msg=Remve_Successfully...");
                    } else {
                        out.write("msg=Unable to complete the action.");
                    }
                }else if (type.equals("AllInterface")) {
                    String k = inf.listInterfaces();
                    System.out.println(type);
                    System.out.println(k);
                    out.write(k);
                }else if (type.equals("addinf")) {
                String id = request.getParameter("userType");
                String infid = request.getParameter("intfid");
                if(id!=null && !id.trim().equals("") && Integer.parseInt(id)!=0 && infid!=null && !infid.trim().equals("") && Integer.parseInt(infid)!=0){
                boolean k = pdi.addPrivilage(Integer.parseInt(id), Integer.parseInt(infid), 1);
                if (k) {
                    out.write("msg=Int_Add_Successfully...");
                } else {
                    out.write("msg=Unable to complete the action.");
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
