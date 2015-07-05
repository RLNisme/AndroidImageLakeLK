/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.model;

/**
 *
 * @author Lakmal
 */
public class PaymentPreferences {
    private int ppid;
    private int user_id;
    private String date;
    private String conf_date;
    private int acc_type;
    private int adm_id;
    private double amount;
    private int state;
    private String email;

    public int getPpid() {
        return ppid;
    }

    public void setPpid(int ppid) {
        this.ppid = ppid;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getConf_date() {
        return conf_date;
    }

    public void setConf_date(String conf_date) {
        this.conf_date = conf_date;
    }

    public int getAcc_type() {
        return acc_type;
    }

    public void setAcc_type(int acc_type) {
        this.acc_type = acc_type;
    }

    public int getAdm_id() {
        return adm_id;
    }

    public void setAdm_id(int adm_id) {
        this.adm_id = adm_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
