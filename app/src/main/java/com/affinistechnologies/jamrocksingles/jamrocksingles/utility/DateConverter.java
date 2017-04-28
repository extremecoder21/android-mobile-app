package com.affinistechnologies.jamrocksingles.jamrocksingles.utility;

import com.affinistechnologies.jamrocksingles.jamrocksingles.models.BirthDate;
import com.affinistechnologies.jamrocksingles.jamrocksingles.models.SimpleDateTime;

import java.util.Calendar;

/**
 * Created by clayt on 2/28/2017.
 */

public class DateConverter {
    private static DateConverter _uniqueInstance = null;

    private DateConverter(){

    }

    public static synchronized DateConverter getInstance(){
        if(_uniqueInstance == null){
            _uniqueInstance = new DateConverter();
        }
        return _uniqueInstance;
    }

    public BirthDate convert(Calendar c){
        BirthDate birthDate = new BirthDate();
        birthDate.setDay(c.get(Calendar.DAY_OF_WEEK));
        birthDate.setMonth(Calendar.MONTH);
        birthDate.setYear(Calendar.YEAR);
        return birthDate;
    }

    public BirthDate convert(SimpleDateTime c){
        BirthDate birthDate = new BirthDate();
        birthDate.setDay(c.getDay());
        birthDate.setMonth(c.getMonth());
        birthDate.setYear(c.getYear());
        return birthDate;
    }
}