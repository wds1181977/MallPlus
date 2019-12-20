package com.tymall.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tymall.R;
import com.tymall.model.OrderListModel;
import com.tymall.view.CustomRoundAngleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private List<OrderListModel.OrderListBean> orderList;
    private LayoutInflater layoutInflater;

    //图文
    private static final int TYPE_GRAPHIC = 0;

    //列表
    private static final int TYPE_IMAGE_LIST = 1;


    public OrderAdapter(Context mContext, List<OrderListModel.OrderListBean> orderList) {
        this.mContext = mContext;
        this.orderList = orderList;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getItemViewType(int position) {
        if (orderList.get(position).getOrderDetails().size() == 1) {
            return TYPE_GRAPHIC;
        } else {
            return TYPE_IMAGE_LIST;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_GRAPHIC:
                View view1 = layoutInflater.inflate(R.layout.item_order_graphic, parent, false);
                return new GraphicViewHoler(view1);
            case TYPE_IMAGE_LIST:
                View view2 = layoutInflater.inflate(R.layout.item_order_image, parent, false);
                return new ImageListViewHoler(view2);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GraphicViewHoler) {
            GraphicViewHoler graphicViewHoler = (GraphicViewHoler) holder;
            graphicViewHoler.setGraphicData(position);
        } else {
            ImageListViewHoler imageListViewHoler = (ImageListViewHoler) holder;
            imageListViewHoler.setImageListData(position);
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class GraphicViewHoler extends RecyclerView.ViewHolder {

        /**
         * 店铺名称
         */
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;

        /**
         * 订单状态
         */
        @BindView(R.id.tv_order_status)
        TextView tvOrderStatus;

        /**
         * 共xx件商品
         */
        @BindView(R.id.tv_products_num)
        TextView tvProductsNum;


        /**
         * 实付款金额
         */
        @BindView(R.id.tv_payment_amount)
        TextView tvPaymentAmount;


        /**
         * 按钮1
         */
        @BindView(R.id.tv_button_one)
        TextView tvButtonOne;

        /**
         * 按钮2
         */
        @BindView(R.id.tv_button_two)
        TextView tvButtonTwo;


        @BindView(R.id.ll_bottom)
        LinearLayout llBottom;


        /**
         * 商品图片
         */
        @BindView(R.id.iv_product_picture)
        CustomRoundAngleImageView ivProductPicture;

        /**
         * 商品简介
         */
        @BindView(R.id.tv_product_description)
        TextView tvProductDescription;

        @BindView(R.id.ll_item_all)
        LinearLayout llItemAll;

        public GraphicViewHoler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setGraphicData(final int position) {
            OrderListModel.OrderListBean orderListBean = orderList.get(position);

            tvShopName.setText(orderListBean.getShopName());
            tvOrderStatus.setText(orderListBean.getOrderStatusName());
            tvProductsNum.setText("共" + orderListBean.getGoodsNum() + "件商品");
            tvPaymentAmount.setText(orderListBean.getPriceCurrency() + orderListBean.getCurrencyName());


            tvProductDescription.setText(orderListBean.getOrderDetails().get(0).getGoodName());
            Glide.with(mContext).load(orderListBean.getOrderDetails().get(0).getPicUrl()).into(ivProductPicture);


            //0:未付款 101:订单已取消 201:待发货 202: 待收货 301:订单已完成
            switch (orderListBean.getOrderStatus()) {
                case 0:
                    llBottom.setVisibility(View.VISIBLE);
                    tvButtonOne.setVisibility(View.VISIBLE);
                    tvButtonTwo.setVisibility(View.VISIBLE);
                    tvButtonOne.setText("取消订单");
                    tvButtonTwo.setText("立即付款");
                    break;
                case 101:
                    llBottom.setVisibility(View.GONE);
                    break;
                case 201:
                    llBottom.setVisibility(View.GONE);
                    break;
                case 202:
                    llBottom.setVisibility(View.VISIBLE);
                    tvButtonOne.setVisibility(View.GONE);
                    tvButtonTwo.setVisibility(View.VISIBLE);
                    tvButtonTwo.setText("确认收货");
                    break;
                case 301:
                    llBottom.setVisibility(View.GONE);
                    break;
            }

            tvButtonOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _OnItemClickListener.onItemClickOne(view, position);
                }
            });

            tvButtonTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _OnItemClickListener.onItemClickTwo(view, position);
                }
            });

            llItemAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _OnItemClickListener.onItemOnClickListener(view, position);
                }
            });
        }
    }

    class ImageListViewHoler extends RecyclerView.ViewHolder {
        private ItemOrederAdapter itemOrederAdapter;

        @BindView(R.id.tv_shop_name)
        TextView tvShopName;

        @BindView(R.id.tv_order_status)
        TextView tvOrderStatus;

        @BindView(R.id.rl_recycleview_item)
        RecyclerView rlRecycleviewItem;

        @BindView(R.id.tv_products_num)
        TextView tvProductsNum;

        @BindView(R.id.tv_payment_amount)
        TextView tvPaymentAmount;

        @BindView(R.id.tv_button_one)
        TextView tvButtonOne;

        @BindView(R.id.tv_button_two)
        TextView tvButtonTwo;


        @BindView(R.id.ll_bottom)
        LinearLayout llBottom;


        @BindView(R.id.ll_item_all)
        LinearLayout llItemAll;

        public ImageListViewHoler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setImageListData(final int position) {
            OrderListModel.OrderListBean orderListBean = orderList.get(position);

            tvShopName.setText(orderListBean.getShopName());
            tvOrderStatus.setText(orderListBean.getOrderStatusName());
            tvProductsNum.setText("共" + orderListBean.getGoodsNum() + "件商品");
            tvPaymentAmount.setText(orderListBean.getPriceCurrency() + orderListBean.getCurrencyName());
            //0:未付款 101:订单已取消 201:待发货 202: 待收货 301:订单已完成
            switch (orderListBean.getOrderStatus()) {
                case 0:
                    llBottom.setVisibility(View.VISIBLE);
                    tvButtonOne.setVisibility(View.VISIBLE);
                    tvButtonTwo.setVisibility(View.VISIBLE);
                    tvButtonOne.setText("取消订单");
                    tvButtonTwo.setText("立即付款");
                    break;
                case 101:
                    llBottom.setVisibility(View.GONE);
                    break;
                case 201:
                    llBottom.setVisibility(View.GONE);
                    break;
                case 202:
                    llBottom.setVisibility(View.VISIBLE);
                    tvButtonOne.setVisibility(View.GONE);
                    tvButtonTwo.setVisibility(View.VISIBLE);
                    tvButtonTwo.setText("确认收货");
                    break;
                case 301:
                    llBottom.setVisibility(View.GONE);
                    break;
            }

            List<OrderListModel.OrderListBean.OrderDetailsBean> orderDetailsBeanList = orderList.get(position).getOrderDetails();
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rlRecycleviewItem.setLayoutManager(layoutManager);
            itemOrederAdapter = new ItemOrederAdapter(mContext, orderDetailsBeanList);
            rlRecycleviewItem.setAdapter(itemOrederAdapter);


            tvButtonOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _OnItemClickListener.onItemClickOne(view, position);
                }
            });

            tvButtonTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _OnItemClickListener.onItemClickTwo(view, position);
                }
            });

            llItemAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _OnItemClickListener.onItemOnClickListener(view, position);
                }
            });
        }
    }

    private OnItemClickListener _OnItemClickListener;

    public void set_OnItemClickListener(OnItemClickListener _OnItemClickListener) {
        this._OnItemClickListener = _OnItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickOne(View view, int position);

        void onItemClickTwo(View view, int position);

        void onItemOnClickListener(View view, int position);
    }
}
