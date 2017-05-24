package com.bfgirl.administrator.bfgirl.Adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bfgirl.administrator.bfgirl.Adapter.helper.AbsRecyclerViewAdapter;
import com.bfgirl.administrator.bfgirl.Models.HuaBanResult;
import com.bfgirl.administrator.bfgirl.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator。 on 2017/5/20.
 */

public class HuaBanAdapter extends AbsRecyclerViewAdapter {
    private List<HuaBanResult> huaban = new ArrayList<>();
    private Uri imguri;

    public HuaBanAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(
                LayoutInflater.from(getContext()).inflate(R.layout.huabanitem, parent, false));
    }
    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {

        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.tv_huaban.setText(huaban.get(position).getTitle());
            Glide.with(getContext())
                    .load(huaban.get(position).getThumb())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(itemViewHolder.img)
                ;
        }

        super.onBindViewHolder(holder, position);
    }
    @Override
    public int getItemCount() {
        return huaban == null ? 0 : huaban.size();
    }
    public class ItemViewHolder extends ClickableViewHolder {
        public TextView tv_huaban;
        public ImageView img;
        public View item;


        public ItemViewHolder(View itemView) {

            super(itemView);
            item = itemView;
            img = $(R.id.huaban_img);
            tv_huaban = $(R.id.tv_huaban);
        }

    }
    public void AddDatas(List<HuaBanResult> meizis)
    {
        huaban = meizis;
    }
}
