package com.example.goods.adapter;


import  android.content.Intent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.example.goods.bean.ChatMessage;
import com.example.goods.bean.Review;
import com.example.goods.ui.activity.goodsXActivity;
import com.example.goods.R;
import com.example.goods.bean.goods;
import com.example.goods.bean.Orders;
import com.example.goods.bean.User;
import com.example.goods.util.SPUtils;

import org.litepal.crud.DataSupport;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

//聊天适配器
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<ChatMessage> list =new ArrayList<>();
    private Context mActivity;
    private RequestOptions headerRO = new RequestOptions().circleCrop();//圆角变换
    private LinearLayout llEmpty;
    private RecyclerView rvChatList;
    private ItemListener mItemListener;

    public void setItemListener(ItemListener itemListener){
        this.mItemListener = itemListener;
    }
    public ChatAdapter(LinearLayout llEmpty, RecyclerView rvChatList){
        this.llEmpty = llEmpty;
        this.rvChatList =rvChatList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mActivity = viewGroup.getContext();

        View view= LayoutInflater.from(mActivity).inflate(R.layout.item_chat_message,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        ChatMessage chatMessage = list.get(i);
        User user = DataSupport.where("sender = ? ", chatMessage.getSender()).findFirst(User.class);
        if (chatMessage != null && user!=null ) {
            viewHolder.nickName.setText(String.format("用户:%s",user.getNickName()));

            viewHolder.date.setText(chatMessage.getTimestamp());

            viewHolder.content.setText(chatMessage.getMessage());

            String account1 = (String) SPUtils.get(mActivity, SPUtils.ACCOUNT,"");
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemListener!=null){
                        mItemListener.ItemClick(chatMessage);
                    }
                }
            });
        }
    }
    public void addItem(List<ChatMessage> listAdd) {
        //如果是加载第一页，需要先清空数据列表
        this.list.clear();
        if (listAdd!=null){
            //添加数据
            this.list.addAll(listAdd);
        }
        //通知RecyclerView进行改变--整体
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nickName;
        private TextView date;


        private TextView content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nickName = itemView.findViewById(R.id.inickName);
            date = itemView.findViewById(R.id.idate);

            content = itemView.findViewById(R.id.icontent);


        }
    }
    public interface ItemListener{
        void ItemClick(ChatMessage chatMessage);
    }
}