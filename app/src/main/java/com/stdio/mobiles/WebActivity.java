package com.stdio.mobiles;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.*;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebViewClient;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebActivity extends AppCompatActivity {
	WebView mWebView;
	String cookie;
	private Toolbar toolbar;
	private AgentWeb mAgentWeb;
	
	@SuppressLint("ObsoleteSdkInt")
	public static void syncCookie(WebView webView, String url, String cookies, String... emptyKeys) {
		if (!TextUtils.isEmpty(url)) {
			try {
				CookieManager cookieManager = CookieManager.getInstance();
				if (cookieManager == null) {
					return;
				}
				cookieManager.setAcceptCookie(true);
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
					cookieManager.setAcceptThirdPartyCookies(webView, true);        //跨域cookie读取
				}
				if (!TextUtils.isEmpty(cookies)) {
					String[] split = cookies.split(";");
					for (String s : split) {
						cookieManager.setCookie(url, s);
					}
					if (emptyKeys != null && emptyKeys.length > 0) {
						for (String key : emptyKeys) {
							if (!TextUtils.isEmpty(key) && !cookies.contains(key)) {
								cookieManager.setCookie(url, key);
							}
						}
					}
				} else {
					if (emptyKeys != null && emptyKeys.length > 0) {
						for (String key : emptyKeys) {
							if (!TextUtils.isEmpty(key)) {
								cookieManager.setCookie(url, key);
							}
						}
					}
				}
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
					CookieManager.getInstance().flush();
				} else {
					CookieSyncManager.getInstance().sync();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressLint({"SetJavaScriptEnabled", "MissingInflatedId"})
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
		
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		window.setStatusBarColor(ContextCompat.getColor(this, R.color.gree));
		
		Intent intent = getIntent();
		String key = intent.getStringExtra("key");
		cookie = intent.getStringExtra("cookie");
		String url = intent.getStringExtra("url"); // 通过getStringExtra方法获取参数
		
		toolbar = findViewById(R.id.toolbar);
		toolbar.setNavigationIcon(R.drawable.back);
		// 设置点击事件
		
		
		toolbar.setTitle(intent.getStringExtra("title"));
		setSupportActionBar(toolbar);
		
		LinearLayout webView = findViewById(R.id.webview);
		mAgentWeb = AgentWeb.with(this).setAgentWebParent(webView, new LinearLayout.LayoutParams(-1, -1)).useDefaultIndicator().createAgentWeb().ready().get();
		mWebView = mAgentWeb.getWebCreator().getWebView();
		
		WebSettings webSettings = mWebView.getSettings();
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setDrawingCacheEnabled(true);
		Map<String, String> additionalHeaders = new HashMap<>();
		additionalHeaders.put("Cookie", cookie);
		additionalHeaders.put("User-Agent", "Mozilla/5.0 (Linux; Android 10; YourDeviceModel) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.82 Mobile Safari/537.36");
		mWebView.setWebViewClient(new WebViewClient() {
			@SuppressLint("SetTextI18n")
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
				String url = request.getUrl().toString();
				toolbar.setSubtitle(url);
				//ToDo 写入日志访问情况
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						
						
						Calendar calendar = Calendar.getInstance();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ", Locale.getDefault());
						String currentTime = sdf.format(calendar.getTime());
						homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTime + "爬取:" + url);
						homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
						
					}
				});
				syncCookie(view, "https://passport2.chaoxing.com/getauthstatus", cookie, "cookie");
				syncCookie(view, "https://mooc-res2.chaoxing.com/", cookie, "cookie");
				syncCookie(view, url.replace("&clazzid=0", "&clazzid=" + key), cookie, "cookie");
				syncCookie(view, "https://mooc1.chaoxing.com/mooc-ans/phone/wxcard?", cookie, "cookie");
				syncCookie(view, "https://tsjy.chaoxing.com/plaza/app.html?/219832749/357445529/83242946/296556354/timeid", cookie, "cookie");
				syncCookie(view, "http://passport2.chaoxing.com/login?refer=http://i.mooc.chaoxing.com&fid=2396&newversion=true&_blank=0", cookie, "cookie");
				syncCookie(view, "https://pan-yz.chaoxing.com/screen/file_310a01777f934868c24e6bc0d2fd944e?ext=%7B%22_from_%22%3A%22237208488_83142373_296556354_cdefcd20be6084a4288b6ebf930fbfd0%22%7D", cookie, "cookie");
				syncCookie(view, "https://mooc1.chaoxing.com/ananas/modules/pub/preview.html?objectid=f54141acc339d85f2273dae25e3c323a&v=2020-0329-1322", cookie, "cookie");
				//syncCookie(view, "https://mooc1.chaoxing.com/mooc-ans/work/phone/doHomeWork?keyboardDisplayRequiresUserAction=1&courseId=236155167&workAnswerId=52679475&workId=31223570&knowledgeId=780824211&classId=82243408&oldWorkId=884a21d90c9b4275afc943c5bfa70d7a&jobId=work-884a21d90c9b4275afc943c5bfa70d7a&mooc=0&enc=1367a6983642d2cbb2d79c01210e9c33&cpi=343816933", cookie, "cookie");
				if (url.contains("mooc1.chaoxing.com") || url.contains("pan-yz.chaoxing.com")) {
					Log.w("\\\\\\", url);
					mWebView.loadUrl(url.replace("&clazzid=0", "&clazzid=" + key), additionalHeaders);
				}
				return super.shouldOverrideUrlLoading(view, request);
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				Log.w("\\\\\\链接", url);
				
				
				//ToDo 写入日志访问情况
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						
						Calendar calendar = Calendar.getInstance();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ", Locale.getDefault());
						String currentTime = sdf.format(calendar.getTime());
						homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTime + "爬取:" + url);
						homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
					}
				});
				view.evaluateJavascript("javascript:(function() { document.getElementsByClassName('fixed-icon')[0].style.display='none'; })();", null);
				view.evaluateJavascript("javascript:(function() { document.getElementsByClassName('icon-back')[0].style.display='none'; })();", null);
				if (url.contains("https://mooc1.chaoxing.com/")) {
					//TODO 打印标签数量
					点击标签(mWebView, 0);
					
				}
				
			}
			
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				
				view.evaluateJavascript("javascript:(function() { document.getElementsByClassName('fixed-icon')[0].style.display='none'; })();", null);
				view.evaluateJavascript("javascript:(function() { document.getElementsByClassName('icon-back')[0].style.display='none'; })();", null);
				
			}
			
		});
		
		
		syncCookie(mWebView, url.replace("&clazzid=0", "&clazzid=" + key), cookie, "cookie");
		mWebView.loadUrl(url.replace("&clazzid=0", "&clazzid=" + key), additionalHeaders);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mWebView.stopLoading();
				// 清除缓存
				mWebView.clearCache(true);
				// 销毁WebView
				mWebView.destroy();
				finish();
			}
		});
		
	}
	
	private void 点击标签(WebView view, int tab) {
		
		//ToDO 判断标签是否存在
		view.evaluateJavascript("javascript: " +
				"var element =document.querySelectorAll('.cardList a')[" + tab + "];" +
				"element.innerText;", new ValueCallback<String>() {
			@SuppressLint("SetTextI18n")
			@Override
			public void onReceiveValue(String value) {
				
				//ToDo 写入日志
				
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ", Locale.getDefault());
				String currentTime = sdf.format(calendar.getTime());
				homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTime + "第" + tab + "标签" + value);
				homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
				Log.w("\\\\\\", "第" + tab + "标签" + value);
				
				if (String.valueOf(value).contains("null")) {
					
					//ToDO 判断是否关闭跳转下一节
					if (getFile("v4.so").contains("true")) {
						//ToDo 写入日志
						String currentTimes = sdf.format(calendar.getTime());
						homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "没有标签，点击下一节");
						homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
						Log.w("\\\\\\", "没有标签了");
						
						//ToDO 判断是否已经最后一章
						view.evaluateJavascript("javascript: " +
								"var element =document.getElementsByClassName('clearfix')[0];" +
								"element.innerHTML;", new ValueCallback<String>() {
							@Override
							public void onReceiveValue(String value) {
								if (value.contains("下一节")) {
									//ToDO 点击下一节
									view.evaluateJavascript("javascript:(function() { document.getElementsByClassName('link')[0].click();})();", null);
								} else {
									Intent intent = getIntent();
									//ToDo 写入日志
									String currentTimes = sdf.format(calendar.getTime());
									homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "当前课程《" + intent.getStringExtra("title") + "》已经完全学习结束");
									homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
									Log.w("\\\\\\", "已经全部刷完");
									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											if (getFile("v5.so").contains("true")) {
												if (getFile("qq.txt").isEmpty()) {
												} else {
													qqmailbox(getFile("qq.txt"), "时间:" + currentTimes + "<br>当前课程《" + intent.getStringExtra("title") + "》已经完全学习结束");
												}
											} else {
												Log.w("\\\\\\", "没有开启提醒功能");
											}
										}
									});
									
								}
							}
						});
						
						
					} else {
						//ToDo 写入日志
						String currentTimes = sdf.format(calendar.getTime());
						homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "没有标签，无法跳转下一节，您已关闭此功能");
						homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
						Log.w("\\\\\\", "您已经关闭自动跳转下一节");
					}
				} else if (tab == 0) {
					//ToDo 写入日志
					String currentTimes = sdf.format(calendar.getTime());
					homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "标签为0");
					homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
					任务点处理(view, 0, 0, tab, 0, 0);
				} else {
					
					//ToDo 写入日志
					String currentTimes = sdf.format(calendar.getTime());
					homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "执行点击标签" + tab);
					homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
					
					//ToDO 点击标签
					view.evaluateJavascript("javascript:(function() {document.querySelectorAll('.cardList a')[" + tab + "].click();})();", new ValueCallback<String>() {
						@Override
						public void onReceiveValue(String value) {
							Handler handler = new Handler();
							handler.postDelayed(new Runnable() {
								@Override
								public void run() {
									//ToDO 检查有几个任务点
									view.evaluateJavascript("javascript: " +
											"var element =document.getElementsByClassName('ans-cc')[0].getElementsByClassName('ans-job-icon');" +
											"element.length;", new ValueCallback<String>() {
										@Override
										public void onReceiveValue(String value) {
											if (value.equals("0")) {
												int shu = tab + 1;
												
												//ToDo 写入日志
												String currentTimes = sdf.format(calendar.getTime());
												homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "共有" + value + "任务点，执行点击下一个id为" + shu + "的标签");
												homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
												
												Log.w("\\\\\\", "共有" + value + "任务点，执行点击下一个id为" + shu + "的标签");
												点击标签(view, tab + 1);
											} else {
												runOnUiThread(new Runnable() {
													@Override
													public void run() {
														
														//ToDo 写入日志
														String currentTimes = sdf.format(calendar.getTime());
														homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "共有" + value + "任务点，执行任务点检测");
														homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
														
														Log.w("\\\\\\", "共有" + value + "任务点，执行任务点检测");
														任务点处理(view, 0, 0, tab, 0, 0);
													}
												});
												
											}
										}
									});
								}
							}, Integer.parseInt(getFile("time.txt")));
							
						}
					});
				}
			}
		});
	}
	
	private void qqmailbox(String file, String nr) {
		OkHttpClient okHttpClient = new OkHttpClient();
		Request request = new Request.Builder()
				.url("http://www.stdio.com.cn/mail/mail.php?address=" + file + "@qq.com&name=学不通&certno=" + nr)
				.get()
				.build();
		Call call = okHttpClient.newCall(request);
		call.enqueue(new Callback() {
			@Override
			public void onFailure(@NotNull Call call, @NotNull IOException e) {
			
			}
			
			@Override
			public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
				Log.w("\\\\\\", response.body().string());
				
			}
		});
		
	}
	
	@SuppressLint("SetTextI18n")
	private void 任务点处理(WebView view, int num, int paly, int tab, int mp3, int wznum) {
		
		//ToDo 写入日志
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ", Locale.getDefault());
		String currentTimes = sdf.format(calendar.getTime());
		homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "当前num:" + num);
		homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
		
		Log.w("\\\\\\", "当前num:" + num);
		//ToDO 判断任务点是否为视频
		view.evaluateJavascript("javascript: " +
				"var num = " + num + "; " +
				"var element =document.getElementsByClassName('ans-attach-ct')[" + num + "];" +
				"element.innerHTML;", new ValueCallback<String>() {
			@Override
			public void onReceiveValue(String value) {
				Log.w("\\\\\\", "当前任务标签" + value);
				if (value.contains("modules/video/")) {
					
					//ToDo 写入日志
					String currentTimes = sdf.format(calendar.getTime());
					homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "发现视频任务点");
					homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
					
					//ToDO 判断是否关闭自动播放视频
					if (getFile("v3.so").contains("true")) {
						//ToDO 判断视频任务点是否完成
						view.evaluateJavascript("javascript: " +
								"var num = " + num + "; " +
								"var element =document.getElementsByClassName('ans-attach-ct')[" + num + "].getElementsByClassName('ans-job-icon')[0];" +
								"element.innerText;", new ValueCallback<String>() {
							@Override
							public void onReceiveValue(String value) {
								if (value.contains("任务点已完成")) {
									//ToDo 写入日志
									String currentTimes = sdf.format(calendar.getTime());
									homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "视频任务点视频已经完成" + value);
									homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
									Log.w("\\\\\\", "任务点视频已经完成" + value);
									任务点处理(view, num + 1, paly + 1, tab, mp3, wznum);
								} else if (value.contains("任务点")) {
									
									//ToDo 写入日志
									String currentTimes = sdf.format(calendar.getTime());
									homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "点击播放视频任务点");
									homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
									
									//ToDO 点击播放视频任务点
									view.loadUrl("javascript: " +
											"var paly = " + paly + "; " +
											"var element =document.getElementsByClassName('ans-insertwork-online')[" + paly + "].contentDocument.getElementsByClassName('vjs-big-play-button')[0].click();");
									Log.w("\\\\\\", "发现一个视频任务点，点击播放");
									
									if (getFile("v7.so").contains("true")) {
										view.loadUrl("javascript: " +
												"var element =document.getElementsByClassName('ans-insertwork-online')[" + paly + "].contentDocument.getElementsByClassName('vjs-tech')[0].playbackRate=2;");
										Log.w("\\\\\\", "开启2倍数播放");
									}
									if (getFile("v6.so").contains("true")) {
										view.loadUrl("javascript: " +
												"var paly = " + paly + "; " +
												"var element =document.getElementsByClassName('ans-insertwork-online')[" + paly + "].contentDocument.getElementsByClassName(' vjs-mute-control')[0].click();");
										Log.w("\\\\\\", "点击静音");
									}
									
									Handler handler = new Handler();
									handler.postDelayed(new Runnable() {
										@Override
										public void run() {
											//TODO 检测视频播放完毕
											vide检测(view, num, paly, tab, mp3, wznum);
										}
									}, 1000);
								} else {
									//ToDo 写入日志
									String currentTimes = sdf.format(calendar.getTime());
									homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "视频任务点没有了");
									homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
									
									Log.w("\\\\\\", "视频任务点没有了:" + value);
									任务点处理(view, num + 1, paly, tab, mp3, wznum);
								}
							}
						});
					} else {
						String currentTimess = sdf.format(calendar.getTime());
						homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimess + "您已经关闭自动播放视频，执行跳转下一节");
						homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
						点击标签(view, tab + 1);
						Log.w("\\\\\\", "您已关闭自动播放视频");
					}
				} else if (value.contains("modules/downloadfile/") || value.contains("modules/zt/pdf/") || value.contains("modules/zt/ppt/") || value.contains("modules/word/") || value.contains("ananas/modules/zt/doc/")) {
					//ToDO 判断ppt任务点是否完成
					view.evaluateJavascript("javascript: " +
							"var num = " + num + "; " +
							"var element =document.getElementsByClassName('ans-attach-ct')[" + num + "].getElementsByClassName('ans-job-icon')[0];" +
							"element.innerText;", new ValueCallback<String>() {
						@Override
						public void onReceiveValue(String value) {
							if (value.contains("任务点已完成")) {
								//ToDo 写入日志
								String currentTimes = sdf.format(calendar.getTime());
								homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "ppt等文件任务点已经完成" + value);
								homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
								Log.w("\\\\\\", "ppt任务点已经完成" + value);
								任务点处理(view, num + 1, paly, tab, mp3, wznum);
								
							} else if (value.contains("任务点")) {
								
								//TODO 获取里面的id
								view.evaluateJavascript("javascript: " +
										"var element =document.getElementsByClassName('content-inner')[0];" +
										"element.innerHTML;", new ValueCallback<String>() {
									@Override
									public void onReceiveValue(String value) {
										Log.w("\\\\\\", value);
										int startIndex = value.indexOf("window.AttachmentSetting =");
										int endIndex = value.indexOf("window._from =");
										
										if (startIndex != -1 && endIndex != -1) {
											startIndex += "window.AttachmentSetting =".length(); // 跳过开始字符串本身
											String extractedString = value.substring(startIndex, endIndex);
											String data = extractedString.replace("};", "}").replace("\\", "").replace("nt", "");
											
											//ToDO 查找id是否相同
											view.evaluateJavascript("javascript: " +
													"var num = " + num + "; " +
													"var element =document.getElementsByClassName('ans-attach-ct')[" + num + "];" +
													"element.innerHTML;", new ValueCallback<String>() {
												@Override
												public void onReceiveValue(String value) {
													Log.w("\\\\\\", "当前ppt标签" + value);
													try {
														JSONObject jsonObject = new JSONObject(data);
														Log.w("\\\\\\", String.valueOf(jsonObject));
														JSONArray attachmesArray = jsonObject.getJSONArray("attachmes");
														JSONObject jsonObject1 = jsonObject.getJSONObject("defaults");
														String clazzid = jsonObject1.getString("clazzId");
														String courseid = jsonObject1.getString("courseid");
														String knowledgeid = jsonObject1.getString("knowledgeid");
														for (int i = 0; i < attachmesArray.length(); i++) {
															JSONObject attachObject = attachmesArray.getJSONObject(i);
															Log.w("\\\\\\ss", String.valueOf(attachObject));
															JSONObject property = attachObject.getJSONObject("property");
															
															if (attachObject.has("jobid") && attachObject.has("jtoken") && property.has("objectid")) {
																String objectid = property.optString("objectid");
																String jobid = attachObject.getString("jobid");
																Log.w("\\\\\\jobid", jobid);
																String jtoken = attachObject.getString("jtoken");
																if (value.contains(jobid) && value.contains(objectid)) {
																	Log.w("\\\\\\", "执行ppt任务点代码");
																	getppt(view, clazzid, courseid, knowledgeid, jobid, jtoken, num, tab, paly, mp3, wznum, "文件");
																}
															}
														}
													} catch (JSONException e) {
														e.printStackTrace();
													}
													
												}
											});
										} else {
											//ToDo 写入日志
											String currentTimes = sdf.format(calendar.getTime());
											homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "ppt任务点截取3个id失败:跳过本节");
											homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
											
											Log.w("\\\\\\", "ppt任务点截取3个id失败:" + value);
											任务点处理(view, num + 1, paly, tab, mp3, wznum);
										}
										
									}
								});
								
								
							} else {
								//ToDo 写入日志
								String currentTimes = sdf.format(calendar.getTime());
								homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "ppt等文件任务点没有了");
								homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
								Log.w("\\\\\\", "pp文件等任务点没有了:" + value);
								任务点处理(view, num + 1, paly, tab, mp3, wznum);
							}
						}
					});
				} else if (value.contains("ananas/modules/audio/")) {
					Log.w("\\\\\\", "发现音频文件");
					view.evaluateJavascript("javascript: " +
							"var num = " + num + "; " +
							"var element =document.getElementsByClassName('ans-attach-ct')[" + num + "].getElementsByClassName('ans-job-icon')[0];" +
							"element.innerText;", new ValueCallback<String>() {
						@Override
						public void onReceiveValue(String value) {
							if (value.contains("任务点已完成")) {
								//ToDo 写入日志
								String currentTimes = sdf.format(calendar.getTime());
								homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "ppt等文件任务点已经完成" + value);
								homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
								Log.w("\\\\\\", "ppt任务点已经完成" + value);
								任务点处理(view, num + 1, paly, tab, mp3 + 1, wznum);
								
							} else if (value.contains("任务点")) {
								//ToDo 判断进度条是否相等
								view.evaluateJavascript("javascript: " +
										"var mp3 = " + mp3 + "; " +
										"var element =document.getElementsByClassName('ans-attach-online')[" + mp3 + "].contentDocument.getElementsByClassName('audioplayer-time-current')[0];" +
										"element.innerText;", new ValueCallback<String>() {
									@Override
									public void onReceiveValue(String value) {
										
										String pattern = "(\\d{2}:\\d{2})";
										Pattern p = Pattern.compile(pattern);
										Matcher m = p.matcher(value);
										if (m.find()) {
											String extracted = m.group();
											if (extracted.length() >= 4) {
												String result = extracted.substring(0, 4);
												
												Log.w("\\\\\\", "当前播放进度" + result);
												view.evaluateJavascript("javascript: " +
														"var mp3 = " + mp3 + "; " +
														"var element =document.getElementsByClassName('ans-attach-online')[" + mp3 + "].contentDocument.getElementsByClassName('audioplayer-time-duration')[0];" +
														"element.innerText;", new ValueCallback<String>() {
													@Override
													public void onReceiveValue(String value) {
														Log.w("\\\\\\", "当前结束进度" + value);
														if (value.contains(result)) {
															Log.w("\\\\\\", "该音频任务点已经学习过");
															任务点处理(view, num + 1, paly, tab, mp3 + 1, wznum);
														} else {
															view.loadUrl("javascript: " +
																	"var mp3 = " + mp3 + "; " +
																	"var element =document.getElementsByClassName('ans-attach-online')[" + mp3 + "].contentDocument.getElementsByClassName('audioplayer-playpause')[0].click();");
															Log.w("\\\\\\", "发现一个音频任务点，点击播放");
															Handler handler = new Handler();
															handler.postDelayed(new Runnable() {
																@Override
																public void run() {
																	//TODO 检测音频播放完毕
																	mp3检测(view, num, paly, tab, mp3, wznum);
																}
															}, 1000);
															
														}
													}
												});
											}
										}
									}
								});
							} else {
								//ToDo 写入日志
								String currentTimes = sdf.format(calendar.getTime());
								homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "音频任务点没有了" + value);
								homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
								Log.w("\\\\\\", "音频任务点没有了" + value);
								任务点处理(view, num + 1, paly, tab, mp3, wznum);
							}
						}
					});
				} /*else if (value.contains("/ananas/modules/work/index_wap.html")) {
                    //ToDO 章节测验
                    //ToDO 判断视频任务点是否完成
                    view.evaluateJavascript("javascript: " +
                            "var num = " + num + "; " +
                            "var element =document.getElementsByClassName('ans-attach-ct')[" + num + "].getElementsByClassName('ans-job-icon')[0];" +
                            "element.innerText;", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            if (value.contains("任务点已完成")) {


                            } else if (value.contains("任务点")) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        view.evaluateJavascript("javascript: " +
                                                "var num = " + num + "; " +
                                                "var parentIframe = document.getElementsByClassName('ans-insertwork-online')[" + num + "].contentDocument.getElementById('frame_content').contentDocument.getElementsByClassName('zquestions')[0].getElementsByClassName('Py-mian1')[1].getElementsByClassName('Py-m1-title')[0].getElementsByClassName('quesType')[0];" +
                                                "parentIframe.innerText", new ValueCallback<String>() {
                                            @Override
                                            public void onReceiveValue(String value) {
                                                Log.w("\\\\\\", "当前题型：" + value);
                                                if (value.contains("其它")) {
                                                    Log.w("\\\\\\", "当前是其他题型");

                                                } else {
                                                    view.evaluateJavascript("javascript: " +
                                                            "var num = " + num + "; " +
                                                            "var parentIframe = document.getElementsByClassName('ans-insertwork-online')[" + num + "].contentDocument.getElementById('frame_content').contentDocument.getElementsByClassName('zquestions')[0].getElementsByClassName('Py-mian1')[1];" +
                                                            "parentIframe.innerText", new ValueCallback<String>() {
                                                        @Override
                                                        public void onReceiveValue(String value) {
                                                            value = value.trim();
                                                            Log.w("\\\\\\课堂测验", value);

                                                            value = value.trim().replaceAll("\\\\n", "").trim();
                                                            Log.w("\\\\\\", value);
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }
                                }, 2000);
                            } else {

                            }
                        }
                    });
                }*/ else if (value.contains("任务点")) {
					//ToDo 判断是否为文章任务点
					view.evaluateJavascript("javascript: " +
							"var wznum = " + wznum + "; " +
							"var element =document.getElementsByClassName('ans-book')[" + wznum + "];" +
							"element.innerHTML;", new ValueCallback<String>() {
						@Override
						public void onReceiveValue(String value) {
							String rwdpd = value;
							Log.w("\\\\\\", "当前为文章任务点" + value);
							if (value.contains("resapi.chaoxing.com/realReadNew")) {
								view.evaluateJavascript("javascript: " +
										"var num = " + num + "; " +
										"var element =document.getElementsByClassName('ans-attach-ct')[" + num + "].getElementsByClassName('ans-job-icon')[0];" +
										"element.innerText;", new ValueCallback<String>() {
									@Override
									public void onReceiveValue(String value) {
										if (value.contains("任务点已完成")) {
											
											//ToDo 写入日志
											String currentTimes = sdf.format(calendar.getTime());
											homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "文章任务点已经完成" + value);
											homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
											Log.w("\\\\\\", "文章任务点已经完成" + value);
											任务点处理(view, num + 1, paly, tab, mp3, wznum + 1);
											
										} else if (value.contains("任务点")) {
											
											//TODO 获取里面的id
											view.evaluateJavascript("javascript: " +
													"var element =document.getElementsByClassName('content-inner')[0];" +
													"element.innerHTML;", new ValueCallback<String>() {
												@Override
												public void onReceiveValue(String value) {
													int startIndex = value.indexOf("window.AttachmentSetting =");
													int endIndex = value.indexOf("window._from =");
													
													if (startIndex != -1 && endIndex != -1) {
														startIndex += "window.AttachmentSetting =".length(); // 跳过开始字符串本身
														String extractedString = value.substring(startIndex, endIndex);
														String data = extractedString.replace("};", "}").replace("\\", "").replace("nt", "");
														Log.w("\\\\\\", data);
														
														try {
															JSONObject jsonObject = new JSONObject(data);
															Log.w("\\\\\\", String.valueOf(jsonObject));
															JSONArray attachmesArray = jsonObject.getJSONArray("attachmes");
															JSONObject jsonObject1 = jsonObject.getJSONObject("defaults");
															String clazzid = jsonObject1.getString("clazzId");
															String courseid = jsonObject1.getString("courseid");
															String knowledgeid = jsonObject1.getString("knowledgeid");
															for (int i = 0; i < attachmesArray.length(); i++) {
																JSONObject attachObject = attachmesArray.getJSONObject(i);
																JSONObject property = attachObject.getJSONObject("property");
																if (property.has("bookname") && property.has("author") && property.has("publishdate")) {
																	String bookname = property.optString("bookname");
																	String author = property.optString("author");
																	String publishdate = property.optString("publishdate");
																	Log.w("\\\\\\", bookname + "ss" + author + "ss" + bookname + "ss" + publishdate);
																	if (attachObject.has("jobid") && attachObject.has("jtoken")) {
																		if (rwdpd.contains(author) && rwdpd.contains(bookname) && rwdpd.contains(publishdate)) {
																			String jobid = attachObject.getString("jobid");
																			String jtoken = attachObject.getString("jtoken");
																			getppt(view, clazzid, courseid, knowledgeid, jobid, jtoken, num, tab, paly, mp3, wznum, "文章");
																		}
																	}
																}
															}
														} catch (JSONException e) {
															e.printStackTrace();
														}
													} else {
														//ToDo 写入日志
														String currentTimes = sdf.format(calendar.getTime());
														homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "文章任务点截取3个id失败:跳过本节");
														homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
														Log.w("\\\\\\", "文章任务点截取3个id失败:" + value);
														任务点处理(view, num + 1, paly, tab, mp3, wznum + 1);
													}
													
												}
											});
											
											
										} else {
											//ToDo 写入日志
											String currentTimes = sdf.format(calendar.getTime());
											homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "文章任务点已经没有了" + value);
											homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
											Log.w("\\\\\\", "文章任务点没有了" + value);
											任务点处理(view, num + 1, paly, tab, mp3, wznum);
										}
									}
								});
							} else {
								//ToDo 写入日志
								String currentTimes = sdf.format(calendar.getTime());
								homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "本节已经没有任务了，执行下一节");
								homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
								Log.w("\\\\\\", "文章没有任务了:" + value);
								任务点处理(view, num + 1, paly, tab, mp3, wznum);
							}
						}
					});
					
					
				} else {
					//ToDo 写入日志
					String currentTimes = sdf.format(calendar.getTime());
					homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "已经没有任务点，执行下一节");
					homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
					Log.w("\\\\\\", "已经没有任务点:" + value);
					点击标签(view, tab + 1);
				}
			}
		});
	}
	
	private void getppt(WebView view, String clazzid, String courseid, String knowledgeid, String jobid, String jtoken, int num, int tab, int paly, int mp3, int wznum, String name) {
		String url;
		if (name.contains("文件")) {
			url = "https://mooc1.chaoxing.com/ananas/job/document?jobid=";
		} else {
			url = "https://mooc1.chaoxing.com/ananas/job?jobid=";
		}
		OkHttpClient okHttpClient = new OkHttpClient();
		Request request = new Request.Builder()
				.url(url + jobid + "&knowledgeid=" + knowledgeid + "&courseid=" + courseid + "&clazzid=" + clazzid + "&jtoken=" + jtoken)
				.addHeader("cookie", cookie)
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
				try {
					JSONObject jsonObject = new JSONObject(response.body().string());
					Log.w("\\\\\\", String.valueOf(jsonObject));
					String status = jsonObject.getString("status");
					String msg = jsonObject.getString("msg");
					if (status.equals("true")) {
						runOnUiThread(new Runnable() {
							@SuppressLint("SetTextI18n")
							@Override
							public void run() {
								//ToDo 写入日志
								Calendar calendar = Calendar.getInstance();
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ", Locale.getDefault());
								String currentTimes = sdf.format(calendar.getTime());
								homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + msg);
								homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
								
								if (name.equals("文件")) {
									Log.w("\\\\\\", "文件考核点添加成功");
									任务点处理(view, num + 1, paly, tab, mp3, wznum);
								} else {
									Log.w("\\\\\\", "文章考核点添加成功");
									任务点处理(view, num + 1, paly, tab, mp3, wznum + 1);
								}
							}
						});
						
					} else {
						
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if (name.equals("文件")) {
									Log.w("\\\\\\", "文件考核点添加失败");
									任务点处理(view, num + 1, paly, tab, mp3, wznum);
								} else {
									Log.w("\\\\\\", "文章考核点添加失败");
									任务点处理(view, num + 1, paly, tab, mp3, wznum + 1);
								}
							}
						});
						
					}
				} catch (JSONException e) {
					throw new RuntimeException(e);
				}
				
			}
		});
	}
	
	private void vide检测(WebView view, int num, int paly, int tab, int mp3, int wznum) {
		// ToDo 检测播放是否结束，检测中途是否被暂停播放
		String javascriptCode = "javascript: " +
				"var num = " + num + "; " +
				"var timerId = setInterval(function() { " +
				"   var element = document.getElementsByClassName('ans-attach-ct')[" + num + "].getElementsByClassName('ans-job-icon')[0];" +
				"   var elements = document.getElementsByClassName('ans-insertwork-online')[" + paly + "].contentDocument.getElementsByClassName('vjs-play-control')[0];" +
				"   var elementx = document.getElementsByClassName('ans-insertwork-online')[" + paly + "].contentDocument.getElementsByClassName('vjs-duration-display')[0];" +
				"   var elementk = document.getElementsByClassName('ans-insertwork-online')[" + paly + "].contentDocument.getElementsByClassName('vjs-current-time-display')[0];" +
				"   var timeString = elementk.innerText;" +
				"   if (element.innerText.includes('任务点已完成')) {" +
				"      if (elementx.innerText.includes(timeString)) {" +
				"      clearInterval(timerId);" +
				"      window.prompt('Android', element.innerText);" +
				"      }else{" +
				"      clearInterval(timerId);" +
				"      window.prompt('Android','进度条未满但任务点已完成');" +
				"      }" +
				"   }else if(elementx.innerText.includes('0:00')){" +
				"   clearInterval(timerId);" +
				"   window.prompt('Android', elementx.innerText);" +
				"   }else if(elements.title.includes('播放')){" +
				"       clearInterval(timerId);" +
				"       window.prompt('Android', elements.title);" +
				"   }" +
				"}, 500);";
		view.setWebChromeClient(new WebChromeClient() {
			@SuppressLint("SetTextI18n")
			@Override
			public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
				if ("Android".equals(message)) {
					result.confirm(defaultValue);
					Log.w("\\\\\\", defaultValue);
					if (defaultValue.contains("任务点已完成")) {
						//ToDo 写入日志
						Calendar calendar = Calendar.getInstance();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ", Locale.getDefault());
						String currentTimes = sdf.format(calendar.getTime());
						homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "视频播放已经完毕，执行下一个任务点处理");
						homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
						Log.w("\\\\\\", "视频播放完毕");
						任务点处理(view, num + 1, paly + 1, tab, mp3, wznum);
					} else if (defaultValue.contains("播放")) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								//ToDo 写入日志
								Calendar calendar = Calendar.getInstance();
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ", Locale.getDefault());
								String currentTimes = sdf.format(calendar.getTime());
								homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "发现视频被暂停播放，立即执行播放");
								homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
								view.loadUrl("javascript: " +
										"var paly = " + paly + "; " +
										"var element =document.getElementsByClassName('ans-insertwork-online')[" + paly + "].contentDocument.getElementsByClassName('vjs-big-play-button')[0].click();");
								Log.w("\\\\\\", "发现视频被暂停，执行点击播放");
								vide检测(view, num, paly, tab, mp3, wznum);
							}
						});
						
					} else if (defaultValue.contains("0:00")) {
						//ToDo 写入日志
						Calendar calendar = Calendar.getInstance();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ", Locale.getDefault());
						String currentTimes = sdf.format(calendar.getTime());
						homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "发现视频没有加载成功，执行加载");
						homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
						view.loadUrl("javascript: " +
								"var paly = " + paly + "; " +
								"var element =document.getElementsByClassName('ans-insertwork-online')[" + paly + "].contentDocument.getElementsByClassName('vjs-big-play-button')[0].click();");
						Log.w("\\\\\\", "发现视频被暂停，执行点击播放");
						vide检测(view, num, paly, tab, mp3, wznum);
					} else if (defaultValue.contains("进度条未满但进度条已完成")) {
						if (getFile("v8.so").contains("true")) {
							view.loadUrl("javascript: " +
									"var paly = " + paly + "; " +
									"var element =document.getElementsByClassName('ans-insertwork-online')[" + paly + "].contentDocument.getElementsByClassName('vjs-big-play-button')[0].click();");
							Log.w("\\\\\\", "发现视频任务点完成，但进度条未满，执行播放");
							vide检测(view, num, paly, tab, mp3, wznum);
						} else {
							Calendar calendar = Calendar.getInstance();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ", Locale.getDefault());
							String currentTimes = sdf.format(calendar.getTime());
							homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "视频播放已经完毕，执行下一个任务点处理");
							homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
							Log.w("\\\\\\", "视频播放完毕");
							任务点处理(view, num + 1, paly + 1, tab, mp3, wznum);
						}
					}
					return true;
				}
				return super.onJsPrompt(view, url, message, defaultValue, result);
			}
		});
		view.evaluateJavascript(javascriptCode, null);
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		
		if (mAgentWeb.handleKeyEvent(keyCode, event)) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
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
	
	
	private void mp3检测(WebView view, int num, int paly, int tab, int mp3, int wznum) {
		Log.w("\\\\\\", "已开始音频检测");
		// ToDo 检测播放是否结束，检测中途是否被暂停播放
		String javascriptCode = "javascript: " +
				"var num = " + num + "; " +
				"var timerId = setInterval(function() { " +
				"   var element = document.getElementsByClassName('ans-attach-ct')[" + num + "].getElementsByClassName('ans-job-icon')[0];" +
				"   var elements = document.getElementsByClassName('ans-attach-online')[" + mp3 + "].contentDocument.getElementsByClassName('audioplayer-playpause')[0];" +
				"   var ptimes =document.getElementsByClassName('ans-attach-online')[" + mp3 + "].contentDocument.getElementsByClassName('audioplayer-time-current')[0];" +
				"   var ptimej =document.getElementsByClassName('ans-attach-online')[" + mp3 + "].contentDocument.getElementsByClassName('audioplayer-time-duration')[0];" +
				"   var timeString = ptimes.innerText;" +
				"   var pattern = /\\b(\\d{2}:\\d{2})\\b/;" +
				"   var matches = timeString.match(pattern);" +
				"   if(matches && matches.length > 0) {" +
				"   var extracted = matches[0];" +
				"   var result = extracted.substring(0, 4);" +
				"   }  " +
				"   if (element.innerText.includes('任务点已完成')) {" +
				"       clearInterval(timerId);" +
				"       window.prompt('Android', element.innerText);" +
				"   }else if(elements.ariaLabel.includes('播放')) {" +
				"       clearInterval(timerId);" +
				"       window.prompt('Android', elements.ariaLabel);" +
				"   }else if(ptimej.innerText.includes(result)){" +
				"       clearInterval(timerId);" +
				"       window.prompt('Android', '进度已满');" +
				" } " +
				"}, 1000);";
		view.setWebChromeClient(new WebChromeClient() {
			@SuppressLint("SetTextI18n")
			@Override
			public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
				if ("Android".equals(message)) {
					result.confirm(defaultValue);
					Log.w("\\\\\\aa", defaultValue);
					if (defaultValue.contains("任务点已完成")) {
						//ToDo 写入日志
						Calendar calendar = Calendar.getInstance();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ", Locale.getDefault());
						String currentTimes = sdf.format(calendar.getTime());
						homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "音频播放已经完毕，执行下一个任务点处理");
						homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
						Log.w("\\\\\\", "音频播放完毕");
						任务点处理(view, num + 1, paly, tab, mp3 + 1, wznum);
					} else if (defaultValue.contains("播放")) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								
								//ToDo 写入日志
								Calendar calendar = Calendar.getInstance();
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ", Locale.getDefault());
								String currentTimes = sdf.format(calendar.getTime());
								homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "发现音频被暂停播放，立即执行播放");
								homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
								
								view.loadUrl("javascript: " +
										"var mp3 = " + mp3 + "; " +
										"var element =document.getElementsByClassName('ans-attach-online')[" + mp3 + "].contentDocument.getElementsByClassName('audioplayer-playpause')[0].click();");
								Log.w("\\\\\\", "发现音频被暂停，执行点击播放");
								mp3检测(view, num, paly, tab, mp3, wznum);
							}
						});
					} else if (defaultValue.contains("进度已满")) {
						Calendar calendar = Calendar.getInstance();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ", Locale.getDefault());
						String currentTimes = sdf.format(calendar.getTime());
						homeActivity.logtext.setText(homeActivity.logtext.getText() + "\nCD:" + currentTimes + "音频播放完毕,进度条已到，执行下一个任务点处理");
						homeActivity.loggd.scrollTo(0, homeActivity.loggd.getChildAt(0).getHeight());
						Log.w("\\\\\\", "音频播放完毕,进度条已到");
						任务点处理(view, num + 1, paly, tab, mp3 + 1, wznum);
						
					} else if (defaultValue.contains("null")) {
						Log.w("\\\\\\", "正在加载中");
						mp3检测(view, num, paly, tab, mp3, wznum);
					}
					return true;
				}
				return super.onJsPrompt(view, url, message, defaultValue, result);
			}
		});
		view.evaluateJavascript(javascriptCode, null);
	}
}