package com.qx.yifuyou.test.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.qx.yifuyou.test.data.Package;
import com.qx.yifuyou.test.data.QueryAccount;
import com.qx.yifuyou.test.data.QueryPackage;
import com.qx.yifuyou.test.data.Quest;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity
{

    private ArrayList<Package> sentPackageList = new ArrayList<>();
    private ArrayList<Package> receivePackageList = new ArrayList<>();
    private Fragment sentFragment;
    private Fragment receiveFragment;
    private Handler handler;
    private QueryPackage queryPackage;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new MyHandler();
        sentPackageList = (ArrayList<Package>) getIntent().getSerializableExtra("sentPackageList");
        receivePackageList = (ArrayList<Package>) getIntent().getSerializableExtra("receivePackageList");

        sentFragment = new SentFragment();
        receiveFragment = new ReceiveFragment();
        onclickMyReceive(findViewById(R.id.myreceive));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        editText = findViewById(R.id.edt_search);
        editText.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP)
                {
                    onClickSearch(findViewById(R.id.search_button1));
                }
                return false;
            }
        });
    }

    public void onclickMySent(View view)
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable("sentPackageList", sentPackageList);
        sentFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, sentFragment);
        transaction.commit();
    }

    public void onclickMyReceive(View view)
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable("receivePackageList", receivePackageList);
        receiveFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, receiveFragment);
        transaction.commit();
    }

    public void onClickMyBill(View view)
    {

        Intent intent = new Intent(this, BillActivity.class);
        intent.putExtra("sentPackageList", sentPackageList);
        startActivity(intent);
    }


    public void onClickSearch(View view)
    {
        //要向服务端查询包裹
        queryPackage = new QueryPackage(new Quest(2, editText.getText().toString()), handler);
        Thread t1 = new Thread(queryPackage);
        t1.start();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private class MyHandler extends Handler
    {
        @Override
        public void handleMessage(@NonNull Message msg)
        {
            super.handleMessage(msg);
            if (msg.what == 0)
            {
                if (queryPackage.getaPackage().getFreight() > 0)
                {
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("package", queryPackage.getaPackage());
                    startActivity(intent);
                } else
                {
                    Toast.makeText(MainActivity.this, "没有此包裹！", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}
