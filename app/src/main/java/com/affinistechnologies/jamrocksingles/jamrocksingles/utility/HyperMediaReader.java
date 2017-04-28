package com.affinistechnologies.jamrocksingles.jamrocksingles.utility;

import com.affinistechnologies.jamrocksingles.jamrocksingles.models.HypermediaLink;

import java.util.List;

/**
 * Created by clayt on 2/28/2017.
 */

public class HyperMediaReader {
    private static HyperMediaReader _uniqueInstance = null;

    private HyperMediaReader(){

    }

    public static synchronized HyperMediaReader getInstance(){
        if(_uniqueInstance == null){
            _uniqueInstance = new HyperMediaReader();
        }
        return _uniqueInstance;
    }

    public String getRefreshTokenUrl(List<HypermediaLink> links){
        return getUrl(links,"refresh token");
    }

    public String getLoginUrl(List<HypermediaLink> links){
        return getUrl(links,"login");
    }

    public String getRegisterUrl(List<HypermediaLink> links){
        return getUrl(links,"register");
    }

    private String getUrl(List<HypermediaLink> links,String rel){
        String url = null;
        for (int i = 0; i < links.size(); i++) {
            if (links.get(i).getRel().equalsIgnoreCase(rel)) {
                url = links.get(i).getUri();
            }
        }
        return url;
    }
}
