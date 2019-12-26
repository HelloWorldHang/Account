package com.example.account;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

import models.MyPackage;
import models.TradeClass;
import models.consumeClass;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddEvent extends Activity{
	private TextView addDate = null;
	// 下拉菜单
	private Spinner typeSpinner;
	public String addType="";
	private Button addButton;
	private EditText money;
	DatePickerDialog.OnDateSetListener OnDateSetListener ;
	MediaPlayer mediaPlayer;
	public void initPlay(){
		mediaPlayer = MediaPlayer.create(AddEvent.this,R.raw.consume);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addevent);

		this.typeSpinner = ((Spinner)findViewById(R.id.type));
		ArrayList localArrayList = new ArrayList();
		localArrayList.add("日常购物");
		localArrayList.add("交际送礼");
		localArrayList.add("餐饮开销");
		localArrayList.add("购置衣物");
		localArrayList.add("娱乐开销");
		localArrayList.add("网费话费");
		localArrayList.add("交通出行");
		localArrayList.add("水电煤气");
		localArrayList.add("其他花费");
		ArrayAdapter localArrayAdapter = new ArrayAdapter(this, R.layout.spinner_item, R.id.spinnerItem, localArrayList);
		this.typeSpinner.setAdapter(localArrayAdapter);
		this.typeSpinner.setPrompt("请选择消费类型");
		this.typeSpinner.setOnItemSelectedListener(new SpinnerSelected());
		this.addDate = ((TextView)findViewById(R.id.addDate));
		this.addDate.setOnClickListener(new DateOnClick());
		this.addButton = ((Button)findViewById(R.id.addButton));
		this.addButton.setOnClickListener(new AddPocketClick());
		this.money = ((EditText)findViewById(R.id.money));
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		addDate.setText(format.format(date));
		OnDateSetListener = new DatePickerDialog.OnDateSetListener()
		{
			public void onDateSet(DatePicker paramDatePicker, int paramInt1, int paramInt2, int paramInt3)
			{
				String mm = "0";
				String dd = "0";
				if(paramInt2 + 1 < 10){
					mm += String.valueOf(paramInt2+1);
				}else{
					mm = String.valueOf(paramInt2+1);
				}
				if (paramInt3 < 10){
					dd += String.valueOf(paramInt3);
				}else{
					dd = String.valueOf(paramInt3);
				}
				AddEvent.this.addDate.setText(paramInt1 + "-" + mm + "-" + dd);
			}
		};
		/*
	    this.addDate=(TextView)findViewById(R.id.addDate);
	    addDate.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(AddEvent.this, "This is a Test!", Toast.LENGTH_LONG).show();

				return false;
			}
	    });*/

	}
	protected Dialog onCreateDialog(int paramInt)
	{
		Calendar localCalendar = Calendar.getInstance();
		int i = localCalendar.get(Calendar.YEAR);
		int j = localCalendar.get(Calendar.MONTH);
		int k = localCalendar.get(Calendar.DAY_OF_MONTH);
		switch (paramInt)
		{
			default:
				return null;
			case 1:
		}
		return new DatePickerDialog(this, this.OnDateSetListener, i, j, k);
	}

	/*private void showSettings() { // 读首选项
		String prefsName = getPackageName() + "_preferences";  //[PACKAGE_NAME]_preferences
		SharedPreferences prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE);

		String nickName = prefs.getString("nickName", "机器人");
		textView.setText("欢迎您：" + nickName);

		boolean nightMode = prefs.getBoolean("nightMode", false);
		textView.setBackgroundColor(nightMode ? Color.BLACK : Color.WHITE);

		String textSize = prefs.getString("textSize", "0");
		if (textSize.equals("0")) {
			textView.setTextSize(18f);
		} else if (textSize.equals("1")) {
			textView.setTextSize(22f);
		} else if (textSize.equals("2")) {
			textView.setTextSize(36f);
		}
	}*/

	//弹出提示
	private void dialog()
	{
		new AlertDialog.Builder(this).setTitle("添加一笔新消费?").setMessage("消费类型：" + this.addType + "\n消费金额：" + this.money.getText().toString() + "\n消费日期：" + this.addDate.getText().toString()).setPositiveButton("确定", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface paramDialogInterface, int paramInt)
			{
				AddEvent.this.setResult(-1);
				//AddEvent.this.addPocket();
				//确定添加
				consumeClass trade=new consumeClass(0, Float.parseFloat("-"+AddEvent.this.money.getText().toString()), AddEvent.this.addDate.getText().toString(), "123", addType, AddEvent.this);

				trade.trade_add();
				MyPackage myPackage = new MyPackage(AddEvent.this);
				float consumeSum = myPackage.getConsumeSum();
				String prefsName = getPackageName() + "_preferences";  //[PACKAGE_NAME]_preferences
				SharedPreferences prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE);
				String moneyFlagStr = prefs.getString("moneyFlag", "1000");
				float moneyFlag = Float.valueOf(moneyFlagStr);
				if (consumeSum < -moneyFlag){
					initPlay();
					if (!mediaPlayer.isPlaying()){
						mediaPlayer.start();
					}
				}
				Toast.makeText(AddEvent.this, consumeSum+"添加完成", Toast.LENGTH_SHORT).show();

			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface paramDialogInterface, int paramInt)
			{
			}
		}).show();
	}


	class AddPocketClick implements View.OnClickListener
	{
		AddPocketClick()
		{
		}

		public void onClick(View paramView)
		{
			if (AddEvent.this.addDate.getText().equals("点击选择日期"))
			{
				Toast.makeText(AddEvent.this, "请先选择消费日期", Toast.LENGTH_SHORT).show();
				return;
			}
			if (AddEvent.this.money.getText().toString().trim().length() == 0)
			{
				Toast.makeText(AddEvent.this, "请先填写消费金额", Toast.LENGTH_SHORT).show();
				return;
			}
			AddEvent.this.dialog();
		}
	}

	class DateOnClick implements View.OnClickListener
	{
		DateOnClick()
		{

		}

		public void onClick(View paramView)
		{
			AddEvent.this.showDialog(1);
		}
	}


	class SpinnerSelected implements OnItemSelectedListener
	{
		SpinnerSelected()
		{
		}

		@Override
		public void onItemSelected(AdapterView<?> paramAdapterView, View arg1, int paramInt,
								   long arg3) {
			// TODO Auto-generated method stub
			String str = paramAdapterView.getItemAtPosition(paramInt).toString();
			AddEvent.this.addType = str;
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	}
}



