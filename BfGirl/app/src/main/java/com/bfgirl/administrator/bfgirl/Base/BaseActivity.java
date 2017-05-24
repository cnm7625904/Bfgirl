package com.bfgirl.administrator.bfgirl.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 王玺权 on 2017/5/10.
 * 抽象函数，基类
 */

public abstract class BaseActivity extends RxAppCompatActivity
{
    private Unbinder unbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置布局
        setContentView(getLayoutId());
        //初始化黄油刀
        unbinder= ButterKnife.bind(this);
        //初始化控件
        initViews(savedInstanceState);
        initToolbar();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public abstract int getLayoutId();
    public abstract void initViews(Bundle savedInstanceState);
    public abstract void initToolbar();
}
