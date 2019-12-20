package com.tymall.ui.login;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.tymall.R;
import com.tymall.base.XBaseActivity;
import com.tymall.ui.MainActivity;
import com.tymall.ui.adapter.GuidePageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class IntroduceActivity extends XBaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.guide_ib_next)
    TextView next;
    @BindView(R.id.guide_ib_start)
    Button ib_start;
    @BindView(R.id.toolbar_back)
    ConstraintLayout toolbarBack;
    @BindView(R.id.content_title)
    TextView contentTitle;
    @BindView(R.id.content_msg)
    TextView contentMsg;
    private ViewPager vp;
    private int[] imageIdArray;//图片资源的数组
    private List<View> viewList;//图片资源的集合
    private ViewGroup vg;//放置圆点

    //实例化原点View
    private ImageView iv_point;
    private ImageView[] ivPointArray;




    @Override
    protected int getLayoutId() {
        return R.layout.activity_introduce;
    }

    @Override
    protected void initView() {
        toolbarBack.setVisibility(View.GONE);
        if (!this.isTaskRoot()) {
            Intent mainIntent = getIntent();
            String action = mainIntent.getAction();
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }


        //加载ViewPager
        initViewPager();
        //加载底部圆点
        initPoint();

    }

    @OnClick({R.id.guide_ib_start, R.id.guide_ib_next})
    public void onClick(View v) {

        startActivity(new Intent(IntroduceActivity.this, MainActivity.class));

        finish();
    }

    /**
     * 加载底部圆点
     */
    private void initPoint() {
        //这里实例化LinearLayout
        vg = (ViewGroup) findViewById(R.id.guide_ll_point);
        //根据ViewPager的item数量实例化数组
        ivPointArray = new ImageView[viewList.size()];
        //循环新建底部圆点ImageView，将生成的ImageView保存到数组中
        int size = viewList.size();
        for (int i = 0; i < size; i++) {
            iv_point = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(16, 16);
            params.setMargins(0, 0, 12, 0);
            iv_point.setLayoutParams(params);
//            iv_point.setPadding(40, 40, 40, 40);//left,top,right,bottom
            ivPointArray[i] = iv_point;
            // 第一个页面需要设置为选中状态，这里采用两张不同的图片
            if (i == 0) {
                iv_point.setBackgroundResource(R.drawable.icon_dot2);
            } else {
                iv_point.setBackgroundResource(R.drawable.icon_dot1);
            }
            //将数组中的ImageView加入到ViewGroup
            vg.addView(ivPointArray[i]);
        }
    }

    /**
     * 加载图片ViewPager
     */
    private void initViewPager() {
        vp = (ViewPager) findViewById(R.id.guide_vp);
        //实例化图片资源
        imageIdArray = new int[]{R.drawable.icon_introduce_one_cn, R.drawable.icon_introduce_two_cn,
                R.drawable.icon_introduce_three_cn};
//        if(!CacheUtils.isChineseLanguage(this)){
//            imageIdArray = new int[]{R.drawable.icon_introduce_one_en, R.drawable.icon_introduce_two_en,
//                    R.drawable.icon_introduce_three_en, R.drawable.icon_introduce_four_en};
//        }
        viewList = new ArrayList<>();
        //获取一个Layout参数，设置为全屏
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        //循环创建View并加入到集合中
        int len = imageIdArray.length;
        for (int i = 0; i < len; i++) {
            //new ImageView并设置全屏和图片资源
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setBackgroundResource(imageIdArray[i]);

            //将ImageView加入到集合中
            viewList.add(imageView);
        }

        //View集合初始化好后，设置Adapter
        vp.setAdapter(new GuidePageAdapter(viewList));
        //设置滑动监听
        vp.addOnPageChangeListener(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 滑动后的监听
     *
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
        //循环设置当前页的标记图
        int length = imageIdArray.length;
        for (int i = 0; i < length; i++) {
            ivPointArray[position].setBackgroundResource(R.drawable.icon_dot2);
            if (position != i) {
                ivPointArray[i].setBackgroundResource(R.drawable.icon_dot1);
            }
        }
        switch (position){
            case 0:
                contentTitle.setText(getString(R.string.string_introduce_title_one));
                contentMsg.setText(getString(R.string.string_introduce_title_content_one));

                break;
            case 1:
                contentTitle.setText(getString(R.string.string_introduce_title_two));
                contentMsg.setText(getString(R.string.string_introduce_title_content_two));
                break;
            case 2:
                contentTitle.setText(getString(R.string.string_introduce_title_three));
                contentMsg.setText(getString(R.string.string_introduce_title_content_three));
                break;

        }
        //判断是否是最后一页，若是则显示按钮
        if (position == imageIdArray.length - 1) {
            ib_start.setVisibility(View.VISIBLE);
            next.setVisibility(View.GONE);
        } else {
            ib_start.setVisibility(View.INVISIBLE);
            next.setVisibility(View.VISIBLE);
        }
    }



    @Override
    public void onPageScrollStateChanged(int state) {

    }
}