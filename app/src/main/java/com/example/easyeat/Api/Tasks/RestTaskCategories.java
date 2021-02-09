package com.example.easyeat.Api.Tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.easyeat.Api.Holders.CategoryHolder;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RestTaskCategories extends AsyncTask<Void, Void, CategoryHolder[]> {


    @Override
    protected CategoryHolder[] doInBackground(Void... params) {
        CategoryHolder[] categoryHolders = {};
        try {
            String url = "http://192.168.43.253:8082/allCategories";
            Log.i("#######",url);
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
