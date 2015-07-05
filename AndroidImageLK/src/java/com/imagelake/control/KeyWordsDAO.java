/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.KeyWords;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public interface KeyWordsDAO {
    public String getAllKeyWords();
    public boolean addKeyWords(int images_images_id,String keyword);
    public boolean getKeyWords(String keyword,int images_images_id);
    public List<KeyWords> getkeyWordList(int images_images_id);
    public boolean removeKeyWord(int key_word_id);
    public List<KeyWords> listKeyWords(String key);
}
