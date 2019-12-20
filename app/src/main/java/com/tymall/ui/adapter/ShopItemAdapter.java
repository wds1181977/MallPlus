package com.tymall.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tymall.R;
import com.tymall.model.ShopModel;
import com.tymall.view.CustomRoundAngleImageView;
import com.tymall.view.OnClickAddCloseListenter;
import com.tymall.view.OnClickListenterModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopItemAdapter extends RecyclerView.Adapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<ShopModel.CartListBean.ItemsBean> list;
    private int positionItem;

    public ShopItemAdapter(Context context, int position, List<ShopModel.CartListBean.ItemsBean> list) {
        this.positionItem = position;
        this.list = list;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_shop_item, parent, false);
        return new MyViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHoler myViewHoler = (MyViewHoler) holder;
        myViewHoler.setData(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHoler extends RecyclerView.ViewHolder {
        @BindView(R.id.cb_checkbox)
        CheckBox cbCheckbox;

        /**
         * 商品图片
         */
        @BindView(R.id.iv_product_picture)
        CustomRoundAngleImageView ivProductPicture;

        /**
         * 商品介绍
         */
        @BindView(R.id.tv_content)
        TextView tvContent;

        /**
         * 商品价格
         */
        @BindView(R.id.item_dbm_num)
        TextView itemDbmNum;

        /**
         * 减
         */
        @BindView(R.id.ll_less)
        LinearLayout llLess;

        /**
         * 添加商品数量
         */
        @BindView(R.id.tv_commodity_num)
        TextView tvCommodityNum;

        /**
         * 加
         */
        @BindView(R.id.ll_add)
        LinearLayout llAdd;


        @BindView(R.id.ll_item)
        LinearLayout llItem;

        public MyViewHoler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(final int position) {
            cbCheckbox.setChecked(list.get(position).isIscheck());
            tvContent.setText(list.get(position).getGoods_name());
            itemDbmNum.setText(list.get(position).getPriceCurrency() + list.get(position).getCurrencyName());
            tvCommodityNum.setText(list.get(position).getNumber() + "");
            Glide.with(context).load(list.get(position).getPrimary_pic_url()).into(ivProductPicture);
            cbCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListenterModel.onItemClick(cbCheckbox.isChecked(), v, positionItem, position);
                }
            });



            llAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickAddCloseListenter.onItemClick(view, 2, positionItem, position, list.get(position).getNumber());
                }
            });
            llLess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickAddCloseListenter.onItemClick(view, 1, positionItem, position, list.get(position).getNumber());
                }
            });

            llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickItemListenterAll.onItemAllOnClickListener(view, positionItem, position);
                }
            });
        }
    }


    // 数量加减接口的方法
    private OnClickAddCloseListenter onClickAddCloseListenter = null;

    public void setOnClickAddCloseListenter(OnClickAddCloseListenter listener) {
        this.onClickAddCloseListenter = listener;
    }


    // CheckBox2接口的方法
    private OnClickListenterModel onClickListenterModel = null;

    public void setOnClickListenterModel(OnClickListenterModel listener) {
        this.onClickListenterModel = listener;
    }


    // CheckBox2接口的方法
    private OnClickItemListenterAll onClickItemListenterAll = null;

    public void setOnClickListenterAll(OnClickItemListenterAll listener) {
        this.onClickItemListenterAll = listener;
    }


    //点击item
    public interface OnClickItemListenterAll {
        void onItemAllOnClickListener(View view, int positionItem, int position);
    }
}
