package com.tymall.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tymall.R;
import com.tymall.model.OrderListModel;
import com.tymall.view.CustomRoundAngleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemOrederAdapter extends RecyclerView.Adapter {

    private Context mContext;
    List<OrderListModel.OrderListBean.OrderDetailsBean> orderDetailsBeanList;
    private LayoutInflater layoutInflater;

    public ItemOrederAdapter(Context mContext, List<OrderListModel.OrderListBean.OrderDetailsBean> orderDetailsBeanList) {
        this.mContext = mContext;
        this.orderDetailsBeanList = orderDetailsBeanList;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_order_image_item, parent, false);
        return new MyViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHoler myViewHoler = (MyViewHoler) holder;
        myViewHoler.setData(position);
    }

    @Override
    public int getItemCount() {
        return orderDetailsBeanList.size();
    }

    class MyViewHoler extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_product_picture)
        CustomRoundAngleImageView ivProductPicture;

        @BindView(R.id.tv_product_num)
        TextView tvProductNum;

        public MyViewHoler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(final int position) {
            OrderListModel.OrderListBean.OrderDetailsBean model = orderDetailsBeanList.get(position);
//            if (model.getGoodNumber() > 1) {
//                tvProductNum.setVisibility(View.VISIBLE);
                tvProductNum.setText("x" + model.getGoodNumber());
//            } else {
//                tvProductNum.setVisibility(View.GONE);
//            }

            Glide.with(mContext).load(model.getPicUrl()).into(ivProductPicture);
        }
    }

}
