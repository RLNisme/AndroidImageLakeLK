/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.PaymentAccount;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public interface PaymentAccountDAO {
    public String getPaymetAccountName(int acid);
    public List<PaymentAccount> getpaymentAccounts();
}
