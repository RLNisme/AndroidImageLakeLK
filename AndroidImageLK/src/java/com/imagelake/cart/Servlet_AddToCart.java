/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.cart;

import com.imagelake.control.CartDAOImp;
import com.imagelake.control.CreditsDAOImp;
import com.imagelake.control.ImagesDAOImp;
import com.imagelake.model.Cart;
import com.imagelake.model.CartHasImages;
import com.imagelake.model.CartItems;
import com.imagelake.model.Images;
import com.imagelake.model.ImagesSub;
import com.imagelake.model.User;

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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Lakmal
 */
public class Servlet_AddToCart extends HttpServlet {

   protected void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
       PrintWriter out=response.getWriter();
       String imgid="";
       try{
       Date d=new Date();
       SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
       String date=sf.format(d);
       
       imgid=request.getParameter("imgid");
       String subid=request.getParameter("subsubid");
       String cre=request.getParameter("cre"+subid);
       
       System.out.println(imgid);
       System.out.println(subid);
       System.out.println(cre);
       HttpSession ses=request.getSession();
       
       ImagesDAOImp idip=new ImagesDAOImp();
       
       Images img=idip.getImageDetail(Integer.parseInt(imgid));
           
       if(img!=null){
       int i=0;
       int subimg_Id=Integer.parseInt(subid);
       ImagesSub isu=idip.getSubImage(subimg_Id);
       
       if(isu!=null){
           
       CreditsDAOImp cdi=new CreditsDAOImp();
       
       int credits=cdi.getCredit(isu.getCredits());
       
//       List<ImagesSub> subList=new ImagesDAOImp().getImagedescription(Integer.parseInt(imgid));
//       for (ImagesSub imagesSub : subList) {
//           if(i==0){
//               
//               subimg_Id=imagesSub.getSub_images_id();
//               System.out.println("subimg_Id :"+subimg_Id);
//               i++;
//               break;
//           }
//       }
       
       UnsignInCart unscart=null;
       
       CartItems ci=null;
       
       int cartCount=0;
       
       if(ses.getAttribute("user")==null){
           System.out.println("Not signIn user");
           
           if(ses.getAttribute("cart_Un")==null){
               System.out.println("cart_Un is null");
               unscart=new UnsignInCart();
               ci=new CartItems();
               ci.setImgId(Integer.parseInt(imgid));
               ci.setSubimg_Id(subimg_Id);
               ci.setCredit(credits);
               ci.setDate(date);
               
               System.out.println("--------------------------------------------null");
               cartCount++;
               System.gc();
           }else{
               System.out.println("cart_Un is not null");
               
               unscart=(UnsignInCart) ses.getAttribute("cart_Un");
               System.out.println("--------------------------------------------not null");
               ci=new CartItems();
               ci.setImgId(Integer.parseInt(imgid));
               ci.setSubimg_Id(subimg_Id);
               ci.setCredit(credits);
               ci.setDate(date);
               
               
               System.gc();
               
           }
           
           unscart.addImages(ci);
           ses.setAttribute("cart_Un", unscart);
           
            List<CartItems> clist=unscart.getUnSignCart();
               System.out.println("Cart Item list is empty:"+clist.isEmpty());
               int total=0;
               for (CartItems cartItems : clist) {
                  total++;
                  System.gc();
                  
               }
                 cartCount=total;
       }else{
           //if user signin
          System.out.println("signIn user");
//          
          User u=(User) ses.getAttribute("user");
          List<CartItems> citems;
          
           Cart b=new CartDAOImp().getCart(u.getUser_id());
//           
//           System.out.println("cart :"+b.getCart_id());
//           System.out.println("cart is null"+b==null);
//           
          if(b.getCart_id()==0){
              
          
               //if user didn't have cart in the database
               System.out.println("don't have a cart");
               Cart c=new Cart();
               c.setUser_id(u.getUser_id());
               c.setCredit_count(0);
               c.setCredit_unit_price_id(1);
               c.setImage_count(0);
               c.setSubscription_unit_price_id(1);
               
              //creating a new cart
               
               boolean bb=new CartDAOImp().setACart(c);
               System.out.println("created a new cart"+bb);
               System.gc();
               
               if(bb){
               //getting that new cart 
                   
               b=new CartDAOImp().getCart(u.getUser_id());
//                   System.out.println("has a cart name after user"+b);
//                   System.out.println("Cart ID in servlet:"+b.getCart_id());
//                   System.out.println("Cart User ID in servlet:"+b.getUser_id());
//                   
               unscart=(UnsignInCart) ses.getAttribute("cart_Un");   
               System.out.println("session cart:"+unscart==null);
               
               if(unscart!=null){
                   
                 int imgcount=0;
                 int creditcount=0;
                  citems=unscart.getUnSignCart();
                  System.out.println("is Empty "+citems.isEmpty());
                  
                   if(!citems.isEmpty()){
               //if session have some CartItems add it to the DB
                       for (CartItems cartItems : citems) {
                           System.gc();
                           boolean kk=idip.checkOwner(u.getUser_id(),cartItems.getImgId());
                          // boolean alreadyPur=new CartDAOImp().checkAlreadyPurchase(b.getCart_id(),cartItems.getImgId());
                           if(kk /*|| alreadyPur*/){
                               continue;
                           }else{
                               imgcount++;
                               creditcount+=cartItems.getCredit();
                           }
                           
                       }
                       boolean update=new CartDAOImp().updateCartQty(imgcount, creditcount,b.getCart_id());
                       System.out.println("updatiing:"+update);
                       boolean ok=new CartDAOImp().addImagesToCart(citems, b);
                       
                   System.out.println("add to DB:"+ok);
                   
                  CartItems cartI=new CartItems();
                  cartI.setImgId(Integer.parseInt(imgid));
                  cartI.setCredit(credits);
                  cartI.setSubimg_Id(subimg_Id);
                  cartI.setDate(date);
                  cartI.setCredit(credits);
                  
                  boolean kk=idip.checkOwner(u.getUser_id(),cartI.getImgId());
                  if(kk){
                      System.out.println("this is your images");
                  }else{
                    //  boolean alreadyPur=new CartDAOImp().checkAlreadyPurchase(b.getCart_id(),cartI.getImgId());
                    // if(!alreadyPur){ 
                        boolean duplicat=new CartDAOImp().checkImageDuplication(b.getCart_id(),cartI.getImgId());
                      if(duplicat){

                          boolean updated=new CartDAOImp().updateCartHasImagesSubImageID(b.getCart_id(),cartI.getImgId(),cartI.getSubimg_Id(),cartI.getCredit());
                      }else{
                            System.out.println("addingCarItems");
                          boolean o=new CartDAOImp().addingCarItems(cartI, b);
                          System.gc();

                      }
                    // }
                  }
                  
                     
//                      
                   }else{
                       System.out.println("else");
                       CartItems cartI=new CartItems();
                       cartI.setImgId(Integer.parseInt(imgid));
                       cartI.setSubimg_Id(subimg_Id);
                       cartI.setCredit(credits);
                       cartI.setDate(date);
                       
                     boolean owner=idip.checkOwner(u.getUser_id(), Integer.parseInt(imgid));
                     if(owner){
                         
                         request.setAttribute("error", "This is your image");
                        getServletContext().getRequestDispatcher("/description.jsp?image_id="+imgid).forward(
                        request, response);
                        
                     }else{
                        //  boolean alreadyPur=new CartDAOImp().checkAlreadyPurchase(b.getCart_id(),cartI.getImgId());
                         //   if(!alreadyPur){ 
                                   boolean duplicat=new CartDAOImp().checkImageDuplication(b.getCart_id(),cartI.getImgId());
                                   if(duplicat){
                                       boolean updated=new CartDAOImp().updateCartHasImagesSubImageID(b.getCart_id(),cartI.getImgId(),cartI.getSubimg_Id(),cartI.getCredit());
                                   }else{
                                         System.out.println("addingCarItems");
                                       boolean o=new CartDAOImp().addingCarItems(cartI, b);
                                       System.gc();

                                  }
                           /* }else{
                                 request.setAttribute("error", "You have already purchase this image");
                               getServletContext().getRequestDispatcher("/description.jsp?image_id="+imgid).forward(
                               request, response);
                            }*/
                     }  
                   
//                   
                   }
                  unscart=new UnsignInCart();
                   ses.setAttribute("cart_Un", unscart);
               }else{
                     CartItems cartI=new CartItems();
                       cartI.setImgId(Integer.parseInt(imgid));
                       cartI.setSubimg_Id(subimg_Id);
                       cartI.setCredit(credits);
                       cartI.setDate(date);
                       
                   boolean owner=idip.checkOwner(u.getUser_id(), Integer.parseInt(imgid));
                     if(owner){
                          request.setAttribute("error", "This is your image");
                        getServletContext().getRequestDispatcher("/description.jsp?image_id="+imgid).forward(
                        request, response);
                        
                     }else{    
                      //  boolean alreadyPur=new CartDAOImp().checkAlreadyPurchase(b.getCart_id(),cartI.getImgId());
                     //if(!alreadyPur){ 
                            boolean duplicat=new CartDAOImp().checkImageDuplication(b.getCart_id(),cartI.getImgId());
                            if(duplicat){
                                boolean updated=new CartDAOImp().updateCartHasImagesSubImageID(b.getCart_id(),cartI.getImgId(),cartI.getSubimg_Id(),cartI.getCredit());
                            }else{
                                  System.out.println("addingCarItems");
                                boolean o=new CartDAOImp().addingCarItems(cartI, b);
                                System.gc();

                            }
                       /* }else{
                             request.setAttribute("error", "You have already purchase this image");
                               getServletContext().getRequestDispatcher("/description.jsp?image_id="+imgid).forward(
                               request, response);
                        }*/
                     }
              }
//               
//               
//               
              }     
               System.gc();
           }else{
              //if user have a cart in the database
              
             System.out.println("this user have a car in the DB");
               b=new CartDAOImp().getCart(u.getUser_id());
               unscart=(UnsignInCart) ses.getAttribute("cart_Un"); 
              List<CartHasImages> ch=null;
               
               int imgcount=0;
               int creditcount=0;
               
               if(unscart!=null){
                   System.out.println("is is is is is is is is -------------------------------");
                  citems=unscart.getUnSignCart();
                  
                   if(!citems.isEmpty()){
                        //if session have some CartItems add it to the DB
                       
                       
                       boolean ok=new CartDAOImp().addImagesToCart(citems, b);
                       System.out.println("else add to DB:"+ok);
//                        
                        CartItems cartI=new CartItems();
                        cartI.setImgId(Integer.parseInt(imgid));
                        cartI.setSubimg_Id(subimg_Id);
                        cartI.setCredit(credits);
                        cartI.setDate(date);
                        
                  boolean owner=idip.checkOwner(u.getUser_id(), Integer.parseInt(imgid));
                     if(owner){
                         
                         request.setAttribute("error", "This is your image");
                        getServletContext().getRequestDispatcher("/description.jsp?image_id="+imgid).forward(
                        request, response);
                     }else{      
                      //   boolean alreadyPur=new CartDAOImp().checkAlreadyPurchase(b.getCart_id(),cartI.getImgId());
                    // if(!alreadyPur){ 
                                boolean duplicat=new CartDAOImp().checkImageDuplication(b.getCart_id(),cartI.getImgId());
                                if(duplicat){
                                    boolean updated=new CartDAOImp().updateCartHasImagesSubImageID(b.getCart_id(),cartI.getImgId(),cartI.getSubimg_Id(),cartI.getCredit());
                                }else{
                                      System.out.println("addingCarItems");
                                    boolean o=new CartDAOImp().addingCarItems(cartI, b);
                                    System.gc();

                                }
                       /* }else{
                             request.setAttribute("error", "You have already purchase this image");
                               getServletContext().getRequestDispatcher("/description.jsp?image_id="+imgid).forward(
                               request, response);
                        }*/
                     }
                    }else{
//                        
                       CartItems cartI=new CartItems();
                       cartI.setImgId(Integer.parseInt(imgid));
                       cartI.setSubimg_Id(subimg_Id);
                       cartI.setCredit(credits);
                       cartI.setDate(date);
                       
                  boolean owner=idip.checkOwner(u.getUser_id(), Integer.parseInt(imgid));
                     if(owner){
                          request.setAttribute("error", "This is your image");
                               getServletContext().getRequestDispatcher("/description.jsp?image_id="+imgid).forward(
                               request, response);
                     }else{     
                    //    boolean alreadyPur=new CartDAOImp().checkAlreadyPurchase(b.getCart_id(),cartI.getImgId());
                    // if(!alreadyPur){ 
                                boolean duplicat=new CartDAOImp().checkImageDuplication(b.getCart_id(),cartI.getImgId());
                                if(duplicat){
                                    boolean updated=new CartDAOImp().updateCartHasImagesSubImageID(b.getCart_id(),cartI.getImgId(),cartI.getSubimg_Id(),cartI.getCredit());
                                }else{
                                      System.out.println("addingCarItems");
                                    boolean o=new CartDAOImp().addingCarItems(cartI, b);
                                    System.gc();

                                }
                      /* }else{
                             request.setAttribute("error", "You have already purchase this image");
                               getServletContext().getRequestDispatcher("/description.jsp?image_id="+imgid).forward(
                               request, response);
                        }*/
                     }  
                       ch=new CartDAOImp().listCartHasImages();
                       for (CartHasImages carhi : ch) {
                           if(carhi.getCart_id()==b.getCart_id() && carhi.getIs_purchase()==0){
                               imgcount++;
                           creditcount+=carhi.getCredits();
                            System.out.println("-----------------totalcount------------------------"+imgcount);
                            System.out.println("-----------------credits------------------------"+creditcount);
                           }
                       }
                       boolean update=new CartDAOImp().updateCartQty(imgcount, creditcount,b.getCart_id());
//                        
                   }
                   unscart=new UnsignInCart();
                  ses.setAttribute("cart_Un", unscart);
            }else{
//               
                        CartItems cartI=new CartItems();
                       cartI.setImgId(Integer.parseInt(imgid));
                       cartI.setSubimg_Id(subimg_Id);
                       cartI.setCredit(credits);
                       cartI.setDate(date);
                      
                   boolean owner=idip.checkOwner(u.getUser_id(), Integer.parseInt(imgid));
                     if(owner){
                                request.setAttribute("error", "This is your image");
                               getServletContext().getRequestDispatcher("/description.jsp?image_id="+imgid).forward(
                               request, response);
                     }else{    
                    //    boolean alreadyPur=new CartDAOImp().checkAlreadyPurchase(b.getCart_id(),cartI.getImgId());
                    // if(!alreadyPur){ 
                                boolean duplicat=new CartDAOImp().checkImageDuplication(b.getCart_id(),cartI.getImgId());
                                if(duplicat){
                                    boolean updated=new CartDAOImp().updateCartHasImagesSubImageID(b.getCart_id(),cartI.getImgId(),cartI.getSubimg_Id(),cartI.getCredit());
                                }else{
                                      System.out.println("addingCarItems");
                                    boolean o=new CartDAOImp().addingCarItems(cartI, b);
                                    System.gc();

                                }
                     /* }else{
                             request.setAttribute("error", "You have already purchase this image");
                               getServletContext().getRequestDispatcher("/description.jsp?image_id="+imgid).forward(
                               request, response);
                        }*/
                     
                     }    
                       ch=new CartDAOImp().listCartHasImages();
                       for (CartHasImages carhi : ch) {
                          
                           if(carhi.getCart_id()==b.getCart_id() && carhi.getIs_purchase()==0){
                               imgcount++;
                           creditcount+=carhi.getCredits();
                            System.out.println("-----------------totalcount------------------------"+imgcount);
                            System.out.println("-----------------credits------------------------"+creditcount);
                           }
                           
                       }
                       boolean update=new CartDAOImp().updateCartQty(imgcount, creditcount,b.getCart_id());
                       
              } 
//               
               System.gc();
          }
       }
       
       System.out.println("near end");
       
       
               
                 System.gc();
      response.sendRedirect("description.jsp?image_id="+imgid);
                
                }else{
                    
                 request.setAttribute("error", "Error");
                               getServletContext().getRequestDispatcher("/description.jsp?image_id="+imgid).forward(
                               request, response);   
           
                }
                
      
           }else{
           response.sendRedirect("description.jsp?image_id="+imgid);
           }
       }catch(Exception e){
           e.printStackTrace();
            request.setAttribute("error", "Error");
                               getServletContext().getRequestDispatcher("/description.jsp?image_id="+imgid).forward(
                               request, response);
       }
   }
}
