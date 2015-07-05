/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.cart.UnsignInCart;
import com.imagelake.model.Cart;
import com.imagelake.model.CartHasImages;
import com.imagelake.model.CartItems;
import com.imagelake.model.Credits;
import com.imagelake.model.Images;
import com.imagelake.model.UserHasPackage;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public class CartDAOImp {
    public Cart getCart(int uid){
            Cart cc=null;
            try {
            String sql="SELECT * FROM cart WHERE user_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, uid);
                ResultSet rs=ps.executeQuery();
                while (rs.next()) {        
                    cc = new Cart();
                    cc.setCart_id(rs.getInt(1));
                    cc.setCredit_count(rs.getInt(3));
                    cc.setCredit_unit_price_id(rs.getInt(5));
                    cc.setImage_count(rs.getInt(2));
                    cc.setSubscription_unit_price_id(rs.getInt(4));
                    cc.setUser_id(rs.getInt(6));
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
            return cc;
    }
    
    public boolean setACart(Cart c){
        boolean b=false;
        try {
            String sql="INSERT INTO cart(image_count,credit_count,subscription_unit_price_id,credit_unit_price_id,user_id)VALUES(?,?,?,?,?)";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, c.getImage_count());
            ps.setInt(2, c.getCredit_count());
            ps.setInt(3, c.getSubscription_unit_price_id());
            ps.setInt(4, c.getCredit_unit_price_id());
            ps.setInt(5, c.getUser_id());
            
            int i=ps.executeUpdate();
            if(i>0){
                b=true;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }
    
        java.util.Date d=new java.util.Date();
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
        String date=sd.format(d);
        
    public boolean addImagesToCart(List<CartItems> ci,Cart c){
        System.out.println("old cart ID:"+c.getCart_id());
        boolean b=false;
        UserHasPackage uh=null;
        try {
            String sql="INSERT INTO cart_has_images(img_id,subimg_id,is_purchase,cart_id,date,credits)VALUES(?,?,?,?,?,?)";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            int i=0;
            for (CartItems cartItems : ci) {
                System.gc();
                uh=new UserHasPackageDAOImp().getUserActivePackage(c.getUser_id(), 1);
                
                if(uh!=null){
                    if(uh.getPackage_type()!=1 && uh.getPackage_type()!=2){

                    boolean checkImageOwner=new ImagesDAOImp().checkOwner(c.getUser_id(), cartItems.getImgId());
                    if(checkImageOwner){
                        System.out.println("-------------checkImageOwner "+checkImageOwner);
                        continue;
                    }else{
                       // boolean alreadyPur=new CartDAOImp().checkAlreadyPurchase(c.getCart_id(),cartItems.getImgId());
                        //if(!alreadyPur){
                                    boolean duplicate=new CartDAOImp().checkImageDuplication(c.getCart_id(), cartItems.getImgId());
                                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Checking for duplication"+duplicate);
                                System.gc();
                                if(duplicate){
                                   boolean update=new CartDAOImp().updateCartHasImagesSubImageID(c.getCart_id(), cartItems.getImgId(), cartItems.getSubimg_Id(),cartItems.getCredit());
                                   System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< duplication updated"+update);
                                   System.gc();
                                }else{
                                     System.out.println("cartitem:"+cartItems.getImgId());
                                ps.setInt(1, cartItems.getImgId());
                                ps.setInt(2, cartItems.getSubimg_Id());
                                ps.setInt(3, 0);
                                ps.setInt(4, c.getCart_id());
                                ps.setString(5,cartItems.getDate());
                                ps.setInt(6, cartItems.getCredit());
                                System.gc();
                                i=ps.executeUpdate();

                                }
                                System.gc();

                        //}
                    }
                    }else{
                        if(cartItems.getCredit()>1){
                            continue;
                        }else{
                            boolean checkImageOwner=new ImagesDAOImp().checkOwner(c.getUser_id(), cartItems.getImgId());
                    if(checkImageOwner){
                        System.out.println("-------------checkImageOwner "+checkImageOwner);
                        continue;
                    }else{
                       // boolean alreadyPur=new CartDAOImp().checkAlreadyPurchase(c.getCart_id(),cartItems.getImgId());
                       // if(!alreadyPur){
                                    boolean duplicate=new CartDAOImp().checkImageDuplication(c.getCart_id(), cartItems.getImgId());
                                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Checking for duplication"+duplicate);
                                System.gc();
                                if(duplicate){
                                   boolean update=new CartDAOImp().updateCartHasImagesSubImageID(c.getCart_id(), cartItems.getImgId(), cartItems.getSubimg_Id(),cartItems.getCredit());
                                   System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< duplication updated"+update);
                                   System.gc();
                                }else{
                                     System.out.println("cartitem:"+cartItems.getImgId());
                                ps.setInt(1, cartItems.getImgId());
                                ps.setInt(2, cartItems.getSubimg_Id());
                                ps.setInt(3, 0);
                                ps.setInt(4, c.getCart_id());
                                ps.setString(5,cartItems.getDate());
                                ps.setInt(6, cartItems.getCredit());
                                System.gc();
                                i=ps.executeUpdate();

                                }
                                System.gc();

                         // }
                        }
                      }
                    }//------------------
                }else{//if user don't have a package
                
                           boolean duplicate=new CartDAOImp().checkImageDuplication(c.getCart_id(), cartItems.getImgId());
                                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Checking for duplication"+duplicate);
                                System.gc();
                                if(duplicate){
                                   boolean update=new CartDAOImp().updateCartHasImagesSubImageID(c.getCart_id(), cartItems.getImgId(), cartItems.getSubimg_Id(),cartItems.getCredit());
                                   System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< duplication updated"+update);
                                   System.gc();
                                }else{
                                     System.out.println("cartitem:"+cartItems.getImgId());
                                ps.setInt(1, cartItems.getImgId());
                                ps.setInt(2, cartItems.getSubimg_Id());
                                ps.setInt(3, 0);
                                ps.setInt(4, c.getCart_id());
                                ps.setString(5,cartItems.getDate());
                                ps.setInt(6, cartItems.getCredit());
                                System.gc();
                                i=ps.executeUpdate();

                                }
                                System.gc();
                    
                }
                }
                
            if(i>0){
                b=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }
    
    public boolean addingCarItems(CartItems cartItems,Cart c){
        System.out.println("cart ID:"+c.getCart_id());
        boolean ok=false;
        try {
           String sql="INSERT INTO cart_has_images(img_id,subimg_id,is_purchase,cart_id,date,credits)VALUES(?,?,?,?,?,?)";
           Connection con=DBFactory.getConnection();
           PreparedStatement ps=con.prepareStatement(sql);
                ps.setInt(1, cartItems.getImgId());
                ps.setInt(2, cartItems.getSubimg_Id());
                ps.setInt(3, 0);
                ps.setInt(4, c.getCart_id());
                ps.setString(5,cartItems.getDate());
                ps.setInt(6, cartItems.getCredit());
                
                int i=ps.executeUpdate();
                if(i>0){
                    ok=true;
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }
    
    public List<Cart> listCart(){
     List<Cart> list=new ArrayList<Cart>();
        try {
            String sql="SELECT * FROM cart";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()) {                
                Cart c=new Cart();
                c.setCart_id(rs.getInt(1));
                c.setCredit_count(rs.getInt(3));
                c.setCredit_unit_price_id(rs.getInt(5));
                c.setImage_count(rs.getInt(2));
                c.setSubscription_unit_price_id(rs.getInt(4));
                c.setUser_id(rs.getInt(6));
                list.add(c);
                System.gc();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<CartHasImages> listCartHasImages(){
    List<CartHasImages> list=new ArrayList<CartHasImages>();
        try {
            String sql="SELECT * FROM cart_has_images";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                CartHasImages chi=new CartHasImages();
                chi.setCart_has_images_id(rs.getInt(1));
                chi.setCart_id(rs.getInt(5));
                chi.setDate(rs.getDate(6));
                chi.setImg_id(rs.getInt(2));
                chi.setIs_purchase(rs.getInt(4));
                chi.setSubimg_id(rs.getInt(3));
                chi.setCredits(rs.getInt(7));
                chi.setRatting(rs.getInt(8));
                list.add(chi);
                System.out.println("cccccccccccccc"+rs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    //changing size
    
    public int getImageIDFromCartHasImages(int chiID){
        int i=0;
        try {
            String sql="SELECT img_id FROM cart_has_images WHERE cart_has_images_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, chiID);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                i=rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
    
    public int getsubImageIDFromCartHasImages(int chiID){
        int i=0;
        try {
            String sql="SELECT subimg_id FROM cart_has_images WHERE cart_has_images_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, chiID);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                i=rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
    
    public boolean updateCartHasImagesSubImageID(int cart_id,int img_id,int subimg_id,int credit){
        boolean ok=false;
        try {
            String sql="UPDATE cart_has_images SET subimg_id=?,credits=? WHERE cart_id=? AND img_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, subimg_id);
            ps.setInt(2, credit);
            ps.setInt(3, cart_id);
            ps.setInt(4, img_id);
            int i=ps.executeUpdate();
            if(i>0){
                ok=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }
    
    public boolean updateCartQty(int img,int credit,int cartID){
    boolean ok=false;
        try {
            String sql="UPDATE cart SET image_count=?,credit_count=? WHERE cart_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, img);
            ps.setInt(2, credit);
            ps.setInt(3, cartID);
            int i=ps.executeUpdate();
            if(i>0){
            ok=true;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }
    
    public boolean checkImageDuplication(int cart_id,int img_id){
        boolean ok=false;
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>"+cart_id);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>"+img_id);
        try {
            String sql="SELECT * FROM cart_has_images WHERE cart_id=? AND img_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, cart_id);
            ps.setInt(2, img_id);
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()) {     
                System.out.println("duplicated image:"+rs.getInt(2));
                if(rs.getInt(4)==0){
                    ok=true;
                    
                }
                System.gc();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }
    
    
    public boolean checkAlreadyPurchase(int cart_id,int img_id){
        boolean ok=false;
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>"+cart_id);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>"+img_id);
        try {
            String sql="SELECT * FROM cart_has_images WHERE cart_id=? AND img_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, cart_id);
            ps.setInt(2, img_id);
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()) {     
                System.out.println("duplicated image:"+rs.getInt(2));
                if(rs.getInt(4)==1 || rs.getInt(4)==3){
                    ok=true;
                    
                }
                System.gc();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }
    
    
    public List<CartHasImages> listUserCartHasImages(int cid,int purchase){
        List<CartHasImages> cList=new ArrayList<CartHasImages>();
        try {
            String sql="SELECT * FROM cart_has_images WHERE cart_id=? AND is_purchase=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, cid);
            ps.setInt(2, purchase);
            
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()) {  
                System.out.println("111");
                System.gc();
                CartHasImages chi=new CartHasImages();
                chi.setCart_has_images_id(rs.getInt(1));
                chi.setCart_id(rs.getInt(5));
                chi.setDate(rs.getDate(6));
                chi.setImg_id(rs.getInt(2));
                chi.setIs_purchase(rs.getInt(4));
                chi.setSubimg_id(rs.getInt(3));
                chi.setCredits(rs.getInt(7));
                chi.setRatting(rs.getInt(8));
                cList.add(chi);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return cList;
    }
    
    public boolean updatePurchase(CartHasImages ci){
        boolean ok=false;
        System.gc();
        try {
            String sql="UPDATE cart_has_images SET is_purchase=?,date=? WHERE cart_has_images_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, 1);
            ps.setString(2, date);
            ps.setInt(3, ci.getCart_has_images_id());
            
            int i=ps.executeUpdate();
            if(i>0){
                ok=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }
 
    public boolean updatePurchaseToRemove(CartHasImages ci){
        System.out.println("you are in side the purchase remove");
        boolean ok=false;
        System.gc();
        try {
            String sql="DELETE FROM cart_has_images WHERE cart_has_images_id=?";
            
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,ci.getCart_has_images_id());
           
            
            int i=ps.executeUpdate();
            if(i>0){
                ok=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }
    
    public List<CartHasImages> listCartNewImages(int cartID){
        List<CartHasImages> list=new ArrayList<CartHasImages>();
        try {
            String sql="SELECT * FROM cart_has_images WHERE cart_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, cartID);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {   
                System.gc();
                if(rs.getInt(4)==0){
                CartHasImages chi=new CartHasImages();
                chi.setCart_has_images_id(rs.getInt(1));
                chi.setCart_id(rs.getInt(5));
                chi.setDate(rs.getDate(6));
                chi.setImg_id(rs.getInt(2));
                chi.setIs_purchase(rs.getInt(4));
                chi.setSubimg_id(rs.getInt(3));
                chi.setCredits(rs.getInt(7));
                chi.setRatting(rs.getInt(8));
                list.add(chi);
                System.out.println("xxxxxxxxxxx"+rs.getInt(1));
                }
                System.out.println("xxxxxxemp"+rs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    
    public boolean removeFromCartHasimages(int chiID){
    boolean ok=false;
        try {
            String sql="DELETE FROM cart_has_images WHERE cart_has_images_id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, chiID);
            int i=ps.executeUpdate();
            if(i>0){
                ok=true;
            }
        } catch (Exception e) {
        }
    return ok;
    }
    
    
    public boolean updateToLightBox(int chiID,int state){
        boolean ok=false;
        try {
            String sql="UPDATE cart_has_images SET is_purchase=? WHERE cart_has_images_id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, state);
            ps.setInt(2, chiID);
            int i=ps.executeUpdate();
            if(i>0){
                ok=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }
    
    public boolean imageAlradyHasImageInLightBox(int img_id,int cart_id,int state){
        boolean ok=false;
        System.out.println("--"+img_id+"--"+cart_id+"--"+state);
        try {
            String sql="SELECT * FROM cart_has_images WHERE img_id=? AND cart_id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, img_id);
            ps.setInt(2, cart_id);
            
            ResultSet rs=ps.executeQuery();
            while (rs.next()) { 
                System.gc();
                if(rs.getInt(4)==state){
                    ok=true;
                }
                System.gc();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }
    
    public List<CartHasImages> sortByDate(int state,String sort,int cartID,String sortingColum){
        List<CartHasImages> li=new ArrayList<CartHasImages>();
        try {
            String sql="SELECT * FROM cart_has_images WHERE cart_id=? AND is_purchase=? ORDER BY "+sortingColum+" "+sort;
            System.out.println(sql);
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, cartID);
            ps.setInt(2, state);
            
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                CartHasImages c=new CartHasImages();
                c.setCart_has_images_id(rs.getInt(1));
                c.setCart_id(rs.getInt(5));
                c.setCredits(rs.getInt(7));
                c.setDate(rs.getDate(6));
                c.setImg_id(rs.getInt(2));
                c.setIs_purchase(rs.getInt(4));
                c.setSubimg_id(rs.getInt(3));
                c.setRatting(rs.getInt(8));
                System.out.println("adding...");
                li.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return li;
    }
    
   public List<CartHasImages> SearchDownloadByTitle(int cid,int img_id,int state){
       List<CartHasImages> li=new ArrayList<CartHasImages>();
       try {
           String sql="SELECT * FROM cart_has_images WHERE img_id=? AND cart_id=?";
           PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
           ps.setInt(1, img_id);
           ps.setInt(2, cid);
           
           ResultSet rs=ps.executeQuery();
           while (rs.next()) {    
               if(rs.getInt(4)==state){
               CartHasImages c=new CartHasImages();
                c.setCart_has_images_id(rs.getInt(1));
                c.setCart_id(rs.getInt(5));
                c.setCredits(rs.getInt(7));
                c.setDate(rs.getDate(6));
                c.setImg_id(rs.getInt(2));
                c.setIs_purchase(rs.getInt(4));
                c.setSubimg_id(rs.getInt(3));
                c.setRatting(rs.getInt(8));
                System.out.println("adding...");
                li.add(c);
               }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return li;
   }
    
   public int getCartDownloadCount(int imgid){
       int i=0;
       
       try {
           String sql="SELECT * FROM cart_has_images WHERE img_id=? AND is_purchase=? OR img_id=? AND is_purchase=? ";
           PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
           ps.setInt(1, imgid);
           ps.setInt(2, 1);
           ps.setInt(3, imgid);
           ps.setInt(4, 3);
           ResultSet rs=ps.executeQuery();
           while (rs.next()) {  
               System.out.println("cart id"+rs.getInt(1));
               i+=1;
           }
           System.out.println(sql);
       } catch (Exception e) {
           e.printStackTrace();
       }
       return i;
   }
   
   public List<CartHasImages> listdownloadedImages(){
       List<CartHasImages> li=new ArrayList<CartHasImages>();
       try {
           String sql="SELECT * FROM cart_has_images WHERE is_purchase=? OR is_purchase=? ";
           PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
           
           ps.setInt(1, 1);
          
           ps.setInt(2, 3);
           ResultSet rs=ps.executeQuery();
           while (rs.next()) {  
               System.out.println("cart id"+rs.getInt(1));
               CartHasImages c=new CartHasImages();
                c.setCart_has_images_id(rs.getInt(1));
                c.setCart_id(rs.getInt(5));
                c.setCredits(rs.getInt(7));
                c.setDate(rs.getDate(6));
                c.setImg_id(rs.getInt(2));
                c.setIs_purchase(rs.getInt(4));
                c.setSubimg_id(rs.getInt(3));
                c.setRatting(rs.getInt(8));
                
                System.out.println("adding...");
                li.add(c);
           }
           System.out.println(sql);
       } catch (Exception e) {
           e.printStackTrace();
       }
       return li;
   }
   
   
   public Cart getACart(int cart_id){
       Cart c=null;
       try {
           String sql="SELECT * FROM cart WHERE cart_id=?";
           PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
           ps.setInt(1, cart_id);
           ResultSet rs=ps.executeQuery();
           while (rs.next()) {               
               c=new Cart();
                c.setCart_id(rs.getInt(1));
                c.setCredit_count(rs.getInt(3));
                c.setCredit_unit_price_id(rs.getInt(5));
                c.setImage_count(rs.getInt(2));
                c.setSubscription_unit_price_id(rs.getInt(4));
                c.setUser_id(rs.getInt(6));
                System.gc();
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return c;
   }
   
   public boolean isThisInLightBoxImage(int cart_id,int is_purchase,int img_id){
       boolean ok=false;
       try {
           String sql="SELECT * FROM cart_has_images WHERE cart_id=? AND is_purchase=? AND img_id=?";
           Connection con=DBFactory.getConnection();
           PreparedStatement ps=con.prepareStatement(sql);
           ps.setInt(1, cart_id);
           ps.setInt(2, is_purchase);
           ps.setInt(3, img_id);
           ResultSet rs=ps.executeQuery();
           while (rs.next()) {               
               ok=true;
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return ok;
   }
   
   public boolean addToLightBox(int imgid,int subimgid,int ispurchase,int cartid,String date,int credits){
       boolean ok=false;
       try {
           String sql="INSERT INTO cart_has_images(img_id,subimg_id,is_purchase,cart_id,date,credits)VALUES(?,?,?,?,?,?)";
           PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
           ps.setInt(1, imgid);
           ps.setInt(2, subimgid);
           ps.setInt(3, ispurchase);
           ps.setInt(4, cartid);
           ps.setString(5, date);
           ps.setInt(6, credits);
           
           int i=ps.executeUpdate();
           if(i>0){
               ok=true;
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return ok;
   }
   
   public boolean updateRatting(int chimid,int rate){
       boolean ok=false;
       try {
           String sql="UPDATE cart_has_images SET ratting=? WHERE cart_has_images_id=?";
           PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
           ps.setInt(1, rate);
           ps.setInt(2, chimid);
           int i=ps.executeUpdate();
           if(i>0){
               ok=true;
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return ok;
   }
   public boolean updateRatting(int chimid,double rate){
       boolean ok=false;
       try {
           String sql="UPDATE cart_has_images SET ratting=? WHERE cart_has_images_id=?";
           PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
           ps.setDouble(1, rate);
           ps.setInt(2, chimid);
           int i=ps.executeUpdate();
           if(i>0){
               ok=true;
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return ok;
   }
   
   public List<CartHasImages> getRattingList(int img_id){
   List<CartHasImages> li=new ArrayList<CartHasImages>();
   
       try {
           String sql="SELECT * FROM cart_has_images WHERE img_id=? AND ratting!='0' AND is_purchase='1'";
           PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
           ps.setInt(1, img_id);
           ResultSet rs=ps.executeQuery();
           while (rs.next()) {               
               CartHasImages c=new CartHasImages();
                c.setCart_has_images_id(rs.getInt(1));
                c.setCart_id(rs.getInt(5));
                c.setCredits(rs.getInt(7));
                c.setDate(rs.getDate(6));
                c.setImg_id(rs.getInt(2));
                c.setIs_purchase(rs.getInt(4));
                c.setSubimg_id(rs.getInt(3));
                c.setRatting(rs.getInt(8));
                System.out.println("adding...");
                li.add(c);
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return li;
   }
   
   public String getImageSellingRate(){
     StringBuffer sb=null;
     int days=0;
     int down=0;
     double avg=0.0;
        sb=new StringBuffer("{'imgselrate':{"); 
        sb.append("'name':'Image Selling rate',");
        sb.append("'rate':[");
        try {
           String sql="SELECT date,COUNT(cart_has_images_id) FROM cart_has_images WHERE is_purchase='1' OR is_purchase='3' GROUP BY date";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {   
                days+=1;
                down+=rs.getDouble(2);
                 if(rs.isLast()){
                    sb.append("{'date':'"+rs.getString(1)+"','download':'"+rs.getDouble(2)+"'}");
                }else{
                    sb.append("{'date':'"+rs.getString(1)+"','download':'"+rs.getDouble(2)+"'},");
                }
            }
            System.out.println("days count:"+days);
            System.out.println("download count:"+down);
            avg=down/days;
            System.out.println("avg:"+avg);
             sb.append("],");
            sb.append("'avg':'"+avg+"'"); 
            sb.append("}");
            sb.append("}");
       } catch (Exception e) {
           e.printStackTrace();
       }
        return sb.toString();
        
   }
   
   public String getSiseDownloads(){
       StringBuffer sb=null;
       CreditsDAOImp crdi=new CreditsDAOImp();
       Credits credits=null;
       sb=new StringBuffer("{'selldwn':{"); 
        sb.append("'name':'Size wise downloads',");
        sb.append("'rate':[");
        try {
           String sql="SELECT YEAR(date),MONTH(date) FROM cart_has_images WHERE is_purchase='1' OR is_purchase='3' GROUP BY YEAR(date) AND MONTH(date)";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {   
               
                 if(rs.isLast()){
                    sb.append("{'date':'"+rs.getString(1)+"-"+rs.getString(2)+"',");
                    List<Credits> li=crdi.getCreditList();
                     for (int i = 0; i < li.size(); i++) {
                         credits = li.get(i);
                         if(li.size()-i!=1){
                         sb.append("'"+credits.getSize()+"':'"+getCreditDownloadsCount(credits.getCredits(),rs.getString(1),rs.getString(2))+"',");
                         }else{
                         sb.append("'"+credits.getSize()+"':'"+getCreditDownloadsCount(credits.getCredits(),rs.getString(1),rs.getString(2))+"'");
                         }
                     }
                         
                     sb.append("}");    
                    
                    
                }else{
                    sb.append("{'date':'"+rs.getString(1)+"-"+rs.getString(2)+"',");
                    List<Credits> li=crdi.getCreditList();
                     for (int i = 0; i < li.size(); i++) {
                         credits = li.get(i);
                         if(li.size()-i!=1){
                         sb.append("'"+credits.getSize()+"':'"+getCreditDownloadsCount(credits.getCredits(),rs.getString(1),rs.getString(2))+"',");
                         }else{
                         sb.append("'"+credits.getSize()+"':'"+getCreditDownloadsCount(credits.getCredits(),rs.getString(1),rs.getString(2))+"'");
                         }
                     }
                         
                     sb.append("},");   
                }
            }
            
            
             sb.append("],");
            sb.append("'rate2':[");
            List<Credits> li=crdi.getCreditList();
            
                 for (int i = 0; i < li.size(); i++) {
                         credits = li.get(i);
                         if(li.size()-i!=1){
                         sb.append("{'size':'"+credits.getSize()+"'},");
                         }else{
                         sb.append("{'size':'"+credits.getSize()+"'}");
                         }
                     }
            
            sb.append("]");
            sb.append("}");
            sb.append("}");
       } catch (Exception e) {
           e.printStackTrace();
       }
        return sb.toString();
   }
   
   public int getCreditDownloadsCount(int credit,String year,String month){
       int i=0;
       try {
           String sql="SELECT COUNT(cart_has_images_id) FROM cart_has_images WHERE credits=? AND YEAR(date)=? AND MONTH(date)=?";
           PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
           ps.setInt(1, credit);
           ps.setString(2, year);
           ps.setString(3, month);
           ResultSet rs=ps.executeQuery();
           
           while (rs.next()) {               
               i=rs.getInt(1);
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return i;
   }
   
   public int allUserDownloadCount(int cid,int st){
   int dow=0;
       System.out.println("cid: "+cid);
       System.out.println("state: "+st);
       try {
           String sql="SELECT * FROM cart_has_images WHERE cart_id=? AND is_purchase=?";
           PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
           ps.setInt(1, cid);
           ps.setInt(2, st);
           ResultSet rs=ps.executeQuery();
           while (rs.next()) {               
               dow++;
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       System.out.println("down"+dow);
       return dow;
   }
   
}
