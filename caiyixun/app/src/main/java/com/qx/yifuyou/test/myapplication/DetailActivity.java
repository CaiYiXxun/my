package com.qx.yifuyou.test.myapplication;
import android.view.View;
import android.widget.TextView;
import com.qx.yifuyou.test.data.Package;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author CYX
 */
public class DetailActivity extends AppCompatActivity
{


    private Package myPackage;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();
    }

    private void initView()
    {
        Intent intent=getIntent();
        myPackage=(Package) intent.getSerializableExtra("package");

        toolbar = findViewById(R.id.bill_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        TextView rec_name_tv=findViewById(R.id.rec_name_tv);
        TextView rec_phone_tv=findViewById(R.id.rec_phone_tv);
        TextView rec_city_tv=findViewById(R.id.rec_city_tv);
        TextView rec_address_tv=findViewById(R.id.rec_address_tv);
        TextView sent_name_tv=findViewById(R.id.sent_name_tv);
        TextView sent_phone_tv=findViewById(R.id.sent_phone_tv);
        TextView sent_city_tv=findViewById(R.id.sent_city_tv);
        TextView sent_address_tv=findViewById(R.id.sent_address_tv);
        TextView order_number_tv=findViewById(R.id.order_number_tv);
        TextView courier_number_tv=findViewById(R.id.courier_number_tv);

        rec_name_tv.setText(myPackage.getRecipientName());
        rec_phone_tv.setText(myPackage.getRecipientPhone());
        rec_city_tv.setText(myPackage.getRecipientProvince()+"-"+myPackage.getRecipientCity());
        rec_address_tv.setText(myPackage.getRecipientAddress());
        sent_name_tv.setText(myPackage.getSenderName());
        sent_phone_tv.setText(myPackage.getSenderPhone());
        sent_city_tv.setText(myPackage.getSendingProvince()+"-"+myPackage.getSendingCity());
        sent_address_tv.setText(myPackage.getSendingAddress());
        order_number_tv.setText("订单号:"+myPackage.getOrderNumber());
        courier_number_tv.setText("快递单号:"+myPackage.getCourierNumber());

        RecyclerView recyclerView = findViewById(R.id.detail_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DetailAdapter detailAdapter = new DetailAdapter(myPackage.getTransportRecordList(),this);
        recyclerView.setAdapter(detailAdapter);
    }


}
