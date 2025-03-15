package com.stdio.mobiles;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.kongzue.dialogx.dialogs.MessageDialog;
import com.kongzue.dialogx.interfaces.OnBindView;
import com.kongzue.dialogx.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialogx.util.TextInfo;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class sponsor extends AppCompatActivity {
	private MessageDialog mMessageDialog;
	private RecyclerView recyclerView;
	private sponsor_spq msponsor_spq;
	private List<sponsor_msg> sponsor_msgList = new ArrayList<sponsor_msg>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sponsor);
		
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.drawable.back);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		window.setStatusBarColor(ContextCompat.getColor(this, R.color.gree));
		
		recyclerView = findViewById(R.id.lb);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(linearLayoutManager);
		
		post();
		
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sponsor_menu, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		if (id == R.id.ds) {
			mMessageDialog = MessageDialog.show("赞助支持", "软件为免费开放，服务器由本应用工作室承担,1元也是对我们的鼓励\n赞助时请备注好：昵称，QQ账号！！！", "知晓")
					.setBackgroundColor(Color.parseColor("#FDF8FC"))
					.setCustomView(new OnBindView<MessageDialog>(R.layout.img) {
						@Override
						public void onBind(MessageDialog dialog, View v) {
							ImageView vx = v.findViewById(R.id.wx);
							Glide.with(sponsor.this)
									.load(MainActivity.vx_url)
									.into(vx);
							ImageView zfb = v.findViewById(R.id.zfb);
							Glide.with(sponsor.this)
									.load(MainActivity.zfb_url)
									.into(zfb);
						}
					})
					.setOkButton(new OnDialogButtonClickListener<MessageDialog>() {
						@Override
						public boolean onClick(MessageDialog baseDialog, View v) {
							
							return false;
						}
					})
					.setOkTextInfo(new TextInfo().setFontColor(Color.parseColor("#E30113")).setBold(true));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	protected void post() {
		OkHttpClient okHttpClient = new OkHttpClient();
		Request request = new Request.Builder()
				.url("http://mobiles.stdio.com.cn/bk/")
				.get()
				.build();
		Call call = okHttpClient.newCall(request);
		call.enqueue(new Callback() {
			@Override
			public void onFailure(@NotNull Call call, @NotNull IOException e) {
				e.printStackTrace();
				
			}
			
			@Override
			public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
				Document doc = Jsoup.parse(response.body().string());
				Elements items = doc.select("div.mdui-container-fluid mdui-list");
				if (items.size() != 0) {
					for (org.jsoup.nodes.Element item : items) {
						String data = item.select("mdui-list mdui-list-item").text();
						String title = data.substring(data.indexOf(""), data.indexOf("-"));
						String qq = data.substring(data.indexOf("-") + 1, data.indexOf("-赞助"));
						String je = data.substring(data.indexOf("-赞助") + 3, data.indexOf("元"));
						Log.w("\\\\\\", title);
						Log.w("\\\\\\", qq);
						Log.w("\\\\\\", je);
						sponsor_msgList.add(new sponsor_msg(title, qq, je));
						//msgList.add(new msg(teacherfactor, schools, imageurl, name, courseSquareUrl, CourseId, personId, classId, key, cookie));
					}
					sponsor.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							msponsor_spq = new sponsor_spq(sponsor_msgList, sponsor.this);
							recyclerView.setAdapter(msponsor_spq);
						}
					});
				} else {
				
				}
			}
		});
		
	}
}