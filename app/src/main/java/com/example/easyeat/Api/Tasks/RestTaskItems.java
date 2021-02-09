package com.example.easyeat.Api.Tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.easyeat.Api.Holders.ItemHolder;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RestTaskItems extends AsyncTask<Void, Void, ItemHolder[]> {
    @Override
    protected ItemHolder[] doInBackground(Void... voids) {
        ItemHolder[] itemHolders = {};
        try {
            String url = "http://192.168.43.253:8082/allItems";
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
