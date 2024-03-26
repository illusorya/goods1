package com.example.goods.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.goods.R;
import com.example.goods.bean.goods;
import com.example.goods.bean.goodsadded;
import com.example.goods.util.SPUtils;
import com.example.goods.widget.ActionBar;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 添加页面
 */
public class AddgoodsActivity extends AppCompatActivity {
    private ActionBar mActionBar;//标题栏
    private Activity myActivity;
    private EditText etTitle;//标题
    private EditText etIssuer;//发布单位
    private EditText etImg;//图片
    private Spinner spType;//类型
    private EditText etContent;//内容
    private ImageView ivImg;//图片
    SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private goods mgoods;
    private goodsadded added;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity = this;
        setContentView(R.layout.activity_goods_add);
        etTitle = findViewById(R.id.title);
        etIssuer = findViewById(R.id.issuer);
        spType = findViewById(R.id.type);
        etImg = findViewById(R.id.img);
        etContent = findViewById(R.id.content);
        ivImg = findViewById(R.id.iv_img);
        mActionBar = findViewById(R.id.myActionBar);
        //侧滑菜单
        mActionBar.setData(myActivity,"编辑商品信息", R.drawable.ic_back, 0, 0, getResources().getColor(R.color.colorPrimary), new ActionBar.ActionBarClickListener() {
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
        mgoods = (goods) getIntent().getSerializableExtra("goods");
        if (mgoods !=null){
            etTitle.setText(mgoods.getTitle());
            spType.setSelection(mgoods.getTypeId());
            etImg.setText(mgoods.getImg());
            etIssuer.setText(mgoods.getIssuer());
            etContent.setText(mgoods.getContent());
            spType.setSelection(mgoods.getTypeId(),true);
            Glide.with(myActivity)
                    .asBitmap()
                    .load(mgoods.getImg())
                    .error(R.drawable.ic_error)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(ivImg);
        }
        ivImg.setVisibility(mgoods ==null? View.GONE: View.VISIBLE);
    }

    public void save(View view){
        String title = etTitle.getText().toString();
        String issuer = etIssuer.getText().toString();
        String img = etImg.getText().toString();
        String content = etContent.getText().toString();
        Integer typeId =  spType.getSelectedItemPosition();
        String account = (String) SPUtils.get(myActivity, SPUtils.ACCOUNT,"");
        if ("".equals(title)) {
            Toast.makeText(myActivity,"标题不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if ("".equals(issuer)) {
            Toast.makeText(myActivity,"价格不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if ("".equals(img)) {
            Toast.makeText(myActivity,"图片地址不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if ("".equals(content)) {
            Toast.makeText(myActivity,"描述不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        goodsadded goodsadded = null;
        goods goods = null;
        if (!title.equals(mgoods != null? mgoods.getTitle():"")){
            goods = DataSupport.where("title = ?",title).findFirst(goods.class);
        }
        if (goods == null ){
            if (mgoods != null){
                goods = DataSupport.where("title = ?", mgoods.getTitle()).findFirst(goods.class);
                goods.setTypeId(typeId);
                goods.setTitle(title);
                goods.setIssuer(issuer);
                goods.setImg(img);
                goods.setContent(content);
                goodsadded.setAccount(account);
            }else {
                goods = new goods(typeId,title,img,content,issuer,sf.format(new Date()));
            }
            goods.save();
            goodsadded = new goodsadded(account, title);
            goodsadded.save();
            setResult(RESULT_OK);
            finish();
            Toast.makeText(myActivity,"保存成功", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(myActivity,"该标题已存在", Toast.LENGTH_LONG).show();
        }
    }
}
