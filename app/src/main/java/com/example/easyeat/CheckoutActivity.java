package com.example.easyeat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.easyeat.adapter.MyAdapter;
import com.example.easyeat.model.Order;
import com.example.easyeat.model.User;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {


    private ArrayList<Order> orderList;
    private User user;
    private double total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Bundle bundleObject = getIntent().getExtras();
        orderList = (ArrayList<Order>) bundleObject.getSerializable("orderList");
        user = (User) bundleObject.getSerializable("user");

        ListView commandeList = (ListView) findViewById(R.id.commandeList);
        TextView restaurantName = (TextView) findViewById(R.id.restaurantName);
        TextView totalitems = (TextView) findViewById(R.id.totalitems);
        Button backToOrders = (Button) findViewById(R.id.backToOrders);
        Button nextToPaiment = (Button) findViewById(R.id.nextToPaiment);

        ArrayList<String> mTitle = new ArrayList<>();
        ArrayList<Double> extendedPrice = new ArrayList<>();
        ArrayList<Integer> quantity = new ArrayList<>();
        ArrayList<Integer> imgs = new ArrayList<>();
        ArrayList<Integer> nums = new ArrayList<>();


        for (Order order : orderList) {
            mTitle.add(order.getItem().getName());
            extendedPrice.add(order.getExtendedPrice());
            quantity.add(order.getQuantity());
            imgs.add(order.getItem().getUrl());
            if (!nums.contains(order.getItem().getCategoryId())){
                nums.add(order.getItem().getCategoryId());
            }
            total += order.getExtendedPrice();
        }

        restaurantName.setText(orderList.get(0).getItem().getCategoryName());



        MyAdapter adapter = new MyAdapter(this, mTitle, extendedPrice, quantity, imgs);
        commandeList.setAdapter(adapter);

        totalitems.setText("Total : " + total + " Dhs");

        backToOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        nextToPaiment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckoutActivity.this, PaymentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("orderList",orderList);
                intent.putExtras(bundle);
                intent.putExtra("total",String.valueOf(total));
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
    }


}