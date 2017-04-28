package com.affinistechnologies.jamrocksingles.jamrocksingles.managers.interfaces;


import com.affinistechnologies.jamrocksingles.jamrocksingles.managers.base.Manager;
import com.affinistechnologies.jamrocksingles.jamrocksingles.managers.imp.AccountManagerImp;
import com.affinistechnologies.jamrocksingles.jamrocksingles.models.Config;
import com.affinistechnologies.jamrocksingles.jamrocksingles.models.User;

/**
 * Created by clayt on 9/27/2016.
 */

public interface AccountManager extends Manager {
    void setActionMethod(AccountManagerImp.Action actionMethod);

    interface LoginCallback {
        // TODO: Add interactor callback methods here
        void onLoginSuccess(User user);

        void onLoginFalied(String message);

        String getUserName();

        String getPassword();

        Config getConfig();
    }

    interface RegisterCallBack{
        void onRegisterSuccess(User user);

        void onRegisterFalure(String message);

        User getUserToRegister();

        Config getConfig();
    }

    void Register();
    void Login();
}