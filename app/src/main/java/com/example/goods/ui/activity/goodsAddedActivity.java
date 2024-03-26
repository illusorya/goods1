package com.example.goods.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goods.R;
import com.example.goods.adapter.goodsaddedAdapter;
import com.example.goods.bean.goods;
import com.example.goods.bean.goodsadded;
import com.example.goods.util.SPUtils;
import com.example.goods.widget.ActionBar;

import org.litepal.crud.DataSupport;

import java.util.List;



public class goodsAddedActivity extends AppCompatActivity {
    private Activity myActivity;
    private ActionBar mTitleBar;//标题栏
    private LinearLayout llEmpty;
    private RecyclerView rvgoodsaddedList;
    private goodsaddedAdapter mgoodsaddedAdapter;
    private List<goodsadded> madded;
    private String account;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_added);
        myActivity = this;
        rvgoodsaddedList = findViewById(R.id.goodsAdded);
        llEmpty = findViewById(R.id.ll_empty);
        mTitleBar = (ActionBar)findViewById(R.id.myActionBar);
        mTitleBar.setData(myActivity,"已上传的商品", R.drawable.ic_back, 0, 0,getResources().getColor(R.color.colorPrimary), new ActionBar.ActionBarClickListener() {
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
        account = (String) SPUtils.get(myActivity, SPUtils.ACCOUNT,"");
        LinearLayoutManager layoutManager=new LinearLayoutManager(myActivity);
        //=1.2、设置为垂直排列，用setOrientation方法设置(默认为垂直布局)
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //=1.3、设置recyclerView的布局管理器
        rvgoodsaddedList.setLayoutManager(layoutManager);
        //==2、实例化适配器
        //=2.1、初始化适配器
        mgoodsaddedAdapter=new goodsaddedAdapter(llEmpty,rvgoodsaddedList);
        //=2.3、设置recyclerView的适配器
        rvgoodsaddedList.setAdapter(mgoodsaddedAdapter);
        loadData();//加载数据
        mgoodsaddedAdapter.setItemListener(new goodsaddedAdapter.ItemListener() {
            @Override
            public void ItemClick(goodsadded collect1) {
                Intent intent = new Intent(myActivity, goodsAddedDetailActivity.class);;
                goods news = DataSupport.where("title = ?",collect1.getTitle()).findFirst(goods.class);
                intent.putExtra("goods",news);
                startActivityForResult(intent,100);
            }
        });
    }

    /**
     * 加载数据
     */
    private void loadData(){
        madded = DataSupport.where("account = ?",account).find(goodsadded.class);//查询上传记录
        if (madded !=null && madded.size()>0){
            rvgoodsaddedList.setVisibility(View.VISIBLE);
            llEmpty.setVisibility(View.GONE);
            mgoodsaddedAdapter.addItem(madded);
        }else {
            rvgoodsaddedList.setVisibility(View.GONE);
            llEmpty.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}

