package com.example.goods.ui.activity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.goods.R;
import com.example.goods.bean.Review;
import com.example.goods.bean.goods;
import com.example.goods.util.SPUtils;
import com.example.goods.widget.ActionBar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Date;

public class goodsPingjiaActivity extends AppCompatActivity {

    private EditText etReview;
    private TextView etTitle;
    private RatingBar ratingBar;
    private Button btnSubmit;
    private ActionBar mActionBar;
    private Activity mActivity;
    private Review mreview;
    SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_goods_pingjia);


        etTitle = findViewById(R.id.etitle);
        etReview = findViewById(R.id.etReview);
        ratingBar = findViewById(R.id.ratingBar);
        mActionBar = findViewById(R.id.myActionBar);
        //侧滑菜单
        mActionBar.setData(mActivity,"我的评论", R.drawable.ic_back, 0, 0, getResources().getColor(R.color.colorPrimary), new ActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {
            }
        });
        initView();
    }


    private void initView() {
        mreview = (Review) getIntent().getSerializableExtra("review");


    }

    public void save(View view){
        goods goods = (goods) getIntent().getSerializableExtra("goods");
        String title = goods.getTitle();
        String content = etReview.getText().toString();
        String account = (String) SPUtils.get(mActivity, SPUtils.ACCOUNT,"");
        String date = sf.format(new Date());
        Float rating = ratingBar.getRating();
        if ("".equals(content)) {
            Toast.makeText(mActivity,"评论不能为空", Toast.LENGTH_LONG).show();
            return;
        }

        Review review = null;
        if (review == null ){
            if (mreview != null){
                review = DataSupport.where("title = ?", mreview.getTitle()).findFirst(Review.class);

                review.setTitle(title);
                review.setAccount(account);
                review.setDate(date);
                review.setContent(content);
                review.setRating(rating);

            }else {
                review = new Review(title,account,content,sf.format(new Date()),rating);
            }
            review.save();
            setResult(RESULT_OK);
            finish();
            Toast.makeText(mActivity,"评论成功", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(mActivity,"评论失败", Toast.LENGTH_LONG).show();
        }
    }
}


