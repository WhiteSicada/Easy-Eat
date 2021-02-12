package com.example.easyeat.api.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.easyeat.api.holders.ItemHolder;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RestTaskItems extends AsyncTask<Context, Void, ItemHolder[]> {
    @Override
    protected ItemHolder[] doInBackground(Context... params) {
        ItemHolder[] itemHolders = {};
        try {
            String ip = Helper.getConfigValue(params[0], "ip");
            String url = ip +"allItems";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return restTemplate.getForObject(url, ItemHolder[].class);
        } catch (Exception exception) {
            Log.e("", exception.getMessage());
        }
        return itemHolders;
    }

    public RestTaskItems() {
        super();
    }
}
