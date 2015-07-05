/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.android.uploadmanagement;

import com.imagelake.control.AdminHasNotificationDAOImp;
import com.imagelake.control.AdminNotificationDAOImp;
import com.imagelake.control.CategoriesDAOImp;
import com.imagelake.control.CreditsDAOImp;
import com.imagelake.control.ImagesDAOImp;
import com.imagelake.control.KeyWordsDAOImp;
import com.imagelake.control.NotificationDAOImp;
import com.imagelake.control.UserDAOImp;
import com.imagelake.model.AdminNotification;
import com.imagelake.model.Credits;
import com.imagelake.model.Images;
import com.imagelake.model.ImagesSub;
import com.imagelake.model.KeyWords;
import com.imagelake.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
public class Servlet_UploadManagement extends HttpServlet {

    JSONArray ja;
    ImagesDAOImp idi;
    CreditsDAOImp crdi;
    UserDAOImp udi;
    CategoriesDAOImp cdi;
    NotificationDAOImp ndi;
    AdminNotificationDAOImp andi;
    AdminHasNotificationDAOImp ahndi;
    public Servlet_UploadManagement() {
        idi = new ImagesDAOImp();
        udi = new UserDAOImp();
        cdi = new CategoriesDAOImp();
        crdi = new CreditsDAOImp();
        ndi = new NotificationDAOImp();
        andi = new AdminNotificationDAOImp();
        ahndi = new AdminHasNotificationDAOImp();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(d);

        SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss");
        String Time = sdf2.format(d);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        try {

            String type = request.getParameter("type");
            System.out.println("type:" + type);
            if (type != null && !type.equals("")) {

                if (type.equals("all")) {
                    ja = new JSONArray();
                    String category;
                    String seller;
                    List<Images> allList = idi.getNewUploadList();
                    if (!allList.isEmpty()) {
                        for (Images images : allList) {

                            JSONObject jo = new JSONObject();
                            jo.put("img_id", images.getImages_id());
                            jo.put("title", images.getTitle());
                            jo.put("date", images.getDate());
                            jo.put("url", images.getImg_url());
                            category = idi.getCategoryName(images.getCategories_category_id());
                            jo.put("cat", category);
                            seller = idi.getSellerName(images.getUser_user_id());
                            jo.put("sel", seller);
                            ja.add(jo);
                        }
                        System.out.println(ja.toJSONString());
                        out.write("json=" + ja.toJSONString());
                    } else {
                        out.write("msg=No item found.");
                    }

                } else if (type.equals("single_details")) {
                    ja = new JSONArray();
                    String imgid = request.getParameter("imgid");

                    System.out.println("imgid:" + imgid);

                    if (imgid != null && !imgid.equals("")) {
                        Images im = idi.getImageDetail(Integer.parseInt(imgid));

                        if (im.getImage_state_image_state_id() == 3 || im.getImage_state_image_state_id() == 1 || im.getImage_state_image_state_id() == 5) {
                            JSONObject jo = new JSONObject();
                            jo.put("img_id", im.getImages_id());
                            jo.put("title", im.getTitle());
                            jo.put("url", im.getImg_url());
                            //--------------------------------

                            List<ImagesSub> sublist = idi.getImagedescription(Integer.parseInt(imgid));
                            JSONArray jk = new JSONArray();
                            if (!sublist.isEmpty()) {
                                for (ImagesSub imagesSub : sublist) {

                                    String dim = imagesSub.getDimention();
                                    String dimArray[] = dim.split(" x ");
                                    String width = dimArray[0];
                                    if (!width.equals("400")) {
                                        JSONObject jso = new JSONObject();
                                        jso.put("dimention", dim);
                                        Credits c = crdi.getCreditDetails(imagesSub.getCredits());
                                        jso.put("size", c.getSize());
                                        jso.put("crd", c.getCredits());
                                        jk.add(jso);
                                    }

                                    jo.put("subs", jk.toJSONString());
                                }
                            } else {
                                jo.put("subs", jk.toJSONString());
                            }
                            //--------------------------------
                            List<Images> imgList = new ImageRec().imgDiff(im);
                            JSONArray na = new JSONArray();
                            if (!imgList.isEmpty()) {

                                for (Images images : imgList) {
                                    if(images.getImage_state_image_state_id() != 3){        
                                    JSONObject jso = new JSONObject();
                                    jso.put("equaltitle", images.getTitle());
                                    jso.put("equalurl", images.getImg_url());
                                    jso.put("equalseller", idi.getSellerName(images.getUser_user_id()));
                                    jso.put("equalstate", images.getImage_state_image_state_id());
                                    na.add(jso);
                                    }        
                                }
                                jo.put("equal", na.toJSONString());
                            } else {
                                jo.put("equal", na.toJSONString());
                            }

                            ja.add(jo);
                            System.out.println(ja.toJSONString());
                            out.write("json=" + ja.toJSONString());
                        } else {
                            System.out.println(ja.toJSONString());
                            out.write("json=" + ja.toJSONString());
                        }
                        //--------------------------------
                    } else {
                        out.write("msg=Internal server error,Please try again later.");
                    }
                } else if (type.equals("keywords")) {
                    String imgid = request.getParameter("imgid");

                    System.out.println("imgid:" + imgid);

                    if (imgid != null && !imgid.equals("")) {
                        List<KeyWords> listKeyWords = new KeyWordsDAOImp().getkeyWordList(Integer.parseInt(imgid));
                        JSONArray jsa = new JSONArray();
                        if (!listKeyWords.isEmpty()) {
                            for (KeyWords keyWords : listKeyWords) {
                                JSONObject jso = new JSONObject();
                                jso.put("kid", keyWords.getKey_words_id());
                                jso.put("key", keyWords.getKey_word());
                                jsa.add(jso);
                            }
                            System.out.println(jsa.toJSONString());
                            out.write("json=" + jsa.toJSONString());
                        } else {
                            out.write("json" + jsa.toJSONString());
                        }
                    } else {
                        out.write("msg=Internal server error,Please try again later.");
                    }

                } else if (type.equals("add_keywords")) {
                    String key = request.getParameter("key");
                    String imgId = request.getParameter("imgid");

                    System.out.println("key:" + key);
                    System.out.println("img ID:" + imgId);
                    

                    if (key != null && !key.equals("") && imgId != null && !imgId.equals("")) {
                        
                        String mx[] = key.trim().split(",");
                        String nk ="";
                        for (int i = 0; i < mx.length; i++) {
                            if(i == (mx.length -1)){
                                nk += mx[i];
                            }else{
                                 nk += mx[i]+" ";
                            }
                        }
                        
                        key = nk;
                        
                        boolean duplicate = new KeyWordsDAOImp().getKeyWords(key, Integer.parseInt(imgId));
                        if (duplicate) {

                            boolean add = new KeyWordsDAOImp().addKeyWords(Integer.parseInt(imgId), key);
                            if (add) {
                                out.write("msg=Key_Successful...");
                            } else {
                                out.write("msg=Unable to complete,Please try again later.");
                            }

                        } else {
                            out.write("msg=The keyword is already entered.");
                        }
                    } else {
                        out.write("msg=Internal server error,Please try again later.");
                    }

                } else if (type.equals("confirem")) {

                    String imgid = request.getParameter("imgid");
                    String userid = request.getParameter("userid");

                    if (imgid != null && !imgid.equals("") && userid != null && !userid.equals("")) {

                        User user = udi.getUser(Integer.parseInt(userid));

                        Images im = idi.getImageDetail(Integer.parseInt(imgid));

                        boolean k = new ImageRec().isDuplicate(im);
                        if (k) {
                            if (im.getImage_state_image_state_id() != 1) {
                                List<KeyWords> keyList = new KeyWordsDAOImp().getkeyWordList(Integer.parseInt(imgid));
                                if (!keyList.isEmpty()) {

                                    System.out.println(imgid + "llll");
                                    System.out.println(userid + "kkkkkkkkkk");
                                    System.out.println("");
                                    boolean ok = idi.adminUpdateImages(Integer.parseInt(imgid), 1);
                                    System.out.println(ok + " admin update image");
                                    if (ok) {
                                        //boolean notified = new NotificationDAOImp().addNotification(noti, date, Time, im.getUser_user_id(), 1, 4);
                                        System.out.println("image title:" + im.getTitle());
                                        System.out.println("(((((((((((((" + im.getUser_user_id() + ")))))))))))");
                                        String noti = "Admin " + user.getUser_name() + " has Confirmed your upload " + im.getTitle() + " image.";
                                        boolean notiok = ndi.addNotification(noti, date, Time, im.getUser_user_id(), 1, 4);

                                        AdminNotification a = new AdminNotification();
                                        a.setUser_id(user.getUser_id());
                                        a.setDate(timeStamp);

                                        a.setShow(1);
                                        a.setType(1);
                                        String not = "Admin " + user.getUser_name() + " has Confirmed upload " + im.getTitle() + " image.";
                                        a.setNotification(not);

                                        int an = andi.insertNotificaation(a);
                                        System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPP " + an);

                                        boolean kl = ahndi.insertNotification(udi.notMeList(user.getUser_id()), an, 1);

                                        if (notiok && kl) {
                                            out.write("msg=Con_Successful...");
                                        } else {
                                            out.write("msg=Unable to send notifications.");
                                        }

                                    } else {
                                        out.write("msg=Unable to confirem upload.");
                                    }

                                } else {
                                    out.write("msg=Key words are empty.");
                                }
                            } else {
                                out.write("msg=This image is already uploaded.");
                            }
                        } else {
                            out.write("msg=This image has a duplicate.");
                        }

                    } else {
                        out.write("msg=Internal server error,Please try again later.");
                    }

                } else if (type.equals("cancel")) {

                    String imgid = request.getParameter("imgid");
                    String userid = request.getParameter("userid");

                    if (imgid != null && !imgid.equals("") && userid != null && !userid.equals("")) {

                        User user = udi.getUser(Integer.parseInt(userid));

                        Images im = idi.getImageDetail(Integer.parseInt(imgid));

                        if (im != null) {

                            //if(imok){
                            List<ImagesSub> imsub = new ImagesDAOImp().getImagedescription(Integer.parseInt(imgid));
                            System.out.println(imsub.isEmpty());
                            /* for (ImagesSub imagesSub : imsub) {
                             boolean subok=new DeleteImage().deleteImage(imagesSub.getImg_url());

                             System.gc();
                             }*/

                            boolean ok = new ImagesDAOImp().removeAdminImage(Integer.parseInt(imgid));
                            if (ok) {

                                System.out.println("image title:" + im.getTitle());
                                System.out.println("(((((((((((((" + im.getUser_user_id() + ")))))))))))");
                                String noti = "Admin " + user.getUser_name() + " has canceled your upload " + im.getTitle() + " image.";
                                boolean notiok = ndi.addNotification(noti, date, Time, im.getUser_user_id(), 1, 3);

                                AdminNotification a = new AdminNotification();
                                a.setUser_id(user.getUser_id());
                                a.setDate(timeStamp);

                                a.setShow(1);
                                a.setType(1);
                                String not = "Admin " + user.getUser_name() + " has canceled upload " + im.getTitle() + " image.";
                                a.setNotification(not);

                                int an = andi.insertNotificaation(a);
                                System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPP " + an);

                                boolean kl = ahndi.insertNotification(udi.notMeList(user.getUser_id()), an, 1);

                                if (notiok && kl) {

                                    out.write("msg=Can_Successful...");

                                } else {
                                    out.write("msg=Unable to send notifications.");
                                }
                            } else {

                                out.write("msg=Unable to cancel upload.");
                            }

                        } else {
                            out.write("msg=Unable to find image.");
                        }

                    } else {
                        out.write("msg=Internal server error,Please try again later.");
                    }

                } else if (type.equals("state")) {
                    
                    String imid = request.getParameter("imgid");
                    String state = request.getParameter("state");
                    String userid = request.getParameter("userid");

                    System.out.println("imid:" + imid);
                    System.out.println("usid:" + userid);
                    System.out.println("state:" + state);
                    
                    User user = udi.getUser(Integer.parseInt(userid));

                    Images im =idi.getImageDetail(Integer.parseInt(imid));
                    if (!imid.equals("") && !imid.equals(null) && !state.equals("") && !state.equals(null)) {
                        boolean b = idi.updateState(Integer.parseInt(imid), Integer.parseInt(state));
                        if (b) {

                            System.out.println("image title:" + im.getTitle());
                            System.out.println("(((((((((((((" + im.getUser_user_id() + ")))))))))))");
                            boolean notiok = false;
                            if (state.equals("5")) {
                                String noti = "Admin " + user.getUser_name() + " has blocked your " + im.getTitle() + " image.";
                                notiok = ndi.addNotification(noti, date, Time, im.getUser_user_id(), 1, 2);
                            } else if (state.equals("1")) {
                                String noti = "Admin " + user.getUser_name() + " has activated your " + im.getTitle() + " image.";
                                notiok = ndi.addNotification(noti, date, Time, im.getUser_user_id(), 1, 4);
                            }
                            AdminNotification a = new AdminNotification();
                            a.setUser_id(user.getUser_id());
                            a.setDate(timeStamp);

                            a.setShow(1);
                            if (state.equals("5")) {
                                a.setType(2);
                                String not = "Admin " + user.getUser_name() + " has blocked " + im.getTitle() + " image.";
                                a.setNotification(not);
                            } else if (state.equals("1")) {
                                a.setType(4);
                                String not = "Admin " + user.getUser_name() + " has activated " + im.getTitle() + " image.";
                                a.setNotification(not);
                            }

                            int an = andi.insertNotificaation(a);
                            System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPP " + an);

                            boolean kl = ahndi.insertNotification(udi.notMeList(user.getUser_id()), an, 1);

                            if (notiok && kl) {
                                out.write("msg=Sta_Successful...");
                            } else {
                                out.write("msg=Unable to send notifications.");
                            }

                        } else {
                            out.write("msg=Unable to update state.");
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
        }
    }

}
