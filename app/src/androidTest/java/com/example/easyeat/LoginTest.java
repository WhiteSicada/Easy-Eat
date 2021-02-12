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
public class LoginTest {

    @Test
    public void loginUser() throws Exception{
        Context appContext = InstrumentationRegistry.getTargetContext();
        DataBaseHelper myDb = new DataBaseHelper(appContext);
        User user = myDb.checkUser("adnane","adnane");
        assertEquals("adnane",user.getUsernname());
    }
}