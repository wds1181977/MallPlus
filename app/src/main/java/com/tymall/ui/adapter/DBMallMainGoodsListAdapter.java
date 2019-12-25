package com.tymall.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tymall.R;
import com.tymall.base.BaseActivity;
import com.tymall.model.GoodsListNewModel;
import com.tymall.ui.mall.DBMallH5Activity;
import com.tymall.utils.ClickOnUtils;
import com.tymall.view.CustomRoundAngleImageView;

import java.util.List;


public class DBMallMainGoodsListAdapter extends DelegateAdapter.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutHelper mHelper;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private List<GoodsListNewModel.RecordsBean> mData;
    RequestOptions options = new RequestOptions()
            .placeholder(R.mipmap.icon_shop_default)//图片加载出来前，显示的图片
            .fallback(R.mipmap.icon_shop_default) //url为空的时候,显示的图片
            .error(R.mipmap.icon_shop_default);//图片加载失败后，显示的图片

    public DBMallMainGoodsListAdapter(Context context, List<GoodsListNewModel.RecordsBean> mData, GridLayoutHelper helper) {
        this.mContext = context;
        this.mData = mData;
        this.mHelper = helper;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home_goods_list, parent, false);
        return new RecyclerViewItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (mData == null) return;
        GoodsListNewModel.RecordsBean data = mData.get(position);
        if (data == null) return;
        RecyclerViewItemHolder recyclerViewHolder = (RecyclerViewItemHolder) holder;


        try {
            if (!TextUtils.isEmpty(data.getPic())) {
                Glide.with(holder.itemView.getContext()).load(data.getPic()).apply(options).into(recyclerViewHolder.goodsImage);
            }

            recyclerViewHolder.goods_price.setText(TextUtils.isEmpty(data.getOriginalPrice()) ? "" :  " ¥ "+data.getOriginalPrice());
            recyclerViewHolder.goodsTitle.setText(TextUtils.isEmpty(data.getName()) ? "" :  data.getName());
            String tv = mContext.getString(R.string.string_dbmall_novigation_sales);
            String unit = TextUtils.isEmpty(data.getUnit()) ? "" :data.getUnit();
            recyclerViewHolder.goods_sales.setText(tv+data.getSale()+unit);


            holder.itemView.setOnClickListener(v -> {
//
//                if (data == null || TextUtils.isEmpty(data.getGoodDetailsUrl())) {
//                    return;
//                }
//                if (ClickOnUtils.isFastClick())
//                    DBMallH5Activity.openH5((BaseActivity)holder.itemView.getContext(), data.getGoodDetailsUrl(), TextUtils.isEmpty(data.getName()) ? "" : data.getName());

            });


        } catch (Exception e) {

        }



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * 正常条目的item的ViewHolder
     */
    private class RecyclerViewItemHolder extends RecyclerView.ViewHolder {



        public ImageView goodsImage;
        public TextView goodsTitle;
        public TextView goods_price;
        public  TextView goods_sales;



        public RecyclerViewItemHolder(View itemView) {
            super(itemView);


            goodsImage =itemView.findViewById(R.id.goods_image);
            goodsTitle = itemView.findViewById(R.id.goods_title);
            goods_price = itemView.findViewById(R.id.goods_price);
            goods_sales = itemView.findViewById(R.id.goods_sales);



        }
    }

}
