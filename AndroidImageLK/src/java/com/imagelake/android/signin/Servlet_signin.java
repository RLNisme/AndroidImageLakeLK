/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.android.signin;

import com.imagelake.control.CartDAOImp;
import com.imagelake.control.DBFactory;
import com.imagelake.control.DownloadCountDAOImp;
import com.imagelake.control.ImagesDAOImp;
import com.imagelake.control.SubscriptionPackageDAOImp;
import com.imagelake.control.UserDAOImp;
import com.imagelake.control.UserHasPackageDAOImp;
import com.imagelake.control.UserloginDAOImp;
import com.imagelake.model.Cart;
import com.imagelake.model.DownloadCount;
import com.imagelake.model.SubscriptionPackage;
import com.imagelake.model.User;
import com.imagelake.model.UserHasPackage;
import com.imagelake.model.Userlogin;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;

/**
 *
 * @author RLN
 */
public class Servlet_signin extends HttpServlet {

    String un, pw;
    UserDAOImp udi = null;
    UserloginDAOImp uldi = null;
    UserHasPackageDAOImp uhpdi = null;
    SubscriptionPackageDAOImp spdi = null;
    DownloadCountDAOImp dcdi = null;
    CartDAOImp cdi = null;
    Cart crt;

    public Servlet_signin() {
        udi = new UserDAOImp();
        uldi = new UserloginDAOImp();
        cdi = new CartDAOImp();
        uhpdi = new UserHasPackageDAOImp();
        spdi = new SubscriptionPackageDAOImp();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse respose) throws IOException, ServletException {
        PrintWriter out = respose.getWriter();
        try {
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(d);
            SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss");
            String loginTime = sdf2.format(d);

            HttpSession ses = request.getSession();

            un = request.getParameter("un");
            pw = request.getParameter("pw");

            if (un != null && pw != null) {

                User u = udi.searchSignIn(un, pw);
                if (u != null) {

                    if (u.getState() == 1) {

                        Userlogin userlogin = new Userlogin();
                        userlogin.setBrowser("Android");
                        userlogin.setIp_address(request.getRemoteHost());
                        userlogin.setSession_id(ses.getId());
                        userlogin.setStart_date(sdf.format(d));
                        userlogin.setStart_time(loginTime);
                        userlogin.setCountry("Sri Lanka");
                        userlogin.setCode("LK");

                        userlogin.setUser_user_id(u.getUser_id());
                        userlogin.setState(1);

                        int ok = uldi.insertLogin(userlogin);
                        System.out.println("okkk " + ok);
                        if (ok > 0) {

                            JSONObject jo = new JSONObject();
                            jo.put("id", u.getUser_id());
                            jo.put("un", u.getUser_name());
                            jo.put("pw", u.getPassword());
                            jo.put("fn", u.getFirst_name());
                            jo.put("ln", u.getLast_name());
                            jo.put("em", u.getEmail());
                            jo.put("user_type", u.getUser_type());
                            jo.put("state", u.getState());

                            

                            out.write("json=" + jo.toJSONString());
                            System.out.println("json" + jo.toJSONString());

                        } else {
                            out.write("msg=Internal server error,Please try again later.");
                        }

                    } else {
                        out.write("msg=Blocked by the admin");
                    }

                } else {
                    out.write("msg=Incorrect user name or password");
                }

            } else {

                out.write("msg=Please enter user details");

            }

        } catch (Exception e) {
            e.printStackTrace();
            out.write("msg=Internal server error,Please try again later.");
        }

    }

}
