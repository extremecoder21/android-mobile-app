package com.affinistechnologies.jamrocksingles.jamrocksingles.utility;

/**
 * Created by clayt on 2/28/2017.
 */

public class UrlFormatter {
    private static UrlFormatter _uniqueInstance = null;

    private UrlFormatter(){

    }

    public static synchronized UrlFormatter getInstance(){
        if(_uniqueInstance == null){
            _uniqueInstance = new UrlFormatter();
        }
        return _uniqueInstance;
    }

   public String formatLogin(String url,String userName, String password){
       String formatedUrl  =  url.replace("{username}",userName).replace("{password}",password);
       return  formatedUrl;
   }
}