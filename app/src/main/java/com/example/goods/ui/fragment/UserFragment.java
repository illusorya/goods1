package com.example.goods.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.goods.bean.goods;
import com.example.goods.ui.activity.BrowseActivity;
import com.example.goods.ui.activity.ChatActivity;
import com.example.goods.ui.activity.ChatMessageActivity;
import com.example.goods.ui.activity.ManageActivity;
import com.example.goods.MyApplication;
import com.example.goods.ui.activity.OrderActivity;
import com.example.goods.R;
import com.example.goods.ui.activity.LoginActivity;
import com.example.goods.ui.activity.PasswordActivity;
import com.example.goods.ui.activity.PersonActivity;
import com.example.goods.ui.activity.ReviewActivity;
import com.example.goods.util.SPUtils;

import org.litepal.crud.DataSupport;

/**
 * 个人中心
 */
public class UserFragment extends Fragment {
    private Activity mActivity;
    private LinearLayout llPerson;
    private LinearLayout llSecurity;

    private LinearLayout llFavorite;
    private LinearLayout llBrowse;
    private LinearLayout llchat;
    private LinearLayout order;
    private LinearLayout review;
    private LinearLayout manage;
    private Button btnLogout;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,container,false);
        llPerson = view.findViewById(R.id.person);
        llSecurity = view.findViewById(R.id.security);
        llBrowse = view.findViewById(R.id.browse);
        llchat = view.findViewById(R.id.chat);
        order = view.findViewById(R.id.order);
        review = view.findViewById(R.id.review);
        manage = view.findViewById(R.id.manage);
        btnLogout = view.findViewById(R.id.logout);
        llFavorite = view.findViewById(R.id.favorite);
        initView();
        return view;
    }

    private void initView() {
        Boolean isAdmin = (Boolean) SPUtils.get(mActivity,SPUtils.IS_ADMIN,false);
        Boolean isSeller = (Boolean) SPUtils.get(mActivity,SPUtils.IS_SELLER,false);

        llFavorite.setVisibility(isAdmin?View.GONE:View.VISIBLE);
        order.setVisibility(isAdmin || isSeller?View.GONE:View.VISIBLE);
        review.setVisibility(isAdmin || isSeller?View.GONE:View.VISIBLE);
        llBrowse.setVisibility(isAdmin?View.GONE:View.VISIBLE);
        manage.setVisibility(isAdmin?View.VISIBLE:View.GONE);
        //个人信息
        llPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转页面
                Intent intent = new Intent(mActivity, PersonActivity.class);
                startActivity(intent);
            }
        });
        //用户管理
        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转页面
                Intent intent = new Intent(mActivity, ManageActivity.class);
                startActivity(intent);
            }
        });
        //账号安全
        llSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转页面
                Intent intent = new Intent(mActivity, PasswordActivity.class);
                startActivity(intent);
            }
        });
        //浏览记录
        llBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转页面
                Intent intent = new Intent(mActivity, BrowseActivity.class);
                startActivity(intent);
            }
        });
        llchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转页面
                Intent intent = new Intent(mActivity, ChatMessageActivity.class);
                startActivity(intent);
            }
        });

        //我的订单
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转页面
                Intent intent = new Intent(mActivity, OrderActivity.class);
                startActivity(intent);
            }
        });
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转页面
                Intent intent = new Intent(mActivity, ReviewActivity.class);
                startActivity(intent);
            }
        });
        //联系客服
        llFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, ChatActivity.class);
                goods news = DataSupport.where("account = ?","admin").findFirst(goods.class);
                intent.putExtra("goods",news);
                startActivity(intent);
            }
        });
        //退出登录
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.Instance.getMainActivity().finish();
                SPUtils.remove(mActivity,SPUtils.IS_ADMIN);
                SPUtils.remove(mActivity,SPUtils.ACCOUNT);
                startActivity(new Intent(mActivity, LoginActivity.class));
            }
        });
    }
}
