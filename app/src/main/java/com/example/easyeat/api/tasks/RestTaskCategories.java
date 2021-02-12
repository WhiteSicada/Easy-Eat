package com.example.easyeat.api.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.easyeat.api.holders.CategoryHolder;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RestTaskCategories extends AsyncTask<Context, Void, CategoryHolder[]> {


    @Override
    protected CategoryHolder[] doInBackground(Context... params) {
        CategoryHolder[] categoryHolders = {};
        try {
            String ip = Helper.getConfigValue(params[0], "ip");
            String url = ip + "allCategories";
            Log.i("####",url);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return restTemplate.getForObject(url, CategoryHolder[].class);
        } catch (Exception exception) {
            Log.e("", exception.getMessage());
        }
        return categoryHolders;
    }

    public RestTaskCategories() {
        super();
    }

}
