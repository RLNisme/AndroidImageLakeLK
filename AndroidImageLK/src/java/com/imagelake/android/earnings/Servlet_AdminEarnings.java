/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.android.earnings;

import com.imagelake.control.AdminHasNotificationDAOImp;
import com.imagelake.control.AdminNotificationDAOImp;
import com.imagelake.control.DBFactory;
import com.imagelake.control.NotificationDAOImp;
import com.imagelake.control.PaymentAccountDAOImp;
import com.imagelake.control.PaymentPreferenceDAOImp;
import com.imagelake.control.SellerIncomeDAOImp;
import com.imagelake.control.UserDAOImp;
import com.imagelake.model.AdminNotification;
import com.imagelake.model.SellerIncome;
import com.imagelake.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
public class Servlet_AdminEarnings extends HttpServlet {

    PaymentPreferenceDAOImp ppdi;
    JSONArray ja;

    public Servlet_AdminEarnings() {
        ppdi = new PaymentPreferenceDAOImp();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        try {

            String type = request.getParameter("type");
            String usid = request.getParameter("id");

            User us = new UserDAOImp().getUser(Integer.parseInt(usid));

            UserDAOImp udi = new UserDAOImp();

            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date1 = sdf.format(d);

            SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss");
            String Time = sdf2.format(d);
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

            if (type.equals("new")) {
                ja = new JSONArray();
                System.out.println("type:" + type);
                ja = ppdi.getPayments(1);
                System.out.println(ja);
                response.setContentType("text/html");
                response.getWriter().write("json=" + ja.toJSONString());

            } else if (type.equals("sort")) {
                ja = new JSONArray();
                System.out.println("type:" + type);

                String uid = request.getParameter("uid");
                String date = request.getParameter("date");
                String conr = request.getParameter("conr");
                String newr = request.getParameter("newr");

                System.out.println("uid: " + uid);
                System.out.println("date: " + date);
                System.out.println("conrequest:" + conr);
                System.out.println("newrequest:" + newr);

                String dbDate[] = date.split("-");

                String sql = "SELECT * FROM payment_preferences WHERE ";

                if (uid != null && uid.trim() != "") {
                    sql += "user_id='" + Integer.parseInt(uid) + "' ";
                    if (date != null && date.trim() != "" || !conr.equals("false") || !newr.equals("false")) {
                        sql += "AND ";
                    } else {
                        sql += "AND ";
                    }
                }

                if (conr.equals("true")) {
                    if (date != null && date.trim() != "") {
                        sql += "conf_date='" + dbDate[2] + "-" + dbDate[1] + "-" + dbDate[0] + "' AND state='2' ";
                        if (!newr.equals("false")) {
                            sql += "OR ";
                        }
                    } else {
                        sql += "state='2' ";
                        if (!newr.equals("false")) {
                            sql += "OR ";
                        }

                    }
                }
                if (newr.equals("true")) {
                    if (date != null && date.trim() != "") {
                        sql += "date='" + dbDate[2] + "-" + dbDate[1] + "-" + dbDate[0] + "' AND state='1' ";

                    } else {
                        sql += "state='1' ";
                    }
                }
                if (conr.trim().equals("false") && newr.equals("false")) {
                    if (date != null && date.trim() != "") {
                        sql += "conf_date='" + dbDate[2] + "-" + dbDate[1] + "-" + dbDate[0] + "' OR date='" + dbDate[2] + "-" + dbDate[1] + "-" + dbDate[0] + "' ";
                    } else {
                        sql += "state='1' ";
                    }
                }
                System.out.println("sql: " + sql);
                try {
                    PreparedStatement ps = DBFactory.getConnection().prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();

                    int pages = 0;
                    PaymentAccountDAOImp pad = new PaymentAccountDAOImp();

                    while (rs.next()) {

                        pages++;

                        JSONObject jo = new JSONObject();
                        jo.put("id", rs.getString(1));
                        jo.put("userid", rs.getString(2));
                        jo.put("reqdate", rs.getString(3));
                        jo.put("acctype", pad.getPaymetAccountName(rs.getInt(5)));
                        jo.put("amount", rs.getDouble(7));
                        jo.put("state", rs.getInt(8));
                        jo.put("email", rs.getString(9));
                        jo.put("no", pages);
                        ja.add(jo);

                    }
                    System.out.println(ja.toJSONString());
                    response.getWriter().write("json=" + ja.toJSONString());
                } catch (Exception e) {
                    e.printStackTrace();
                    response.getWriter().write("msg=Internal server error,Please try again later.");
                }

            } else if (type.equals("settle")) {
                String id = request.getParameter("idd");
                String staType = request.getParameter("state");
                String uid = request.getParameter("uid");
                String amount = request.getParameter("amount");

                double damo = Double.parseDouble(amount);
                System.out.println("amount:" + amount + " " + damo);

                if (id != null && !id.trim().equals("") && staType != null && !staType.trim().equals("") && uid != null && !uid.trim().equals("") && amount != null && !amount.trim().equals("")) {
                    boolean ok = ppdi.requestSettle(Integer.parseInt(id), Integer.parseInt(staType), us.getUser_id(), "conf_date");
                    if (ok) {

                        SellerIncomeDAOImp sid = new SellerIncomeDAOImp();
                        //old-income
                        SellerIncome lastSellerIncome = sid.getSellerIncome(Integer.parseInt(uid));
                        //new-income
                        double newIncome = lastSellerIncome.getTotal() - damo;
                        SellerIncome newSellerincome = new SellerIncome();
                        newSellerincome.setUser_id(Integer.parseInt(uid));
                        newSellerincome.setSell_inc_id(lastSellerIncome.getSell_inc_id());
                        newSellerincome.setTotal(newIncome);

                        boolean update = sid.updateSellerIncome(newSellerincome);
                        if (update) {
//                        boolean notiok = false;
//                        if (staType.equals("2")) {
//                            String noti = "Admin " + us.getUser_name() + " has confirmed your earning request";
//                            notiok = new NotificationDAOImp().addNotification(noti, date1, Time, Integer.parseInt(uid), 1, 4);
//                        } else if (staType.equals("3")) {
//                            String noti = "Admin " + us.getUser_name() + " has canceled your earning request";
//                            notiok = new NotificationDAOImp().addNotification(noti, date1, Time, Integer.parseInt(uid), 1, 3);
//                        }
//                        AdminNotification a = new AdminNotification();
//                        a.setUser_id(us.getUser_id());
//                        a.setDate(timeStamp);
//
//                        a.setShow(1);
//                        if (staType.equals("3")) {
//                            a.setType(2);
//                            String not = "Admin " + us.getUser_name() + " has canceled " + amount + " earning request.";
//                            a.setNotification(not);
//                        } else if (staType.equals("2")) {
//                            a.setType(4);
//                            String not = "Admin " + us.getUser_name() + " has confirmed " + amount + " earning requests.";
//                            a.setNotification(not);
//                        }
//
//                        int an = new AdminNotificationDAOImp().insertNotificaation(a);
//                        System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPP " + an);
//
//                        boolean kl = new AdminHasNotificationDAOImp().insertNotification(udi.notMeList(us.getUser_id()), an, 1);
//
//                        if (notiok && kl) {
//                            out.write("ok");
//                        } else {
//                            out.write("error");
//                        }
                            User u = udi.getUser(Integer.parseInt(uid));
                            out.write("msg=x-"+u.getPhone());
                        } else {
                            out.write("msg=Unable to update sellers income.");
                        }

                    } else {
                        out.write("msg=Unable to complete your request,Please try agin later.");
                    }
                } else {
                    out.write("msg=Internal server error,Please try again later.");
                }

            } else if (type.equals("cancel")) {
                String id = request.getParameter("idd");
                String staType = request.getParameter("state");
                String uid = request.getParameter("uid");
                String amount = request.getParameter("amount");
                double damo = Double.parseDouble(amount);
                System.out.println("amount:" + amount + " " + damo);
                System.out.println("idd:" + id);
                System.out.println("statype:" + staType);
                System.out.println("uid:" + uid);
                System.out.println("id:" + usid);
                System.out.println("type:" + type);

                if (id != null && !id.trim().equals("") && staType != null && !staType.trim().equals("") && uid != null && !uid.trim().equals("") && amount != null && !amount.trim().equals("")) {
                    boolean ok = ppdi.requestSettle(Integer.parseInt(id), Integer.parseInt(staType), us.getUser_id(), "conf_date");
                    if (ok) {
//                    boolean notiok = false;
//                        if (staType.equals("2")) {
//                            String noti = "Admin " + us.getUser_name() + " has confirmed your earning request";
//                            notiok = new NotificationDAOImp().addNotification(noti, date1, Time, Integer.parseInt(uid), 1, 4);
//                        } else if (staType.equals("3")) {
//                            String noti = "Admin " + us.getUser_name() + " has canceled your earning request";
//                            notiok = new NotificationDAOImp().addNotification(noti, date1, Time, Integer.parseInt(uid), 1, 3);
//                        }
//                        AdminNotification a = new AdminNotification();
//                        a.setUser_id(us.getUser_id());
//                        a.setDate(timeStamp);
//
//                        a.setShow(1);
//                        if (staType.equals("3")) {
//                            a.setType(2);
//                            String not = "Admin " + us.getUser_name() + " has canceled " + amount + " earning request.";
//                            a.setNotification(not);
//                        } else if (staType.equals("2")) {
//                            a.setType(4);
//                            String not = "Admin " + us.getUser_name() + " has confirmed " + amount + " earning requests.";
//                            a.setNotification(not);
//                        }
//
//                        int an = new AdminNotificationDAOImp().insertNotificaation(a);
//                        System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPP " + an);
//
//                        boolean kl = new AdminHasNotificationDAOImp().insertNotification(udi.notMeList(us.getUser_id()), an, 1);
//
//                        if (notiok && kl) {
//                            out.write("ok");
//                        } else {
//                            out.write("error");
//                        }
                        out.write("msg=Successful...");
                    } else {
                        out.write("msg=Unable to complete your request,Please try agin later.");
                    }
                } else {
                    out.write("msg=Internal server error,Please try again later.");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            out.write("msg=Internal server error,Please try again later.");
        }

    }

}
