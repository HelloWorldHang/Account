package com.example.account;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.widget.TabHost;

public class MainActivity extends TabActivity {
	private TabHost tabHost;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		// 选项卡容器对象
		tabHost=getTabHost();
		Resources localResources = getResources();
		// 账单查询的intent
		Intent localIntent2 = new Intent();
		localIntent2.setClass(this, QueryBill.class);
		// 添加选项卡
		tabHost.addTab(tabHost.newTabSpec("账单查询")
				.setIndicator("账单查询",localResources.getDrawable(R.drawable.zhangdanchaxun))
				.setContent(localIntent2));

		// 添加支出的intent
		Intent localIntent1 = new Intent();
		localIntent1.setClass(this, AddEvent.class);
		
		TabHost.TabSpec localTabSpec1=tabHost.newTabSpec("添加支出");
		localTabSpec1.setIndicator("添加支出",localResources.getDrawable(R.drawable.tianjiazhangdan));
		//localTabSpec1.setContent(R.id.RelativeLayout01);
		localTabSpec1.setContent(localIntent1);
		tabHost.addTab(localTabSpec1);
        /*
		LayoutInflater.from(this).inflate(R.layout.baobiao, tabHost.getTabContentView(), true);*/
		// 添加收入的intent
        Intent localIntent4 = new Intent();
		localIntent4.setClass(this, shouru.class);
		// 新建选项卡
		tabHost.addTab(tabHost.newTabSpec("添加收入")
				.setIndicator("添加收入",localResources.getDrawable(R.drawable.tongjibaobiao))
				.setContent(localIntent4));
		//LayoutInflater.from(this).inflate(R.layout.activity_main, tabHost.getTabContentView(), true);
		Intent localIntent3 = new Intent();
		localIntent3.setClass(this, PrefsActivity.class);
		tabHost.addTab(tabHost.newTabSpec("软件设置")
				.setIndicator("软件设置",localResources.getDrawable(R.drawable.fenleichaxun))
				.setContent(localIntent3));


	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/

}
