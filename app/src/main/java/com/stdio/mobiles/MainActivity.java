package com.stdio.mobiles;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.kongzue.dialogx.DialogX;
import com.kongzue.dialogx.dialogs.MessageDialog;
import com.kongzue.dialogx.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialogx.util.TextInfo;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

public class MainActivity extends AppCompatActivity {
	public static String API_address;
	public static String vx_url;
	public static String zfb_url;
	public static String vide_address;
	private static String vis = "1.5";
	
	@SuppressLint("MissingInflatedId")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		DialogX.init(this);
		com.kongzue.dialogx.DialogX.globalStyle = com.kongzue.dialogxmaterialyou.style.MaterialYouStyle.style();
		
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		window.setStatusBarColor(ContextCompat.getColor(this, R.color.gree));
		
		LottieAnimationView lottieAnimationView = findViewById(R.id.lottie);
		lottieAnimationView.setAnimation(R.raw.load);
		lottieAnimationView.setRepeatMode(LottieDrawable.REVERSE);
		lottieAnimationView.setRepeatCount(LottieDrawable.INFINITE);
		lottieAnimationView.playAnimation();
		//main()
		if (getFile("v2.so").equals("")) {
			setFile("true", "v2.so");
		}
		if (getFile("v1.so").equals("")) {
			setFile("true", "v2.so");
		}
		if (getFile("v3.so").equals("")) {
			setFile("true", "v3.so");
		}
		Log.w("------", getFile("v2.so"));
		if (getFile("v4.so").equals("")) {
			setFile("true", "v4.so");
		}
		Log.w("\\\\\\", getFile("time.txt"));
		if (getFile("time.txt").equals("")) {
			setFile("2000", "time.txt");
		}
		
		if (getFile("v6.so").equals("")) {
			setFile("true", "v6.so");
		}
		load();
	}
	
	private void load() {
		OkHttpClient okHttpClient = new OkHttpClient();
		Request request = new Request.Builder()
				.url("https://wds.ecsxs.com/231547.json")
				.get()
				.build();
		Call call = okHttpClient.newCall(request);
		call.enqueue(new Callback() {
			@Override
			public void onFailure(@NotNull Call call, @NotNull IOException e) {
			
			}
			
			@Override
			public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
				if (response.body() != null) {
					try {
						JSONObject jsonObject = new JSONObject(response.body().string());
						String version = jsonObject.getString("version");
						String Update_titl = jsonObject.getString("Update_title");
						String Updated_content = jsonObject.getString("Updated_content");
						String Update_address = jsonObject.getString("Update_address");
						API_address = jsonObject.getString("API_address");
						vide_address = jsonObject.getString("vide_address");
						vx_url = jsonObject.getString("vx_url");
						zfb_url = jsonObject.getString("zfb_url");
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if (Float.parseFloat(version) > Float.parseFloat(vis)) {
									MessageDialog.show(Update_titl, Updated_content, "更新")
											//.setTitleIcon(R.mipmap.i)
											.setBackgroundColor(Color.parseColor("#FDF8FC"))
											.setOkButton(new OnDialogButtonClickListener<MessageDialog>() {
												@Override
												public boolean onClick(MessageDialog baseDialog, View v) {
													startActivity(new android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(Update_address)));
													return true;
												}
											})
											.setOkTextInfo(new TextInfo().setFontColor(Color.parseColor("#E30113")).setBold(true))
											.setCancelable(false);
								} else {
									Handler handler = new Handler();
									handler.postDelayed(new Runnable() {
										@Override
										public void run() {
											Intent intent = new Intent(MainActivity.this, homeActivity.class);
											startActivity(intent);
											finish();
										}
									}, 2000);
								}
							}
						});
						
					} catch (JSONException e) {
						throw new RuntimeException(e);
					}
				}
			}
		});
	}
	
	
	public void setFile(String str, String file) {
		FileOutputStream fos = null;
		BufferedWriter writer = null;
		
		try {
			fos = openFileOutput(file, Context.MODE_PRIVATE);
			writer = new BufferedWriter(new OutputStreamWriter(fos));
			try {
				writer.write(str);
				Log.w("file", "记录成功");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//读取cookie
	public String getFile(String file) {
		FileInputStream fis = null;
		BufferedReader reader = null;
		StringBuilder content = new StringBuilder();
		try {
			fis = openFileInput(file);
			reader = new BufferedReader(new InputStreamReader(fis));
			String str;
			
			while ((str = reader.readLine()) != null) {
				content.append(str);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return content.toString();
	}
}
