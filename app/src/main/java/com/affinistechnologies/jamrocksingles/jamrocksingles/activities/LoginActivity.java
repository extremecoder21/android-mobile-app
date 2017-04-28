package com.affinistechnologies.jamrocksingles.jamrocksingles.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.affinistechnologies.jamrocksingles.R;
import com.affinistechnologies.jamrocksingles.jamrocksingles.executor.imp.ThreadExecutor;
import com.affinistechnologies.jamrocksingles.jamrocksingles.managers.imp.AccountManagerImp;
import com.affinistechnologies.jamrocksingles.jamrocksingles.managers.interfaces.AccountManager;
import com.affinistechnologies.jamrocksingles.jamrocksingles.models.Config;
import com.affinistechnologies.jamrocksingles.jamrocksingles.models.User;
import com.affinistechnologies.jamrocksingles.jamrocksingles.threading.MainThreadImp;

public class LoginActivity extends BaseActivity implements AccountManager.LoginCallback, Button.OnClickListener{
    private AutoCompleteTextView _mEmailView;

    private EditText _mPasswordView;

    private AccountManager mAccountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        _mEmailView =   (AutoCompleteTextView)findViewById(R.id.email)  ;

        _mPasswordView = (EditText)findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

        mEmailSignInButton.setOnClickListener(this);
    }

    @Override
    public void onLoginSuccess(User user) {
        setAuthorizedUser(user);
        LaunchMainActivity();
    }

    @Override
    public void onLoginFalied(String message) {
        Alert("Accoutn Login",message,null);
        _mPasswordView.setText("");
    }

    @Override
    public String getUserName() {
        return _mEmailView.getText().toString();
    }

    @Override
    public String getPassword() {
      return _mPasswordView.getText().toString();
    }

    @Override
    public Config getConfig() {
        return getConfigurationData();
    }

    @Override
    public void onClick(View v) {
        boolean valid = true;
        if (getPassword() == null || getPassword().isEmpty()) {
            valid =false;
        }
        if (getUserName() == null || getUserName().isEmpty()) {
            valid =false;
        }
        if(valid){
            mAccountManager = new AccountManagerImp(ThreadExecutor.getInstance(), MainThreadImp.getInstance(), this,null, getRestClient());
            mAccountManager.setActionMethod(AccountManagerImp.Action.Login);
            mAccountManager.execute();
        }
    }

    public void LaunchMainActivity()
    {
        Intent i = new Intent(this, MainActivity.class);

        startActivity(i);
    }
}
