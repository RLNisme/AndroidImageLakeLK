/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.android.search;

import com.imagelake.model.Images;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public class SearchResults {
    
    private String category=null;
    private String keyWord=null;
    private String color=null;
    private int creditFrom=0;
    private int creditTo=0;
    private int sellerId=0;
    private String size=null;
    
    private List<Images> imList=null;
    
    public SearchResults(){
        imList=new ArrayList<Images>();
    }

    public List<Images> getImList() {
        return imList;
    }

    public void setImList(List<Images> imList) {
        this.imList = imList;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

   
    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getCreditFrom() {
        return creditFrom;
    }

    public void setCreditFrom(int creditFrom) {
        this.creditFrom = creditFrom;
    }

    public int getCreditTo() {
        return creditTo;
    }

    public void setCreditTo(int creditTo) {
        this.creditTo = creditTo;
    }
}
