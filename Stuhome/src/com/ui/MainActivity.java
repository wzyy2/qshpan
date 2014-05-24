package com.ui;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.Data.Request;
import com.api.Api;
import com.dao.PostsDataHelper;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.ui.adapter.PostsAdapter;
import com.utils.MD5;
import com.utils.TaskUtils;
import com.utils.Utils;
import com.utils.sharedpreference.Athority;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.app.LoaderManager;
import android.database.Cursor;

public class MainActivity extends ActionBarActivity {
	private ActionBar actionBar;
	private SlidingMenu menu;
	public ListView lvmn, lvpost;
	private Context mContext;
	private int keyClickCount;
	String mCategory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;

		// 得到Activity的ActionBar
		actionBar = getSupportActionBar();
		// 将Activity的头部去掉
		// actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowCustomEnabled(true);
		// actionBar.setDisplayShowHomeEnabled(false);
		// actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setTitle("清水河畔");
		// actionBar.setIcon(R.drawable.show_menu);

		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		menu.setMenu(R.layout.slidingmenu);
		lvmn = (ListView) findViewById(R.id.listView2);
		lvmn.setItemChecked(0, true);
		lvmn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				lvmn.setItemChecked(position, true);
				setCategory(Utils.FORUM_CATEGORY[position]);
			}
		});

		ArrayAdapter<String> listItemAdapter = new ArrayAdapter<String>(
				MainActivity.this, android.R.layout.simple_list_item_1,
				Utils.FORUM_CATEGORY);
		// 添加并且显示
		lvmn.setAdapter(listItemAdapter);

		final SharedPreferences mShared = mContext.getSharedPreferences(
				Athority.ACCOUNT_INFORMATION, Context.MODE_PRIVATE);
		if (mShared.getString("login", "no").equals("yes")) {
			actionBar.setTitle("清水河畔：已登录");
		} else {
			actionBar.setTitle("清水河畔：未登录");
		}

	}

	View header;
	private PostsDataHelper mpDataHelper;
	private PostsAdapter mAdapter;
	private int mPage;
	public PostsFragment mContentFragment;

	public void setCategory(String category) {
		menu.toggle();
		if (mCategory == category) {
			return;
		}
		mCategory = category;
		setTitle(mCategory);
		mContentFragment = PostsFragment.newInstance(category);
		// Fragment_A mFragment = new Fragment_A();
		replaceFragment(R.id.content, mContentFragment);
		
	}

	protected void replaceFragment(int viewId, Fragment fragment) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(viewId, fragment).commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	String username;
	String password;
	EditText usernameText;
	EditText passwordText;
	private ProgressDialog progressDialog;

	protected void actionAlertDialog_login() {
		AlertDialog.Builder builder;
		AlertDialog alertDialog;
		Button confirm;

		Context context = getApplicationContext();
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.login,
				(ViewGroup) findViewById(R.id.login_layout));
		usernameText = (EditText) layout.findViewById(R.id.username);
		passwordText = (EditText) layout.findViewById(R.id.password);
		confirm = (Button) layout.findViewById(R.id.confirm);
		confirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				username = usernameText.getText().toString();
				password = passwordText.getText().toString();
				if (username.equals("")) {
					Toast.makeText(mContext, "用户名不能为空！", Toast.LENGTH_SHORT)
							.show();
				} else if (password.equals("")) {
					Toast.makeText(mContext, "密码不能为空！", Toast.LENGTH_SHORT)
							.show();
				} else {
					MD5 m = new MD5();
					password = m.getMD5ofStr(password);
					progressDialog = ProgressDialog.show(mContext, null, "请稍后");
					TaskUtils
							.executeAsyncTask(new AsyncTask<Void, Void, Boolean>() {
								@Override
								protected Boolean doInBackground(Void... voids) {

									return isLoginSuccess();
								}

								@Override
								protected void onPostExecute(Boolean success) {
									super.onPostExecute(success);
									if (!success) {
										progressDialog.dismiss();
										Toast.makeText(mContext, "用户名或密码错误!",
												Toast.LENGTH_SHORT).show();
										usernameText.setText("");
										passwordText.setText("");
									} else {
										progressDialog.dismiss();
										Toast.makeText(mContext, "登录成功!",
												Toast.LENGTH_SHORT).show();
										Intent intent = new Intent(mContext,
												MainActivity.class);
										mContext.startActivity(intent);
										finish();
									}
								}
							});
				}
			}
		});

		builder = new AlertDialog.Builder(this);
		builder.setView(layout);
		alertDialog = builder.create();
		alertDialog.show();
	}

	private boolean isLoginSuccess() {
		Document doc = null;
		String formhash = null;
		String loginaction = null;
		Map<String, String> datas = new HashMap<String, String>();
		Map<String, String> cookies = new HashMap<String, String>();
		try {
			Connection.Response before_login = Request.execute(
					Api.COOKIE_LOGIN, "Mozilla", Connection.Method.POST);
			doc = before_login.parse();
			Element hash = doc.getElementById("scbar_form");
			Elements hashs = hash.getElementsByTag("input");
			for (Element element : hashs) {
				if (element.attr("name").equals("formhash")) {
					formhash = element.attr("value");
				}
			}

			Elements login = doc.getElementsByClass("cl");
			loginaction = login.attr("action");
			loginaction = loginaction.replace("amp", "");
			datas.put("formhash", formhash);
			datas.put("referer", Api.HOST);
			datas.put("loginfield", "username");
			datas.put("password", password);
			datas.put("questionid", "0");
			datas.put("loginsubmit", "true");
			datas.put("username", username);
			cookies = before_login.cookies();
			cookies.put("sendmail", "1");
			Connection.Response logining = Request.execute(
					String.format(Api.LOGIN, loginaction), "Mozilla", datas,
					cookies, Connection.Method.POST);
			if (logining.cookie("v3hW_2132_auth") == null) {
				return false;
			} else {
				cookies = logining.cookies();
				cookies.put("v3hW_2132_saltkey",
						before_login.cookie("v3hW_2132_saltkey"));
				cookies.put("login", "yes");
				Athority.addCookies(cookies);
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			menu.showMenu();
			return true;
		case R.id.login:
			actionAlertDialog_login();
			return true;
		case R.id.logout:
			Toast.makeText(this, "还没加呢", Toast.LENGTH_SHORT).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			switch (keyClickCount++) {
			case 0:
				Toast.makeText(this, "双击退出", Toast.LENGTH_SHORT).show();
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						keyClickCount = 0;
					}
				}, 3000);
				break;
			case 1:
				finish();
				break;
			default:
				keyClickCount = 0;
				break;
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
