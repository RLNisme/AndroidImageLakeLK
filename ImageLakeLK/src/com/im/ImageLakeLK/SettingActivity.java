package com.im.ImageLakeLK;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.im.adapter.SettingPageAdapter;


/**
 * Created by RLN on 5/4/2015.
 */
public class SettingActivity extends FragmentActivity implements ActionBar.TabListener{

    Intent i;
    ActionBar actionBar;
    ViewPager viewPager;
    SettingPageAdapter sa;
    public int user_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);

        i =getIntent();
        user_id = i.getIntExtra("id",-1);

        actionBar = getActionBar();
        sa = new SettingPageAdapter(getSupportFragmentManager());
        viewPager = (ViewPager)findViewById(R.id.set_pager);
        viewPager.setAdapter(sa);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(actionBar.newTab().setText("User Setting").setTabListener(this));

        actionBar.addTab(actionBar.newTab().setText("Package Setting").setTabListener(this));

        actionBar.addTab(actionBar.newTab().setText("Privilage Setting").setTabListener(this));

        actionBar.addTab(actionBar.newTab().setText("Image Setting").setTabListener(this));

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){


            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                actionBar.setSelectedNavigationItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });



    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

}
