package com.qx.yifuyou.test.myapplication;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qx.yifuyou.test.data.Package;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * create by yifyou on 2021/3/31
 * project My Application
 */
public class SRVadapter extends RecyclerView.Adapter<SRVadapter.Viewholder>
{

    private ArrayList<Package> packageList;
    private Context context;

    public SRVadapter(ArrayList<Package> packageList, Context context)
    {
        this.packageList = packageList;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(context).inflate(R.layout.sen_recyclerview_item, parent, false);
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
        return packageList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder
    {
        int position;
        private TextView tv_num;
        private LinearLayout sen_recyclerview_item;
        private TextView tv_from;
        private TextView tv_from1;
        private TextView tv_to;
        private TextView tv_to1;

        public Viewholder(@NonNull View itemView)
        {
            super(itemView);
            sen_recyclerview_item = itemView.findViewById(R.id.sen_recyclerview_item);
            tv_num = itemView.findViewById(R.id.sen_recyclerView_tv_num);
            tv_from = itemView.findViewById(R.id.sen_recyclerView_tv_from);
            tv_from1 = itemView.findViewById(R.id.sen_recyclerView_tv_from1);
            tv_to = itemView.findViewById(R.id.sen_recyclerView_tv_to);
            tv_to1 = itemView.findViewById(R.id.sen_recyclerView_tv_to1);
            sen_recyclerview_item.setOnClickListener(new View.OnClickListener()
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
            tv_num.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipDataSet = ClipData.newPlainText(null, tv_num.getText().toString().split("：")[1]);
                    clipboard.setPrimaryClip(clipDataSet);
                    Toast.makeText(context,"已复制",Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }

        public void setPosition(int position)
        {
            this.position = position;
        }
    }
}
