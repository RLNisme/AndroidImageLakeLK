/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.android.settings;

import com.imagelake.control.CreditPackageDAOImp;
import com.imagelake.control.CreditsDAOImp;
import com.imagelake.control.DownloadCountDAOImp;
import com.imagelake.control.FreeTrialDAOImp;
import com.imagelake.control.PaymentPreferenceDAOImp;
import com.imagelake.control.PercentDAOImp;
import com.imagelake.control.SubscriptionPackageDAOImp;
import com.imagelake.control.UserHasPackageDAOImp;
import com.imagelake.model.Credits;
import com.imagelake.model.CreditsPackage;
import com.imagelake.model.DownloadCount;
import com.imagelake.model.FreeTrial;
import com.imagelake.model.MinEarning;
import com.imagelake.model.Percent;
import com.imagelake.model.SubscriptionPackage;
import java.io.IOException;
import java.io.PrintWriter;
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
public class Servlet_PackageSetting extends HttpServlet {

    FreeTrialDAOImp fdi;
    PercentDAOImp pdi;
    PaymentPreferenceDAOImp ppdi;
    CreditsDAOImp crdi;
    CreditPackageDAOImp cpdi;
    SubscriptionPackageDAOImp spdi;
    UserHasPackageDAOImp uhpi;
    JSONArray ja;

    public Servlet_PackageSetting() {
        fdi = new FreeTrialDAOImp();
        pdi = new PercentDAOImp();
        ppdi = new PaymentPreferenceDAOImp();
        crdi = new CreditsDAOImp();
        cpdi = new CreditPackageDAOImp();
        spdi = new SubscriptionPackageDAOImp();
        uhpi = new UserHasPackageDAOImp();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        try {
            String type = request.getParameter("type");
            System.out.println(type);
            if (type != null && !type.equals("")) {

                if (type.equals("Daily")) {
                    List<FreeTrial> fl = fdi.getFreeTrail();

                    int i = 0;
                    ja = new JSONArray();
                    for (FreeTrial f : fl) {
                        if (i == 0) {
                            JSONObject jo = new JSONObject();
                            jo.put("free_id", f.getFreeId());
                            jo.put("free_pack_type", f.getPackage_type_id());
                            jo.put("days", f.getDays());
                            jo.put("downloads", f.getDownload_count());
                            ja.add(jo);
                        }
                        i++;
                    }
                    System.out.println(ja.toJSONString());
                    out.write("json=" + ja.toJSONString());
                } else if (type.equals("DailyUpdate")) {

                    String freeday = request.getParameter("freeday");
                    String freecnt = request.getParameter("freecnt");

                    int day = Integer.parseInt(freeday);
                    int cnt = Integer.parseInt(freecnt);

                    System.out.println(day);
                    System.out.println(cnt);

                    if (day != 0 && cnt != 0) {

                        FreeTrial ft = new FreeTrial();
                        ft.setDays(day);
                        ft.setDownload_count(cnt);
                        ft.setFreeId(1);

                        boolean k = fdi.updatePackage(ft);
                        if (k) {
                            out.write("msg=Daily_Update_Successful...");
                        } else {
                            out.write("msg=Unable to complete the action.");
                        }

                    } else {
                        out.write("msg=Internal server error,Please try again later.");
                    }
                } else if (type.equals("Unlimit")) {
                    List<FreeTrial> fl = fdi.getFreeTrail();
                    // Percent p = pdi.getPercentage();
                    // MinEarning me = ppdi.getMin(1);
                    int i = 0;
                    ja = new JSONArray();
                    for (FreeTrial f : fl) {
                        if (i == 1) {
                            JSONObject jo = new JSONObject();
                            jo.put("free_id", f.getFreeId());
                            jo.put("free_pack_type", f.getPackage_type_id());
                            jo.put("days", f.getDays());
                            jo.put("downloads", f.getDownload_count());
                            ja.add(jo);
                        }
                        i++;
                    }
                    System.out.println(ja.toJSONString());
                    out.write("json=" + ja.toJSONString());
                } else if (type.equals("UnlimitUpdate")) {

                    String freeday = request.getParameter("freeunday");
                    String freecnt = request.getParameter("freeuncnt");

                    int day = Integer.parseInt(freeday);
                    int cnt = Integer.parseInt(freecnt);

                    System.out.println(day);
                    System.out.println(cnt);

                    if (day != 0 && cnt != 0) {

                        FreeTrial ft = new FreeTrial();
                        ft.setDays(day);
                        ft.setDownload_count(cnt);
                        ft.setFreeId(2);

                        boolean k = fdi.updatePackage(ft);
                        if (k) {
                            out.write("msg=Unlimit_Update_Successful...");
                        } else {
                            out.write("msg=Unable to complete the action.");
                        }

                    } else {
                        out.write("msg=Internal server error,Please try again later.");
                    }
                } else if (type.equals("AdminSplitPercent")) {
                    Percent p = pdi.getPercentage();

                    ja = new JSONArray();
                    JSONObject jo = new JSONObject();
                    jo.put("id", p.getPercent_id());
                    jo.put("percent", p.getPercent());

                    ja.add(jo);
                    out.write("json=" + ja.toJSONString());

                } else if (type.equals("AdminSplitMin")) {

                    MinEarning me = ppdi.getMin(1);
                    ja = new JSONArray();
                    JSONObject jo = new JSONObject();

                    DecimalFormat df = new DecimalFormat("#.00");
                    jo.put("min", df.format(me.getMinearning()));
                    jo.put("minid", me.getId());

                    ja.add(jo);
                    out.write("json=" + ja.toJSONString());

                } else if (type.equals("AdminSplitUpdate")) {
                    String percent = request.getParameter("percent");
                    //String id = request.getParameter("id");
                    //System.out.println("id: " + id);
                    System.out.println("Percent: " + percent);
                    if (percent != null && !percent.trim().equals("")) {
                        boolean ok = new PercentDAOImp().updatePercentage(1, Integer.parseInt(percent));
                        if (ok) {
                            out.write("msg=Percentage_Update");
                        } else {
                            out.write("msg=Unable to complete the action.");
                        }
                    } else {
                        out.write("msg=Internal server error,Please try again later.");
                    }
                } else if (type.equals("AdminMinUpdate")) {
                    String earn = request.getParameter("earn");
                    //String id=request.getParameter("id");
                    System.out.println("earning: " + earn);
                    //System.out.println("earn id: "+id);
                    if (earn != null && !earn.trim().equals("")) {
                        boolean k = new PaymentPreferenceDAOImp().updateMinEarning(Double.parseDouble(earn), 1);
                        if (k) {
                            out.write("msg=Earning_Update");
                        } else {
                            out.write("msg=Unable to complete the action.");
                        }
                    } else {
                        out.write("msg=Internal server error,Please try again later.");
                    }

                } else if (type.equals("AllCreditSize")) {
                    ja = new JSONArray();
                    List<Credits> clis = crdi.getCreditList();
                    for (int i = 0; i < clis.size(); i++) {
                        Credits credits = clis.get(i);
                        JSONObject jo = new JSONObject();
                        jo.put("id", credits.getCredit_id());
                        jo.put("credits", credits.getCredits());
                        jo.put("size", credits.getSize());
                        jo.put("width", credits.getWidth());
                        jo.put("height", credits.getHeight());

                        jo.put("state", credits.getState());
                        ja.add(jo);
                    }
                    out.write("json=" + ja.toJSONString());
                } else if (type.equals("AddCreditSize")) {
                    String credit = request.getParameter("addcredits");
                    String size = request.getParameter("addsize");
                    String width = request.getParameter("addwidth");
                    String height = request.getParameter("addheight");
                    System.out.println(credit);
                    System.out.println(width);
                    System.out.println(height);
                    System.out.println(size);

                    if (credit != null && !credit.trim().equals("") && size != null && !size.trim().equals("") && width != null && !width.trim().equals("") && height != null && !height.trim().equals("")) {
                        Credits cr = new Credits();
                        cr.setCredits(Integer.parseInt(credit));
                        cr.setSize(size);
                        cr.setWidth(Integer.parseInt(width));
                        cr.setHeight(Integer.parseInt(height));
                        cr.setState(1);

                        boolean ok = crdi.insertCredits(cr);
                        if (ok) {
                            out.write("msg=CrdSize_Successfully...");
                        } else {
                            out.write("msg=Unable to complete the action.");
                        }
                    } else {
                        out.write("msg=Internal server error,Please try again later.");
                    }
                } else if (type.equals("UpdateCreditSize")) {
                    String id = request.getParameter("upid");
                    String credit = request.getParameter("upcredit");
                    String sta = request.getParameter("upstate");
                    String width = request.getParameter("upwidth");
                    String height = request.getParameter("upheight");
                    String size = request.getParameter("upsize");

                    System.out.println("id:" + id);
                    System.out.println("credits:" + credit);
                    System.out.println("state123:" + sta);
                    System.out.println("width:" + width);
                    System.out.println("height:" + height);
                    System.out.println("size:" + size);

                    Credits cr = new Credits();
                    cr.setCredit_id(Integer.parseInt(id));
                    cr.setCredits(Integer.parseInt(credit));
                    cr.setSize(size);
                    cr.setHeight(Integer.parseInt(height));
                    cr.setWidth(Integer.parseInt(width));
                    cr.setState(Integer.parseInt(sta));

                    boolean ok = crdi.updateCredits(cr);
                    System.out.println(ok);
                    if (ok) {
                        out.write("msg=Update_CrdSize_Successfully...");
                    } else {
                        out.write("msg=Unable to complete the action.");
                    }

                } else if (type.equals("AllCreditPackages")) {
                    List<CreditsPackage> lis = cpdi.listAll();
                    ja = new JSONArray();
                    for (int i = 0; i < lis.size(); i++) {
                        CreditsPackage credits = lis.get(i);
                        JSONObject jo = new JSONObject();
                        jo.put("id", credits.getCreditpack_id());
                        jo.put("credits", credits.getCredits());
                        jo.put("duration", credits.getDuration());
                        jo.put("unitprice", credits.getCredit_unit_price_id());
                        jo.put("disco", credits.getDiscount());
                        jo.put("state", credits.getState());

                        ja.add(jo);
                    }
                    out.write("json=" + ja.toJSONString());
                } else if (type.equals("AddCreditPackage")) {

                    String count = request.getParameter("addcount");
                    String dico = request.getParameter("adddico");
                    String unitprice = request.getParameter("addunitprice");
                    String duration = request.getParameter("addduration");
                    System.out.println("unit:" + unitprice);
                    System.out.println("dico:" + dico);
                    System.out.println("duration:" + duration);
                    System.out.println("dcount:" + count);

                    if (Double.parseDouble(unitprice) != 0.0 && Integer.parseInt(count) != 0 && Integer.parseInt(duration) != 0) {
                        CreditsPackage cp = new CreditsPackage();
                        cp.setCredit_unit_price_id(1);
                        cp.setCredits(Integer.parseInt(count));
                        cp.setDuration(Integer.parseInt(duration));
                        cp.setPer_image(Double.parseDouble(unitprice));
                        cp.setOld_per_image(0);
                        cp.setState(2);
                        cp.setDiscount(Integer.parseInt(dico));
                        boolean ok = cpdi.duplicatePackage(cp);
                        if (ok) {
                            out.write("msg=Package Duplicated.");
                        } else {
                            boolean k = cpdi.insertPackage(cp);
                            if (k) {
                                out.write("msg=Add_Credit_Package");
                            } else {
                                out.write("msg=Unable to complete the action.");
                            }
                        }
                    } else {
                        out.write("msg=Internal server error,Please try again later.");
                    }

                } else if (type.equals("UpdateCreditPackages")) {
                    String id = request.getParameter("upid");
                    String count = request.getParameter("upcount");
                    String dico = request.getParameter("updisco");
                    String duration = request.getParameter("upduration");
                    String unitprice = request.getParameter("upunitprice");
                    String state = request.getParameter("upstate");
                    System.out.println("dicount: " + dico);
                    if (Double.parseDouble(unitprice) != 0.0 && Integer.parseInt(count) != 0 && Integer.parseInt(duration) != 0) {
                        CreditsPackage cp = cpdi.getCreditPackage(Integer.parseInt(id));
                        CreditsPackage pp = new CreditsPackage();
                        pp.setCredit_unit_price_id(1);
                        pp.setCreditpack_id(Integer.parseInt(id));
                        pp.setCredits(Integer.parseInt(count));
                        pp.setDuration(Integer.parseInt(duration));
                        pp.setState(Integer.parseInt(state));
                        pp.setDiscount(Integer.parseInt(dico));

                        if (cp.getPer_image() != Double.parseDouble(unitprice)) {
                            pp.setPer_image(Double.parseDouble(unitprice));
                            pp.setOld_per_image(cp.getPer_image());
                        } else {
                            pp.setPer_image(cp.getPer_image());
                            pp.setOld_per_image(cp.getOld_per_image());
                        }

                        boolean k = cpdi.updatePackage(pp);
                        if (k) {
                            out.write("msg=Update_Credit_Package");
                        } else {
                            out.write("msg=Unable to complete the action.");
                        }

                    } else {
                        out.write("msg=Internal server error,Please try again later.");
                    }
                } else if (type.equals("AllSubscriptionPackages")) {
                    List<SubscriptionPackage> li = spdi.listAll();
                    ja = new JSONArray();
                    for (int i = 0; i < li.size(); i++) {
                        SubscriptionPackage subscriptionPackage = li.get(i);
                        String dw = uhpi.getDwnCount(subscriptionPackage.getCount_id());
                        String ty = uhpi.getPacType(subscriptionPackage.getSubscription_type_id());
                        JSONObject jo = new JSONObject();
                        jo.put("id", subscriptionPackage.getSubscription_id());
                        jo.put("downloads", dw );
                        jo.put("duration", subscriptionPackage.getDuration());
                        jo.put("unit", subscriptionPackage.getPer_image());
                        jo.put("type", ty);
                        jo.put("countid",subscriptionPackage.getCount_id());
                        jo.put("state",subscriptionPackage.getState());
                        jo.put("disco",subscriptionPackage.getDiscount());
                        ja.add(jo);
                    }
                    out.write("json="+ja.toJSONString());
                }else if (type.equals("AddSubscriptionPackages")) {
                    String count = request.getParameter("addcount");
                    String pckty = request.getParameter("addpacktype");
                    String disco=request.getParameter("adddico");
                    String duration = request.getParameter("addduration");
                    String unitprice = request.getParameter("addunitprice");
                    //String state = request.getParameter("state");
                    System.out.println("++++++++++++" + type);
                    System.out.println("++++++++++++" + duration);
                    System.out.println("++++++++++++" + unitprice);
                    System.out.println("++++++++++++" + count);
                    System.out.println("++++++++++++" + pckty);

                    if (Double.parseDouble(unitprice) != 0.0 && Integer.parseInt(count) != 0 && Integer.parseInt(duration) != 0) {
                        SubscriptionPackage p = new SubscriptionPackage();
                        p.setDuration(Integer.parseInt(duration));
                        p.setOld_per_images(00.00);
                        p.setPer_image(Double.parseDouble(unitprice));
                        p.setState(2);
                        p.setCount_id(Integer.parseInt(count));
                        p.setSubscription_type_id(Integer.parseInt(pckty));
                        p.setDiscount(Integer.parseInt(disco));
                        
                        boolean dup = spdi.duplicatePackage(p);
                        System.out.println("dup" + dup);
                        if (dup) {
                            out.write("msg=Package Duplicated.");
                        } else {

                            boolean ok = spdi.insertPackage(p);
                            System.out.println("ok" + ok);
                            if (ok) {
                                out.write("msg=Sub_Add_Successfully.");
                            } else {
                                out.write("msg=Unable to complete the action.");
                            }
                        }
                    } else {
                        out.write("msg=Internal server error,Please try again later.");
                    }
                }else if (type.equals("UpdateSubscriptionPackages")) {
                     String id = request.getParameter("upid");
                    String count = request.getParameter("upcount");
                    String dico=request.getParameter("updico");
                    String duration = request.getParameter("upduration");
                    String unitprice = request.getParameter("upunitprice");
                    String state = request.getParameter("upstate");
                    System.out.println("double:" + Double.parseDouble(unitprice));
                    if (Double.parseDouble(unitprice) != 0.0 && Integer.parseInt(count) != 0 && Integer.parseInt(duration) != 0) {
                        SubscriptionPackage sp = spdi.getSubscription(Integer.parseInt(id));

                        SubscriptionPackage p = new SubscriptionPackage();
                        p.setSubscription_id(Integer.parseInt(id));
                        p.setCount_id(Integer.parseInt(count));
                        p.setDuration(Integer.parseInt(duration));
                        p.setState(Integer.parseInt(state));
                        p.setDiscount(Integer.parseInt(dico));
                        
                        if (sp.getPer_image() != Double.parseDouble(unitprice)) {
                            p.setPer_image(Double.parseDouble(unitprice));
                            p.setOld_per_images(sp.getPer_image());
                        } else {
                            p.setPer_image(sp.getPer_image());
                            p.setOld_per_images(sp.getOld_per_images());
                        }

                        boolean ok = spdi.updatePackage(p);
                        if (ok) {
                            out.write("msg=Sub_up_Successfully...");
                        } else {
                            out.write("msg=unable to complete the action.");
                        }
                    } else {
                        out.write("msg=Internal server error,Please try again later.");
                    }
                }else if(type.equals("AllDownloads")){
                     List<DownloadCount> li = new DownloadCountDAOImp().listAll();
                     ja = new JSONArray();
                    for (DownloadCount downloadCount : li) {
                        JSONObject jo = new JSONObject();
                        jo.put("id", downloadCount.getId());
                        jo.put("dwn", downloadCount.getCount());
                        ja.add(jo);
                        
                    }
                    System.out.println(ja.toJSONString());
                    out.write("json="+ja.toJSONString());
                }else if (type.equals("AddDownloads")) {
                    String dwnloads = request.getParameter("dwnloads");
                    System.out.println("dwn" + dwnloads);

                    if (Integer.parseInt(dwnloads) != 0) {
                        DownloadCount dc = new DownloadCount();
                        dc.setCount(Integer.parseInt(dwnloads));
                        dc.setSubscription_type(1);
                        DownloadCountDAOImp dci = new DownloadCountDAOImp();
                        boolean k = dci.duplication(dc);
                        if (k) {
                            out.write("msg=Duplicated download.");
                        } else {
                            boolean ok = dci.insertDownloadCount(dc);
                            if (ok) {
                                out.write("msg=Add_Download");
                            } else {
                                out.write("msg=Unable to complete the action.");
                            }
                        }
                    } else {
                        out.write("msg=Internal server error,Please try again later.");
                    }
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
