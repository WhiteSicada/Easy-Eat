package com.example.easyeat.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.easyeat.R;
import com.example.easyeat.model.SubCategory;

import java.util.List;


public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder>
{
    private List<SubCategory> subCategoryList;
    public ISubCategoryAdapterItemCallback subCategoryAdapterItemCallback;

    public class SubCategoryViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtSubCategoryName;

        public SubCategoryViewHolder(View view)
        {
            super(view);
            txtSubCategoryName = view.findViewById(R.id.txtSubCategory);
        }
    }

    public interface ISubCategoryAdapterItemCallback
    {
        void onSubCategoryItemCallback(int position);
    }

    public SubCategoryAdapter(List<SubCategory> subCategoryList)
    {
        this.subCategoryList = subCategoryList;
    }

    @Override
    public SubCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_category_item, parent, false);

        return new SubCategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SubCategoryViewHolder holder, @SuppressLint("RecyclerView") final int position)
    {
        final SubCategory subCategoryItem = subCategoryList.get(position);
        holder.txtSubCategoryName.setText(subCategoryItem.getName());

        if (Boolean.TRUE.equals(subCategoryItem.isSelected()))
        {
            holder.txtSubCategoryName.setBackgroundResource(R.drawable.sub_category_selected_item);
            holder.txtSubCategoryName.setTextColor(Color.parseColor("#FFFFFF"));
        } else
        {
            holder.txtSubCategoryName.setBackgroundResource(R.drawable.sub_category_item);
            holder.txtSubCategoryName.setTextColor(Color.parseColor("#3F51B5"));
        }

        holder.txtSubCategoryName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (subCategoryAdapterItemCallback != null)
                {
                    subCategoryAdapterItemCallback.onSubCategoryItemCallback(position);
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return subCategoryList.size();
    }

    public SubCategory getItem(int position)
    {
        return subCategoryList.get(position);
    }
}
