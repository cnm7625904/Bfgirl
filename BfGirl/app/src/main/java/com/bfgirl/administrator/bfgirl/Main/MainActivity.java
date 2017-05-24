package com.bfgirl.administrator.bfgirl.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import com.bfgirl.administrator.bfgirl.Adapter.GankAdapter;
import com.bfgirl.administrator.bfgirl.Adapter.helper.AbsRecyclerViewAdapter;
import com.bfgirl.administrator.bfgirl.Models.BaseModels;
import com.bfgirl.administrator.bfgirl.R;
import com.bfgirl.administrator.bfgirl.Utils.JSONHelper;
import com.bfgirl.administrator.bfgirl.Utils.SnackbarUtil;
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
 * Created by Administrator。 on 2017/5/20.
 */

public class MainActivity extends Activity
{
    private TwinklingRefreshLayout refreshLayout;
    private boolean mIsLoadMore = true;
    private LinearLayoutManager linearLayoutManager;
    private int num=1;
//    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<String> mDatas;
    private GankAdapter mAdapter;
    private RecyclerView rv;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_activity);
        refreshLayout= (TwinklingRefreshLayout) findViewById(R.id.tw);
        mDatas=new ArrayList<>();
        rv= (RecyclerView) findViewById(R.id.rv);
        mAdapter=new GankAdapter(rv);
//        mSwipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.mSwipeRefreshLayout);
        linearLayoutManager=new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);
//        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                int lastVisibleItem = ((LinearLayoutManager) linearLayoutManager).findLastVisibleItemPosition();
//                int totalItemCount = linearLayoutManager.getItemCount();
//                //lastVisibleItem >= totalItemCount - 4 表示剩下4个item自动加载，各位自由选择
//                // dy>0 表示向下滑动
//                if (lastVisibleItem >= totalItemCount - 2&& dy > 0) {
//                    if(mIsLoadMore){
////                        Log.d(TAG,"ignore manually update!");
//                        Toast.makeText(MainActivity.this,"1111111",Toast.LENGTH_SHORT).show();
//                    } else{
////                        loadPage();//这里多线程也要手动控制isLoadingMore
//                        mIsLoadMore = false;
//                        getGank(2);
//                        Toast.makeText(MainActivity.this,"222222222222",Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
        initViews();
    }

    private void initViews() {


        First_show();

        rv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
//                Toast.makeText(MainActivity.this,"当前是第"+String.valueOf(position)+"个"+mDatas.get(position).toString(),Toast.LENGTH_SHORT).show();
                ActivityOptionsCompat aop=ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,
                        holder.getParentView().findViewById(R.id.img),"123");

                  String Img_url=mDatas.get(position).toString();//传递图片地址

                Intent intent=new Intent(getApplicationContext(),DetailActivity.class);
                intent.putExtra("Img_url", Img_url);

                ActivityCompat.startActivity(MainActivity.this,intent,aop.toBundle());
            }
        });

        ProgressLayout header = new ProgressLayout(this);
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
//                        refreshLayout.finishRefreshing();
//                        mDatas.clear();
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
    public void First_show()
    {
//        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
//        mSwipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
////                mSwipeRefreshLayout.setRefreshing(true);
//                getGank(num);
//            }
//        });
//
//
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                num = 1;
//                mDatas.clear();
//                getGank(num);
//            }
//        });






    }
//
//    RecyclerView.OnScrollListener OnLoadMoreListener(LinearLayoutManager layoutManager) {
//
//        return new RecyclerView.OnScrollListener() {
//
//            @Override
//            public void onScrolled(RecyclerView rv, int dx, int dy) {
//
//                boolean isBottom = linearLayoutManager.findLastCompletelyVisibleItemPosition()>= mAdapter.getItemCount() - 3;
//                if (!mSwipeRefreshLayout.isRefreshing() && isBottom) {
////                    if (!mIsLoadMore) {
////                        mSwipeRefreshLayout.setRefreshing(true);
//                    ++num;
//                        getGank(2);
////                        Toast.makeText(MainActivity.this,"加载更多",Toast.LENGTH_LONG).show();
////                    }
////                    else {
////                        mIsLoadMore = false;
////                    }
//                }
//            }
//        };
//    }

    public void  getGank(int num)
    {

        OkGo.get("http://gank.io/api/data/福利/10/"+String.valueOf(num))     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("home")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
//                        if (mSwipeRefreshLayout.isRefreshing()) {
//                            mSwipeRefreshLayout.setRefreshing(false);
//                        }
                        BaseModels baseModels= (BaseModels) JSONHelper.parseToObject(s, BaseModels.class);
//                        Toast.makeText(MainActivity.this, String.valueOf(baseModels.getResults().size()),Toast.LENGTH_SHORT).show();
//                        Toast.makeText(MainActivity.this, baseModels.getResults().get(0).getUrl(),Toast.LENGTH_SHORT).show();
//                        Toast.makeText(MainActivity.this,"num"+String.valueOf(num),Toast.LENGTH_SHORT).show();
                        if(num==1)
                        {
                            mDatas.clear();
                        }
                        for (int i = 0; i < baseModels.getResults().size(); i++)
                        {
                            mDatas.add(baseModels.getResults().get(i).getUrl());
                        }

//                        mAdapter.notifyDataSetChanged();

                        mAdapter.AddDatas(mDatas);
                        mAdapter.notifyDataSetChanged();


                        refreshLayout.finishRefreshing();
                        refreshLayout.finishLoadmore();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
//                        if (mSwipeRefreshLayout.isRefreshing()) {
//                            mSwipeRefreshLayout.setRefreshing(false);
//                        }

                        refreshLayout.finishRefreshing();
                        refreshLayout.finishLoadmore();
                        SnackbarUtil.showMessage(rv,"加载失败了,请检查下网络或者下拉重新刷新下");
                    }
                });

    }
}
