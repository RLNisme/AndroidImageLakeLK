/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.earnings;

import com.imagelake.control.PaymentPreferenceDAOImp;
import com.imagelake.control.SellerIncomeDAOImp;
import com.imagelake.model.PaymentPreferences;
import com.imagelake.model.SellerIncome;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
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
public class Servlet_Earnings extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            String acc = request.getParameter("acc");
            String em = request.getParameter("accem");
            String uid = request.getParameter("uid");

            Date d = new Date();
            SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd");
            String date = sd1.format(d);
            
            PaymentPreferenceDAOImp pimp=new PaymentPreferenceDAOImp();
            
            double min = pimp.getMinEarnings();

            if (!acc.equals("") && !acc.equals(null) && !em.equals("") && !em.equals(null)) {

                PaymentPreferences pr =pimp.getPendingEarning(Integer.parseInt(uid), 1);
                if (pr == null) {

                    SellerIncome sinc = new SellerIncomeDAOImp().getSellerIncome(Integer.parseInt(uid));
                    if (min > 0 && sinc.getTotal() >= min) {

                        PaymentPreferences pp = new PaymentPreferences();
                        pp.setAcc_type(Integer.parseInt(acc));
                        pp.setAdm_id(0);
                        pp.setAmount(sinc.getTotal());
                        
                        pp.setDate(date);
                        pp.setEmail(em);
                        pp.setState(1);
                        pp.setUser_id(Integer.parseInt(uid));
                        
                        boolean ok=pimp.AddPaymentPreference(pp);
                        if(ok){
                            out.write("msg=Successful...");
                        }else{
                           out.write("msg=We are sorry some thing has gone wrrong try again later.");
                        }

                    } else {
                        DecimalFormat df4 = new DecimalFormat("#.##");      
                          min = Double.valueOf(df4.format(min));
                        out.write("msg=Your account balance is less than $" + min + "");
                    }
                } else {
                    out.write("msg=You have a pending request...");
                }
            } else {
                
                    out.write("msg=You must enter your details");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.write("msg=Internal error,please try again later.");
        }

    }

}
