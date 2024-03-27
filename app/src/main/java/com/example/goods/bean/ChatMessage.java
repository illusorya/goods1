package com.example.goods.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

//聊天信息
public class ChatMessage extends DataSupport implements Serializable {
    private String sender;
    private String receiver;
    private String message;
    private String timestamp;


    public String getSender() {
        return sender;
    }
    public void setSender(String sender){
        this.sender = sender;
    }
    public String getReceiver() {
        return receiver;
    }
    public void setReceiver(String receiver){
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp){
        this.timestamp = timestamp;
    }

    public ChatMessage(String sender, String receiver,String message, String timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.timestamp = timestamp;
    }
}