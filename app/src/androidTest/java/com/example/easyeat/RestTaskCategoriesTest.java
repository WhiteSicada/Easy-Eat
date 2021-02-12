package com.example.easyeat;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.easyeat.api.holders.CategoryHolder;
import com.example.easyeat.api.tasks.RestTaskCategories;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class RestTaskCategoriesTest {

    @Test
    public void getAllCategories() throws ExecutionException, InterruptedException {
        Context appContext = InstrumentationRegistry.getTargetContext();
        CategoryHolder[] result = new RestTaskCategories().execute(appContext).get();
        assertFalse(result.length == 0);
    }
}