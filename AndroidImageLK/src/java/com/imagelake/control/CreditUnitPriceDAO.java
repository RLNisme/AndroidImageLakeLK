/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.CreditUnitPrice;

/**
 *
 * @author Lakmal
 */
public interface CreditUnitPriceDAO {
    public CreditUnitPrice getUnitPriceForCredit(int credit_unit_price_id);
}
