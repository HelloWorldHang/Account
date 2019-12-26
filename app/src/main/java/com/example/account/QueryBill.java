package com.example.account;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;

public class QueryBill extends TabActivity {
	/*public static final String TAB_HOME = "tabHome";
	public static final String TAB_MES = "tabMes";
	public static final String TAB_TOUCH = "tab_touch";*/
	private RadioButton checked = null;
	private RadioGroup group;
	private TabHost tabHost;
	  
	  protected void onCreate(Bundle paramBundle)
	  {
	    super.onCreate(paramBundle);
	    setContentView(R.layout.bill_query);
	    
	    this.group = ((RadioGroup)findViewById(R.id.main_radio));
	    this.tabHost = getTabHost();

	    Intent billIntent1=new Intent();
	    billIntent1.setClass(this, QueryByTodayActivity.class);
		//LayoutInflater.from(this).inflate(R.layout.bill_query, tabHost.getTabContentView(), true);
		// 查询今日账单
	    tabHost.addTab(tabHost.newTabSpec("tabHome")
	    		.setIndicator("tabHome")
	    		.setContent(billIntent1));
	    // 查询本月账单
	    tabHost.addTab(tabHost.newTabSpec("tabMes")
	    		.setIndicator("tabMes")
	    		.setContent(new Intent(this, QueryByMouthActivity.class)));
	    // 默认选中今日账单
	    checked = ((RadioButton)findViewById(R.id.radio_button0));
	    checked.setChecked(true);
	    // 点击监听
	    this.group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
	    {
	      public void onCheckedChanged(RadioGroup paramRadioGroup, int paramInt)
	      {
	        switch (paramInt)
	        {
	        case R.id.radio_button0:
	        	QueryBill.this.tabHost.setCurrentTabByTag("tabHome");
	          return;
	        case R.id.radio_button1:
				QueryBill.this.tabHost.setCurrentTabByTag("tabMes");
				return;
	        }
	      }
	    });
	  }
}
