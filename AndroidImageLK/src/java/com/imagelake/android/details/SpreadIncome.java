/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.android.details;

import com.imagelake.control.AdminPackageIncomeDAOImp;
import com.imagelake.control.CreditUnitPriceDAOImp;
import com.imagelake.control.ImagesDAOImp;
import com.imagelake.control.PercentDAOImp;
import com.imagelake.control.SellerIncomeDAOImp;
import com.imagelake.control.SubscriptionUnitPriceDAOImp;
import com.imagelake.model.AdminPackageIncome;
import com.imagelake.model.CartHasImages;
import com.imagelake.model.CreditUnitPrice;
import com.imagelake.model.Images;
import com.imagelake.model.Percent;
import com.imagelake.model.SellerIncome;
import com.imagelake.model.SubscriptionUnitPrice;
import com.imagelake.model.UserHasPackage;

/**
 *
 * @author Lakmal
 */
public class SpreadIncome {
    public boolean income(UserHasPackage uhp,CartHasImages chi){
        boolean ok=false;
        SubscriptionUnitPrice sup=null;
        CreditUnitPrice cup=null;
        double subscriptUP;
        double adminPrice;
        double sellerPrice;
        
        double creditUP;
        double adminCredPrice;
        double sellerCredPrice;
                
        Images img=null;
        AdminPackageIncome apinc=null;
        SellerIncome selinc=null;
        Percent p=new PercentDAOImp().getPercentage();
        System.out.println("we are inside the spreadIncome class");
        System.out.println("checking type "+uhp.getPackage_type());
        if(uhp!=null && chi!=null){
            if(uhp.getPackage_type()==3){
                //sup=new SubscriptionUnitPriceDAOImp().getSubscriptionUnitPrice(1);
                subscriptUP=uhp.getUnit_price();
                System.out.println("<<<<<<<subscription unit price>>>>> "+subscriptUP);
                System.gc();
                
                adminPrice=(subscriptUP*p.getPercent())/100;
                System.out.println("<<<<<<<admin price>>>>>>> "+adminPrice);
                System.gc();
                
                sellerPrice=subscriptUP-adminPrice;
                System.out.println("<<<<<<Seller price>>>>> "+sellerPrice);
                System.gc();
                
                apinc=new AdminPackageIncomeDAOImp().getAdminPackageIncome(uhp.getUhp_id());
                System.out.println("getting admin income object");
                System.gc();
                
                double newAdminTotal=(apinc.getTotal()-sellerPrice);
                
                
                img=new ImagesDAOImp().getImageDetail(chi.getImg_id());
                System.out.println("getting image object");
                System.gc();
                 
                selinc=new SellerIncomeDAOImp().getSellerIncome(img.getUser_user_id());
                System.gc();
                
                double newSellerTotal=(selinc.getTotal()+sellerPrice);
                
                AdminPackageIncome addpinc=new AdminPackageIncome();
                addpinc.setUhp_id(uhp.getUhp_id());
                addpinc.setTotal(newAdminTotal);
                System.gc();
                
                SellerIncome sellinc=new SellerIncome();
                sellinc.setUser_id(img.getUser_user_id());
                sellinc.setTotal(newSellerTotal);
                System.gc();
                
                boolean admin=new AdminPackageIncomeDAOImp().updateAdminincomeTotal(addpinc);
                boolean selin=new SellerIncomeDAOImp().updateSellerIncome(sellinc);
                
                if(admin && selin){
                    ok=true;
                }
            }else if(uhp.getPackage_type()==4){
                //cup=new CreditUnitPriceDAOImp().getUnitPriceForCredit(1);
                System.gc();
                creditUP=uhp.getUnit_price();
                
                adminCredPrice=(creditUP*chi.getCredits()*p.getPercent())/100;
                System.out.println("<<<<<<<<Admin credit price>>>>>> "+adminCredPrice);
                
                sellerCredPrice=creditUP*chi.getCredits()-adminCredPrice;
                System.out.println("<<<<<<<Seller Credit package>>>>>> "+sellerCredPrice);
                
                apinc=new AdminPackageIncomeDAOImp().getAdminPackageIncome(uhp.getUhp_id());
                System.out.println("getting admin income object");
                System.gc();
                
                double newAdminTotal=(apinc.getTotal()-sellerCredPrice);
                System.out.println("new Admin total:-"+newAdminTotal);
                
                img=new ImagesDAOImp().getImageDetail(chi.getImg_id());
                System.out.println("getting image object");
                System.out.println("image id "+img.getImages_id());
                System.gc();
                 
                selinc=new SellerIncomeDAOImp().getSellerIncome(img.getUser_user_id());
                System.gc();
                
                double newSellerTotal=(selinc.getTotal()+sellerCredPrice);
                System.out.println("new Sellet total:-"+newSellerTotal);
                
                AdminPackageIncome addpinc=new AdminPackageIncome();
                addpinc.setUhp_id(uhp.getUhp_id());
                addpinc.setTotal(newAdminTotal);
                System.gc();
                
                SellerIncome sellinc=new SellerIncome();
                sellinc.setUser_id(img.getUser_user_id());
                sellinc.setTotal(newSellerTotal);
                System.gc();
                
                boolean admin=new AdminPackageIncomeDAOImp().updateAdminincomeTotal(addpinc);
                boolean selin=new SellerIncomeDAOImp().updateSellerIncome(sellinc);
                System.out.println("+++++++++++++++++++++==admin"+admin);
                System.out.println("+++++++++++++++++++++==seller"+selin);
                if(admin && selin){
                    ok=true;
                }
                
            }
        }
        return ok;
    }
}
