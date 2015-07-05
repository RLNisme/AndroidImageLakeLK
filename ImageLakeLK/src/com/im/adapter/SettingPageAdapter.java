package com.im.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.im.fragments.ImageSettingFragment;
import com.im.fragments.PackageSettingFragment;
import com.im.fragments.PrivilageSettingFragment;
import com.im.fragments.UserSettingFragment;

/**
 * Created by RLN on 5/4/2015.
 */
public class SettingPageAdapter extends FragmentPagerAdapter {

    public SettingPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new UserSettingFragment();
            case 1:
                return new PackageSettingFragment();
            case 2:
                return new PrivilageSettingFragment();
            case 3:
                return new ImageSettingFragment();
            default:
                break;

        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
