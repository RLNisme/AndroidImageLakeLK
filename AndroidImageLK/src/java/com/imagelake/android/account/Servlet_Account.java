/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.android.account;

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
import org.json.simple.JSONObject;

/**
 *
 * @author RLN
 */
public class Servlet_Account extends HttpServlet {

    UserDAOImp udi = null;
    UserloginDAOImp uldi = null;
    UserHasPackageDAOImp uhpdi = null;
    SubscriptionPackageDAOImp spdi = null;
    DownloadCountDAOImp dcdi = null;
    CartDAOImp cdi = null;
    Cart crt;

    public Servlet_Account() {
        udi = new UserDAOImp();
        uldi = new UserloginDAOImp();
        cdi = new CartDAOImp();
        uhpdi = new UserHasPackageDAOImp();
        spdi = new SubscriptionPackageDAOImp(); 
        dcdi = new DownloadCountDAOImp();
    }

    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String date = sdf.format(d);
    SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss");
    String loginTime = sdf2.format(d);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        try {

            String uid = request.getParameter("uid");

            if (uid != null) {
                User u = udi.getUser(Integer.parseInt(uid));

                if (u != null) {

                    if (u.getState() == 1) {
                        JSONObject jo = new JSONObject();//create a JSONObject
                        
                        if (u.getUser_type() != 2 && u.getUser_type() != 3) {
                            double income = 0.0;
                            DecimalFormat df = new DecimalFormat("#.00");
                            try {
                                String sql = "SELECT SUM(total) FROM admin_package_income";
                                PreparedStatement ps = DBFactory.getConnection().prepareStatement(sql);
                                ResultSet rs = ps.executeQuery();
                                while (rs.next()) {
                                    income = rs.getDouble(1);
                                    jo.put("income", df.format(income));
                                }
                                rs.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            int usersCount = udi.allUsersCount();
                            jo.put("users", usersCount);

                            int imagesCount = new ImagesDAOImp().getImagesCount();
                            jo.put("imgcount", imagesCount);
                        } else {

                            UserHasPackage uss = uhpdi.getUserActivePackage(u.getUser_id(), 1);

                            //check user package-------------------------------------------
                            if (uss != null) {
                                System.out.println(uss.getExpire_date());
                                System.out.println(date);
                                System.out.println("is expierd:" + uss.getExpire_date().compareTo(date));

                                if (uss.getExpire_date().compareTo(date) <= 0) {
                                    boolean okk = new UserHasPackageDAOImp().updateState(uss.getUhp_id());
                                } else {
                                    if (!uss.getLast_date().equals(date)) {
                                        if (uss.getPackage_type() == 1) {
                                            //FreeTrial f=new FreeTrialDAOImp().getAFreeTrail(uss.getPackage_type());
                                            UserHasPackage uhpp = new UserHasPackage();
                                            uhpp.setDownload_count(uss.getOrg_downloads());
                                            uhpp.setCredit_count(uss.getCredit_count());
                                            uhpp.setLast_date(date);
                                            uhpp.setUhp_id(uss.getUhp_id());

                                            System.gc();

                                            boolean kk = new UserHasPackageDAOImp().updatePackage(uhpp);
                                            System.out.println("UPDATE PACKAGE | PACKAGE TYPE IS 1 7 DAY FREE TRIAL");
                                        } else if (uss.getPackage_type() == 2) {

                                            //FreeTrial f=new FreeTrialDAOImp().getAFreeTrail(uss.getPackage_type());
                                            Calendar c = Calendar.getInstance();
                                            c.add(Calendar.DATE, uss.getDuration());

                                            UserHasPackage uhpp = new UserHasPackage();
                                            System.out.println("*******************" + uss.getExpire_date().compareTo(date));
                                            if (uss.getExpire_date().compareTo(date) < 0) {

                                                uhpp.setDownload_count(uss.getDownload_count());
                                                uhpp.setCredit_count(uss.getCredit_count());
                                                uhpp.setExpire_date(sdf.format(c.getTime()));
                                                uhpp.setLast_date(date);
                                                uhpp.setUhp_id(uss.getUhp_id());
                                                System.gc();
                                                boolean kk = new UserHasPackageDAOImp().updateExpireDate(uhpp);
                                                System.out.println("UPDATE EXPIRE DATE | PACKAGE TYPE IS 2 MONTH FREE TRIAL");

                                            } else {

                                                uhpp.setDownload_count(uss.getDownload_count());
                                                uhpp.setCredit_count(uss.getCredit_count());
                                                uhpp.setExpire_date(sdf.format(c.getTime()));
                                                uhpp.setLast_date(date);
                                                uhpp.setUhp_id(uss.getUhp_id());

                                                System.gc();
                                                boolean kk = new UserHasPackageDAOImp().updatePackage(uhpp);
                                                System.out.println("UPDATE JUST LAST DATE | PACKAGE TYPE IS 2 MONTH FREE TRIAL");
                                            }
                                        } else if (uss.getPackage_type() == 3) {
                                            SubscriptionPackage sp = new SubscriptionPackageDAOImp().getSubscription(uss.getPackage_id());
                                            if (sp.getSubscription_type_id() != 2) {
                                                //DownloadCount dc=new DownloadCountDAOImp().getCount(sp.getCount_id());
                                                UserHasPackage uhpp = new UserHasPackage();
                                                uhpp.setDownload_count(uss.getOrg_downloads());
                                                uhpp.setCredit_count(uss.getCredit_count());
                                                uhpp.setLast_date(date);
                                                uhpp.setUhp_id(uss.getUhp_id());

                                                System.gc();

                                                boolean kk = new UserHasPackageDAOImp().updatePackage(uhpp);
                                                System.out.println("UPDATE PACKAGE | PACKAGE TYPE IS 3 SUBSCRIPTION PACKAGE");
                                            }
                                        }
                                    }
                                }
                            }//

                            UserHasPackage up = uhpdi.getUserPackage(u.getUser_id(), 1, 3);

                            if (up == null) {

                                jo.put("subscription", "Image Subscription");
                                jo.put("ps1", "You have no active image subscriptions");
                                jo.put("issNull", "yes");

                            } else {

                                SubscriptionPackage sp = spdi.getSubscription(up.getPackage_id());
                                String type = spdi.getSubscriptionType(sp.getSubscription_type_id());
                                String title = uhpdi.getPackageType(up.getPackage_type());
                                DownloadCount dc = dcdi.getCount(sp.getCount_id());
                                int dowPerDay = dc.getCount();

                                jo.put("subscription", "" + dowPerDay + " images " + type + " " + title + " for " + sp.getDuration() + " Month");
                                jo.put("ps1", "Purchase Date : " + up.getPurchase_date());
                                jo.put("ps2", "Expire Date : " + up.getExpire_date());
                                jo.put("ps3", "you have left " + up.getDownload_count() + " downloads for today");
                                jo.put("issNull", "no");

                            }

                            UserHasPackage p = uhpdi.getUserPackage(u.getUser_id(), 1, 4);

                            if (p == null) {

                                jo.put("credits", "Image Credit Packs");
                                jo.put("pc1", "You have no active credit packs");
                                jo.put("iscNull", "yes");
                                //   jo.put("pckType","2");

                            } else {
                                SubscriptionPackage sp = spdi.getSubscription(p.getPackage_id());
                                String title = uhpdi.getPackageType(p.getPackage_type());

                                jo.put("credits", p.getCredit_count() + " " + title + " Package");
                                jo.put("pc1", "Purchase Date : " + p.getPurchase_date());
                                jo.put("pc2", "Expire Date : " + p.getExpire_date());
                                jo.put("pc3", "you have left " + p.getCredit_count() + " credits for today");
                                jo.put("iscNull", "no");
                                //  jo.put("pckType","2");

                            }

                        }
                        out.write("json=" + jo.toJSONString());
                            System.out.println("json" + jo.toJSONString());    
                    } else {
                        out.write("msg=Blocked by the admin");
                    }

                } else {
                    out.write("msg=Internal server error,Please try again later.");
                }

            } else {

                out.write("msg=Internal server error,Please try again later.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
