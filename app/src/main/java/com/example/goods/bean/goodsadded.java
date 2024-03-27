package com.example.goods.bean;

import org.litepal.crud.DataSupport;


//已上传的商品
public class goodsadded extends DataSupport {
    private String account;//账号
    private String title;//标题

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public goodsadded(String account, String title) {
        this.account = account;
        this.title = title;
    }
}