package com.example.account;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.widget.Toast;

public class PrefsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    private EditTextPreference moneyFlag;
    private EditTextPreference incomeMoneyFlag;
    private ListPreference textSize;
    private Preference cleanHistory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        moneyFlag = (EditTextPreference) findPreference("moneyFlag");
        incomeMoneyFlag = (EditTextPreference) findPreference("incomeMoneyFlag");

        initSummary();
    }

    //初始化summary
    private void initSummary() {
        moneyFlag.setSummary(moneyFlag.getText());
        incomeMoneyFlag.setSummary(incomeMoneyFlag.getText());

    }

    /**
     * 重写PreferenceActivity的onPreferenceTreeClick方法
     * 在首选项被点击时 做出相应处理操作
     */
    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference == cleanHistory) {
            new AlertDialog.Builder(this)
                    .setTitle("清除历史记录")
                    .setMessage("是否真的要清除历史记录？")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //cleaning history...
                            Toast.makeText(PrefsActivity.this, "清除成功", Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        }
        return true;
    }

    /**
     * 重写Preference.OnPreferenceChangeListener的onPreferenceChange方法
     * 当首选项的值更改时 做出相应处理操作
     */
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == moneyFlag) {
            moneyFlag.setSummary(newValue.toString());
        }else if(preference == incomeMoneyFlag){
            incomeMoneyFlag.setSummary(newValue.toString());
        }
        return true;
    }
}
