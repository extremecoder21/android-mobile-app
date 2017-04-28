package com.affinistechnologies.jamrocksingles.jamrocksingles.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.affinistechnologies.jamrocksingles.R;
import com.affinistechnologies.jamrocksingles.jamrocksingles.executor.imp.ThreadExecutor;
import com.affinistechnologies.jamrocksingles.jamrocksingles.managers.imp.ConfigurationManagerImp;
import com.affinistechnologies.jamrocksingles.jamrocksingles.managers.imp.SecurityManagerImp;
import com.affinistechnologies.jamrocksingles.jamrocksingles.managers.interfaces.ConfigurationManager;
import com.affinistechnologies.jamrocksingles.jamrocksingles.managers.interfaces.SecurityManager;
import com.affinistechnologies.jamrocksingles.jamrocksingles.models.Config;
import com.affinistechnologies.jamrocksingles.jamrocksingles.models.User;
import com.affinistechnologies.jamrocksingles.jamrocksingles.threading.MainThreadImp;
import com.affinistechnologies.jamrocksingles.jamrocksingles.utility.HyperMediaReader;

public class LauncherActivity extends BaseActivity implements ConfigurationManager.Callback, SecurityManager.Callback , Button.OnClickListener {

    private SecurityManager mSecurityManager;

    private ConfigurationManager mConfigManager;

    private Button _loginBtn;

    private Button _registerBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        _loginBtn = (Button)findViewById(R.id.signinbtn);
        _loginBtn.setOnClickListener(this);
        _registerBtn = (Button)findViewById(R.id.registerBtn);
        _registerBtn.setOnClickListener(this);
        ShowProgress();
        Config config = getConfigurationData();
        User user = getAuthorizedUser();
        if(config == null || config.getExpireDate().getDay()==0 ){
            mConfigManager = new ConfigurationManagerImp(
                    ThreadExecutor.getInstance()
                    , MainThreadImp.getInstance()
                    ,this
                    ,getRestClient());

            mConfigManager.execute();
        }
        else if(config!=null){
            if(user!=null && user.getToken()!=null && !user.getToken().isEmpty()){
                RefreshToken(config, user);
            }else{
                ShowLoginAndRegisterButtons();
            }
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    public void onBackPressed(){
        if (this.getAuthorizedUser() == null
                || this.getAuthorizedUser().getToken()==null
                || this.getAuthorizedUser().getToken().isEmpty())
        {
            moveTaskToBack(true);
        }
        else
        {
            super.onBackPressed();
        }
    }
    private void RefreshToken(Config config, User user) {
        HyperMediaReader reader = HyperMediaReader.getInstance();

        mSecurityManager = new SecurityManagerImp(
                ThreadExecutor.getInstance()
                , MainThreadImp.getInstance()
                , getRestClient()
                , this
                , reader.getRefreshTokenUrl(config.getLinks())
                , user.getToken());

        mSecurityManager.setActionMethod(SecurityManagerImp.Action.Authenticate);

        mSecurityManager.execute();
    }

    @Override
    public void OnAsyncResult(Config config) {
        HideProgress();
        if(config!=null){
            setConfigurationData(config);
            User user = getAuthorizedUser();
            if(user!=null && user.getToken()!=null && !user.getToken().isEmpty()){
                RefreshToken(config,user);
            }else{
                ShowLoginAndRegisterButtons();
            }
        }
    }

    @Override
    public void OnAsyncResultError(String message) {
        HideProgress();
        Alert("Error",message,null);
        ShowLoginAndRegisterButtons();
    }

    @Override
    public void OnAuthenticateSuccess(User user) {
        HideProgress();
        LaunchMainView();
    }

    @Override
    public void OnAuthenticateError(String message) {
        HideProgress();
        ShowLoginAndRegisterButtons();
    }

    @Override
    public void OnEncryptSuccess(String value) {
        HideProgress();
    }

    @Override
    public void DecryptSuccess(String value) {
        HideProgress();
    }

    @Override
    public void OnEncryptError(String value) {
        HideProgress();
        ShowLoginAndRegisterButtons();
    }

    @Override
    public void DecryptError(String value) {
        HideProgress();
    }

    public void LaunchLoginView()
    {
        Intent i = new Intent(this,LoginActivity.class);

        startActivity(i);
    }

    public void LaunchRegisterView()
    {
        Intent i = new Intent(this, RegisterActivity.class);

        startActivity(i);
    }

    public void LaunchMainView()
    {
        Intent i = new Intent(this, MainActivity.class);

        startActivity(i);
    }

    public void ShowLoginAndRegisterButtons()
    {
        HideProgress();
        _loginBtn.setVisibility(View.VISIBLE);

        _registerBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.registerBtn) {
            LaunchRegisterView();
        }
        else if(v.getId()==R.id.signinbtn){
            LaunchLoginView();
        }
    }
}
