package com.affinistechnologies.jamrocksingles.jamrocksingles.managers.interfaces;

import com.affinistechnologies.jamrocksingles.jamrocksingles.managers.base.Manager;
import com.affinistechnologies.jamrocksingles.jamrocksingles.managers.imp.SecurityManagerImp;
import com.affinistechnologies.jamrocksingles.jamrocksingles.models.User;

/**
 * Created by clayt on 2/28/2017.
 */

public interface SecurityManager extends Manager {
    void Authenticate(String url, String token);

    void Encrypt(String value);

    void Decrypt(String value);

    void setActionMethod(SecurityManagerImp.Action actionMethod);

    public interface Callback {
        void OnAuthenticateSuccess(User user);

        void OnAuthenticateError(String message);

        void OnEncryptSuccess(String value);

        void DecryptSuccess(String value);

        void OnEncryptError(String value);

        void DecryptError(String value);
    }
}
