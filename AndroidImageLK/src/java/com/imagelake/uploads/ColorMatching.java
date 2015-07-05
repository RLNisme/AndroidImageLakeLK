/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.uploads;

/**
 *
 * @author Lakmal
 */
public class ColorMatching {
    public boolean matchColor(String DBColor,String NewColor){
        boolean ok=false;
        
        int r=0;
        int g=0;
        int b=0;
        String dbColorArray[]=DBColor.split(",");
        String newColorArray[]=NewColor.split(",");
        
        System.out.println(DBColor);
        System.out.println(NewColor);
        
        int dbR=Integer.parseInt(dbColorArray[0].toString());
        int dbG=Integer.parseInt(dbColorArray[1].toString());
        int dbB=Integer.parseInt(dbColorArray[2].toString());
        
        System.out.println("DB Color:-"+dbR+"."+dbG+"."+dbB);
        
        int newR=Integer.parseInt(newColorArray[0].toString());
        int newG=Integer.parseInt(newColorArray[1].toString());
        int newB=Integer.parseInt(newColorArray[2].toString());
        
        System.out.println("New Color:-"+newR+"."+newG+"."+newB);
        
        //Red----------------
        System.out.println("//Red----------------");
        for(int i=dbR ;i<dbR+5;i++){
            System.out.println("red"+i+"--"+newR);
           if(newR==i){
               r=1;
               break;
           }else{
               for(int j=dbR ;j>dbR-5;j--){
                System.out.println("red min"+j+"--"+newR);
                    if(newR==j){
                    r=1;
                    break;
                    }else{
                     r=0;
                    }
                }
           }
        }
        //Blue---------------
        System.out.println("//Blue---------------");
        for(int i=dbB ;i<dbB+5;i++){
            System.out.println("blue"+i+"--"+newB);
           if(newB==i){
               b=1;
               break;
           }else{
               for(int j=dbB ;j>dbB-5;j--){
                System.out.println("blue min"+j+"--"+newB);
                    if(newB==j){
                    b=1;
                    break;
                    }else{
                     b=0;
                    }
                }
           }
        }
        //Green-----------------
        System.out.println("//Green-----------------");
        for(int i=dbG ;i<dbG+5;i++){
            System.out.println("green"+i+"--"+newG);
           if(newG==i){
               g=1;
               break;
           }else{
               for(int j=dbG ;j>dbG-5;j--){
                System.out.println("green min"+j+"--"+newG);
                    if(newG==j){
                    g=1;
                    break;
                    }else{
                     g=0;
                    }
                }
           }
        }
        System.out.println(r+" "+g+" "+b);
        if(r==1 && g==1 && b==1){
            ok=true;
        }
        System.out.println("Color Matching :"+ok);
        return ok;
    }
}
