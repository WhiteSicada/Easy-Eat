package com.example.easyeat;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.easyeat.login.DataBaseHelper;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
public class ResetActivityTest {

    @Test
    public void passwordUserExists() throws Exception{
        Context appContext = InstrumentationRegistry.getTargetContext();
        DataBaseHelper myDb = new DataBaseHelper(appContext);
        boolean checkpasswordupdate = myDb.updatePassword("adnane", "adnane");
        assertTrue(checkpasswordupdate);
    }
}