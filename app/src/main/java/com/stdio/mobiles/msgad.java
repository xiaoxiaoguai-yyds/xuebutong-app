package com.stdio.mobiles;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import me.jingbin.library.ByRecyclerView;

import java.util.ArrayList;
import java.util.List;


public class msgad extends ByRecyclerView.Adapter<msgad.ViewHold> {
    private static List<msg> mmsg = new ArrayList<>();
    private Context mContext;

    public msgad(List<msg> list, Context context) {
        this.mmsg = list;
        this.mContext = context;
    }

    @NonNull
    @Override
    public msgad.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, null);
        return new ViewHold(view);
    }

    public void onDataChanged() {
        notifyDataSetChanged(); // 通知适配器数据已改变
    }


    @Override
    public void onBindViewHolder(@NonNull msgad.ViewHold holder, int position) {
        msg msg = mmsg.get(position);
        Glide.with(holder.itemView.getContext())
                .load(String.valueOf(msg.getImageurl()).replace("http://", "https://"))  // 这里传入返回的URL链接
                .into(holder.头像);
        holder.老师名字.setText(msg.getTeacherfactor().trim());
        holder.课程名称.setText(msg.getName().trim());
        holder.课程介绍.setText(msg.getSchools().trim());
        holder.lb_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //userId=
                Uri uri = Uri.parse(msg.getCourseSquareUrl());
                String userId = uri.getQueryParameter("userId");
                Log.w("//////", msg.getCourseSquareUrl().replace("app?courseId=", "app.html?/").replace("&personId=", "/").replace("&classId=", "/").replace("&userId=", "/") + "/timeid#/category?courseId=" + msg.getCourseId() + "&personId=" + msg.getPersonId() + "&classId=" + msg.getClassId() + "&userId=" + userId);
                String url = msg.getCourseSquareUrl().replace("app?courseId=", "app.html?/").replace("&personId=", "/").replace("&classId=", "/").replace("&userId=", "/") + "/timeid#/category?courseId=" + msg.getCourseId() + "&personId=" + msg.getPersonId() + "&classId=" + msg.getClassId() + "&userId=" + userId;
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("url", url);
                intent.putExtra("key", msg.getKey());
                intent.putExtra("title", msg.getName());
                intent.putExtra("cookie", msg.getCookie());
                mContext.startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return mmsg.size();
    }


    static class ViewHold extends ByRecyclerView.ViewHolder {
        ImageView 头像;
        TextView 课程名称;
        TextView 课程介绍;
        TextView 老师名字;
        LinearLayout lb_list;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            头像 = itemView.findViewById(R.id.img);
            课程名称 = itemView.findViewById(R.id.title);
            老师名字 = itemView.findViewById(R.id.name);
            课程介绍 = itemView.findViewById(R.id.js);
            lb_list = itemView.findViewById(R.id.lb_list);
        }
    }
}
