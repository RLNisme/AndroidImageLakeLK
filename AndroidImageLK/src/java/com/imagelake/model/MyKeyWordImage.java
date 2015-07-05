/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public class MyKeyWordImage {
    private String keywprd;
    private List<KeyWords> list=null;

    public MyKeyWordImage(){
        list=new ArrayList<KeyWords>();
    }
    
    public String getKeywprd() {
        return keywprd;
    }

    public void setKeywprd(String keywprd) {
        this.keywprd = keywprd;
    }

    public List<KeyWords> getList() {
        return list;
    }

    public void setList(List<KeyWords> list) {
        this.list = list;
    }
}
