package com.lab47billion.appchooser.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lab47billion.appchooser.R;

import java.util.List;

/**
 * Created by prashant on 6/17/2016.
 */
public class DriverRideListAdapter extends RecyclerView.Adapter<DriverRideListAdapter.DriverRideViewHolder> {
    private Context context;
    private List list;

    public DriverRideListAdapter(Context context,List list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public DriverRideViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.driver_rides_item_layout,null,false);
        return new DriverRideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DriverRideViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DriverRideViewHolder extends RecyclerView.ViewHolder{
        TextView serialNumber,driverName,totalRides;

        public DriverRideViewHolder(View itemView) {
            super(itemView);
            serialNumber= (TextView) itemView.findViewById(R.id.number);
            driverName= (TextView) itemView.findViewById(R.id.driverName);
            totalRides= (TextView) itemView.findViewById(R.id.totalRides);
        }
    }
}
