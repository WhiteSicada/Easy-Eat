package com.example.easyeat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyeat.R;

import java.util.List;

public class MyAdapter extends ArrayAdapter<String> {

    Context context;
    List<String> title;
    List<Double> extendedPrice;
    List<Integer> rImgs;
    List<Integer> quantity;

    public MyAdapter(Context c, List<String> mTitle, List<Double> extendedPrice, List<Integer> quantity, List<Integer> imgs) {
        super(c, R.layout.commande_item, R.id.itemName, mTitle);
        this.context = c;
        this.title = mTitle;
        this.extendedPrice = extendedPrice;
        this.rImgs = imgs;
        this.quantity = quantity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item = layoutInflater.inflate(R.layout.commande_item, parent, false);
        ImageView images = item.findViewById(R.id.imgItem);
        TextView myTitle = item.findViewById(R.id.itemName);
        TextView myextendedPrice = item.findViewById(R.id.extendedPrice);
        TextView mytxtQuantity = item.findViewById(R.id.txtQuantity);

        // now set our resources on views
        images.setImageResource(rImgs.get(position));
        myTitle.setText(title.get(position));
        myextendedPrice.setText(Double.toString(extendedPrice.get(position)));
        mytxtQuantity.setText(Integer.toString(quantity.get(position)));


        return item;
    }
}