package com.qx.yifuyou.test.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qx.yifuyou.test.data.Package;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RVadapter extends RecyclerView.Adapter<RVadapter.Viewholder>
{

    private ArrayList<Package> packageList;
    private Context context;

    public RVadapter(ArrayList<Package> packageList, Context context)
    {
        this.packageList = packageList;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(context).inflate(R.layout.rec_recyclerview_item, parent, false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position)
    {
        holder.tv_num.setText("快递单号："+packageList.get(position).getCourierNumber());
        holder.tv_from.setText(packageList.get(position).getSendingCity());
        holder.tv_from1.setText(packageList.get(position).getSenderName());
        holder.tv_to.setText(packageList.get(position).getRecipientCity());
        holder.tv_to1.setText(packageList.get(position).getRecipientName());
        holder.setPosition(position);
    }

    @Override
    public int getItemCount()
    {
        return (packageList==null)?0:packageList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder
    {

        private int position;
        private LinearLayout rec_recyclerview_item;
        private TextView tv_num;
        private TextView tv_from;
        private TextView tv_from1;
        private TextView tv_to;
        private TextView tv_to1;

        public Viewholder(@NonNull View itemView)
        {
            super(itemView);
            rec_recyclerview_item=itemView.findViewById(R.id.rec_recyclerview_item);
            tv_num = itemView.findViewById(R.id.rec_recyclerView_tv_num);
            tv_from = itemView.findViewById(R.id.rec_recyclerView_tv_from);
            tv_from1 = itemView.findViewById(R.id.rec_recyclerView_tv_from1);
            tv_to = itemView.findViewById(R.id.rec_recyclerView_tv_to);
            tv_to1 = itemView.findViewById(R.id.rec_recyclerView_tv_to1);
            rec_recyclerview_item.setOnClickListener(new View.OnClickListener()
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
            public void setPosition (int position)
            {
                this.position = position;
            }

    }
}
