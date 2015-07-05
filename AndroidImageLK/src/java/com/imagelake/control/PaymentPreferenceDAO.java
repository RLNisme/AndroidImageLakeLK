/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.PaymentPreferences;
import java.util.List;
import org.json.simple.JSONArray;

/**
 *
 * @author Lakmal
 */
public interface PaymentPreferenceDAO {
    public List<PaymentPreferences> getUserEarningHistory(int user_id);
    public PaymentPreferences getPendingEarning(int uid,int state);
    public boolean AddPaymentPreference(PaymentPreferences pp);
    public List<PaymentPreferences> getUserEarnedHistory(int uid,int state);
    public String getJSONPaymentPreferances(int state);
    public JSONArray getPayments(int state);
    public boolean requestSettle(int prid,int state,int admid,String date);
}
