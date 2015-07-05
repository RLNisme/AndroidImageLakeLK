/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.model;

import java.util.Date;

/**
 *
 * @author Lakmal
 */
public class User {
    private int user_id;
    private String user_name=null;
    private String first_name=null;
    private String last_name=null;
    private String password=null;
    private String email=null;
    private String street_add_1=null;
    private String street_add_2=null;
    private String city=null;
    private String state_province=null;    
    private String zip_postal_code=null;
    private String phone=null;
    private String fax=null;
    private String com_name=null;
    private String website=null;
    private String com_phone=null;
    private String com_fax=null;
    private String date=null;
    private int state;
    private int billing_country;
    private int user_type;
    private int current_country_id;
    

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet_add_1() {
        return street_add_1;
    }

    public void setStreet_add_1(String street_add_1) {
        this.street_add_1 = street_add_1;
    }

    public String getStreet_add_2() {
        return street_add_2;
    }

    public void setStreet_add_2(String street_add_2) {
        this.street_add_2 = street_add_2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState_province() {
        return state_province;
    }

    public void setState_province(String state_province) {
        this.state_province = state_province;
    }

    public int getBilling_country() {
        return billing_country;
    }

    public void setBilling_country(int billing_country) {
        this.billing_country = billing_country;
    }

    public String getZip_postal_code() {
        return zip_postal_code;
    }

    public void setZip_postal_code(String zip_postal_code) {
        this.zip_postal_code = zip_postal_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCom_name() {
        return com_name;
    }

    public void setCom_name(String com_name) {
        this.com_name = com_name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCom_phone() {
        return com_phone;
    }

    public void setCom_phone(String com_phone) {
        this.com_phone = com_phone;
    }

    public String getCom_fax() {
        return com_fax;
    }

    public void setCom_fax(String com_fax) {
        this.com_fax = com_fax;
    }

    

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCurrent_country_id() {
        return current_country_id;
    }

    public void setCurrent_country_id(int current_country_id) {
        this.current_country_id = current_country_id;
    }

    
}
