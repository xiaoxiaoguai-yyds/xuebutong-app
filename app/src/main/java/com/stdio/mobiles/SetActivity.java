package com.stdio.mobiles;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import com.kongzue.dialogx.DialogX;
import com.kongzue.dialogx.dialogs.InputDialog;
import com.kongzue.dialogx.dialogs.MessageDialog;
import com.kongzue.dialogx.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialogx.interfaces.OnInputDialogButtonClickListener;
import com.kongzue.dialogx.util.TextInfo;
import com.kongzue.dialogxmaterialyou.style.MaterialYouStyle;
import com.suke.widget.SwitchButton;

import java.io.*;

public class SetActivity extends AppCompatActivity {
	private TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set);
		
		
		DialogX.init(this);
		DialogX.globalStyle = MaterialYouStyle.style();
		
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		window.setStatusBarColor(ContextCompat.getColor(this, R.color.gree));
		
		@SuppressLint({"MissingInflatedId", "LocalSuppress"})
		Toolbar toolbar = findViewById(R.id.toolbar);
		toolbar.setNavigationIcon(R.drawable.back);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		textView = findViewById(R.id.videjc);
		textView.setText(getFile("time.txt"));
		
		SwitchButton v1 = findViewById(R.id.v1);//自动登录
		if (getFile("v1.so").contains("true")) {
			v1.setChecked(true);
		} else {
			v1.setChecked(false);
		}
		v1.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(SwitchButton view, boolean isChecked) {
				setFile(String.valueOf(isChecked), "v1.so");
			}
		});
		
		SwitchButton v2 = findViewById(R.id.v2);//自动保存cookie
		if (getFile("v2.so").contains("true")) {
			v2.setChecked(true);
		} else {
			v2.setChecked(false);
		}
		v2.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(SwitchButton view, boolean isChecked) {
				setFile(String.valueOf(isChecked), "v2.so");
			}
		});
		
		SwitchButton v3 = findViewById(R.id.v3);//自动是否播放
		if (getFile("v3.so").contains("true")) {
			v3.setChecked(true);
		} else {
			v3.setChecked(false);
		}
		v3.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(SwitchButton view, boolean isChecked) {
				setFile(String.valueOf(isChecked), "v3.so");
			}
		});
		
		SwitchButton v4 = findViewById(R.id.v4);//自动跳转下一节
		if (getFile("v4.so").contains("true")) {
			v4.setChecked(true);
		} else {
			v4.setChecked(false);
		}
		v4.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(SwitchButton view, boolean isChecked) {
				setFile(String.valueOf(isChecked), "v4.so");
			}
		});
		
		@SuppressLint({"MissingInflatedId", "LocalSuppress"})
		LinearLayout linearLayout = findViewById(R.id.eim);
		
		SwitchButton v5 = findViewById(R.id.v5);//自动跳转下一节
		if (getFile("v5.so").contains("true")) {
			v5.setChecked(true);
			linearLayout.setVisibility(View.VISIBLE);
		} else {
			v5.setChecked(false);
			linearLayout.setVisibility(View.GONE);
		}
		v5.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(SwitchButton view, boolean isChecked) {
				setFile(String.valueOf(isChecked), "v5.so");
				if (isChecked == true) {
					linearLayout.setVisibility(View.VISIBLE);
				} else {
					linearLayout.setVisibility(View.GONE);
				}
			}
		});
		
		SwitchButton v6 = findViewById(R.id.v6);//自动静音
		if (getFile("v6.so").contains("true")) {
			v6.setChecked(true);
		} else {
			v6.setChecked(false);
		}
		v6.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(SwitchButton view, boolean isChecked) {
				setFile(String.valueOf(isChecked), "v6.so");
			}
		});
		
		SwitchButton v7 = findViewById(R.id.v7);//2倍速度播放
		if (getFile("v7.so").contains("true")) {
			v7.setChecked(true);
		} else {
			v7.setChecked(false);
		}
		v7.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(SwitchButton view, boolean isChecked) {
				setFile(String.valueOf(isChecked), "v7.so");
			}
		});
		
		SwitchButton v8 = findViewById(R.id.v8);//2倍速度播放
		if (getFile("v8.so").contains("true")) {
			v8.setChecked(true);
		} else {
			v8.setChecked(false);
		}
		v8.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(SwitchButton view, boolean isChecked) {
				setFile(String.valueOf(isChecked), "v8.so");
			}
		});
		
		
		EditText editText = findViewById(R.id.qq);
		editText.setText(getFile("qq.txt"));
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			
			}
			
			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				// 文本变化时的回调
				String text = charSequence.toString();
				setFile(text, "qq.txt");
			}
			
			@Override
			public void afterTextChanged(Editable editable) {
				// 文本变化后的回调
			}
		});
		
		@SuppressLint({"MissingInflatedId", "LocalSuppress"})
		LinearLayout bout = findViewById(R.id.bout);
		bout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String url = "mqqapi://card/show_pslcard?src_type=internal&version=1&uin=538234048&card_type=group&source=qrcode";
				startActivity(new android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url)));
			}
		});
		
		LinearLayout sm = findViewById(R.id.sm);
		sm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MessageDialog.show("免责声明", "本应用仅供用户学习或参考使用，版权归原作者所有，仅在非商业用途下使用。\n\n本应用仅对用户在使用引用数据时可能造成的直接或间接损失承担法律责任，对于因使用本应用而引发的任何争议和后果，本应用概不负责。\n\n请您在使用本应用时，合理使用引用数据，遵守版权法律法规和道德约束，如有任何侵犯行为，请联系我们(1228727365@qq.com)及时处理\n\n谢谢！", "我已知晓", null)
						//.setTitleIcon(R.mipmap.i)
						.setBackgroundColor(Color.parseColor("#FDF8FC"))
						.setOkButton(new OnDialogButtonClickListener<MessageDialog>() {
							@Override
							public boolean onClick(MessageDialog baseDialog, View v) {
								
								return false;
							}
						})
						.setOkTextInfo(new TextInfo().setFontColor(Color.parseColor("#E30113")).setBold(true))
						.setCancelTextInfo(new TextInfo().setFontColor(Color.parseColor("#E30113")).setBold(true));
			}
		});
		
		LinearLayout wz = findViewById(R.id.wz);
		wz.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse("http://mobiles.stdio.com.cn/")));
			}
		});
		
		
		//ToDo 检测速度编辑框
		@SuppressLint({"MissingInflatedId", "LocalSuppress"})
		LinearLayout videbbj = findViewById(R.id.videbj);
		videbbj.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				new InputDialog("检测速度", "默认速度2000，等于2秒", "确定", "取消", null)
						//.setInputText("Hello World")
						.setBackgroundColor(Color.parseColor("#FDF8FC"))
						.setOkButton(new OnInputDialogButtonClickListener<InputDialog>() {
							@Override
							public boolean onClick(InputDialog baseDialog, View v, String inputStr) {
								if (isNumeric(inputStr)) {
									if (Integer.parseInt(inputStr) < 2000 || Integer.parseInt(inputStr) >= 4000) {
										Toast.makeText(SetActivity.this, "请输入2000—4000值", Toast.LENGTH_SHORT).show();
										return true;
									} else {
										runOnUiThread(new Runnable() {
											@Override
											public void run() {
												setFile(inputStr, "time.txt");
												textView.setText(inputStr);
											}
										});
										
										return false;
									}
									
								} else {
									Toast.makeText(SetActivity.this, "只能输入包含数字", Toast.LENGTH_SHORT).show();
									return true; // 返回true表示不关闭对话框
								}
							}
						})
						.show()
						.setOkTextInfo(new TextInfo().setFontColor(Color.parseColor("#E30113")).setBold(true))
						.setCancelTextInfo(new TextInfo().setFontColor(Color.parseColor("#E30113")).setBold(true));
				;
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
	
	// 判断字符串是否为数字
	private boolean isNumeric(String str) {
		if (str == null || str.length() == 0) {
			return false;
		}
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}
	
}