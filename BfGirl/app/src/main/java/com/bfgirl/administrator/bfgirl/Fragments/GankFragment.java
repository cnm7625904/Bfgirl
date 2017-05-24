package com.bfgirl.administrator.bfgirl.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bfgirl.administrator.bfgirl.Adapter.GankAdapter;
import com.bfgirl.administrator.bfgirl.Adapter.helper.AbsRecyclerViewAdapter;
import com.bfgirl.administrator.bfgirl.Main.DetailActivity;
import com.bfgirl.administrator.bfgirl.Models.BaseModels;
import com.bfgirl.administrator.bfgirl.R;
import com.bfgirl.administrator.bfgirl.Utils.JSONHelper;
import com.bfgirl.administrator.bfgirl.Utils.SnackbarUtil;
import com.bfgirl.administrator.bfgirl.Utils.SpacesItemDecoration;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


/**
 * GANKfragment
 */
@SuppressLint("ValidFragment")
public class GankFragment extends com.trello.rxlifecycle.components.support.RxFragment {

    private TwinklingRefreshLayout refreshLayout;
    private boolean mIsLoadMore = true;
    private StaggeredGridLayoutManager linearLayoutManager;
    private int num=1;
    private List<String> mDatas;
    private GankAdapter mAdapter;
    private RecyclerView rv;

    public static GankFragment getInstance() {

        GankFragment sf = new GankFragment();

        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.gankfragment, null,false);

        initViews(v);
        return v;
    }



    private void initViews(View v) {
        refreshLayout= (TwinklingRefreshLayout) v.findViewById(R.id.tw);
        mDatas=new ArrayList<>();
        rv= (RecyclerView) v.findViewById(R.id.rv);
        mAdapter=new GankAdapter(rv);

        linearLayoutManager=new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);

        rv.setLayoutManager(linearLayoutManager);

        rv.setAdapter(mAdapter);

//        SpacesItemDecoration decoration=new SpacesItemDecoration(24);
//        rv.addItemDecoration(decoration);

        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
//                Toast.makeText(MainActivity.this,"当前是第"+String.valueOf(position)+"个"+mDatas.get(position).toString(),Toast.LENGTH_SHORT).show();
                ActivityOptionsCompat aop=ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        holder.getParentView().findViewById(R.id.img),"123");

                String Img_url=mDatas.get(position).toString();//传递图片地址

                Intent intent=new Intent(getActivity(),DetailActivity.class);
                intent.putExtra("Img_url", Img_url);

                ActivityCompat.startActivity(getActivity(),intent,aop.toBundle());
            }
        });

        ProgressLayout header = new ProgressLayout(getActivity());
        refreshLayout.setHeaderView(header);
        refreshLayout.setFloatRefresh(true);
        refreshLayout.setOverScrollRefreshShow(false);
        refreshLayout.setHeaderHeight(140);
        refreshLayout.setMaxHeadHeight(240);
        refreshLayout.setOverScrollHeight(200);
        refreshLayout.setEnableLoadmore(true);
        refreshLayout.startRefresh();
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        num=1;
                        mIsLoadMore=false;
                        getGank(num);
                    }
                }, 100);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        loadMoreCard();
                        num++;
                        mIsLoadMore=true;
                        getGank(num);
                    }
                }, 100);
            }
        });

        header.setColorSchemeResources(R.color.Blue, R.color.Orange, R.color.Yellow, R.color.Green);

    }
    public void  getGank(int num)
    {

        OkGo.get("http://gank.io/api/data/福利/40/"+String.valueOf(num))     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("home")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
//
                        BaseModels baseModels= (BaseModels) JSONHelper.parseToObject(s, BaseModels.class);
                        if(num==1)
                        {
                            mDatas.clear();
                        }
                        for (int i = 0; i < baseModels.getResults().size(); i++)
                        {
                            mDatas.add(baseModels.getResults().get(i).getUrl());
                        }
                        mAdapter.AddDatas(mDatas);
                        mAdapter.notifyDataSetChanged();
                        refreshLayout.finishRefreshing();
                        refreshLayout.finishLoadmore();
                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        refreshLayout.finishRefreshing();
                        refreshLayout.finishLoadmore();
                        SnackbarUtil.showMessage(rv,"加载失败了,请检查下网络或者下拉重新刷新下");
                    }
                });

    }
}