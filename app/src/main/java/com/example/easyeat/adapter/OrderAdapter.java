package com.example.easyeat.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.easyeat.R;
import com.example.easyeat.model.Order;

import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>
{
    private List<Order> orderList;
    private Activity activity;

    private IOrderAdapterCallback orderCallback;

    public interface IOrderAdapterCallback
    {
        void onIncreaseDecreaseCallback();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imgThumbnail;
        TextView txtItemName;
        TextView txtExtendedPrice;
        TextView txtQuantity;
        Button btnIncrease;
        Button btnDecrease;

        public OrderViewHolder(View view)
        {
            super(view);
            imgThumbnail = view.findViewById(R.id.imgThumbnail);
            txtItemName = view.findViewById(R.id.txtItemName);
            txtExtendedPrice = view.findViewById(R.id.txtExtendedPrice);
            txtQuantity = view.findViewById(R.id.txtQuantity);
            btnIncrease =view.findViewById(R.id.btnIncrease);
            btnDecrease = view.findViewById(R.id.btnDecrease);
        }
    }

    public OrderAdapter(Activity activity, List<Order> orderList)
    {
        this.activity = activity;
        this.orderList = orderList;
        orderCallback = (IOrderAdapterCallback) activity;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item, parent, false);

        return new OrderViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(final OrderViewHolder holder, @SuppressLint("RecyclerView") final int position)
    {
        final Order order = orderList.get(position);

        Glide.with(activity)
                .load(order.getItem().getUrl())
                .into(holder.imgThumbnail);

        holder.txtItemName.setText(order.getItem().getName());
        holder.txtExtendedPrice.setText(String.format("%.2f", order.getExtendedPrice()));
        holder.txtQuantity.setText(String.valueOf(order.getQuantity()));

        holder.btnIncrease.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                order.setQuantity(order.getQuantity()+1);
                order.setExtendedPrice(order.getQuantity() * order.getItem().getUnitPrice());
                holder.txtQuantity.setText(String.valueOf(order.getQuantity()));
                holder.txtExtendedPrice.setText(String.format("%.2f", order.getExtendedPrice()));

                notifyDataSetChanged();
                orderCallback.onIncreaseDecreaseCallback();
            }
        });

        holder.btnDecrease.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                order.setQuantity(order.getQuantity()-1);
                order.setExtendedPrice(order.getQuantity() * order.getItem().getUnitPrice());
                holder.txtQuantity.setText(String.valueOf(order.getQuantity()));
                holder.txtExtendedPrice.setText(String.format("%.2f", order.getExtendedPrice()));

                if (order.getQuantity() == 0)
                {
                    orderList.remove(position);
                }

                notifyDataSetChanged();
                orderCallback.onIncreaseDecreaseCallback();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return orderList.size();
    }
}

