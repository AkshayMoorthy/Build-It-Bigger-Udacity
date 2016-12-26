package com.udacity.gradle.builditbigger;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Akshay on 05-12-2016.
 */

@RunWith(AndroidJUnit4.class)
public class EndpointsAsyncTaskTest {
    @Test
    public void testDoInBackground() throws Exception{
        try {
            MainActivity mainActivity = new MainActivity();
            OnTaskCompleted listener=null;
            CloudAsyncTask endpointsAsyncTask = new CloudAsyncTask(listener);
            endpointsAsyncTask.execute();
            String result = endpointsAsyncTask.get(30, TimeUnit.SECONDS);

            assertNotNull(result);
            assertTrue(result.length() > 0);
        } catch (Exception e){
            Log.e("EndpointsAsyncTaskTest", "testDoInBackground: Timed out");
        }
    }
}