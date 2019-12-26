package models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import db.DBHelper;

public class MyPackage {
	float income_sum;
	float consume_sum;
	private DBHelper dbhelper;
	private SQLiteDatabase db;
	private Context context;
	//public 
	public MyPackage(Context context){
		this.context=context;
		dbhelper=new DBHelper(context);
	}
	public float getincome(){
		return income_sum;
	} 
	
	public float getconsume(){
		return consume_sum;
	}

	/**
	 *得到所有的账单
	 * @return
	 */
	public List<TradeClass> getAlltrade(){
		List<TradeClass> TradeList=new ArrayList<TradeClass>();
		db=dbhelper.getReadableDatabase();
		// 从支出数据库中查询
		Cursor cu=db.rawQuery("select * from tb_outaccount", null);
		while(cu.moveToNext()){
			TradeList.add(new consumeClass(cu.getInt(cu.getColumnIndex("_id")),cu.getFloat(cu.getColumnIndex("money")),
					cu.getString(cu.getColumnIndex("addTime")),cu.getString(cu.getColumnIndex("mark")),cu.getString(cu.getColumnIndex("pocketType")),
					context));	
		}
		// 从收入数据库中查询
		cu=db.rawQuery("select * from tb_inaccount", null);
		while(cu.moveToNext()){
			TradeList.add(new incomeClass(cu.getInt(cu.getColumnIndex("_id")),cu.getFloat(cu.getColumnIndex("money")),
					cu.getString(cu.getColumnIndex("addTime")),cu.getString(cu.getColumnIndex("mark")),cu.getString(cu.getColumnIndex("pocketType")),
					context));
		}
		return TradeList;
	}

	public float getTodayConsumeSum(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(date);
		String subTime = time.substring(0, 10);
		db=dbhelper.getReadableDatabase();
		// 从支出数据库中查询
		String sql = "select sum(money) as sumMoney from tb_outaccount where substr(addTime, 1, 10)=?";
		Cursor cu=db.rawQuery(sql, new String[]{subTime});
		float sumMoney = 0;
		if (cu!=null)
		{
			if (cu.moveToFirst())
			{
				do
				{
					sumMoney=cu.getFloat(0);
				} while (cu.moveToNext());
			}
		}
		return sumMoney;
	}

	public float getTodayIncomeSum(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(date);
		String subTime = time.substring(0, 10);
		db=dbhelper.getReadableDatabase();
		// 从支出数据库中查询
		String sql = "select sum(money) as sumMoney from tb_inaccount where substr(addTime, 1, 10)=?";
		Cursor cu=db.rawQuery(sql, new String[]{subTime});
		float sumMoney = 0;
		if (cu!=null)
		{
			if (cu.moveToFirst())
			{
				do
				{
					sumMoney=cu.getFloat(0);
				} while (cu.moveToNext());
			}
		}
		return sumMoney;
	}

	/**
	 * 本月花费总额
	 * @return
	 */
	public float getConsumeSum(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(date);
		String subTime = time.substring(0, 7);
		db=dbhelper.getReadableDatabase();
		// 从支出数据库中查询
		String sql = "select sum(money) as sumMoney from tb_outaccount where substr(addTime, 1, 7)=?";
		Cursor cu=db.rawQuery(sql, new String[]{subTime});
		float sumMoney = 0;
		if (cu!=null)
		{
			if (cu.moveToFirst())
			{
				do
				{
					sumMoney=cu.getFloat(0);
				} while (cu.moveToNext());
			}
		}
		return sumMoney;
	}

	/**
	 * 得到本月收入
	 * @return
	 */
	public float getIncomeSum(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(date);
		String subTime = time.substring(0, 7);
		db=dbhelper.getReadableDatabase();
		// 从支出数据库中查询
		String sql = "select sum(money) as sumMoney from tb_inaccount where substr(addTime, 1, 7)=?";
		Cursor cu=db.rawQuery(sql, new String[]{subTime});
		float sumMoney = 0;
		if (cu!=null)
		{
			if (cu.moveToFirst())
			{
				do
				{
					sumMoney=cu.getFloat(0);
				} while (cu.moveToNext());
			}
		}
		return sumMoney;
	}

	/**
	 * 查看本月报表
	 * @return
	 */
	public Map<String, Float> getBaoBiao(){
		HashMap<String, Float> map = new HashMap<>();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(date);
		String subTime = time.substring(0, 7);
		db=dbhelper.getReadableDatabase();
		// 从支出数据库中查询
		String sql = "select pocketType,money from tb_outaccount where substr(addTime, 1, 7)=?";
		Cursor cu=db.rawQuery(sql, new String[]{subTime});
		while(cu.moveToNext()){
			map.put(cu.getString(0),cu.getFloat(1));
		}
		return map;
	}

	/**
	 * 查看本日报表
	 * @return
	 */
	public Map<String, Float> getTodayBaoBiao(){
		HashMap<String, Float> map = new HashMap<>();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(date);
		String subTime = time.substring(0, 10);
		db=dbhelper.getReadableDatabase();
		// 从支出数据库中查询
		String sql = "select pocketType,money from tb_outaccount where substr(addTime, 1, 10)=?";
		Cursor cu=db.rawQuery(sql, new String[]{subTime});
		while(cu.moveToNext()){
			map.put(cu.getString(0),cu.getFloat(1));
		}
		return map;
	}
}
