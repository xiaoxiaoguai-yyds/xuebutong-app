package com.stdio.mobiles;


import android.content.Context;
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

public class sponsor_spq extends ByRecyclerView.Adapter<sponsor_spq.ViewHold> {
	private static List<sponsor_msg> msponsor_msg = new ArrayList<>();
	private Context mContext;
	
	public sponsor_spq(List<sponsor_msg> list, Context context) {
		this.msponsor_msg = list;
		this.mContext = context;
	}
	
	@NonNull
	@Override
	public sponsor_spq.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		//View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sponsor_list, null);
		View view = LayoutInflater.from(mContext).inflate(R.layout.sponsor_list, parent, false);
		return new ViewHold(view);
	}
	
	public void onDataChanged() {
		notifyDataSetChanged(); // 通知适配器数据已改变
	}
	
	
	@Override
	public void onBindViewHolder(@NonNull sponsor_spq.ViewHold holder, int position) {
		sponsor_msg sponsor_msg = msponsor_msg.get(position);
		Glide.with(holder.itemView.getContext())
				.load(String.valueOf("https://q1.qlogo.cn/g?b=qq&nk=" + sponsor_msg.getsponsor_Lx() + "&s=640"))  // 这里传入返回的URL链接
				.into(holder.icon);
		holder.昵称.setText(sponsor_msg.getsponsor_Name().trim());
		holder.联系.setText("QQ:" + sponsor_msg.getsponsor_Lx().trim());
		holder.金额.setText("为学不通赞助" + sponsor_msg.getsponsor_Je().trim() + "元");
	}
	
	
	@Override
	public int getItemCount() {
		return msponsor_msg.size();
	}
	
	
	static class ViewHold extends ByRecyclerView.ViewHolder {
		TextView 昵称;
		TextView 联系;
		TextView 金额;
		ImageView icon;
		LinearLayout lb_list;
		
		public ViewHold(@NonNull View itemView) {
			super(itemView);
			昵称 = itemView.findViewById(R.id.sponsor_name);
			联系 = itemView.findViewById(R.id.sponsor_lx);
			金额 = itemView.findViewById(R.id.sponsor_je);
			icon = itemView.findViewById(R.id.icon);
		}
	}
}
