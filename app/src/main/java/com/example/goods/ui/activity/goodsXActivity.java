package com.example.goods.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.goods.R;
import com.example.goods.bean.goods;
import com.example.goods.util.SPUtils;
import com.example.goods.widget.ActionBar;

import java.text.SimpleDateFormat;


/**
 * 订单明细信息
 */
public class goodsXActivity extends AppCompatActivity {
    private Activity mActivity;
    private ImageView ivImg;
    private TextView tvTitle;
    private TextView tvDate;
    private TextView tvContent;
    private TextView tvIssuer;
    private Button btnCollect;
    private Button btnCancel;
    private Button btnGoToReview;
    private ActionBar mActionBar;//标题栏
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_goods_x);
        ivImg = findViewById(R.id.img);
        tvTitle= findViewById(R.id.title);
        tvDate = findViewById(R.id.date);
        tvContent = findViewById(R.id.content);
        tvIssuer = findViewById(R.id.issuer);
        btnCollect = findViewById(R.id.btn_collect);
        btnCancel = findViewById(R.id.btn_cancel);
        mActionBar = findViewById(R.id.myActionBar);
        //侧滑菜单
        mActionBar.setData(mActivity,"查看详情", R.drawable.ic_back, 0, 0, getResources().getColor(R.color.colorPrimary), new ActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {
            }
        });
        goods goods = (goods) getIntent().getSerializableExtra("goods");
        tvTitle.setText(goods.getTitle());
        tvDate.setText(String.format("上架时间:%s",goods.getDate()));
        tvContent.setText(goods.getContent());
        tvIssuer.setText(String.format("￥ %s",goods.getIssuer()));
        Glide.with(mActivity)
                .asBitmap()
                .skipMemoryCache(true)
                .load(goods.getImg())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(ivImg);
        String account = (String) SPUtils.get(mActivity,SPUtils.ACCOUNT,"");


        btnGoToReview = findViewById(R.id.btnGoToReview);

        btnGoToReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(goodsXActivity.this, goodsPingjiaActivity.class);
                startActivity(intent);
            }
        });

    }



}
