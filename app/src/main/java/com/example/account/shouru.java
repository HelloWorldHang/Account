package com.example.account;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import models.MyPackage;
import models.consumeClass;
import models.incomeClass;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class shouru  extends Activity{
	private TextView addDate = null;
	private Spinner typeSpinner;
	public String addType="";
	private Button addButton;
	private EditText money;
	DatePickerDialog.OnDateSetListener OnDateSetListener ;
	MediaPlayer mediaPlayer;
	public void initPlay(){
		mediaPlayer = MediaPlayer.create(shouru.this,R.raw.income);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addincome);

		this.typeSpinner = ((Spinner)findViewById(R.id.income_type));
		ArrayList<String> localArrayList = new ArrayList();
		localArrayList.add("工资收入");
		localArrayList.add("股票收入");
		localArrayList.add("其他收入");
		@SuppressWarnings("unchecked")
		ArrayAdapter<String> localArrayAdapter = new ArrayAdapter(this, R.layout.spinner_item, R.id.spinnerItem, localArrayList);
		this.typeSpinner.setAdapter(localArrayAdapter);
		this.typeSpinner.setPrompt("请选择收入类型");
		this.typeSpinner.setOnItemSelectedListener(new SpinnerSelected());
		this.addDate = ((TextView)findViewById(R.id.income_addDate));
		this.addDate.setOnClickListener(new DateOnClick());
		this.addButton = ((Button)findViewById(R.id.income_addButton));
		this.addButton.setOnClickListener(new AddPocketClick());
		this.money = ((EditText)findViewById(R.id.income_money));
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
				addDate.setText(paramInt1 + "-" + mm + "-" + dd);
			}
		};

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

	//弹出提示
	private void dialog()
	{
		new AlertDialog.Builder(this).setTitle("添加一笔新收入?").setMessage("收入类型：" + this.addType + "\n收入金额：" + this.money.getText().toString() + "\n收入日期：" + this.addDate.getText().toString()).setPositiveButton("确定", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface paramDialogInterface, int paramInt)
			{
				shouru.this.setResult(-1);
				//AddEvent.this.addPocket();
				//确定添加
				incomeClass trade=new incomeClass(0, Float.parseFloat(shouru.this.money.getText().toString()),
						shouru.this.addDate.getText().toString(), "123", addType, shouru.this);

				trade.trade_add();
				String prefsName = getPackageName() + "_preferences";  //[PACKAGE_NAME]_preferences
				SharedPreferences prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE);
				String incomeMoneyFlagStr = prefs.getString("incomeMoneyFlag", "15000");
				MyPackage myPackage = new MyPackage(shouru.this);
				float incomeSum = myPackage.getIncomeSum();
				if (incomeSum > Float.valueOf(incomeMoneyFlagStr)){
					initPlay();
					if (!mediaPlayer.isPlaying()){
						mediaPlayer.start();
					}
				}
				Toast.makeText(shouru.this, "添加完成", Toast.LENGTH_SHORT).show();
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
			if (shouru.this.addDate.getText().equals("点击选择日期"))
			{
				Toast.makeText(shouru.this, "请先选择收入日期", Toast.LENGTH_SHORT).show();
				return;
			}
			if (shouru.this.money.getText().toString().trim().length() == 0)
			{
				Toast.makeText(shouru.this, "请先填写收入金额", Toast.LENGTH_SHORT).show();
				return;
			}
			shouru.this.dialog();
		}
	}

	class DateOnClick implements View.OnClickListener
	{
		DateOnClick()
		{

		}

		public void onClick(View paramView)
		{
			shouru.this.showDialog(1);
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
			shouru.this.addType = str;
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	}
}