package com.example.goods.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.goods.R;
import com.example.goods.bean.Browse;
import com.example.goods.bean.ChatMessage;
import com.example.goods.util.SPUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 聊天记录适配器
 */

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ViewHolder> {
    private List<ChatMessage> list =new ArrayList<>();
    private Context mActivity;
    private ItemListener mItemListener;
    private LinearLayout llEmpty;
    private RecyclerView rvNewsList;
    private String account1;
    SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public void setItemListener(ItemListener itemListener){
        this.mItemListener = itemListener;
    }
    public ChatMessageAdapter(LinearLayout llEmpty, RecyclerView rvNewsList){
        this.llEmpty = llEmpty;
        this.rvNewsList =rvNewsList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mActivity = viewGroup.getContext();
        View view= LayoutInflater.from(mActivity).inflate(R.layout.item_rv_chat_list,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String account = (String) SPUtils.get(mActivity, SPUtils.ACCOUNT,"");
        ChatMessage chatMessage = list.get(i);
        if (chatMessage.getSender() != account){
            account1 = chatMessage.getSender();}
        else if (chatMessage.getReceiver() != account) {
            account1 = chatMessage.getReceiver();

        }

        if (chatMessage != null) {
            viewHolder.nickname.setText(account1);
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
        private TextView nickname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nickname = itemView.findViewById(R.id.nickname);
        }
    }
    public interface ItemListener{
        void ItemClick(ChatMessage chatMessage);
    }
}

