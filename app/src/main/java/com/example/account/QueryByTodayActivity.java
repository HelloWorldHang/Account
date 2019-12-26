package com.example.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import models.MyPackage;
import models.TradeClass;
import models.consumeClass;
import models.incomeClass;

public class QueryByTodayActivity extends AppCompatActivity {

    private ListView listView;
    private TextView textView;
    private Adapter_TD myadapter;
    Map<typeClass, Boolean> localmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.querybytoday);
        GetTodayBill();
    }
    private void GetTodayBill() {
        // TODO Auto-generated method stub
        //ArrayAdapter<String>  adapter=null;
        listView =(ListView)findViewById(R.id.listView);
        textView =(TextView)findViewById(R.id.textView);
        List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
        float todaymenoy=0;
        float todayIncome = 0;
        MyPackage pack=new MyPackage(this);
        // 查询今日花费
        todaymenoy = pack.getTodayConsumeSum();
        // 查询今日收入
        todayIncome = pack.getTodayIncomeSum();
        // 查询所有支出
        List<TradeClass> list_all=pack.getAlltrade();
        //bill_array=new String[List.size()];
        int i=0;
        Calendar localCalendar = Calendar.getInstance();
        int year = localCalendar.get(Calendar.YEAR);
        int month = localCalendar.get(Calendar.MONTH)+1;
        int day = localCalendar.get(Calendar.DAY_OF_MONTH);
        String str1=new String(year+"-"+month+"-"+day);
        String str;
        for(TradeClass con:list_all){
            str=con.gettime();
            if(str1.equals(str)){
                // todaymenoy+=con.getMoney();
                Map<String,Object> map=new HashMap<String,Object>();
                map.put("_id", con.getId());
                map.put("money", ""+con.getMoney());
                if(con.getPocketType().equals("日常购物")){
                    map.put("icon",R.drawable.richanggouwu);
                    map.put("classtype",0);
                }else if(con.getPocketType().equals("交际送礼")){
                    map.put("icon",R.drawable.jiaojisongli);
                    map.put("classtype",0);
                }else if(con.getPocketType().equals("餐饮开销")){
                    map.put("icon",R.drawable.canyingkaixiao);
                    map.put("classtype",0);
                }else if(con.getPocketType().equals("购置衣物")){
                    map.put("icon",R.drawable.gouziyiwu);
                    map.put("classtype",0);
                }else if(con.getPocketType().equals("娱乐开销")){
                    map.put("icon",R.drawable.yulekaixiao);
                    map.put("classtype",0);
                }else if(con.getPocketType().equals("水电煤气")){
                    map.put("icon",R.drawable.shuidianmeiqi);
                    map.put("classtype",0);
                }else if(con.getPocketType().equals("网费话费")){
                    map.put("icon",R.drawable.wannluohuafei);
                    map.put("classtype",0);
                }else if(con.getPocketType().equals("交通出行")){
                    map.put("icon",R.drawable.jiaotongchuxing);
                    map.put("classtype",0);
                }else if(con.getPocketType().equals("其他花费")){
                    map.put("icon",R.drawable.qita);
                    map.put("classtype",0);
                }else if(con.getPocketType().equals("工资收入")){
                    map.put("icon",R.drawable.gongzi);
                    map.put("classtype",1);
                }else if(con.getPocketType().equals("股票收入")){
                    map.put("icon",R.drawable.gupiao);
                    map.put("classtype",1);
                }else {
                    map.put("icon",R.drawable.qita);
                    map.put("classtype",1);
                }
                map.put("time", con.gettime());
                map.put("type", con.getPocketType());
                list.add(map);
                i++;
            }
        }

        localmap=new HashMap<typeClass, Boolean>();
        myadapter=new Adapter_TD(this, list, localmap);
        textView.setText("今日共花费："+(-todaymenoy)+"元,今日共收入"+todayIncome+"元");
        listView.setAdapter(myadapter);
        if(i==0){
            Toast.makeText(getApplicationContext(), "今天您还没有消费哦！", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu paramMenu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.del_menu,paramMenu);
        return true;
        /*paramMenu.add(0, 1, 1, "删除").setIcon(R.drawable.delete);
        return super.onCreateOptionsMenu(paramMenu);*/
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //if (del()) return true;
        switch (item.getItemId()){
            case R.id.baobiao:
                Intent intent = new Intent(QueryByTodayActivity.this, baobiao.class);
                //如果requestCode >= 0 则返回结果时会回调 onActivityResult()方法
                intent.putExtra("flag","day");
                startActivity(intent);
                break;
            case R.id.delItem:
                del();
                GetTodayBill();
                break;
            case R.id.menu3:
                Toast.makeText(QueryByTodayActivity.this,"敬请期待",Toast.LENGTH_SHORT).show();
                break;
            default:
                GetTodayBill();
        }

        return true;
    }

    private boolean del() {
        // = Adapter_TD.isSelected;
        if (localmap.size() <= 0)
        {
            Toast.makeText(this, "请先选择要删除的消费记录!", Toast.LENGTH_SHORT).show();
            return true;
        }
        consumeClass tradeconsume=new consumeClass(0, 0, "", "123", "", QueryByTodayActivity.this);
        incomeClass tradeincome=new incomeClass(0, 0, "", "123", "", QueryByTodayActivity.this);
        Iterator it = localmap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            if((Boolean)value){
                if(((typeClass)key).type==0){
                    int success=tradeconsume.trade_delect(((typeClass)key)._id);
                    if(success==1)Toast.makeText(this, "删除消费记录成功!", Toast.LENGTH_SHORT).show();
                }else{
                    int success=tradeincome.trade_delect(((typeClass)key)._id);
                    if(success==1)Toast.makeText(this, "删除收入记录成功!", Toast.LENGTH_SHORT).show();
                }
            }
            //Log.i("nihao","key=" + key + " value=" + value);
        }
        return false;
    }

    protected void onResume()
    {
        GetTodayBill();
        super.onResume();
    }

}
