/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.android.editprofile;

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
public class Servlet_editprofile extends HttpServlet {

    protected void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
        PrintWriter out =response.getWriter();
        
        try {
            
            String uid=request.getParameter("uid");
            String em=request.getParameter("em");
            String fn=request.getParameter("fn");
            String ln=request.getParameter("ln");
            String un=request.getParameter("un");
            System.out.println(uid+"&"+em+"&"+fn+"&"+ln+"&"+un);
            int userId=Integer.parseInt(uid);
            if(uid.equals(null)||uid.equals("") && em.equals(null) || em.equals("") && un.equals(null) || un.equals("")){
               out.write("msg=Please check user details");
            }else{
                
                User u=new UserDAOImp().searchUserNameWithUserID(un);
                    if(u==null || u.getUser_id()==userId){
                        
                        User uu=new UserDAOImp().searchEmailWithUserId(em);
                            if(uu==null || uu.getUser_id()==Integer.parseInt(uid)){
                                        
                                
                                if(fn.trim().equals("")|| fn.equals(null)){
                                    fn="---";
                                }
                                if(ln.trim().equals("") || ln.equals(null)){
                                    ln="---";
                                }
                                    User user=new User();
                                  user.setUser_id(userId);
                                  user.setUser_name(un);
                                  user.setFirst_name(fn);
                                  user.setLast_name(ln);
                                  user.setStreet_add_1("");
                                  user.setStreet_add_2("");
                                  user.setCity("");
                                  user.setBilling_country(1);
                                  user.setCom_fax("");
                                  user.setCom_name("");
                                  user.setCom_phone("");
                                  
                                  user.setEmail(em);
                                  user.setFax("");
                                  
                                  user.setPhone("");
                                  user.setState(1);
                                  user.setState_province("");
                                  user.setWebsite("");
                                  user.setUser_type(3);
                                  user.setZip_postal_code("");
                                  user.setCurrent_country_id(1);    
                                  
                                  System.gc();
                                  boolean ok=new UserDAOImp().updatePublicData(user);
                                  if(ok){
                                        out.write("msg=Successful...");
                                  }else{
                                       out.write("msg=unable to update profile");
                                  }

                            }else{
                                out.write("msg=email address already excits");
                            }
                    }else{
                          out.write("msg=User name already excits");
                    }
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            out.write("msg=Internal server error,Please try again later.");
        }
        
    
    }
}
