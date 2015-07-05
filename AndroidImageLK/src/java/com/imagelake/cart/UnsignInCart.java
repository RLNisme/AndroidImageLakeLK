/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.cart;

import com.imagelake.model.CartItems;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public class UnsignInCart {

    private List<CartItems> unSignCart;

    public UnsignInCart() {

        unSignCart = new ArrayList<CartItems>();
    }

    public void addImages(CartItems ci) {

        if (unSignCart.size() == 0) {
            System.out.println("-------------------------------cart size is empty");
            unSignCart.add(ci);
            System.out.println("-------------------------------size()=0 an add one");
        } else {

            System.out.println("cart item ci" + ci.getImgId());
            for (int i = 0; i < unSignCart.size(); i++) {
                
                CartItems cartItems = unSignCart.get(i);
                System.out.println("-------------------------------cart item image ID"+cartItems.getImgId());
                if(cartItems.getImgId()==ci.getImgId()){
                    System.out.println("-------------------------------this item is already in the cart");
                    System.out.println("-------------------------------removing...");
                    unSignCart.remove(cartItems);
                    System.out.println("-------------------------------adding to cart");
                    
                }
            }
            System.out.println("-------------------------------this item is New");
               System.out.println("-------------------------------adding to cart");
                unSignCart.add(ci);

        }

    }
    
    public boolean removeImage(int id){
        boolean ok=false;
        
        for (int i = 0; i < unSignCart.size(); i++) {
            CartItems cartItems = unSignCart.get(i);
            System.gc();
            System.out.println("}}}}}}}}}}}}}}}}}}}}}}}}}}}}}"+cartItems.getImgId());
            
            if(cartItems.getImgId()==id){
                System.out.println("}}}}}}}}}}}}}}}}}}}}}}}}}}}}}"+id);
                unSignCart.remove(cartItems);
                ok=true;
                break;
            }
        }
        
        return ok;
    }

    public List<CartItems> getUnSignCart() {
        return unSignCart;
    }

    public void setUnSignCart(List<CartItems> unSignCart) {
        this.unSignCart = unSignCart;
    }
}
