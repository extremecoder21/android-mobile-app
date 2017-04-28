package com.affinistechnologies.jamrocksingles.jamrocksingles.managers.imp;


import com.affinistechnologies.jamrocksingles.jamrocksingles.executor.interfaces.Executor;
import com.affinistechnologies.jamrocksingles.jamrocksingles.executor.interfaces.MainThread;
import com.affinistechnologies.jamrocksingles.jamrocksingles.infrastructure.RestClient;
import com.affinistechnologies.jamrocksingles.jamrocksingles.managers.base.AbstractManager;
import com.affinistechnologies.jamrocksingles.jamrocksingles.managers.interfaces.ConfigurationManager;
import com.affinistechnologies.jamrocksingles.jamrocksingles.models.Config;
import com.affinistechnologies.jamrocksingles.jamrocksingles.models.OpResultStatus;
import com.affinistechnologies.jamrocksingles.jamrocksingles.models.OperationResult;

import java.io.IOException;

/**
 * Created by clayt on 10/2/2016.
 */

public class ConfigurationManagerImp extends AbstractManager implements ConfigurationManager {

    private ConfigurationManager.Callback mCallback;

    private RestClient mServiceClient;

    private String _getConfigUri = "http://192.168.1.16:5000/api/v1/configuration?action=getConfiguration";

    //private String _getConfigUri = "https://jamrocksingles.com/api/v1/configuration?action=getConfiguration";

    public ConfigurationManagerImp(Executor threadExecutor, MainThread mainThread, Callback callback, RestClient serviceClient) {

        super(threadExecutor, mainThread);

        this.mCallback = callback;

        this.mServiceClient = serviceClient;
    }

    @Override
    public void run() {
        try {

            mServiceClient.AddToken(null);

            final OperationResult<Config> response = mServiceClient.ExecuteGet(_getConfigUri,Config.class);

            if(response!=null && response.getStatusCode() == OpResultStatus.Success) {
                mMainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.OnAsyncResult(response.getResource());
                    }
                });
            }
            else{
                mMainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.OnAsyncResultError(response.getMessage());
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
            // notify on the main thread
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.OnAsyncResultError("Network error! please check connection.");
                }
            });
        }
    }
}

