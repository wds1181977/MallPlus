package com.tymall.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tymall.R;
import com.tymall.model.ShopModel;
import com.tymall.view.OnClickAddCloseListenter;
import com.tymall.view.OnClickListenterModel;
import com.tymall.view.OnItemAddOrDeleteClickListener;
import com.tymall.view.OnItemMoneyClickListener;
import com.tymall.view.OnViewItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopAdapter extends RecyclerView.Adapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<ShopModel.CartListBean> list;
    public ShopItemAdapter shopItemAdapter;
    public boolean isCheck = false;

    public ShopAdapter(Context context, List<ShopModel.CartListBean> list) {
        this.context = context;
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_shop, parent, false);
        return new MyViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHoler myViewHoler = (MyViewHoler) holder;
        myViewHoler.setData(position);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHoler extends RecyclerView.ViewHolder {

        @BindView(R.id.cb_shop_checkbox)
        CheckBox cbShopCheckbox;

        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.rl_recycleview)
        RecyclerView rlRecycleview;

        @BindView(R.id.ll_all_item)
        LinearLayout llAllItem;

        public MyViewHoler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(final int position) {
            tvShopName.setText(list.get(position).getShop_name());
            rlRecycleview.setLayoutManager(new LinearLayoutManager(context));
            shopItemAdapter = new ShopItemAdapter(context, position, list.get(position).getItems());
            rlRecycleview.setAdapter(shopItemAdapter);
            cbShopCheckbox.setChecked(list.get(position).isIscheck());
            cbShopCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(cbShopCheckbox.isChecked(), v, position);
                }
            });

            //店铺下的checkbox
            shopItemAdapter.setOnClickListenterModel(new OnClickListenterModel() {
                @Override
                public void onItemClick(boolean isFlang, View view, int onePosition, int position) {
                    list.get(onePosition).getItems().get(position).setIscheck(isFlang);
                    int length = list.get(onePosition).getItems().size();
                    if (length == 1) {
                        mOnItemClickListener.onItemClick(isFlang, view, onePosition);
                    } else {
                        for (int i = 0; i < length; i++) {
                            if (list.get(onePosition).getItems().get(i).isIscheck()) {
                                isCheck = true;
                            } else {
                                isCheck = false;
                                break;
                            }
                        }
                        list.get(onePosition).setIscheck(isCheck);
                        onItemMoneyClickListener.onItemClick(view, onePosition);
                        notifyDataSetChanged();
                    }
                }
            });
            /***
             * 数量增加和减少
             */
            shopItemAdapter.setOnClickAddCloseListenter(new OnClickAddCloseListenter() {
                @Override
                public void onItemClick(View view, int index, int onePosition, int position, int num) {
//                    if (index == 1) {
//                        if (num > 1) {
//                            list.get(onePosition).getItems().get(position).setNumber((num - 1));
//                            notifyDataSetChanged();
//                        }
//                    } else {
//                        list.get(onePosition).getItems().get(position).setNumber((num + 1));
//                        notifyDataSetChanged();
//                    }
                    onItemAddOrDeleteClickListener.onItemClick(view, onePosition, position, index, num);
                }
            });


//            llAllItem.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    _onItemAllOnClickListener.onItemAllOnClickListener(view, position);
//                }
//            });


            shopItemAdapter.setOnClickListenterAll(new ShopItemAdapter.OnClickItemListenterAll() {
                @Override
                public void onItemAllOnClickListener(View view, int positionItem, int position) {
                    _onItemAllOnClickListener.onItemAllOnClickListener(view, positionItem, position);
                }
            });
        }
    }


    // CheckBox全选的方法
    private OnViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    // 计算价钱
    private OnItemMoneyClickListener onItemMoneyClickListener = null;

    public void setOnItemMoneyClickListener(OnItemMoneyClickListener listener) {
        this.onItemMoneyClickListener = listener;
    }


    //购物车商品数量增减
    private OnItemAddOrDeleteClickListener onItemAddOrDeleteClickListener = null;

    public void setOnItemAddOrDeleteClickListener(OnItemAddOrDeleteClickListener listener) {
        this.onItemAddOrDeleteClickListener = listener;
    }


    //item点击事件
    private OnItemAllOnClickListener _onItemAllOnClickListener;

    public void set_OnItemAllOnClickListener(OnItemAllOnClickListener onItemAllClickListener) {
        this._onItemAllOnClickListener = onItemAllClickListener;
    }

    public interface OnItemAllOnClickListener {
        void onItemAllOnClickListener(View view, int positionItem, int position);
    }


    /**
     * 删除选中item
     */
//    public void removeChecked() {
//        int iMax = list.size() - 1;
//        //这里要倒序，因为要删除mDatas中的数据，mDatas的长度会变化
//        for (int i = iMax; i >= 0; i--) {
//            if (list.get(i).isIscheck()) {
//                list.remove(i);
//                notifyItemRemoved(i);
//                notifyItemRangeChanged(i, list.size());
//            } else {
//                int length = list.get(i).getItems().size() - 1;
//                for (int j = length; j >= 0; j--) {
//                    if (list.get(i).getItems().get(j).isIscheck()) {
//                        list.get(i).getItems().remove(j);
//                        notifyItemRemoved(i);
//                        notifyItemRangeChanged(i, list.size());
//                    }
//                }
//            }
//        }
//    }

}
