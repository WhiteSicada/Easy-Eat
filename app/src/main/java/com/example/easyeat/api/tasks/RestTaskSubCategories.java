package com.example.easyeat.api.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.easyeat.api.holders.SubCategoryHolder;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RestTaskSubCategories extends AsyncTask<Context, Void, SubCategoryHolder[]> {
    @Override
    protected SubCategoryHolder[] doInBackground(Context... params) {
        SubCategoryHolder[] subCategoryHolders = {};
        try {
            String ip = Helper.getConfigValue(params[0], "ip");
            String url = ip +"allSubCategories";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return restTemplate.getForObject(url, SubCategoryHolder[].class);
        } catch (Exception exception) {
            Log.e("", exception.getMessage());
        }
        return subCategoryHolders;
    }

    public RestTaskSubCategories() {
        super();
    }
}