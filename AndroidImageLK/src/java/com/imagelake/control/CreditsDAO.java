/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.Credits;
import com.imagelake.model.SliceImage;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public interface CreditsDAO {
    public Credits getCreditDetails(int credit_id);
    public List<Credits> getCreditList();
    public List<SliceImage> getTypeSliceList(int type,int state);
    public boolean updateCredits(Credits c);
    public boolean insertCredits(Credits c);
    public int getCredit(int subid);
}
