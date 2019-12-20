package com.tymall.ui.adapter;


import android.graphics.Paint;
import android.text.TextUtils;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tymall.R;
import com.tymall.model.GoodsListNewModel;
import com.tymall.view.CustomRoundAngleImageView;


public class GoodsListNewAdapter extends BaseQuickAdapter<GoodsListNewModel.RecordsBean, BaseViewHolder> {
    CustomRoundAngleImageView goodsImage;
    TextView goodsTitle;
    TextView originalPrice;
    TextView dbmRatePrice;
    TextView dbmPrice;
    TextView cashBackPrice;
    TextView salesVolume,coinType;

    RequestOptions options = new RequestOptions()
            .placeholder(R.mipmap.icon_shop_default)//图片加载出来前，显示的图片
            .fallback(R.mipmap.icon_shop_default) //url为空的时候,显示的图片
            .error(R.mipmap.icon_shop_default);//图片加载失败后，显示的图片

    public GoodsListNewAdapter() {
        super(R.layout.item_dbmall_goods_list, null);

    }


    @Override
    protected void convert(BaseViewHolder holder, GoodsListNewModel.RecordsBean data) {
        if(data == null) return;
        try {
            goodsImage = holder.itemView.findViewById(R.id.goods_image);
            goodsTitle = holder.itemView.findViewById(R.id.goods_title);
            originalPrice = holder.itemView.findViewById(R.id.originalPrice);
            dbmRatePrice = holder.itemView.findViewById(R.id.dbmRatePrice);
            originalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线
            dbmPrice = holder.itemView.findViewById(R.id.dbmPrice);
            cashBackPrice = holder.itemView.findViewById(R.id.cash_back_price);
            salesVolume = holder.itemView.findViewById(R.id.sales_volume);
            coinType = holder.itemView.findViewById(R.id.coinType);
            if (data.getPic() != null) {
                Glide.with(holder.itemView.getContext()).load(data.getPic()).apply(options).into(goodsImage);
            }
            goodsTitle.setText(TextUtils.isEmpty(data.getName()) ? "" :data.getName());
//            originalPrice.setText(TextUtils.isEmpty(data.getMarket_price()) ? "" : holder.itemView.getContext().getString(R.string.string_dbmall_novigation_originalPrice)+" ¥"+data.getMarket_price());
//
//            dbmRatePrice.setText(TextUtils.isEmpty(data.getFinal_price()) ? "" : "(≈ ¥" + data.getFinal_price()+")");
//            String coinTypeV = TextUtils.isEmpty(data.getCurrencyName()) ? "" : data.getCurrencyName();
//            coinType.setText(coinTypeV);
//            dbmPrice.setText(TextUtils.isEmpty(data.getCurrencyPrice()) ? "" :data.getCurrencyPrice()+" ");
//            String cashBack = new StringBuilder("¥")
//                    .append(TextUtils.isEmpty(data.getCashBackDbm()) ? "" : data.getCashBackDbm() + "DBM")
//                    .append(" + ")
//                    .append(TextUtils.isEmpty(data.getIntegral()) ? "" : data.getIntegral())
//                    .append(holder.itemView.getContext().getString(R.string.string_dbmall_novigation_points))
//                    .toString();
//            cashBackPrice.setText(TextUtils.isEmpty(cashBack) ? "" :cashBack);
//            String salesValue = new StringBuilder(holder.itemView.getContext().getString(R.string.string_dbmall_novigation__sales))
//                    .append(" ")
//                    .append(TextUtils.isEmpty(data.getSell_volume()) ? "" :data.getSell_volume()).toString();
//
//
//            salesVolume.setText(salesValue);
//
//
//            holder.itemView.setOnClickListener(v -> {
//
//                if (data == null || TextUtils.isEmpty(data.getGoodDetailsUrl())) {
//                    return;
//                }
//                if (ClickOnUtils.isFastClick())
//
//                    DBMallH5Activity.openH5((BaseActivity)holder.itemView.getContext(), data.getGoodDetailsUrl(), TextUtils.isEmpty(data.getName()) ? "" : data.getName());
//
//                 });


        }catch (Exception e){

        }


    }




}
