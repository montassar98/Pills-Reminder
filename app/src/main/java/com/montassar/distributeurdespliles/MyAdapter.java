package com.montassar.distributeurdespliles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<Time> data;
    private LayoutInflater inflater;
    private ItemClickListener itemClickListener;

    public MyAdapter(Context context, ArrayList<Time> data)
    {
        this.inflater=LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.item_reminder,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.init(data.get(position).getMsg(),data.get(position).getHour());
    }



    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtMsg;
        TextView txtHour;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMsg=(TextView) itemView.findViewById(R.id.txtMsg);
            txtHour = (TextView) itemView.findViewById(R.id.txt_clock);
            itemView.setOnClickListener(this);

        }
        public void init(String msg,String hour)
        {
            txtMsg.setText(msg);
            txtHour.setText(hour);

        }


        @Override
        public void onClick(View view) {
            if (itemClickListener != null)
            {
                itemClickListener.onItemClickListener(view,getAdapterPosition());
            }
        }

    }

    public void setClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener=itemClickListener;
    }

     public  interface ItemClickListener{
        void onItemClickListener(View view, int position);
     }
}
