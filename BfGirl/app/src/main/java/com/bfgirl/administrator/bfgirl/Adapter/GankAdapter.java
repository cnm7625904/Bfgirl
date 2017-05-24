package com.bfgirl.administrator.bfgirl.Adapter;

import android.net.Uri;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bfgirl.administrator.bfgirl.Adapter.helper.AbsRecyclerViewAdapter;
import com.bfgirl.administrator.bfgirl.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator。 on 2017/5/20.
 */

public class GankAdapter extends AbsRecyclerViewAdapter {
    private List<String> zmeizis = new ArrayList<>();
    private Uri imguri;
    private List<Integer> heightList;//装产出的随机数
    public GankAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }
    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(
                LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {

        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            heightList = new ArrayList<>();
            for (int i = 0; i < zmeizis.size(); i++) {
                int height = new Random().nextInt(50) + 200;//[100,300)的随机数
                heightList.add(height);
            }
            Glide.with(getContext())
                    .load(zmeizis.get(position))
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(itemViewHolder.img)
            ;
            //由于需要实现瀑布流的效果,所以就需要动态的改变控件的高度了
            ViewGroup.LayoutParams params = itemViewHolder.img.getLayoutParams();
            params.height = heightList.get(position);
            itemViewHolder.img.setLayoutParams(params);

        }

        super.onBindViewHolder(holder, position);
    }
    @Override
    public int getItemCount() {
        return zmeizis == null ? 0 : zmeizis.size();
    }

    public class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {
        public TextView mTextView;
        public ImageView img;
        public View item;


        public ItemViewHolder(View itemView) {

            super(itemView);
            item = itemView;
            img = $(R.id.img);
        }
    }

    public void AddDatas(List<String> meizis) {
        zmeizis = meizis;
    }
}
