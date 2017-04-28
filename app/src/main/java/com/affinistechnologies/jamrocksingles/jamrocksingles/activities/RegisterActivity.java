package com.affinistechnologies.jamrocksingles.jamrocksingles.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import com.affinistechnologies.jamrocksingles.R;
import com.affinistechnologies.jamrocksingles.jamrocksingles.executor.imp.ThreadExecutor;
import com.affinistechnologies.jamrocksingles.jamrocksingles.fragments.DatePickerFragment;
import com.affinistechnologies.jamrocksingles.jamrocksingles.managers.imp.AccountManagerImp;
import com.affinistechnologies.jamrocksingles.jamrocksingles.managers.interfaces.AccountManager;
import com.affinistechnologies.jamrocksingles.jamrocksingles.models.BirthDate;
import com.affinistechnologies.jamrocksingles.jamrocksingles.models.Config;
import com.affinistechnologies.jamrocksingles.jamrocksingles.models.SimpleDateTime;
import com.affinistechnologies.jamrocksingles.jamrocksingles.models.User;
import com.affinistechnologies.jamrocksingles.jamrocksingles.threading.MainThreadImp;
import com.affinistechnologies.jamrocksingles.jamrocksingles.ui.DatePickerDialogView;
import com.affinistechnologies.jamrocksingles.jamrocksingles.utility.DateConverter;

import java.util.Calendar;

public class RegisterActivity extends BaseActivity implements Button.OnClickListener,DatePickerDialogView,EditText.OnFocusChangeListener,AccountManager.RegisterCallBack {

    private boolean _valid;
    private EditText _mEmail;
    private EditText _mUserName;
    private EditText _mPassword;
    private EditText _dateOfBirth;
    private RadioButton _mMale;
    private RadioButton _mFemale;
    private CheckBox _mCertify;
    private BirthDate _birthDate;
    private DatePickerFragment _datePicker;
    private AccountManager mAccountManager;
    private User _userToRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        _mUserName = (EditText)findViewById(R.id.userName);
        _mUserName.setOnFocusChangeListener(this);
        _mEmail =  (EditText)findViewById(R.id.emailAddrss);
        _mEmail.setOnFocusChangeListener(this);
        _mPassword =  (EditText)findViewById(R.id.user_password);
        _mPassword.setOnFocusChangeListener(this);
        _dateOfBirth =  (EditText)findViewById(R.id.age_dob);
        _dateOfBirth.setOnFocusChangeListener(this);
        _mMale =  (RadioButton) findViewById(R.id.maleRadioButton);
        _mFemale =  (RadioButton) findViewById(R.id.femaleRadioButton);
        _mCertify =  (CheckBox) findViewById(R.id.ckboxCertify);
        Button registerButton = (Button)findViewById(R.id.btnSignUp);
        registerButton.setOnClickListener(this);
        _dateOfBirth.setRawInputType(0); //InputTypeNull
        _dateOfBirth.setOnClickListener(this);
        _datePicker = DatePickerFragment.NewInstance(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.age_dob){
            if (v.hasFocus() && _datePicker != null)
            {
                _datePicker.show(getSupportFragmentManager(), DatePickerFragment.TAG);
            }
        }
        else if(v.getId() == R.id.btnSignUp){
            if(!IsValid()){
                return;
            }
            ShowProgress();
            _userToRegister = new User();
            _userToRegister.setUserName(_mUserName.getText().toString());
            _userToRegister.setPassword(_mPassword.getText().toString());
            _userToRegister.setEmail(_mEmail.getText().toString());
            _userToRegister.setDateOfBirth(_birthDate);
            _userToRegister.setCertify(_mCertify.isChecked());
            _userToRegister.setGender(_mFemale.isChecked()?"female":"male");

            mAccountManager = new AccountManagerImp(ThreadExecutor.getInstance(), MainThreadImp.getInstance(), null,this, getRestClient());
            mAccountManager.setActionMethod(AccountManagerImp.Action.Register);
            mAccountManager.execute();
        }
    }

    @Override
    public void onDateSelected(SimpleDateTime dateTime) {
        _birthDate = DateConverter.getInstance().convert(dateTime);
        _dateOfBirth.setText(dateTime.getYear() + "/ " + dateTime.getMonth() + "/ " + dateTime.getDay());
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Drawable drawable =  getResources().getDrawable(R.drawable.ic_error_black_24dp);
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, Color.RED);
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_OVER);
        if(v.getId() == R.id.age_dob && _datePicker!=null && !_datePicker.isVisible()){
            if (v.hasFocus() && _datePicker != null)
            {
                _datePicker.show(getSupportFragmentManager(), DatePickerFragment.TAG);
            }
        }
        if(v.getId() == R.id.userName){
            if (_mUserName!=null && _mUserName.getText().toString().isEmpty())
            {
                _valid = false;
            }
            else
            {
                _valid = true;
            }
        }
        if(v.getId() == R.id.emailAddrss ){
            if (_mEmail!=null && _mEmail.getText().toString().isEmpty())
            {
                _valid = false;
            }
            else
            {
                _valid = true;
            }
        }
        if(v.getId() == R.id.user_password ){
            if (_mPassword!=null && _mPassword.getText().toString().isEmpty())
            {
                _valid = false;
            }
            else
            {
                _valid = true;
            }
        }
    }

    @Override
    public void onRegisterSuccess(User user) {
        HideProgress();
        _mPassword.setText("");
        setAuthorizedUser(user);
        Alert("Account Creation", "An email has been sent to" + _mEmail.getText() + ". You have 48 hrs to activate your account to continue using this service.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LaunchMainActivity();
            }
        });
    }

    public void LaunchMainActivity()
    {
        Intent i = new Intent(this, MainActivity.class);

        startActivity(i);
    }

    @Override
    public void onRegisterFalure(String message) {
        HideProgress();
        Alert("Account Creation",message,null);
    }

    @Override
    public User getUserToRegister() {
        return _userToRegister;
    }

    @Override
    public Config getConfig() {
       return getConfigurationData();
    }

    private BirthDate getBirthDate() {
        return _birthDate;
    }

    private void setBirthDate(BirthDate birthDate){
        _birthDate = birthDate;
    }
    private boolean IsValid() {
        int requiredAge = 18;

        if (!_mMale.isChecked() && !_mFemale.isChecked()) {
            _valid = false;
            Alert("Required Field", "Please select a gender.", null);
        } else if (!_mCertify.isChecked()) {
            _valid = false;
            Alert("Required Field", "You must accept terms of service, and privacy policy to continue.", null);
        } else if ((_birthDate == null
                || _birthDate.getDay() == 0
                || _birthDate.getMonth() == 0
                || _birthDate.getYear() == 0)) {
            Alert("Required Field", "we do require your birth date in order for you to use our service.", null);
            _valid = false;
        } else if (_birthDate != null && CalculateAge() < requiredAge) {
            _valid = false;
            Alert("Age  Requirement", "You must be 18 years or older to use this service. Please see our terms of service.", null);
        }

        if (_mUserName != null && _mUserName.getText().toString().isEmpty()) {
            _valid = false;
        } else {
            _valid = true;
        }

        if (_mEmail != null && _mEmail.getText().toString().isEmpty()) {
            _valid = false;
        } else {
            _valid = true;
        }

        if (_mPassword != null && _mPassword.getText().toString().isEmpty()) {
            _valid = false;
        } else {
            _valid = true;
        }

        return _valid;
    }

    private int CalculateAge()
    {
        Calendar t = Calendar.getInstance();
        BirthDate today = new BirthDate(t.get(Calendar.YEAR),t.get(Calendar.MONTH),t.get(Calendar.DAY_OF_WEEK));
        BirthDate dob = new BirthDate(_birthDate.getYear(), _birthDate.getMonth(),_birthDate.getDay());
        int age = today.getYear() - dob.getYear();
        today.AddYears(age);
        if (dob.getYear() > today.getYear())
            age--;
        return age;
    }
}
