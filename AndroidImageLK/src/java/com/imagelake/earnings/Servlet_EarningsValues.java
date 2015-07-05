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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author RLN
 */
public class Servlet_EarningsValues extends HttpServlet {

    int uidd;
    PaymentPreferenceDAOImp ppimp;
    SellerIncomeDAOImp sidi;

    public Servlet_EarningsValues() {

        ppimp = new PaymentPreferenceDAOImp();
        sidi = new SellerIncomeDAOImp();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            PrintWriter out = response.getWriter();
        try {
            String uid = request.getParameter("uid");
            if (uid != null && !uid.equals("")) {

                uidd = Integer.parseInt(uid);

                PaymentPreferences pp = ppimp.getPendingEarning(uidd, 1);
                double pd = 00.00;
                double ad = 00.00;
                if (pp != null) {
                    pd = pp.getAmount();
                    DecimalFormat df1 = new DecimalFormat("#.##");
                    pd = Double.valueOf(df1.format(pd));

                }

                SellerIncome sin = sidi.getSellerIncome(uidd);
                ad = sin.getTotal();
                DecimalFormat df = new DecimalFormat("#.##");
                ad = Double.valueOf(df.format(ad));

                double netamo = ad - pd;
                DecimalFormat df2 = new DecimalFormat("#.##");
                netamo = Double.valueOf(df2.format(netamo));
                
                
                JSONObject jo =new JSONObject();
                jo.put("pe", pd);
                jo.put("ab", ad);
                jo.put("ne",netamo);

                out.write("json="+jo.toJSONString());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
