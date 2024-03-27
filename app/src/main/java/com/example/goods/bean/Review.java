package com.example.goods.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

//评论
public class Review extends DataSupport implements Serializable {
    private String title;//标题
    private String account;//账号
    private String content;//内容
    private String date;
    private Float rating;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
    public Review(String title,String account,String content,String date,Float rating){
        this.title = title;
        this.account = account;
        this.content = content;
        this.date = date;
        this.rating = rating;

    }
}
