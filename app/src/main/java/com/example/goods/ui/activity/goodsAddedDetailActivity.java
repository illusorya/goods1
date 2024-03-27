package com.example.goods.ui.activity;

import android.app.Activity;
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

import com.example.goods.bean.goods;
import com.example.goods.adapter.goodsAdapter;
import com.example.goods.bean.goodsadded;
import com.example.goods.util.SPUtils;
import com.example.goods.widget.ActionBar;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * 已上传的商品明细信息
 */
public class goodsAddedDetailActivity extends AppCompatActivity {
    private Activity mActivity;
    private List<goods> list =new ArrayList<>();
    private ImageView ivImg;
    private TextView tvTitle;
    private TextView tvDate;
    private TextView tvContent;
    private TextView tvIssuer;
    private Button btnDelete;
    private Button btnDeleted;
    private ActionBar mActionBar;//标题栏
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_goods_added_detail);
        ivImg = findViewById(R.id.img);
        tvTitle= findViewById(R.id.title);
        tvDate = findViewById(R.id.date);
        tvContent = findViewById(R.id.content);
        tvIssuer = findViewById(R.id.issuer);
        btnDelete = findViewById(R.id.btn_delete);
        btnDeleted = findViewById(R.id.btn_deleted);
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

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsadded added = DataSupport.where("account = ? and title = ?",account,goods.getTitle()).findFirst(goodsadded.class);
                goods goods1 = DataSupport.where("title = ?",goods.getTitle()).findFirst(goods.class);
                added.delete();
                goods1.delete();

                list.remove(goods1);
                

                Toast.makeText(mActivity,"商品已下架", Toast.LENGTH_SHORT).show();
                btnDeleted.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.GONE);
            }
        });


    }
}
