/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.SellerIncome;

/**
 *
 * @author Lakmal
 */
public interface SellerIncomeDAO {
    public boolean setIncome(int user_id);
    public SellerIncome getSellerIncome(int user_id);
    public boolean updateSellerIncome(SellerIncome sinc);
}
