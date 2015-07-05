/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.android.signup;

import com.imagelake.control.UserDAOImp;
import com.imagelake.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author RLN
 */
public class Servlet_signup extends HttpServlet {
            
            String un,em,pw,phn;
            UserDAOImp udi = null;
            boolean checkEm;
            boolean checkUn;
            boolean insertUser;
            
    public Servlet_signup() {
        
        udi = new UserDAOImp();
    }
                     
    protected void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
            PrintWriter out = response.getWriter();
        try {
            
            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            
            un = request.getParameter("un");
            em = request.getParameter("em");
            pw = request.getParameter("pw");
            phn = request.getParameter("phn");
            
            if(un != null && em != null && pw != null && phn != null){
                
                
                checkUn = udi.searchUserName(un);
                
                if(checkUn == true){
                    
                       checkEm = udi.serachEmail(em);
                    
                    if(checkEm == true){
                        
                        User u = new User();
                        u.setUser_name(un);
                        u.setEmail(em);
                        u.setPassword(pw);
                        u.setDate(sdf.format(dt));
                        u.setFirst_name("---");
                        u.setLast_name("---");
                        u.setStreet_add_1("");
                        u.setStreet_add_2("");
                        u.setCity("");
                        u.setBilling_country(1);
                        u.setCom_fax("");
                        u.setCom_name("");
                        u.setCom_phone("");
                        u.setFax("");
                        u.setPhone(phn);
                        u.setState(1);
                        u.setState_province("");
                        u.setWebsite("");
                        u.setUser_type(3);
                        u.setZip_postal_code("");
                        u.setCurrent_country_id(1);   
                        
                        
                        insertUser = udi.insertUser(u);
                        
                        if(insertUser == true){
                            
                            out.write("msg=Successful...");
                            
                        }else{
                            
                            out.write("msg=Internal server error,try again later");
                            
                        }
                        
                    }else{
                    
                        out.write("msg=Email already excise");
                        
                    }
                    
                }else{
                    
                    out.write("msg=User name already excise");
                    
                }
                
            }else{
                
                out.write("msg=Please check user details");
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            out.write("msg=Internal server error,try again later");
        }
    
    
    }
}
