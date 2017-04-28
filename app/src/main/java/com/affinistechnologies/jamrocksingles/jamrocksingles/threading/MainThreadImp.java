package com.affinistechnologies.jamrocksingles.jamrocksingles.threading;

/**
 * Created by clayt on 10/1/2016.
 */

import android.os.Handler;
import android.os.Looper;

import com.affinistechnologies.jamrocksingles.jamrocksingles.executor.interfaces.MainThread;


/**
 * This class makes sure that the runnable we provide will be run on the main UI thread.
 */
public class MainThreadImp implements MainThread {

    private static MainThread sMainThread;

    private Handler mHandler;

    private MainThreadImp() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void post(Runnable runnable) {
        mHandler.post(runnable);
    }

    public static MainThread getInstance() {
        if (sMainThread == null) {
            sMainThread = new MainThreadImp();
        }

        return sMainThread;
    }
}