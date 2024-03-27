package com.example.goods.ui.fragment;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goods.R;
import com.example.goods.adapter.goodsaddedAdapter;
import com.example.goods.bean.goods;
import com.example.goods.bean.goodsadded;
import com.example.goods.ui.activity.goodsAddedDetailActivity;
import com.example.goods.util.SPUtils;
import com.example.goods.widget.ActionBar;

import org.litepal.crud.DataSupport;

import java.util.List;

public class goodsAddedFragment extends Fragment{
    private Activity myActivity;
    private LinearLayout llEmpty;
    private RecyclerView rvgoodsaddedList;
    private goodsaddedAdapter mgoodsaddedAdapter;
    private List<goodsadded> madded;
    private String account;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myActivity = (Activity) context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_goods_added,container,false);
        rvgoodsaddedList = view.findViewById(R.id.goodsAdded);
        llEmpty = view.findViewById(R.id.ll_empty);
        //获取控件
        initView();
        return view;
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
