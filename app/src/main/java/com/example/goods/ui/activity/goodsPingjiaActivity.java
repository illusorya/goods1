package com.example.goods.ui.activity;

import com.example.goods.R;
import com.example.goods.widget.ActionBar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class goodsPingjiaActivity extends AppCompatActivity {

    private EditText etReview;
    private RatingBar ratingBar;
    private Button btnSubmit;
    private ActionBar mActionBar;
    private Activity mActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_pingjia);

        etReview = findViewById(R.id.etReview);
        ratingBar = findViewById(R.id.ratingBar);
        btnSubmit = findViewById(R.id.btnSubmit);
        mActionBar = findViewById(R.id.myActionBar);
        //侧滑菜单
        mActionBar.setData(mActivity,"商品评价", R.drawable.ic_back, 0, 0, getResources().getColor(R.color.colorPrimary), new ActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String review = etReview.getText().toString();
                float rating = ratingBar.getRating();

                // 将评价信息发送给服务器或处理本地存储逻辑
                // 这里只是演示一个简单的Toast提示
                Toast.makeText(goodsPingjiaActivity.this, "评价成功", Toast.LENGTH_SHORT).show();

            }
        });
    }
}

