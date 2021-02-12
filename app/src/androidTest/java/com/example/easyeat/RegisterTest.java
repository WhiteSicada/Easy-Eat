package com.example.easyeat;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.easyeat.login.DataBaseHelper;
import com.example.easyeat.model.User;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class RegisterTest {

    @Test
    public void insertUser() throws Exception{
        Context appContext = InstrumentationRegistry.getTargetContext();
        DataBaseHelper myDb = new DataBaseHelper(appContext);
        boolean var = myDb.registerUser("Gahi","gahi@hotmail.fr","gahi");
        assertTrue(var);
    }

}