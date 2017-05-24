package com.bfgirl.administrator.bfgirl.Main;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.bfgirl.administrator.bfgirl.Base.BaseActivity;
import com.bfgirl.administrator.bfgirl.Others.MyViewPagerAdapter;
import com.bfgirl.administrator.bfgirl.R;

import butterknife.BindView;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;

/**
 * Created by tckj03 on 2017/5/23.
 */

public class BottomPage extends BaseActivity
{
    @BindView(R.id.tab)
    PageBottomTabLayout tab;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @Override
    public int getLayoutId() {
        return R.layout.bottompage;
    }
    @Override
    public void initViews(Bundle savedInstanceState)
    {
        viewPager.setOffscreenPageLimit(3);
        NavigationController navigationController = tab.custom()
                .addItem(newItem(R.drawable.ic_airline_seat_flat_angled_gray_24dp,R.drawable.ic_airline_seat_flat_angled_till_24dp,"小清新"))
                .addItem(newItem(R.drawable.ic_airline_seat_flat_gray_24dp,R.drawable.ic_airline_seat_flat_till_24dp,"大长腿"))
                .addItem(newItem(R.drawable.ic_airline_seat_individual_suite_gray_24dp,R.drawable.ic_airline_seat_individual_suite_till_24dp,"文艺范"))
                .build();
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(),navigationController.getItemCount()));
        //自动适配ViewPager页面切换
        navigationController.setupWithViewPager(viewPager);
    }
    //创建一个Item
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text){
        NormalItemView normalItemView = new NormalItemView(this);
        normalItemView.initialize(drawable,checkedDrawable,text);
        normalItemView.setTextDefaultColor(Color.GRAY);
        normalItemView.setTextCheckedColor(0xFF009688);
        return normalItemView;
    }
    @Override
    public void initToolbar() {

    }
}
