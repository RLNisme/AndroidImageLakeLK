/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.android.packages;

import com.imagelake.control.CreditPackageDAOImp;
import com.imagelake.control.DownloadCountDAOImp;
import com.imagelake.control.FreeTrialDAOImp;
import com.imagelake.control.SubscriptionPackageDAOImp;
import com.imagelake.control.UserDAOImp;
import com.imagelake.control.UserHasPackageDAOImp;
import com.imagelake.model.CreditsPackage;
import com.imagelake.model.DownloadCount;
import com.imagelake.model.FreeTrial;
import com.imagelake.model.SubscriptionPackage;
import com.imagelake.model.User;
import com.imagelake.model.UserHasPackage;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author RLN
 */
public class Servlet_Packages extends HttpServlet {

    FreeTrialDAOImp fdi;
    UserDAOImp udi;
    DownloadCountDAOImp dcdi;
    SubscriptionPackageDAOImp sdi;
    CreditPackageDAOImp cdi;
    JSONArray ja;

    public Servlet_Packages() {
        fdi = new FreeTrialDAOImp();
        udi = new UserDAOImp();
        dcdi = new DownloadCountDAOImp();
        sdi = new SubscriptionPackageDAOImp();
        cdi = new CreditPackageDAOImp();
    }

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
    Calendar cal = Calendar.getInstance();
    Calendar calReturn = Calendar.getInstance();

    DecimalFormat df = new DecimalFormat("#.00");

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        try {
            String type = request.getParameter("type");
            if (type != null) {
                if (type.equals("Load_all")) {
                    JSONObject jo = new JSONObject();

                    FreeTrial ft1 = fdi.getAFreeTrail(1);

                    jo.put("ft_id", ft1.getFreeId());
                    jo.put("ft_days", ft1.getDays());
                    jo.put("ft_dwn", ft1.getDownload_count());

                    FreeTrial ft2 = fdi.getAFreeTrail(2);

                    jo.put("ft2_id", ft2.getFreeId());
                    jo.put("ft2_days", ft2.getDays());
                    jo.put("ft2_dwn", ft2.getDownload_count());

                    out.write("json=" + jo.toJSONString());
                    System.out.println(jo.toJSONString());
                } else if (type.equals("free_trial")) {

                    String uid = request.getParameter("uid");

                    List<UserHasPackage> uhpList = null;
                    UserHasPackage uhp = null;
                    List<FreeTrial> fr = null;
                    FreeTrial ft = null;

                    User u = udi.getUser(Integer.parseInt(uid));
                    if (u == null) {
                        //checkig user has signIn
                        out.write("msg=Internal server error.");
                    } else {

                        if (u.getUser_type() == 2 || u.getUser_type() == 3) {
                            uhpList = new UserHasPackageDAOImp().getUserPackages(u.getUser_id());
                            fr = new FreeTrialDAOImp().getFreeTrail();

                            for (FreeTrial freeTrial : fr) {
                                System.gc();
                                if (freeTrial.getPackage_type_id() == 1) {
                                    ft = freeTrial;
                                    break;
                                }
                            }

                            //get all list of all the packages user have and had
                            if (uhpList.isEmpty()) {
                                //if that list is not empty
                                calReturn.add(Calendar.DATE, ft.getDays());
                                uhp = new UserHasPackage();
                                uhp.setCredit_count(0);
                                uhp.setDownload_count(ft.getDownload_count());
                                uhp.setExpire_date(dateFormat.format(calReturn.getTime()));
                                uhp.setPackage_id(ft.getFreeId());
                                uhp.setPackage_type(1);
                                uhp.setPurchase_date(dateFormat.format(cal.getTime()));
                                uhp.setState(1);
                                uhp.setUser_id(u.getUser_id());
                                uhp.setLast_date(dateFormat.format(cal.getTime()));
                                uhp.setDuration(ft.getDays());
                                System.gc();
                                boolean ok = new UserHasPackageDAOImp().addAPackage(uhp);
                                out.write("msg=Free trial package is successfully activated");
                            } else {
                                //if the user have some packages
                                boolean seven = false;
                                for (UserHasPackage userHasPackage : uhpList) {
                                    System.gc();
                                    //check whether he have perchase 7 day free pack before
                                    if (userHasPackage.getPackage_type() == 1 && userHasPackage.getUser_id() == u.getUser_id()) {
                                        seven = true;
                                        break;
                                    } else if (userHasPackage.getPackage_type() == 2 && userHasPackage.getUser_id() == u.getUser_id()) {
                                        seven = true;
                                        break;
                                    }

                                }
                                //if user have alrady had a free trial
                                if (seven) {
                                    out.write("msg=Free trial packages are available to new users only");
                                } else {
                                    calReturn.add(Calendar.DATE, ft.getDays());
                                    uhp = new UserHasPackage();
                                    uhp.setCredit_count(0);
                                    uhp.setDownload_count(ft.getDownload_count());
                                    uhp.setExpire_date(dateFormat.format(calReturn.getTime()));
                                    uhp.setPackage_id(ft.getFreeId());
                                    uhp.setPackage_type(1);
                                    uhp.setPurchase_date(dateFormat.format(cal.getTime()));
                                    uhp.setState(1);
                                    uhp.setUser_id(u.getUser_id());
                                    uhp.setLast_date(dateFormat.format(cal.getTime()));
                                    uhp.setDuration(ft.getDays());
                                    System.gc();
                                    boolean ok = new UserHasPackageDAOImp().addAPackage(uhp);
                                    out.write("msg=Free trial package is successfully activated");
                                }
                            }

                        } else {
                            out.write("msg=Packages are available to buyers & contributors only.");
                        }
                    }
                } else if (type.equals("Ultimate_free_trial")) {

                    String uid = request.getParameter("uid");

                    List<UserHasPackage> uhpList = null;
                    UserHasPackage uhp = null;
                    List<FreeTrial> fr = null;
                    FreeTrial ft = null;

                    User u = udi.getUser(Integer.parseInt(uid));
                    if (u == null) {
                        //checkig user has signIn
                        out.write("msg=Internal server error.");
                    } else {
                        if (u.getUser_type() == 2 || u.getUser_type() == 3) {
                            uhpList = new UserHasPackageDAOImp().getUserPackages(u.getUser_id());
                            fr = new FreeTrialDAOImp().getFreeTrail();

                            for (FreeTrial freeTrial : fr) {
                                System.gc();
                                if (freeTrial.getPackage_type_id() == 2) {
                                    ft = freeTrial;
                                    break;
                                }
                            }
                            //get all list of all the packages user have and had
                            if (uhpList.isEmpty()) {
                                //if that list is not empty
                                calReturn.add(Calendar.DATE, ft.getDays());
                                uhp = new UserHasPackage();
                                uhp.setCredit_count(0);
                                uhp.setDownload_count(ft.getDownload_count());
                                uhp.setExpire_date(dateFormat.format(calReturn.getTime()));
                                uhp.setPackage_id(ft.getFreeId());
                                uhp.setPackage_type(2);
                                uhp.setPurchase_date(dateFormat.format(cal.getTime()));
                                uhp.setState(1);
                                uhp.setUser_id(u.getUser_id());
                                uhp.setLast_date(dateFormat.format(cal.getTime()));
                                uhp.setDuration(ft.getDays());

                                System.gc();
                                boolean ok = new UserHasPackageDAOImp().addAPackage(uhp);
                                out.write("msg=Unlimited Free trial package is successfully activated");
                            } else {
                                //if the user have some packages
                                boolean seven = false;
                                for (UserHasPackage userHasPackage : uhpList) {
                                    System.gc();
                                    //check whether he have perchase 7 day free pack before
                                    if (userHasPackage.getPackage_type() == 1 && userHasPackage.getUser_id() == u.getUser_id()) {
                                        seven = true;
                                        break;
                                    } else if (userHasPackage.getPackage_type() == 2 && userHasPackage.getUser_id() == u.getUser_id()) {
                                        seven = true;
                                        break;
                                    }

                                }
                                //if user have alrady had a free trial
                                if (seven) {
                                    out.write("msg=Unlimited Free trial packages are available to new users only");
                                } else {
                                    calReturn.add(Calendar.DATE, ft.getDays());
                                    uhp = new UserHasPackage();
                                    uhp.setCredit_count(0);
                                    System.out.println("ft get count" + ft.getDownload_count());
                                    uhp.setDownload_count(ft.getDownload_count());
                                    uhp.setExpire_date(dateFormat.format(calReturn.getTime()));
                                    uhp.setPackage_id(ft.getFreeId());
                                    uhp.setPackage_type(2);
                                    uhp.setPurchase_date(dateFormat.format(cal.getTime()));
                                    uhp.setState(1);
                                    uhp.setUser_id(u.getUser_id());
                                    uhp.setLast_date(dateFormat.format(cal.getTime()));
                                    uhp.setDuration(ft.getDays());

                                    System.gc();
                                    boolean ok = new UserHasPackageDAOImp().addAPackage(uhp);
                                    out.write("msg=Unlimited Free trial package is successfully activated");
                                }
                            }

                        } else {
                            out.write("msg=Packages are available to buyers & contributors only.");
                        }
                    }
                } else if (type.equals("load_sub")) {
                    System.out.println(type);
                    List<DownloadCount> li = dcdi.listAll();
                    ja = new JSONArray();
                    if (!li.isEmpty()) {

                        for (DownloadCount dw : li) {

                            List<SubscriptionPackage> list = sdi.listByDownCount(dw.getId());
                            DownloadCount d = dcdi.getCount(dw.getId());

                            for (SubscriptionPackage s : list) {
                                if (s.getState() != 3 && s.getSubscription_type_id() == 1) {
                                    JSONObject jo = new JSONObject();
                                    jo.put("id", s.getSubscription_id());
                                    jo.put("du", s.getDuration());
                                    jo.put("dwn", dw.getCount());
                                    jo.put("pimg", s.getPer_image());
                                    jo.put("prc", df.format(s.getPer_image() * 30 * s.getDuration() * d.getCount()));
                                    jo.put("disc", s.getDiscount());
                                    jo.put("state", s.getState());
                                    ja.add(jo);
                                }
                            }

                        }
                    }
                    out.write("json=" + ja.toJSONString());
                    System.out.println(ja.toJSONString());
                } else if (type.equals("load_crd")) {
                    List<CreditsPackage> creList = cdi.listAll();
                    ja = new JSONArray();    
                    for (CreditsPackage c : creList) {
                        if (c.getState() != 3) {
                             JSONObject jo = new JSONObject();
                                    jo.put("id", c.getCreditpack_id());
                                    jo.put("du", c.getDuration());
                                    jo.put("dwn", c.getCredits());
                                    jo.put("pimg", c.getPer_image());
                                    jo.put("prc", df.format(c.getPer_image()*c.getCredits()));
                                    jo.put("disc", c.getDiscount());
                                    jo.put("state", c.getState());
                                    ja.add(jo);
                        }
                    }
                    
                    out.write("json="+ja.toJSONString());
                    System.out.println(ja.toJSONString());
                }
            } else {
                out.write("msg=Internal server error,Please try again later.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.write("msg=Internal server error,Please try again later.");
        }

    }

}
