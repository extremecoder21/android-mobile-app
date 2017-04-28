package com.affinistechnologies.jamrocksingles.jamrocksingles.managers.imp;


import com.affinistechnologies.jamrocksingles.jamrocksingles.executor.interfaces.Executor;
import com.affinistechnologies.jamrocksingles.jamrocksingles.executor.interfaces.MainThread;
import com.affinistechnologies.jamrocksingles.jamrocksingles.infrastructure.RestClient;
import com.affinistechnologies.jamrocksingles.jamrocksingles.managers.base.AbstractManager;
import com.affinistechnologies.jamrocksingles.jamrocksingles.managers.interfaces.AccountManager;
import com.affinistechnologies.jamrocksingles.jamrocksingles.models.OpResultStatus;
import com.affinistechnologies.jamrocksingles.jamrocksingles.models.OperationResult;
import com.affinistechnologies.jamrocksingles.jamrocksingles.models.User;
import com.affinistechnologies.jamrocksingles.jamrocksingles.utility.HyperMediaReader;
import com.affinistechnologies.jamrocksingles.jamrocksingles.utility.UrlFormatter;
import com.google.gson.Gson;

import java.io.IOException;

/**
 * Created by clayt on 9/27/2016.
 */

public class AccountManagerImp extends AbstractManager implements AccountManager {
    @Override
    public void setActionMethod(Action actionMethod) {
        this.ActionMethod = actionMethod;
    }

    @Override
    public void Register() {
        // TODO: Implement this with your business logic
        Gson gson = new Gson();

        String url = HyperMediaReader.getInstance().getRegisterUrl(mRegisterCallback.getConfig().getLinks());

        try {

            final OperationResult<User> response = mRestServiceClient.ExecutePost(url, mRegisterCallback.getUserToRegister(), User.class);

            if (response.getStatusCode() == OpResultStatus.Success) {
                // notify on the main thread
                mMainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        mRegisterCallback.onRegisterSuccess(response.getResource());
                    }
                });
            } else {
                // notify on the main thread
                mMainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        mRegisterCallback.onRegisterFalure(response.getMessage());
                    }
                });
            }
        } catch (IOException e) {

            e.printStackTrace();

            // notify on the main thread
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mLoginCallback.onLoginFalied("Network error! please check connection.");
                }
            });
        }
    }

    @Override
    public void Login() {
        // TODO: Implement this with your business logic
        Gson gson = new Gson();

        String url = UrlFormatter.getInstance().formatLogin(HyperMediaReader.getInstance().getLoginUrl(mLoginCallback.getConfig().getLinks()), mLoginCallback.getUserName(), mLoginCallback.getPassword());

        try {

            final OperationResult<User> response = mRestServiceClient.ExecutePost(url, null, User.class);

            if (response.getStatusCode() == OpResultStatus.Success) {
                // notify on the main thread
                mMainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        mLoginCallback.onLoginSuccess(response.getResource());
                    }
                });
            } else {
                // notify on the main thread
                mMainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        mLoginCallback.onLoginFalied(response.getMessage());
                    }
                });
            }
        } catch (IOException e) {

            e.printStackTrace();

            // notify on the main thread
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mLoginCallback.onLoginFalied("Network error! please check connection.");
                }
            });
        }
    }

    public enum Action {
        Login,
        Register
    }

    private Action ActionMethod;

    private AccountManager.LoginCallback mLoginCallback;

    private AccountManager.RegisterCallBack mRegisterCallback;

    private RestClient mRestServiceClient;

    private User user = new User();

    public AccountManagerImp(Executor threadExecutor, MainThread mainThread, LoginCallback callback, RegisterCallBack registerCallBack, RestClient restServiceClient) {

        super(threadExecutor, mainThread);

        this.mLoginCallback = callback;

        this.mRegisterCallback = registerCallBack;

        this.mRestServiceClient = restServiceClient;
    }

    @Override
    public void run() {
        if (this.ActionMethod == Action.Register) {
            Register();
        }
        else if(this.ActionMethod == Action.Login) {
            Login();
        }
    }
}
