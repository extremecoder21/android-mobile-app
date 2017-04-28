package com.affinistechnologies.jamrocksingles.jamrocksingles.managers.imp;

import com.affinistechnologies.jamrocksingles.jamrocksingles.executor.interfaces.Executor;
import com.affinistechnologies.jamrocksingles.jamrocksingles.executor.interfaces.MainThread;
import com.affinistechnologies.jamrocksingles.jamrocksingles.infrastructure.RestClient;
import com.affinistechnologies.jamrocksingles.jamrocksingles.managers.base.AbstractManager;
import com.affinistechnologies.jamrocksingles.jamrocksingles.managers.interfaces.SecurityManager;
import com.affinistechnologies.jamrocksingles.jamrocksingles.models.OpResultStatus;
import com.affinistechnologies.jamrocksingles.jamrocksingles.models.OperationResult;
import com.affinistechnologies.jamrocksingles.jamrocksingles.models.User;

import java.io.IOException;

/**
 * Created by clayt on 2/28/2017.
 */

public class SecurityManagerImp extends AbstractManager implements SecurityManager {

    public enum Action {
        Authenticate,
        Encrypt,
        Decrypt
    }

    private Action ActionMethod;
    private String Url;
    private String Token;
    private RestClient mServiceClient;
    private SecurityManager.Callback mCallBack;

    public SecurityManagerImp(Executor threadExecutor, MainThread mainThread, RestClient client, SecurityManager.Callback callback, String url, String token) {
        super(threadExecutor, mainThread);
        this.Url = url;
        this.Token = token;
        this.mServiceClient = client;
        this.mCallBack = callback;
    }

    @Override
    public void setActionMethod(Action actionMethod) {
        this.ActionMethod = actionMethod;
    }

    @Override
    public void run() {
        if (this.ActionMethod == Action.Authenticate) {
            this.Authenticate(this.Url, this.Token);
        } else if (this.ActionMethod == Action.Encrypt) {

        } else if (this.ActionMethod == Action.Decrypt) {

        }
    }

    @Override
    public void Authenticate(String url, String token) {
        ActionMethod = Action.Authenticate;

        // TODO: Implement this with your business logic

        try {
            mServiceClient.AddToken(Token);
            final OperationResult<User> response = mServiceClient.ExecutePost(Url, null, User.class);
            if (response != null && response.getStatusCode() == OpResultStatus.Success) {
                mMainThread.post(new Runnable() {

                    @Override
                    public void run() {
                        mCallBack.OnAuthenticateSuccess(response.getResource());
                    }
                });
            } else {
                mMainThread.post(new Runnable() {

                    @Override
                    public void run() {
                        mCallBack.OnAuthenticateError(response.getMessage());
                    }

                });
            }
        } catch (IOException e) {

            e.printStackTrace();

            // notify on the main thread
            mMainThread.post(new Runnable() {
                @Override
                public void run() {

                    mCallBack.OnAuthenticateError("Network error! please check connection.");
                }
            });
        }
    }

    @Override
    public void Encrypt(String value) {
        ActionMethod = Action.Encrypt;
    }

    @Override
    public void Decrypt(String value) {
        ActionMethod = Action.Decrypt;
    }
}
