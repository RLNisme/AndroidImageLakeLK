/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.android.details;

import com.imagelake.cart.UnsignInCart;
import com.imagelake.control.CartDAOImp;
import com.imagelake.control.CreditsDAOImp;
import com.imagelake.control.DBFactory;
import com.imagelake.control.ImagesDAOImp;
import com.imagelake.control.KeyWordsDAOImp;
import com.imagelake.control.UserDAOImp;
import com.imagelake.control.UserHasPackageDAOImp;
import com.imagelake.control.ZipFileDAOImp;
import com.imagelake.model.Cart;
import com.imagelake.model.CartHasImages;
import com.imagelake.model.CartItems;
import com.imagelake.model.Credits;
import com.imagelake.model.Images;
import com.imagelake.model.ImagesSub;
import com.imagelake.model.KeyWords;
import com.imagelake.model.User;
import com.imagelake.model.UserHasPackage;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class Servlet_ImageDetails extends HttpServlet {

    ImagesDAOImp idip;
    UserDAOImp udi;
    KeyWordsDAOImp kdi;
    CartDAOImp cdi;
    CreditsDAOImp crdi;
    UserHasPackageDAOImp uhdi;
    SpreadIncome spi;
    JSONObject detailObject;

    public Servlet_ImageDetails() {
        idip = new ImagesDAOImp();
        udi = new UserDAOImp();
        kdi = new KeyWordsDAOImp();
        cdi = new CartDAOImp();
        crdi = new CreditsDAOImp();
        uhdi = new UserHasPackageDAOImp();
        spi = new SpreadIncome();

    }

    Date d = new Date();
    SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd");
    String date = sd1.format(d);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        try {
            String type = request.getParameter("type");
            System.out.println(type);
            if (!type.equals("") && type != null) {

                if (type.equals("info")) {
                    String imgid = request.getParameter("imgid");
                    String uid = request.getParameter("uid");

                    Images img = idip.getImageDetail(Integer.parseInt(imgid));

                    if (img != null) {
                        boolean k = new ImagesDAOImp().updateImageViewCount(img.getImages_id(), img.getView() + 1);
                        if (k) {
                            if (img.getImage_state_image_state_id() == 1) {
                                detailObject = new JSONObject();
                                detailObject.put("title", img.getTitle());
                                detailObject.put("url", img.getImg_url());
                                detailObject.put("view", img.getView());
                                int chh = cdi.getCartDownloadCount(Integer.parseInt(imgid));
                                detailObject.put("dwn", chh);

                                JSONArray subImagesArray = new JSONArray();
                                Credits cre;
                                List<ImagesSub> subList = idip.getImagedescription(Integer.parseInt(imgid));
                                for (ImagesSub imagesSub : subList) {
                                    JSONObject jo = new JSONObject();
                                    cre = crdi.getCreditDetails(imagesSub.getCredits());

                                    jo.put("sid", imagesSub.getSub_images_id());
                                    jo.put("size", cre.getSize());
                                    jo.put("dim", imagesSub.getDimention());
                                    jo.put("crd", cre.getCredits());

                                    subImagesArray.add(jo);

                                }
                                detailObject.put("subList", subImagesArray);

                                User u = udi.getUser(img.getUser_user_id());
                                detailObject.put("uName", u.getUser_name());

                                List<KeyWords> keyList = kdi.getkeyWordList(Integer.parseInt(imgid));
                                JSONArray keyArray = new JSONArray();
                                for (KeyWords keyWords : keyList) {
                                    JSONObject jo = new JSONObject();
                                    jo.put("kid", keyWords.getKey_words_id());
                                    jo.put("key", keyWords.getKey_word());
                                    keyArray.add(jo);
                                }
                                detailObject.put("keyList", keyArray);
                                //List<CartHasImages> chiList = cdi.getRattingList(Integer.parseInt(imgid));

                                String sql = "SELECT * FROM images WHERE categories_category_id='" + img.getCategories_category_id() + "' AND image_state_image_state_id='1'";
                                PreparedStatement ps = DBFactory.getConnection().prepareStatement(sql);
                                ResultSet rs = ps.executeQuery();
                                JSONArray simImage = new JSONArray();
                                while (rs.next()) {
                                    JSONObject jo = new JSONObject();
                                    jo.put("simid", rs.getInt(1));
                                    jo.put("url", rs.getString(15));
                                    simImage.add(jo);
                                }
                                detailObject.put("imgList", simImage);
                                System.out.println("user id " + uid);
                                if (Integer.parseInt(uid) != (-1) && Integer.parseInt(uid) != 0) {
                                    Cart cct = cdi.getCart(Integer.parseInt(uid));
                                    if (cct != null) {
                                        boolean have = cdi.isThisInLightBoxImage(cct.getCart_id(), 2, Integer.parseInt(imgid));
                                        if (have) {
                                            detailObject.put("lightbox", 1);
                                        } else {
                                            detailObject.put("lightbox", 0);
                                        }
                                    } else {
                                        detailObject.put("lightbox", 0);
                                    }
                                } else {
                                    detailObject.put("lightbox", 0);
                                }
                                out.write("json=" + detailObject.toJSONString());
                            }
                        } else {
                            out.write("msg=Unable to complete the action.Please try again later.");
                        }
                    } else {
                        out.write("msg=Internal server error,Please try again later.");
                    }
                } else if (type.equals("lightbox")) {

                    String imgid = request.getParameter("imgid");
                    String uid = request.getParameter("uid");

                    System.out.println(imgid);
                    System.out.println(uid);
                    
                    ImagesSub is = null;
                    Credits cre = null;
                    Cart cart = null;

                    is = idip.getLowestQuality(Integer.parseInt(imgid));
                    cre = new CreditsDAOImp().getCreditDetails(is.getCredits());
                    cart = new CartDAOImp().getCart(Integer.parseInt(uid));
                    System.gc();
                    if (is != null) {
                        boolean ok = new CartDAOImp().addToLightBox(Integer.parseInt(imgid), is.getSub_images_id(), 2, cart.getCart_id(), date, cre.getCredits());
                        if (ok) {
                            out.write("msg=Successfully_add_lightbox");
                        } else {
                            out.write("msg=Unable to complete the action,Please try again later.");

                        }

                    } else {
                        out.write("msg=Internal server error,Please try again later.");

                    }
                } else if (type.equals("Add_cart")) {
                    String imgid = request.getParameter("imgid");
                    String sid = request.getParameter("sid");
                    String uid = request.getParameter("uid");

                    System.out.println(imgid);
                    System.out.println(sid);
                    System.out.println(uid);

                    Images img = idip.getImageDetail(Integer.parseInt(imgid));
                    boolean update = false;
                    boolean o = false;

                    if (img != null) {
                        int i = 0;
                        int subimg_Id = Integer.parseInt(sid);
                        ImagesSub isu = idip.getSubImage(subimg_Id);

                        if (isu != null) {

                            CreditsDAOImp cdi = new CreditsDAOImp();

                            int credits = cdi.getCredit(isu.getCredits());

                            CartItems ci = null;

                            int cartCount = 0;

                            if (uid == null) {

                            } else {
                                //if user signin
                                System.out.println("signIn user");
//          

                                List<CartItems> citems;

                                Cart b = new CartDAOImp().getCart(Integer.parseInt(uid));
//           
//           System.out.println("cart :"+b.getCart_id());
//           System.out.println("cart is null"+b==null);
//           
                                if (b.getCart_id() == 0) {

                                    //if user didn't have cart in the database
                                    System.out.println("don't have a cart");
                                    Cart c = new Cart();
                                    c.setUser_id(Integer.parseInt(uid));
                                    c.setCredit_count(0);
                                    c.setCredit_unit_price_id(1);
                                    c.setImage_count(0);
                                    c.setSubscription_unit_price_id(1);

                                    //creating a new cart
                                    boolean bb = new CartDAOImp().setACart(c);
                                    System.out.println("created a new cart" + bb);
                                    System.gc();

                                    if (bb) {
                                        //getting that new cart 

                                        b = new CartDAOImp().getCart(Integer.parseInt(uid));

                                        if (b == null) {

                                        } else {
                                            CartItems cartI = new CartItems();
                                            cartI.setImgId(Integer.parseInt(imgid));
                                            cartI.setSubimg_Id(subimg_Id);
                                            cartI.setCredit(credits);
                                            cartI.setDate(date);

                                            boolean owner = idip.checkOwner(Integer.parseInt(uid), Integer.parseInt(imgid));
                                            if (owner) {
                                                out.write("msg=This is your image,you can't add to cart.");

                                            } else {
                                                //  boolean alreadyPur=new CartDAOImp().checkAlreadyPurchase(b.getCart_id(),cartI.getImgId());
                                                //if(!alreadyPur){ 
                                                boolean duplicat = new CartDAOImp().checkImageDuplication(b.getCart_id(), cartI.getImgId());
                                                if (duplicat) {
                                                    boolean updated = new CartDAOImp().updateCartHasImagesSubImageID(b.getCart_id(), cartI.getImgId(), cartI.getSubimg_Id(), cartI.getCredit());
                                                } else {
                                                    System.out.println("addingCarItems");
                                                    o = new CartDAOImp().addingCarItems(cartI, b);
                                                    System.gc();

                                                }

                                            }
                                        }

                                    }
                                    System.gc();
                                } else {
                                    //if user have a cart in the database

                                    System.out.println("this user have a car in the DB");
                                    b = new CartDAOImp().getCart(Integer.parseInt(uid));

                                    List<CartHasImages> ch = null;

                                    int imgcount = 0;
                                    int creditcount = 0;

                                    if (b == null) {

                                    } else {
//               
                                        CartItems cartI = new CartItems();
                                        cartI.setImgId(Integer.parseInt(imgid));
                                        cartI.setSubimg_Id(subimg_Id);
                                        cartI.setCredit(credits);
                                        cartI.setDate(date);

                                        boolean owner = idip.checkOwner(Integer.parseInt(uid), Integer.parseInt(imgid));
                                        if (owner) {
                                            out.write("msg=This is your image,you can't add to cart.");
                                        } else {

                                            boolean duplicat = new CartDAOImp().checkImageDuplication(b.getCart_id(), cartI.getImgId());
                                            if (duplicat) {
                                                boolean updated = new CartDAOImp().updateCartHasImagesSubImageID(b.getCart_id(), cartI.getImgId(), cartI.getSubimg_Id(), cartI.getCredit());
                                                out.write("msg=Image is added to the cart.");
                                            } else {
                                                System.out.println("addingCarItems");
                                                o = new CartDAOImp().addingCarItems(cartI, b);
                                                System.gc();
                                                out.write("msg=Image is added to the cart.");

                                            }

                                        }
                                        ch = new CartDAOImp().listCartHasImages();
                                        for (CartHasImages carhi : ch) {

                                            if (carhi.getCart_id() == b.getCart_id() && carhi.getIs_purchase() == 0) {
                                                imgcount++;
                                                creditcount += carhi.getCredits();
                                                System.out.println("-----------------totalcount------------------------" + imgcount);
                                                System.out.println("-----------------credits------------------------" + creditcount);
                                            }

                                        }
                                        update = new CartDAOImp().updateCartQty(imgcount, creditcount, b.getCart_id());

                                    }
//               
                                    System.gc();
                                }
                            }

                            System.out.println("near end");
                           
//                            System.gc();
//                            response.sendRedirect("description.jsp?image_id=" + imgid);

                        } else {

                            out.write("msg=Internal server error,Please try again later.");
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
