package com.example.easyeat.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyeat.R;
import com.example.easyeat.fragment.ItemFragment;
import com.example.easyeat.model.Solution;

import java.util.ArrayList;
import java.util.List;



public class CategoryPagerAdapter extends FragmentPagerAdapter
{
    private List<Solution> solutionList = new ArrayList<>();
    private Context context;

    public CategoryPagerAdapter(FragmentManager manager, Context context, List<Solution> solutionList)
    {
        super(manager);
        this.solutionList = solutionList;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        return ItemFragment.newInstance(solutionList.get(position));
    }

    @Override
    public int getCount()
    {
        return solutionList.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return solutionList.get(position).getCategory().getName();
    }

    public View getTabView(final int position)
    {
        // Given you have a custom layout in `res/layout/tab_item.xml` with a TextView and ImageView
        View v = LayoutInflater.from(context).inflate(R.layout.tab_item, null);

        TextView txtCategoryName = v.findViewById(R.id.txtCategoryName);
        ImageView imgCategoryIcon = v.findViewById(R.id.imgCategoryIcon);

        txtCategoryName.setText(solutionList.get(position).getCategory().getName());
        imgCategoryIcon.setImageResource(solutionList.get(position).getCategory().getResourceId());

        return v;
    }
}
