package com.example.goods.ui.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goods.R;
import com.example.goods.adapter.CarAdapter;
import com.example.goods.adapter.OrderAdapter;
import com.example.goods.adapter.ChatAdapter;
import com.example.goods.bean.Browse;
import com.example.goods.bean.ChatMessage;
import com.example.goods.bean.Orders;
import com.example.goods.bean.Review;
import com.example.goods.bean.User;
import com.example.goods.bean.goods;
import com.example.goods.util.SPUtils;
import com.example.goods.widget.ActionBar;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private ActionBar mTitleBar;//标题栏
    private Activity myActivity;
    private LinearLayout llEmpty;
    private RecyclerView rvChatList;
    public ChatAdapter myChatAdapter;
    private Boolean mIsAdmin;

    private List<ChatMessage> myChatMessage = new ArrayList<>();
    private String account;
    private EditText etmessage;
    private Button btnsend;
    private ChatMessage mchatmessage;
    SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        goods goods = (goods) getIntent().getSerializableExtra("goods");
        User user = DataSupport.where("account = ?",goods.getAccount()).findFirst(User.class);

        myActivity = this;
        rvChatList =findViewById(R.id.rv_chat_list);
        llEmpty =findViewById(R.id.ll_empty);

        mTitleBar = (ActionBar) findViewById(R.id.myActionBar);
        mTitleBar.setData(myActivity,user.getNickName(), R.drawable.ic_back, 0, 0, getResources().getColor(R.color.colorPrimary), new ActionBar.ActionBarClickListener() {
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
        mchatmessage = (ChatMessage) getIntent().getSerializableExtra("ChatMessage");


        account = (String) SPUtils.get(myActivity, SPUtils.ACCOUNT, "");

        LinearLayoutManager layoutManager=new LinearLayoutManager(myActivity);
        //=1.2、设置为垂直排列，用setOrientation方法设置(默认为垂直布局)
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //=1.3、设置recyclerView的布局管理器
        rvChatList.setLayoutManager(layoutManager);
        //==2、实例化适配器
        //=2.1、初始化适配器
        myChatAdapter =new ChatAdapter(llEmpty,rvChatList);
        //=2.3、设置recyclerView的适配器
        rvChatList.setAdapter(myChatAdapter);
        loadData();//加载数据




    }
    public void save(View view){
        goods goods = (goods) getIntent().getSerializableExtra("goods");
        String receiver = goods.getAccount();
        String content = etmessage.getText().toString();
        String account = (String) SPUtils.get(myActivity, SPUtils.ACCOUNT,"");
        String date = sf.format(new Date());

        if ("".equals(content)) {
            Toast.makeText(myActivity,"消息不能为空", Toast.LENGTH_LONG).show();
            return;
        }

        ChatMessage chatMessage = null;
        if (chatMessage == null ){
            if (mchatmessage != null){
                chatMessage = DataSupport.where("timestamp = ?", mchatmessage.getTimestamp()).findFirst(ChatMessage.class);

                chatMessage.setSender(account);
                chatMessage.setReceiver(receiver);
                chatMessage.setMessage(content);


            }else {
                chatMessage = new ChatMessage(account,receiver,content,sf.format(new Date()));
            }
            chatMessage.save();
            myChatAdapter.notifyDataSetChanged();
            loadData();
            setResult(RESULT_OK);
            finish();
        }else {
            return ;
        }
    }
    /**
     * 加载数据
     */
    private void loadData(){



        myChatMessage = DataSupport.where("sender = ? or receiver = ?",account,account).find(ChatMessage.class);

        Collections.reverse(myChatMessage);

        if (myChatMessage !=null && myChatMessage.size()>0){
            rvChatList.setVisibility(View.VISIBLE);
            llEmpty.setVisibility(View.GONE);
            myChatAdapter.addItem(myChatMessage);
        }else {
            rvChatList.setVisibility(View.GONE);
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
