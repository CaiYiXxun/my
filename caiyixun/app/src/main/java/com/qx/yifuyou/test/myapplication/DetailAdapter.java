package com.qx.yifuyou.test.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qx.yifuyou.test.data.TransportRecord;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.Viewholder>
{
    private ArrayList<TransportRecord> transportRecordList = new ArrayList<>();
    private Context context;

    public DetailAdapter(ArrayList<TransportRecord> transportRecordList, Context context)
    {
        this.transportRecordList = transportRecordList;
        this.context = context;
    }

    @NonNull
    @Override
    public DetailAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(context).inflate(R.layout.detail_recyclerview_item, parent, false);
        return new DetailAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAdapter.Viewholder holder, int position)
    {
        int index=transportRecordList.size()-1-position;
        holder.tv_time.setText(transportRecordList.get(index).getStartTime());
        holder.tv_message2.setText(transportRecordList.get(index).getStartWarehouse());
        holder.tv_message4.setText(transportRecordList.get(index).getDestinationWarehouse());
    }

    @Override
    public int getItemCount()
    {
        return transportRecordList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder
    {

        private TextView tv_time;
        private TextView tv_message2;
        private TextView tv_message4;

        public Viewholder(@NonNull View itemView)
        {
            super(itemView);
            tv_time = itemView.findViewById(R.id.detail_time);
            tv_message2 = itemView.findViewById(R.id.detail_message2);
            tv_message4 = itemView.findViewById(R.id.detail_message4);
        }
    }
}
