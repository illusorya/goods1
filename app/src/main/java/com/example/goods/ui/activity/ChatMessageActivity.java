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
import com.example.goods.adapter.BrowseAdapter;
import com.example.goods.adapter.ChatAdapter;
import com.example.goods.adapter.ChatMessageAdapter;
import com.example.goods.bean.Browse;
import com.example.goods.bean.ChatMessage;
import com.example.goods.bean.goods;
import com.example.goods.util.SPUtils;
import com.example.goods.widget.ActionBar;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * 聊天记录
 */
public class ChatMessageActivity extends AppCompatActivity {
    private Activity myActivity;
    private ActionBar mTitleBar;//标题栏
    private LinearLayout llEmpty;
    private RecyclerView rvChatMessageList;
    private ChatMessageAdapter mChatAdapter;
    private List<ChatMessage> mChatMessage;
    private String account;
    SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        myActivity = this;
        rvChatMessageList = findViewById(R.id.rv_chatmessage_list);
        llEmpty = findViewById(R.id.ll_empty);
        mTitleBar = (ActionBar)findViewById(R.id.myActionBar);
        mTitleBar.setData(myActivity,"我的消息", R.drawable.ic_back, 0, 0,getResources().getColor(R.color.colorPrimary), new ActionBar.ActionBarClickListener() {
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
        rvChatMessageList.setLayoutManager(layoutManager);
        //==2、实例化适配器
        //=2.1、初始化适配器
        mChatAdapter=new ChatMessageAdapter(llEmpty,rvChatMessageList);
        //=2.3、设置recyclerView的适配器
        rvChatMessageList.setAdapter(mChatAdapter);
        loadData();//加载数据
        mChatAdapter.setItemListener(new ChatMessageAdapter.ItemListener() {
            @Override
            public void ItemClick(ChatMessage chatMessage) {
                Intent intent = new Intent(myActivity, ChatActivity.class);
                goods news = DataSupport.where("account = ? and account = ?",
                        DataSupport.where("sender = ?",account).findFirst(ChatMessage.class).getReceiver(),
                        DataSupport.where("receiver = ?",account).findFirst(ChatMessage.class).getSender()).findFirst(goods.class);
                intent.putExtra("goods",news);
                startActivityForResult(intent,100);
            }
        });
    }

    /**
     * 加载数据
     */
    private void loadData(){
        mChatMessage = DataSupport.where("sender = ? or receiver = ?",account,account).find(ChatMessage.class);//查询浏览记录
        Collections.reverse(mChatMessage);
        if (mChatMessage !=null && mChatMessage.size()>0){
            rvChatMessageList.setVisibility(View.VISIBLE);
            llEmpty.setVisibility(View.GONE);
            mChatAdapter.addItem(mChatMessage);
        }else {
            rvChatMessageList.setVisibility(View.GONE);
            llEmpty.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}