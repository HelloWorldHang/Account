package com.example.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import models.MyPackage;
import models.TradeClass;
import models.consumeClass;

public class QueryByMouthActivity extends AppCompatActivity
{
	String[] bill_array=null;
	private ListView listView;
	private Map<Integer, Boolean> localmap;
	private Adapter_LS myadapter;
	private TextView textView;
	private void fillList()
	{
		// TODO Auto-generated method stub
		//ArrayAdapter<String>  adapter=null;
		listView =(ListView)findViewById(R.id.listViewlisi);
		textView =(TextView)findViewById(R.id.textViewlisi);

		Calendar localCalendar = Calendar.getInstance();
		int year = localCalendar.get(Calendar.YEAR);
		int month = localCalendar.get(Calendar.MONTH)+1;
		String str1=new String(year+"-"+month);
		String str;
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		float monthOutMenoy=0;
		float monthIncomeMenoy = 0;
		MyPackage pack=new MyPackage(this);
		monthOutMenoy = pack.getConsumeSum();
		monthIncomeMenoy = pack.getIncomeSum();
		List<TradeClass> List=pack.getAlltrade();
		for(TradeClass con:List){
			str = con.gettime();
			str=str.substring(0, str.lastIndexOf('-'));
			if(str1.equals(str)){
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("_id", con.getId());
				map.put("money", ""+con.getMoney());
				if(con.getPocketType().equals("日常购物")){
					map.put("icon",R.drawable.richanggouwu);
				}else if(con.getPocketType().equals("交际送礼")){
					map.put("icon",R.drawable.jiaojisongli);
				}else if(con.getPocketType().equals("餐饮开销")){
					map.put("icon",R.drawable.canyingkaixiao);
				}else if(con.getPocketType().equals("购置衣物")){
					map.put("icon",R.drawable.gouziyiwu);
				}else if(con.getPocketType().equals("娱乐开销")){
					map.put("icon",R.drawable.yulekaixiao);
				}else if(con.getPocketType().equals("水电煤气")){
					map.put("icon",R.drawable.shuidianmeiqi);
				}else if(con.getPocketType().equals("网费话费")){
					map.put("icon",R.drawable.wannluohuafei);
				}else if(con.getPocketType().equals("交通出行")){
					map.put("icon",R.drawable.jiaotongchuxing);
				}else if(con.getPocketType().equals("其他花费")){
					map.put("icon",R.drawable.qita);
				}else{
					map.put("icon",R.drawable.qita);
				}
				map.put("time", con.gettime());
				map.put("type", con.getPocketType());
				list.add(map);
			}
		}
		localmap = new HashMap<Integer, Boolean>();
		myadapter=new Adapter_LS(this, list, localmap);
		textView.setText("本月共花费："+(-monthOutMenoy)+"元,共收入"+monthIncomeMenoy+"元");
		listView.setAdapter(myadapter);
	}

	public void onCreate(Bundle paramBundle)
	{
		super.onCreate(paramBundle);
		setContentView(R.layout.all_bill);
		fillList();
	}

	public boolean onCreateOptionsMenu(Menu paramMenu)
	{
		MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.del_menu,paramMenu);
        return true;
		/*paramMenu.add(0, 1, 1, "删除").setIcon(R.drawable.delete);
		return super.onCreateOptionsMenu(paramMenu);*/
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.baobiao:
				Intent intent = new Intent(QueryByMouthActivity.this, baobiao.class);
				//如果requestCode >= 0 则返回结果时会回调 onActivityResult()方法
				intent.putExtra("flag","month");
				startActivity(intent);
				break;
			case R.id.delItem:
				Toast.makeText(QueryByMouthActivity.this,"月账单不能删除",Toast.LENGTH_SHORT).show();
				break;
			case R.id.menu3:
				Toast.makeText(QueryByMouthActivity.this,"敬请期待",Toast.LENGTH_SHORT).show();
				break;
			default:
				finish();
		}

		return true;
	}

	/*public boolean onOptionsItemSelected(MenuItem paramMenuItem){
		// = Adapter_TD.isSelected;
		Toast.makeText(this, "不能做假账哦!", 0).show();
		  *//*
		    if (localmap.size() <= 0)
		    {
		      Toast.makeText(this, "请先选择要删除的消费记录!", 0).show();
		      return true;
		    }
	        consumeClass trade=new consumeClass(0, 0, "", "123", "", QueryByMouthActivity.this);
		    Iterator<?> it = localmap.entrySet().iterator();
		    while (it.hasNext()) {
			     Map.Entry entry = (Map.Entry) it.next();
			     Object key = entry.getKey();
			     Object value = entry.getValue();
			     if((Boolean)value){
			    	 trade.trade_delect((Integer)key);
			     }
			     //Log.i("nihao","key=" + key + " value=" + value);
		    }
		    fillList(); *//*
		return true;
	}*/

	protected void onResume()
	{
		fillList();
		super.onResume();
	}
}