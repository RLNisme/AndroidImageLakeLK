/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.android.download;

import com.imagelake.android.details.SpreadIncome;
import com.imagelake.control.CartDAOImp;
import com.imagelake.control.CreditsDAOImp;
import com.imagelake.control.EmailSend;
import com.imagelake.control.ImagesDAOImp;
import com.imagelake.control.UserDAOImp;
import com.imagelake.control.UserHasPackageDAOImp;
import com.imagelake.model.Cart;
import com.imagelake.model.CartHasImages;
import com.imagelake.model.Credits;
import com.imagelake.model.Images;
import com.imagelake.model.ImagesSub;
import com.imagelake.model.User;
import com.imagelake.model.UserHasPackage;
import com.imagelake.model.UserZipFile;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author RLN
 */
public class Servlet_Download extends HttpServlet {

    CartDAOImp cdi;
    ImagesDAOImp idi;
    UserHasPackageDAOImp uhdi;
    SpreadIncome spi;
    UserDAOImp udi;
    CreditsDAOImp cdo;
    JSONObject jo;

    public Servlet_Download() {
        cdi = new CartDAOImp();
        uhdi = new UserHasPackageDAOImp();
        idi = new ImagesDAOImp();
        spi = new SpreadIncome();
        udi = new UserDAOImp();
        cdo = new CreditsDAOImp();
    }

    Date dat = new Date();
    SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd");
    String day = sd1.format(dat);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        PrintWriter out = response.getWriter();
        try {
            String type = request.getParameter("type");

            if (type.equals("All_Cart_Items")) {
                String uid = request.getParameter("uid");
                System.out.println(uid);
                System.out.println(type);
                jo = new JSONObject();
                List<Cart> cartList = cdi.listCart();
                List<CartHasImages> itemList = null;
                Images im = null;
                ImagesSub sim = null;
                Credits cre = null;

                if (!cartList.isEmpty()) {
                    for (Cart c : cartList) {
                        System.gc();
                        if (c.getUser_id() == Integer.parseInt(uid)) {

                            itemList = cdi.listCartNewImages(c.getCart_id());
                            int i = 0;
                            JSONArray ja = new JSONArray();
                            if (!itemList.isEmpty()) {

                                for (CartHasImages ci : itemList) {
                                    System.gc();

                                    System.out.println(".................................cartList is iii:" + i);
                                    if (c.getCart_id() == ci.getCart_id() && ci.getIs_purchase() == 0) {

                                        System.out.println(".................................cartList is iii second:" + i);
                                        im = idi.getImageDetail(ci.getImg_id());
                                        sim = idi.getSubImage(ci.getSubimg_id());

                                        JSONObject joo = new JSONObject();

                                        if (im.getImage_state_image_state_id() == 1) {

                                            i += ci.getCredits();
                                            joo.put("imgid", im.getImages_id());
                                            joo.put("sid", sim.getSub_images_id());
                                            joo.put("dim", sim.getDimention());
                                            joo.put("title", im.getTitle());
                                            joo.put("crd", ci.getCredits());
                                            joo.put("url", im.getImg_url());
                                            joo.put("state", im.getImage_state_image_state_id());
                                            joo.put("chid", ci.getCart_has_images_id());
                                            String np = "";
                                            System.out.println(sim.getImg_url());
                                            int jj = 0;
                                            int kk = 0;
                                            adds:
                                            for (int j = 0; j < sim.getImg_url().length(); j++) {
                                                if (sim.getImg_url().charAt(j) == '\\') {
                                                    jj++;
                                                }

                                                if (jj == 3) {
                                                    if (kk > 0) {
                                                        np += sim.getImg_url().charAt(j);
                                                    } else {
                                                        kk++;
                                                        continue adds;
                                                    }
                                                }
                                            }
                                            System.out.println(np);
                                            joo.put("surl", np);
                                            ja.add(joo);
                                        } else if (im.getImage_state_image_state_id() == 7) {
                                            joo.put("imgid", im.getImages_id());
                                            joo.put("sid", sim.getSub_images_id());
                                            joo.put("dim", sim.getDimention());
                                            joo.put("title", im.getTitle());
                                            joo.put("crd", ci.getCredits());
                                            joo.put("url", im.getImg_url());
                                            joo.put("state", im.getImage_state_image_state_id());
                                            joo.put("chid", ci.getCart_has_images_id());
                                            joo.put("surl", sim.getImg_url());
                                            ja.add(joo);

                                        }
                                    }
                                }

                            }
                            jo.put("cartList", ja);
                            jo.put("total", i);
                        }
                    }
                    System.out.println(jo.toJSONString());
                    out.write("json=" + jo.toJSONString());
                } else {
                    int i = 0;
                    JSONArray ja = new JSONArray();
                    jo.put("cartList", ja);
                    jo.put("total", i);
                    out.write("json=" + jo.toJSONString());
                }

            } else if (type.equals("Remove_DB")) {
                String imgID = request.getParameter("chiID");
                String uid = request.getParameter("uid");
                String crecount = request.getParameter("crec");

                Cart c = cdi.getCart(Integer.parseInt(uid));

                boolean ok = new CartDAOImp().removeFromCartHasimages(Integer.parseInt(imgID));
                if (ok) {

                    System.out.println("full imgcount:" + c.getImage_count());

                    int imgCount = c.getImage_count() - 1;
                    System.out.println("After imgcount:" + imgCount);
                    System.out.println("fill creditst:" + c.getCredit_count());

                    int creditCount = c.getCredit_count() - Integer.parseInt(crecount);
                    System.out.println("After creditcount:" + creditCount);

                    boolean k = new CartDAOImp().updateCartQty(imgCount, creditCount, c.getCart_id());
                    if (k) {
                        out.write("msg=Successfuly_Remove");
                    } else {
                        out.write("msg=Unable to complete the action.");
                    }
                    System.gc();

                } else {
                    out.write("msg=Internal server error,Please try again later.");
                }

            } else if (type.equals("Add_Light")) {

                String imgID = request.getParameter("chiID");
                String uid = request.getParameter("uid");
                String crecount = request.getParameter("crec");

                System.out.println(imgID);
                System.out.println(uid);
                System.out.println(crecount);

                Cart c = cdi.getCart(Integer.parseInt(uid));

                int imgCount = c.getImage_count() - 1;
                System.out.println("After imgcount:" + imgCount);
                System.out.println("fill creditst:" + c.getCredit_count());

                int creditCount = c.getCredit_count() - Integer.parseInt(crecount);
                System.out.println("After creditcount:" + creditCount);

                String imgid = request.getParameter("img_id");

                boolean dup = new CartDAOImp().imageAlradyHasImageInLightBox(Integer.parseInt(imgID), c.getCart_id(), 2);
                System.out.println("duplictio..." + dup);
                if (dup) {
                    boolean k = new CartDAOImp().removeFromCartHasimages(Integer.parseInt(imgID));
                    boolean k2 = new CartDAOImp().updateCartQty(imgCount, creditCount, c.getCart_id());
                    if (k || k2) {
                        System.gc();
                        out.write("msg=Successfully_Add_Lightbox");
                    } else {
                        out.write("msg=Unable to complete the action,Please try again later.");
                        System.gc();
                    }
                } else {
                    boolean k = new CartDAOImp().updateCartQty(imgCount, creditCount, c.getCart_id());
                    boolean a = new CartDAOImp().updateToLightBox(Integer.parseInt(imgID), 2);
                    if (k || a) {
                        System.gc();
                        out.write("msg=Successfully_Add_Lightbox");
                    } else {
                        out.write("msg=Unable to complete the action,Please try again later.");
                        System.gc();

                    }
                }

            } else if (type.equals("Download")) {
                String uid = request.getParameter("uid");

                Cart c = cdi.getCart(Integer.parseInt(uid));

                List<CartHasImages> cList = cdi.listUserCartHasImages(c.getCart_id(), 0);
                List<CartHasImages> downloadList = new ArrayList<CartHasImages>();

                boolean update;

                UserHasPackage up = null;

                Images im = null;
                User owner = null;
                ImagesSub is = null;
                ImagesSub minsub = null;
                Credits cd = null;

                int activeImagesCount = 0;
                int fullCreditCount = 0;

                up = uhdi.getUserActivePackage(Integer.parseInt(uid), 1);

                if (up != null) {

                    System.out.println(":::::::::::::::::::::" + up.getPackage_id());
                    System.out.println(":::::::::::::::::::::" + up.getPackage_type());
                    if (up.getPackage_type() == 1 || up.getPackage_type() == 2 || up.getPackage_type() == 3) {
                        System.out.println("This user have subscription package");
                        System.out.println(":::::::::::::::::::::" + up.getPackage_type());
                    //FreeTrial free=new FreeTrialDAOImp().getAFreeTrail(up.getPackage_type());

                        //check today is equel to the expire date of the package
                        System.out.println("today: " + day);

                        System.out.println("Expiredate: " + day.compareTo(up.getExpire_date()));
                        System.out.println("Expire: " + up.getExpire_date());
                        if (day.compareTo(up.getExpire_date()) < 0 || day.compareTo(up.getExpire_date()) == 0) {

                            System.out.println("expire date Not equal::::::::::");
                            //check today is equel to the last updated date
                            //  if(day.equals(up.getLast_date())){
                            System.out.println("last date Not equal::::::::::");
                            //check total image count of the cart is equal or less than the amount that can download in one day
                            if (c.getImage_count() <= up.getDownload_count()) {

                                System.out.println("expire date Not equal::::::::::");

                                int imgCount = 0;

                                int totimgCount = 0;

                                int creditCount = 0;

                                int totcreditCount = 0;

                                if (!cList.isEmpty()) {

                                    for (CartHasImages ch : cList) {

                                        if (up.getPackage_type() == 3 || up.getPackage_type() == 4) {

                                            im = idi.getImageDetail(ch.getImg_id());//get image

                                            //*****need to check current image is upload by a contributor or admin******
                                            if (im.getImage_state_image_state_id() == 1) {
                                                activeImagesCount++;//adding 1 to active image count 

                                                owner = udi.getUser(im.getUser_user_id());
                                                is = idi.getSubImage(ch.getSubimg_id());//get sub image object
                                                cd = cdo.getCreditDetails(is.getCredits());
                                                if (owner.getUser_type() == 2) {

                                                    boolean income = spi.income(up, ch);//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

                                                    if (income) {
                                                        imgCount++;

                                                        creditCount += cd.getCredits();

                                                        boolean ok = cdi.updatePurchase(ch);
                                                        if (ok) {
                                                            downloadList.add(ch);
                                                        }
                                                        System.gc();
                                                    } else {
                                                        System.out.println("loop breaks");
                                                        break;
                                                    }

                                                } else {

                                                    imgCount++;

                                                    creditCount += cd.getCredits();

                                                    boolean ok = cdi.updatePurchase(ch);
                                                    if (ok) {
                                                        downloadList.add(ch);
                                                    }
                                                    System.gc();
                                                }

                                            }/*else{

                                             boolean ok=cdi.updatePurchaseToRemove(ch);    

                                             System.gc();

                                             }*/

                                            totimgCount++;

                                            totcreditCount += ch.getCredits();

                                        } else {//this is where we handle user trial packages++++++++++++++++++++++++++
                                            System.out.println("this user has a free trial package");
                                            im = idi.getImageDetail(ch.getImg_id());
                                            //is=idi.getSubImage(ch.getSubimg_id());//get the subimage that use has add to cart

                                            if (im != null) {

                                                minsub = idi.getLowestQuality(ch.getImg_id());
                                                System.out.println("min sub image id: " + minsub.getSub_images_id());
                                                System.out.println("min sub image id=subimage id: " + (minsub.getSub_images_id() == ch.getSubimg_id()));

                                                if (im.getImage_state_image_state_id() == 1 && minsub.getSub_images_id() == ch.getSubimg_id()) {
                                                    activeImagesCount++;
                                                    imgCount++;

                                                    is = idi.getSubImage(ch.getSubimg_id());//get the subimage that use has add to cart
                                                    cd = cdo.getCreditDetails(is.getCredits());

                                                    creditCount += cd.getCredits();

                                                    boolean ok = cdi.updatePurchase(ch);
                                                    if (ok) {
                                                        downloadList.add(ch);
                                                    }

                                                    System.gc();

                                                }/*else{
                                                
                                                 boolean ok=cdi.updatePurchaseToRemove(ch);    
                                            
                                                 System.gc();
                                            
                                                 }*/

                                            }
                                            totimgCount++;

                                            totcreditCount += ch.getCredits();
                                            System.out.println("total image count :" + totimgCount);
                                            System.out.println("image count :" + imgCount);
                                            System.out.println("active image count :" + activeImagesCount);
                                            System.out.println("total credit count :" + totcreditCount);
                                            System.out.println("credit count :" + creditCount);
                                        }

                                    }
                                    boolean ok = cdi.updateCartQty(c.getImage_count() - activeImagesCount, c.getCredit_count() - creditCount, c.getCart_id());
                                    if (ok) {
                                        UserHasPackage uhp = new UserHasPackage();
                                        if (up.getPackage_type() != 4) {
                                            uhp.setUhp_id(up.getUhp_id());
                                            uhp.setCredit_count(0);
                                            uhp.setDownload_count(up.getDownload_count() - imgCount);
                                            uhp.setLast_date(day);

                                            update = uhdi.updatePackage(uhp);

                                        } else {

                                            uhp.setUhp_id(up.getUhp_id());
                                            uhp.setCredit_count(up.getCredit_count() - creditCount);
                                            uhp.setDownload_count(0);
                                            uhp.setLast_date(day);

                                            update = uhdi.updatePackage(uhp);

                                        }
                                        System.out.println("----------------------------------------------------------" + activeImagesCount);

                                        if (update && activeImagesCount > 0) {
                                            out.write("msg=Dwnloaded.");
                                        } else {
                                            out.write("msg=Unable to complete the action,Please try again later.");
                                        }
                                    } else {
                                        out.write("msg=Unable to complete the action,Please try again later.");
                                    }

                                } else {
                                    out.write("msg=You'r cart is empty!");

                                }

                            } else {
                                out.write("msg=You have excided the limite of the downloads!");

                            }
                            /*}else{
                             request.setAttribute("error", "You have excided the limite of the downloads!");
                             getServletContext().getRequestDispatcher("/cart.jsp").forward(
                             request, response);
                             System.gc();
                             }
                             */

                        } else {
                            out.write("msg=You'r current package has expired!");

                        }

                    } else {
                        System.out.println("This user have credit package");
                        //check today is equel to the expire date of the package
                        if (day.compareTo(up.getExpire_date()) < 0 || day.compareTo(up.getExpire_date()) == 0) {
                            System.out.println("expire date Not equal::::::::::");
                            //check today is equel to the last updated date
//                       if(day.equals(up.getLast_date())){

                            for (CartHasImages ch : cList) {
                                im = idi.getImageDetail(ch.getImg_id());
                                if (im.getImage_state_image_state_id() == 1) {
                                    is = idi.getSubImage(ch.getSubimg_id());//get the sub image
                                    cd = cdo.getCreditDetails(is.getCredits());//get the credits
                                    fullCreditCount += cd.getCredits();
                                }
                            }

                            System.out.println("last date Not equal::::::::::");
                            //check total image count of the cart is equal or less than the amount that can download in one day
                            if (fullCreditCount <= up.getCredit_count()) {
                                System.out.println("expire date Not equal::::::::::");
                                int imgCount = 0;
                                int totimgCount = 0;
                                int creditCount = 0;
                                int totcreditCount = 0;

                                if (!cList.isEmpty()) {
                                    for (CartHasImages ch : cList) {
                                        im = idi.getImageDetail(ch.getImg_id());

                                        if (im.getImage_state_image_state_id() == 1) {

                                            activeImagesCount++;

                                            owner = udi.getUser(im.getUser_user_id());//check the image owner
//                                           is=idi.getSubImage(ch.getSubimg_id());//get the sub image
//                                           cd=cdo.getCreditDetails(is.getCredits());//get the credits
                                            System.out.println("owner is type " + owner.getUser_type());

                                            if (owner.getUser_type() == 2) {

                                                boolean income = spi.income(up, ch);//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

                                                System.out.println("Seller have income " + income);
                                                if (income) {
                                                    imgCount++;
                                                    // creditCount+=cd.getCredits();
                                                    System.gc();
                                                    boolean ok = cdi.updatePurchase(ch);
                                                    if (ok) {
                                                        downloadList.add(ch);
                                                    }
                                                } else {
                                                    System.out.println("loop breaks");
                                                    break;
                                                }
                                            } else {

                                                imgCount++;
                                                // creditCount+=cd.getCredits();
                                                System.gc();
                                                boolean ok = cdi.updatePurchase(ch);
                                                if (ok) {
                                                    downloadList.add(ch);
                                                }
                                            }

                                        }/*else{
                                         System.gc();
                                         boolean ok=cdi.updatePurchaseToRemove(ch);
                                         System.out.println("removed of the purchase"+ok);
                                         }*/

                                        totimgCount++;
                                        totcreditCount += ch.getCredits();

                                    }
                                    boolean ok = cdi.updateCartQty(c.getImage_count() - activeImagesCount, c.getCredit_count() - fullCreditCount, c.getCart_id());
                                    if (ok) {
                                        UserHasPackage uhp = new UserHasPackage();
                                        if (up.getPackage_type() != 4) {
                                            uhp.setUhp_id(up.getUhp_id());
                                            uhp.setCredit_count(0);
                                            uhp.setDownload_count(up.getDownload_count() - imgCount);
                                            uhp.setLast_date(day);

                                            update = uhdi.updatePackage(uhp);

                                        } else {

                                            uhp.setUhp_id(up.getUhp_id());
                                            uhp.setCredit_count(up.getCredit_count() - fullCreditCount);
                                            uhp.setDownload_count(0);
                                            uhp.setLast_date(day);

                                            update = uhdi.updatePackage(uhp);
                                            System.gc();
                                        }
                                        System.out.println("-------------------------------------------" + activeImagesCount);
                                        if (update && activeImagesCount > 0) {
                                            out.write("msg=Dwnloaded.");
                                        } else {
                                            out.write("msg=Unable to complete the action,Please try again later.");
                                        }
                                    } else {
                                        out.write("msg=Unable to complete the action,Please try again later.");
                                    }

                                } else {
                                    out.write("msg=You'r cart is empty!");
                                }

                            } else {
                                out.write("msg=You have excided the limite of the downloads!");
                            }

                        } else {
                            out.write("msg=You'r current package has expired!");
                        }

                    }

                } else {
                    out.write("msg=You have no package.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
