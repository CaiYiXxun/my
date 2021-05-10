package com.qx.yifuyou.test.myapplication;

import com.qx.yifuyou.test.data.Package;
import com.qx.yifuyou.test.data.QueryAccount;
import com.qx.yifuyou.test.data.Quest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity
{

    private SharedPreferences sp;
    private EditText et_account;
    private EditText et_password;
    private CheckBox checkBox;
    private Button button;
    private ProgressBar progressBar;
    private Handler handler;
    private ArrayList<Package> sentPackageList;
    private ArrayList<Package> receivePackageList;
    private QueryAccount queryAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp = getSharedPreferences("mysp", Context.MODE_PRIVATE);
        initView();
        Log.d("login", "qidong");
        boolean isRemb = sp.getBoolean("isRemb", false);
        if (isRemb)
        {
            String id = sp.getString("id", "");
            String pwd = sp.getString("pwd", "");
            et_account.setText(id);
            et_password.setText(pwd);
            checkBox.setChecked(true);
        }
    }

    private void initView()
    {
        et_account = findViewById(R.id.edit_account);
        et_password = findViewById(R.id.edit_password);
        checkBox = findViewById(R.id.ch_box);
        button = findViewById(R.id.bt_login);
        progressBar = findViewById(R.id.progressbar_login);

        handler = new MyHandler();

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String id = et_account.getText().toString();
                String pwd = et_password.getText().toString();
                if (TextUtils.isEmpty(id) || TextUtils.isEmpty(pwd))
                {
                    Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                } else
                {
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                    if (checkBox.isChecked())
                    {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("id", id);
                        editor.putString("pwd", pwd);
                        editor.putBoolean("isRemb", true);
                        editor.apply();
                    }
                    if (!checkBox.isChecked())
                    {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean("isRemb", false);
                        editor.apply();
                    }

                    //服务端查询账号密码
                    queryAccount = new QueryAccount(new Quest(1, id, pwd), handler);
                    Thread t1 = new Thread(queryAccount);
                    t1.start();
                }
            }
        });
    }

    private class MyHandler extends Handler
    {
        @Override
        public void handleMessage(@NonNull Message msg)
        {
            super.handleMessage(msg);
            if (msg.what == 0)
            {
                if (queryAccount.getUser().isCorrect())
                {
                    sentPackageList = queryAccount.getUser().getSend_packages();
                    receivePackageList = queryAccount.getUser().getReceive_packages();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("sentPackageList", sentPackageList);
                    intent.putExtra("receivePackageList", receivePackageList);
                    Toast.makeText(LoginActivity.this, "已登录", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                } else
                {
                    Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(ProgressBar.GONE);
                }
            }
        }
    }
}
