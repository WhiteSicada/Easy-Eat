package com.example.easyeat.Api.Tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.easyeat.Api.Holders.SubCategoryHolder;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RestTaskSubCategories extends AsyncTask<Void, Void, SubCategoryHolder[]> {
    @Override
    protected SubCategoryHolder[] doInBackground(Void... voids) {
        SubCategoryHolder[] subCategoryHolders = {};
        try {
            String url = "http://192.168.43.253:8082/allSubCategories";
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
