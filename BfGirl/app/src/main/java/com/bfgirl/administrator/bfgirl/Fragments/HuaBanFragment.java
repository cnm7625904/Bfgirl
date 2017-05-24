package com.bfgirl.administrator.bfgirl.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.alibaba.fastjson.JSON;
import com.bfgirl.administrator.bfgirl.Adapter.HuaBanAdapter;
import com.bfgirl.administrator.bfgirl.Adapter.helper.AbsRecyclerViewAdapter;
import com.bfgirl.administrator.bfgirl.Main.DetailActivity;
import com.bfgirl.administrator.bfgirl.Models.BaseModels;
import com.bfgirl.administrator.bfgirl.Models.HuaBanResult;
import com.bfgirl.administrator.bfgirl.R;
import com.bfgirl.administrator.bfgirl.Utils.JSONHelper;
import com.bfgirl.administrator.bfgirl.Utils.SnackbarUtil;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Response;
@SuppressLint("ValidFragment")
public class HuaBanFragment extends com.trello.rxlifecycle.components.support.RxFragment {
    private TwinklingRefreshLayout refreshLayout;
    private boolean mIsLoadMore = true;
    private GridLayoutManager gridLayoutManager;
    private int num = 1;
    private List<HuaBanResult> mDatas;
    private HuaBanAdapter mAdapter;
    private RecyclerView rvhb;
    private Map<String, Object> map;

    public static HuaBanFragment getInstance() {

        HuaBanFragment sf = new HuaBanFragment();

        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.huabanfragment, null, false);
        mDatas = new ArrayList<>();
        initViews(v);
        return v;
    }


    private void initViews(View v) {
        refreshLayout = (TwinklingRefreshLayout) v.findViewById(R.id.twhb);

        mDatas = new ArrayList<>();
        rvhb = (RecyclerView) v.findViewById(R.id.rvhb);
        mAdapter = new HuaBanAdapter(rvhb);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvhb.setLayoutManager(gridLayoutManager);

        rvhb.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                ActivityOptionsCompat aop = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        holder.getParentView().findViewById(R.id.huaban_img), "123");
                String Img_url = mDatas.get(position).getThumb().toString();//传递图片地址
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("Img_url", Img_url);
                ActivityCompat.startActivity(getActivity(), intent, aop.toBundle());
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
                        num = 1;
                        mIsLoadMore = false;
                        getHuaBan(num);
                    }
                }, 100);
            }
            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        num++;
                        mIsLoadMore = true;
                        getHuaBan(num);
                    }
                }, 100);
            }
        });
    }

    public void getHuaBan(int num) {
        OkGo.post("http://route.showapi.com/819-1")
                .tag("huaban")
                .params("num", 10)
                .params("page", String.valueOf(num))
                .params("showapi_appid", 15314)
                .params("type", 38)
                .params("showapi_sign", "d424376f51f1467da1b8c75debebf148")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        BaseModels baseModels = (BaseModels) JSONHelper.parseToObject(s, BaseModels.class);
                        map = json2Map(baseModels.getShowapi_res_body());
                        String str;
                        HuaBanResult hua = null;
                        for (int i = 0; i < map.size() - 1; i++) {
                            str = obj2JsonString(map.get(String.valueOf(i)));
                            hua = (HuaBanResult) JSONHelper.parseToObject(str, HuaBanResult.class);
                            mDatas.add(hua);
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
                        SnackbarUtil.showMessage(rvhb, "加载失败了,请检查下网络或者下拉重新刷新下");
                    }
                });
    }

    public static String obj2JsonString(Object obj) {
        return JSON.toJSONString(obj);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> json2Map(String json) {
        return JSON.parseObject(json, Map.class);
    }


}