package com.example.easyeat;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.easyeat.api.holders.CategoryHolder;
import com.example.easyeat.api.holders.ItemHolder;
import com.example.easyeat.api.tasks.RestTaskCategories;
import com.example.easyeat.api.tasks.RestTaskItems;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class RestTaskItemsTest {

    @Test
    public void getAllItems() throws ExecutionException, InterruptedException {
        Context appContext = InstrumentationRegistry.getTargetContext();
        ItemHolder[] itemHolders = new RestTaskItems().execute(appContext).get();
        assertFalse(itemHolders.length == 0);
    }
}