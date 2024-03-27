package com.example.goods.adapter;

import androidx.recyclerview.widget.RecyclerView;
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

import com.bumptech.glide.request.RequestOptions;
import com.example.goods.R;
import com.example.goods.bean.Review;
import com.example.goods.bean.goods;
import com.example.goods.bean.User;
import com.example.goods.ui.activity.ReviewActivity;
import com.example.goods.ui.activity.ReviewListActivity;

import org.litepal.crud.DataSupport;
import java.util.ArrayList;
import java.util.List;

//评论适配器
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{
    private List<Review> list =new ArrayList<>();
    private Context mActivity;
    private RequestOptions headerRO = new RequestOptions().circleCrop();//圆角变换
    private LinearLayout llEmpty;
    private RecyclerView rvReviewList;
    private ReviewAdapter.ItemListener mItemListener;
    private String title;
    public void setItemListener(ReviewAdapter.ItemListener itemListener){
        this.mItemListener = itemListener;
    }
    public ReviewAdapter(LinearLayout llEmpty, RecyclerView rvReviewList){
        this.llEmpty = llEmpty;
        this.rvReviewList =rvReviewList;
    }
    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mActivity = viewGroup.getContext();
        View view= LayoutInflater.from(mActivity).inflate(R.layout.item_rv_review_list,viewGroup,false);

        return new ReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder viewHolder, int i) {
        Review review = list.get(i);


        User user = DataSupport.where("account = ? ", review.getAccount()).findFirst(User.class);
        if (review != null && user!=null ) {
            viewHolder.date.setText(review.getDate());
            viewHolder.rating.setText(String.format("评分:%s",review.getRating().toString()));

            viewHolder.title.setText(review.getTitle());
            viewHolder.content.setText(review.getContent());

                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
                        dialog.setMessage("确认要删除该评论吗");
                        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.remove(review);
                                review.delete();
                                notifyDataSetChanged();
                                Toast.makeText(mActivity,"删除成功", Toast.LENGTH_LONG).show();
                                if (list!=null && list.size() > 0){
                                    rvReviewList.setVisibility(View.VISIBLE);
                                    llEmpty.setVisibility(View.GONE);
                                }else {
                                    rvReviewList.setVisibility(View.GONE);
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
    public void addItem(List<Review> listAdd) {
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

        private TextView date;
        private TextView rating;

        private TextView title;
        private TextView content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            rating = itemView.findViewById(R.id.rating);

            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);


        }
    }
    public interface ItemListener{
        void ItemClick(Review review);
    }
}
