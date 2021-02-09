package com.example.easyeat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.easyeat.deliveryman.DeliveryActivity;
import com.example.easyeat.menuactivity.ProfileActivity;
import com.example.easyeat.payment.PaymentModel;

public class PostOrderPayment extends AppCompatActivity {
    private PaymentModel paymentModel;
    Button notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_order_payment);

        final Intent intent = getIntent();
        paymentModel = (PaymentModel) intent.getSerializableExtra("paymentModel");
        notification=findViewById(R.id.notification);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("Order","Order", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PostOrderPayment.this, DeliveryActivity.class);

                startActivity(intent);
                NotificationCompat.Builder builder =new NotificationCompat.Builder(PostOrderPayment.this,"My Notification");
                builder.setContentTitle("EasyEat:Order");
                builder.setContentText("Your order will be received after 15 min ");
                builder.setSmallIcon(R.drawable.ic_baseline_check_circle_24);
                builder.setAutoCancel(true);
                NotificationManagerCompat managerCompat=NotificationManagerCompat.from(PostOrderPayment.this);
                managerCompat.notify(1,builder.build());


            }
        });

        TextView fromRestaurant = findViewById(R.id.fromRestaurant);
        TextView toLocation = findViewById(R.id.toLocation);
        Button lookMap = findViewById(R.id.lookMap);



        fromRestaurant.setText(paymentModel.getRestaurant());
        toLocation.setText("ENSAK, Av. de L'Université, Kénitra");


        lookMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String restau = getRestau(paymentModel.getRestaurant());
                    String url = "https://www.google.co.in/maps/dir/" + restau  + "/ENSAK, Av. de L'Université, Kénitra";
                    Log.i("####################",url);
                    Uri uri = Uri.parse(url);
                    Intent intent1 = new Intent(Intent.ACTION_VIEW,uri);
                    intent1.setPackage("com.google.android.apps.maps");
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent1);
                }catch (ActivityNotFoundException e){
                    String url = "https://play.google.com/store/apps/details?id=com.google.android.apps.maps";
                    Uri uri = Uri.parse(url);
                    Intent intent1 = new Intent(Intent.ACTION_VIEW,uri);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent1);
                }
            }
        });
    }

    public String getRestau(String name){
        String restau = "";
        if (name.equals("Molly's Dinner")){
            restau = "Molly's Diner, Rue Maamora, Kénitra";
        }else if(name.equals("STARBUCKS")){
            restau = "Starbucks, Kénitra";
        }else if(name.equals("MCDONALD'S")){
            restau = "McDonald's kénitra, Route Assaknia, Kénitra";
        }else if(name.equals("PIZZA HUT")){
            restau = "Pizzahut, Kénitra";
        }
        return restau;
    }
}