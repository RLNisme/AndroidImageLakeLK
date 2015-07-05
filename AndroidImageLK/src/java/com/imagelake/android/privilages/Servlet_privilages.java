/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.android.privilages;

import com.imagelake.control.DBFactory;
import com.imagelake.control.ImagesDAOImp;
import com.imagelake.control.InterfaceDAOImp;
import com.imagelake.control.PrivilageDAOImp;
import com.imagelake.control.UserDAOImp;
import com.imagelake.model.Interfaces;
import com.imagelake.model.Privilages;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
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
public class Servlet_privilages extends HttpServlet {

    List<Privilages> li = null;
    PrivilageDAOImp pdi = null;
    
    Interfaces inf = null;
    InterfaceDAOImp infs = null;
    JSONArray privilageArray = null;
        
    
   // public int last_index = 0;
    
    public Servlet_privilages() {
        pdi = new PrivilageDAOImp();
        infs = new InterfaceDAOImp();
    }

    
    
    protected void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
                PrintWriter out = response.getWriter();
        try {
            
            String user_type = request.getParameter("user_type");
            
            if(user_type != null){
                
                if(Integer.parseInt(user_type) != 4){
                    privilageArray = new JSONArray();
                    
                    li = pdi.listAll(Integer.parseInt(user_type));
                    
                    if(!li.isEmpty()){
                        
                        
                        for (Privilages privilages : li) {
                            if(privilages.getState() == 1){
                                
                            inf = infs.getInterfaceName(privilages.getInterface_interface_id());
                            
                            JSONObject  jo = new JSONObject();
                            jo.put("interface", inf.getDisplay_name());
                            jo.put("imgId", inf.getImg_id());
                                System.out.println(inf.getImg_id());
                            privilageArray.add(jo);
                            
                            }
                        }
                       
                        
                        
                        out.write("json="+privilageArray.toJSONString());
                        
                    }else{
                        out.write("msg=No privilages.");
                    }
                }else{
                    out.write("msg=Incorrect user type.");
                }
                
            }else{
                out.write("msg=User type error.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            out.write("msg=Internal server error,Please try again later.");
        }
        
    }
}
