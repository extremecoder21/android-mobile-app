package com.affinistechnologies.jamrocksingles.jamrocksingles.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.widget.DatePicker;

import com.affinistechnologies.jamrocksingles.jamrocksingles.models.SimpleDateTime;
import com.affinistechnologies.jamrocksingles.jamrocksingles.ui.DatePickerDialogView;

import java.util.Calendar;

/**
 * Created by clayt on 2/28/2017.
 */

public class DatePickerFragment extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener {
    public static  String TAG = "X:DatePickerFragment";
    private static DatePickerDialogView _view;

    public static DatePickerFragment NewInstance(DatePickerDialogView view)
    {
        DatePickerFragment frag = new DatePickerFragment();
        _view = view;
        return frag;
    }
   @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Calendar currently = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                this,
                currently.get(Calendar.YEAR),
                currently.get(Calendar.MONTH),
                currently.get(Calendar.DAY_OF_WEEK));
        return dialog;
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        _view.onDateSelected(new SimpleDateTime(year, month + 1, dayOfMonth));
    }
}
