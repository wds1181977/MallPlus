package com.tymall.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tymall.R;
import com.tymall.model.MyCashBackModel;
import com.tymall.utils.DateTimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCashBackAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater layoutInflater;
    private List<MyCashBackModel> list;

    public MyCashBackAdapter(Context mContext, List<MyCashBackModel> list) {
        this.mContext = mContext;
        this.list = list;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_my_cash_back, parent, false);
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
        @BindView(R.id.ll_item_all)
        LinearLayout llItemAll;


        @BindView(R.id.tv_type)
        TextView tvType;


        @BindView(R.id.tv_time)
        TextView tvTime;


        @BindView(R.id.tv_integral_num)
        TextView tvIntegralNum;


        public MyViewHoler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(final int position) {
            MyCashBackModel model = list.get(position);
            tvType.setText(model.getRemark());
            tvTime.setText(DateTimeUtil.format2(model.getCreate_time()));
            if ("0".equals(model.getType())) {
                tvIntegralNum.setText(model.getOpt_num() + mContext.getResources().getString(R.string.string_dbmall_novigation_points));
            } else if ("1".equals(model.getType())) {
                tvIntegralNum.setText(mContext.getResources().getString(R.string.ren_min_bi)+model.getOpt_num());
            } else if ("2".equals(model.getType())) {
                tvIntegralNum.setText(model.getOpt_num() + "DBM");
            }
        }
    }
}
