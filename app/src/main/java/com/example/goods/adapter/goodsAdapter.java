package com.example.goods.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.goods.R;
import com.example.goods.bean.goods;
import com.example.goods.util.SPUtils;

import java.util.ArrayList;
import java.util.List;


public class goodsAdapter extends RecyclerView.Adapter<goodsAdapter.ViewHolder> {
    private List<goods> list =new ArrayList<>();
    private Context mActivity;
    private ItemListener mItemListener;
    private LinearLayout llEmpty;
    private RecyclerView rvgoodsList;
    public void setItemListener(ItemListener itemListener){
        this.mItemListener = itemListener;
    }
    public goodsAdapter(LinearLayout llEmpty, RecyclerView rvgoodsList){
        this.llEmpty = llEmpty;
        this.rvgoodsList =rvgoodsList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mActivity = viewGroup.getContext();
        View view= LayoutInflater.from(mActivity).inflate(R.layout.item_rv_goods_list,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        goods goods = list.get(i);
        if (goods != null) {
            viewHolder.title.setText(goods.getTitle());
            viewHolder.author_name.setText(String.format("￥%s",goods.getIssuer()));

            Glide.with(mActivity)
                    .asBitmap()
                    .load(goods.getImg())
                    .error(R.drawable.ic_error)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(viewHolder.img);
            Boolean isAdmin = (Boolean) SPUtils.get(mActivity,SPUtils.IS_ADMIN,false);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemListener!=null){
                        mItemListener.ItemClick(goods);
                    }
                }
            });
            if (isAdmin){
                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
                        dialog.setMessage("确认要删除该商品吗?");
                        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.remove(goods);
                                goods.delete();
                                notifyDataSetChanged();
                                Toast.makeText(mActivity,"删除成功", Toast.LENGTH_LONG).show();
                                if (list!=null && list.size() > 0){
                                    rvgoodsList.setVisibility(View.VISIBLE);
                                    llEmpty.setVisibility(View.GONE);
                                }else {
                                    rvgoodsList.setVisibility(View.GONE);
                                    llEmpty.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                        dialog.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        return false;
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void addItem(List<goods> listAdd) {
        //如果是加载第一页，需要先清空数据列表
        this.list.clear();
        if (listAdd!=null){
            //添加数据
            this.list.addAll(listAdd);
        }
        //通知RecyclerView进行改变--整体
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView author_name;

        private ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            author_name = itemView.findViewById(R.id.author_name);

            img = itemView.findViewById(R.id.img);
        }
    }

    public interface ItemListener{
        void ItemClick(goods goods);
    }
}
