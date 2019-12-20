package com.tymall.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tymall.R;
import com.tymall.model.GoodsListNewModel;

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

    public DBMallMainGoodsListAdapter(Context context, List<GoodsListNewModel.RecordsBean> mData, LayoutHelper helper) {
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
                .inflate(R.layout.item_dbmall_goods_list_main, parent, false);
        return new RecyclerViewItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (mData == null) return;
        GoodsListNewModel.RecordsBean data = mData.get(position);
        if (data == null) return;
        RecyclerViewItemHolder recyclerViewHolder = (RecyclerViewItemHolder) holder;


        try {
            if (!TextUtils.isEmpty(mData.get(position).getPic())) {
                Glide.with(holder.itemView.getContext()).load(data.getPic()).apply(options).into(recyclerViewHolder.goods_entry);
            }
//            recyclerViewHolder.goods_entry.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(data == null || TextUtils.isEmpty(data.getCategoryGoodUrl())){
//                        return;
//                    }
//                    if (ClickOnUtils.isFastClick())
//                        DBMallH5Activity.openH5((BaseActivity)holder.itemView.getContext(),data.getCategoryGoodUrl(),TextUtils.isEmpty(data.getName())?"":data.getName());
//                }
//            });

//            RecyclerView mRecyclerView = recyclerViewHolder.recyclerView;
//            mRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
//            GoodsListNewAdapter mAdapter = new GoodsListNewAdapter();
//            mRecyclerView.setAdapter(mAdapter);
//            if(data != null  && data.getGoodsList() != null) {
//                mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                        GoodsListNewModel.CategoryGoodListBean.GoodsListBean goodsListBean = data.getGoodsList().get(position);
//                        if (goodsListBean == null || TextUtils.isEmpty(goodsListBean.getGoodDetailsUrl())) {
//                            return;
//                        }
//                        if (ClickOnUtils.isFastClick())
//                            DBMallH5Activity.openH5((BaseActivity) holder.itemView.getContext(), goodsListBean.getGoodDetailsUrl(), TextUtils.isEmpty(goodsListBean.getName()) ? "" : goodsListBean.getName());
//                    }
//                });
//                mAdapter.setNewData(data.getGoodsList());
//            }


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

        public RecyclerView recyclerView;
        public ImageView goods_entry;


        public RecyclerViewItemHolder(View itemView) {
            super(itemView);
            goods_entry = itemView.findViewById(R.id.goods_entry);
            recyclerView = itemView.findViewById(R.id.recycler_view);


        }
    }

}
