package com.bfgirl.administrator.bfgirl.Others;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bfgirl.administrator.bfgirl.Fragments.WenyiFragment;
import com.bfgirl.administrator.bfgirl.Fragments.GankFragment;
import com.bfgirl.administrator.bfgirl.Fragments.HuaBanFragment;
public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private int size;
    private GankFragment gankFragment;
    private HuaBanFragment huaBanFragment;
    private WenyiFragment wenyiFragment;
    public MyViewPagerAdapter(FragmentManager fm, int size) {
        super(fm);
        this.size = size;
    }
    @Override
    public Fragment getItem(int position) {

        if(position==0)
        {
            if(gankFragment==null)
            {
                gankFragment= new GankFragment();
            }
            return GankFragment.getInstance();
        }
        else if(position==1)
        {
            if(huaBanFragment==null)
            {
                huaBanFragment= new HuaBanFragment();
            }
            return HuaBanFragment.getInstance();
        }
        else if(position==2)
        {
            if(wenyiFragment==null)
            {
                wenyiFragment= new WenyiFragment();
            }
            return WenyiFragment.getInstance();
        }
        return  null;
    }

    @Override
    public int getCount() {
        return size;
    }
}
