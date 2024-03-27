package com.example.goods.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.goods.R;
import com.example.goods.adapter.CarAdapter;
import com.example.goods.adapter.OrderAdapter;
import com.example.goods.adapter.ReviewListAdapter;
import com.example.goods.bean.Browse;
import com.example.goods.bean.Orders;
import com.example.goods.bean.Review;
import com.example.goods.bean.goods;
import com.example.goods.util.SPUtils;
import com.example.goods.widget.ActionBar;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReviewListActivity extends AppCompatActivity {
    private ActionBar mTitleBar;//标题栏
    private Activity myActivity;
    private LinearLayout llEmpty;
    private RecyclerView rvReviewListList;
    public ReviewListAdapter myReviewListAdapter;
    private Boolean mIsAdmin;

    private List<Review> myReview = new ArrayList<>();
    private String account;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewlist);
        goods goods = (goods) getIntent().getSerializableExtra("goods");
        title = goods.getTitle();
        myActivity = this;
        rvReviewListList =findViewById(R.id.rv_reviewlist_list);
        llEmpty =findViewById(R.id.ll_empty);

        mTitleBar = (ActionBar) findViewById(R.id.myActionBar);
        mTitleBar.setData(myActivity,"商品评论", R.drawable.ic_back, 0, 0, getResources().getColor(R.color.colorPrimary), new ActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {
            }
        });  initView();


    }
    private void initView() {


        account = (String) SPUtils.get(myActivity, SPUtils.ACCOUNT, "");

        LinearLayoutManager layoutManager=new LinearLayoutManager(myActivity);
        //=1.2、设置为垂直排列，用setOrientation方法设置(默认为垂直布局)
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //=1.3、设置recyclerView的布局管理器
        rvReviewListList.setLayoutManager(layoutManager);
        //==2、实例化适配器
        //=2.1、初始化适配器
        myReviewListAdapter =new ReviewListAdapter(llEmpty,rvReviewListList);
        //=2.3、设置recyclerView的适配器
        rvReviewListList.setAdapter(myReviewListAdapter);
        loadData();//加载数据




    }
    /**
     * 加载数据
     */
    private void loadData(){



        myReview = DataSupport.where("title = ?",title).find(Review.class);

        Collections.reverse(myReview);

        if (myReview !=null && myReview.size()>0){
            rvReviewListList.setVisibility(View.VISIBLE);
            llEmpty.setVisibility(View.GONE);
            myReviewListAdapter.addItem(myReview);
        }else {
            rvReviewListList.setVisibility(View.GONE);
            llEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100){
            loadData();
        }
    }

}
