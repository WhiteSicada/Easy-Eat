package com.example.easyeat;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.example.easyeat.api.holders.SubCategoryHolder;
import com.example.easyeat.api.tasks.RestTaskSubCategories;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
public class RestTaskSubCategoriesTest {
    @Test
    public void getAllSubCategories() throws ExecutionException, InterruptedException {
        Context appContext = InstrumentationRegistry.getTargetContext();
        SubCategoryHolder[] subCategoryHolders = new RestTaskSubCategories().execute(appContext).get();
        assertFalse(subCategoryHolders.length == 0);
    }
}