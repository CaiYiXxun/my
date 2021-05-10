package com.qx.yifuyou.test.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qx.yifuyou.test.data.Package;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.Viewholder>
{

    private ArrayList<Package> packageList;
    private Context context;

    public BillAdapter(ArrayList<Package> packageList, Context context)
    {
        this.packageList = packageList;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(context).inflate(R.layout.bill_recyclerview_item, parent, false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position)
    {
        holder.tv_num.setText("订单号:"+packageList.get(position).getOrderNumber());
        holder.tv_price.setText("¥"+packageList.get(position).getFreight());
        holder.tv_from.setText(packageList.get(position).getSendingCity());
        holder.tv_to.setText(packageList.get(position).getRecipientCity());

    }

    @Override
    public int getItemCount()
    {
        return (packageList==null)?0:packageList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder
    {
        private int position;
        private RelativeLayout item;
        private TextView tv_num;
        private TextView tv_price;
        private TextView tv_from;
        private TextView tv_to;

        public Viewholder(@NonNull View itemView)
        {
            super(itemView);
            item=itemView.findViewById(R.id.bill_item);
            tv_num = itemView.findViewById(R.id.bill_recyclerView_tv_num);
            tv_price = itemView.findViewById(R.id.bill_recyclerView_tv_price);
            tv_from = itemView.findViewById(R.id.bill_recyclerView_tv_from);
            tv_to = itemView.findViewById(R.id.bill_recyclerView_tv_to);
            item.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Context context = v.getContext();
                    Intent intent=new Intent(context,DetailActivity.class);
                    intent.putExtra("package",packageList.get(position));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
