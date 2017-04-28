package com.affinistechnologies.jamrocksingles.jamrocksingles.ui;

import android.content.DialogInterface;

import com.affinistechnologies.jamrocksingles.jamrocksingles.models.Config;
import com.affinistechnologies.jamrocksingles.jamrocksingles.models.User;

/**
 * Created by clayt on 2/26/2017.
 */

public interface View {
    void ShowProgress();
    void HideProgress();
    void Alert(String title, String message, DialogInterface.OnClickListener listener);
    Config getConfigurationData();
    User getAuthorizedUser() ;
    void setConfigurationData(Config config);
    void setAuthorizedUser(User user) ;
}
