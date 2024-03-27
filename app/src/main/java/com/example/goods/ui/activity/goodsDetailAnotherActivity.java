package com.example.goods.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.goods.R;
import com.example.goods.bean.Browse;
import com.example.goods.bean.Car;
import com.example.goods.bean.goods;
import com.example.goods.util.SPUtils;
import com.example.goods.widget.ActionBar;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 商品明细信息（商家）
 */
public class goodsDetailAnotherActivity extends AppCompatActivity {
    private Activity mActivity;
    private ImageView ivImg;
    private TextView tvTitle;
    private TextView tvDate;
    private TextView tvContent;
    private TextView tvIssuer;
    private ActionBar mActionBar;//标题栏
    private Button btn_GoReview;
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_goods_detail_another);
        ivImg = findViewById(R.id.img);
        tvTitle= findViewById(R.id.title);
        tvDate = findViewById(R.id.date);
        tvContent = findViewById(R.id.content);
        tvIssuer = findViewById(R.id.issuer);
        btn_GoReview = findViewById(R.id.btn_GoReview);
        mActionBar = findViewById(R.id.myActionBar);
        //侧滑菜单
        mActionBar.setData(mActivity,"商品详情", R.drawable.ic_back, 0, 0, getResources().getColor(R.color.colorPrimary), new ActionBar.ActionBarClickListener() {
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
        //浏览记录
        Browse browse = DataSupport.where("account = ? and title = ?",account,goods.getTitle()).findFirst(Browse.class);//浏览记录
        if (browse == null) {//不存在该条浏览记录  新增记录
            Browse browse1 = new Browse(account,goods.getTitle());
            browse1.save();
        }

        btn_GoReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(goodsDetailAnotherActivity.this, ReviewListActivity.class);
                goods news = DataSupport.where("title = ?",goods.getTitle()).findFirst(goods.class);
                intent.putExtra("goods",news);
                startActivity(intent);
            }
        });

    }
}
