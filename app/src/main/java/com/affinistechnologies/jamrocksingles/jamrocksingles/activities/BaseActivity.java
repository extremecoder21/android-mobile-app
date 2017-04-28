package com.affinistechnologies.jamrocksingles.jamrocksingles.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.affinistechnologies.jamrocksingles.R;
import com.affinistechnologies.jamrocksingles.jamrocksingles.infrastructure.RestClient;
import com.affinistechnologies.jamrocksingles.jamrocksingles.infrastructure.RestClientImp;
import com.affinistechnologies.jamrocksingles.jamrocksingles.models.Config;
import com.affinistechnologies.jamrocksingles.jamrocksingles.models.User;
import com.affinistechnologies.jamrocksingles.jamrocksingles.ui.View;
import com.google.gson.Gson;

import java.util.UUID;

public class BaseActivity extends AppCompatActivity  implements View {

    private final String UserKey = "AuthUser";
    private final String ConfigurationDataKey = "ConfigurationDataKey";
    private SharedPreferences prefs;
    private ProgressDialog _progressDialog;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;
    protected RestClient mServiceClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _progressDialog = new ProgressDialog(this);
        _progressDialog.setCancelable(false);
        _progressDialog.setTitle(R.string.app_name);
        //Read Preferences
        prefs = getSharedPreferences(UserKey,MODE_PRIVATE );
        mServiceClient = new RestClientImp();
    }

    @Override
    public void ShowProgress() {
        _progressDialog.show();
    }

    @Override
    public void HideProgress() {
        _progressDialog.hide();
    }

    @Override
    public void Alert(String title, String message,DialogInterface.OnClickListener listener) {
        android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(this).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.logo);

        // Setting OK Button
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"OK",listener ==null? new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int id){
                dialog.dismiss();
           }
        }:listener);
        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public Config getConfigurationData() {
        Config config = null;
        Gson gson = new Gson();
        String configString = prefs.getString(ConfigurationDataKey,"");
        if(!configString.isEmpty()){
            config = gson.fromJson(configString,Config.class);
        }
        return config;
    }

    @Override
    public User getAuthorizedUser() {
        User user = null;
        Gson gson = new Gson();
        String userString = prefs.getString(UserKey,"");
        if(!userString.isEmpty()){
            user = gson.fromJson(userString,User.class);
        }
        return user;
    }

    @Override
    public void setConfigurationData(Config config) {
        Gson gson = new Gson();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ConfigurationDataKey, gson.toJson(config));
        editor.commit();
    }

    @Override
    public void setAuthorizedUser(User user) {
        Gson gson = new Gson();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(UserKey, gson.toJson(user));
        editor.commit();
    }

    protected void Logout()
    {
        User user = null;
        Gson gson = new Gson();
        String userString = prefs.getString(UserKey,"");
        if (userString!=null && !userString.isEmpty())
        {
            user = gson.fromJson(userString,User.class);
            user.setToken(null);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(UserKey, gson.toJson(user));
            editor.commit();
        }
    }

    protected RestClient getRestClient(){
        return this.mServiceClient = new RestClientImp();
    }

    protected UUID getNewId(){
       return UUID.randomUUID();
    }

    /**
     * Requests given permission.
     * If the permission has been denied previously, a Dialog will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    protected void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            showAlertDialog(getString(R.string.permission_title_rationale), rationale,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(BaseActivity.this,
                                    new String[]{permission}, requestCode);
                        }
                    }, getString(R.string.label_ok), null, getString(R.string.label_cancel));
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    /**
     * This method shows dialog with given title & message.
     * Also there is an option to pass onClickListener for positive & negative button.
     *
     * @param title                         - dialog title
     * @param message                       - dialog message
     * @param onPositiveButtonClickListener - listener for positive button
     * @param positiveText                  - positive button text
     * @param onNegativeButtonClickListener - listener for negative button
     * @param negativeText                  - negative button text
     */
    protected void showAlertDialog(@Nullable String title, @Nullable String message,
                                   @Nullable DialogInterface.OnClickListener onPositiveButtonClickListener,
                                   @NonNull String positiveText,
                                   @Nullable DialogInterface.OnClickListener onNegativeButtonClickListener,
                                   @NonNull String negativeText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
        builder.setNegativeButton(negativeText, onNegativeButtonClickListener);
        android.support.v7.app.AlertDialog mAlertDialog = builder.show();
    }
}
