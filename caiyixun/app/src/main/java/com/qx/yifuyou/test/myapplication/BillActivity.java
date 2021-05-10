package com.qx.yifuyou.test.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;
import com.qx.yifuyou.test.data.Package;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BillActivity extends AppCompatActivity
{

    private ArrayList<Package> packageList = new ArrayList<>();
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        initView();
    }

    private void initView()
    {
        toolbar = findViewById(R.id.bill_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        Intent intent=getIntent();
        packageList=(ArrayList<Package>)intent.getSerializableExtra("sentPackageList");

        TextView sum_tv = findViewById(R.id.sum_tv);
        sum_tv.setText("¥"+sumFreight());

        RecyclerView recyclerView = findViewById(R.id.bill_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        BillAdapter billadapter = new BillAdapter(packageList, this);
        recyclerView.setAdapter(billadapter);
    }


    private int sumFreight()
    {
        int sum=0;
        for (int i = 0; i < packageList.size(); i++)
        {
            sum+=packageList.get(i).getFreight();
        }
        return sum;
    }

}
