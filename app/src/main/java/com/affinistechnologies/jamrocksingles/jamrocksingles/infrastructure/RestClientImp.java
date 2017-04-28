package com.affinistechnologies.jamrocksingles.jamrocksingles.infrastructure;

import android.support.annotation.NonNull;

import com.affinistechnologies.jamrocksingles.jamrocksingles.models.OpResultStatus;
import com.affinistechnologies.jamrocksingles.jamrocksingles.models.OperationResult;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by clayt on 10/1/2016.
 */

public  class RestClientImp implements RestClient {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private String Token;

    public void AddToken(String token)
    {
        this.Token = token;
    }

    @Override
    public <TResult> OperationResult<TResult> ExecuteGet(String url, Class<TResult> klass) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        Gson gson = new Gson();

        OperationResult<TResult> serviceResponse;

        try{
            Request request;

            if(Token!=null && !Token.isEmpty()){
                request = new Request.Builder()
                        .url(url)
                        .header("Authorization",Token)
                        .get()
                        .build();
            }else{
                request = new Request.Builder()
                        .url(url)
                        .get()
                        .build();
            }

            Response response = client.newCall(request).execute();

            serviceResponse = ResolveResponseCode(response,klass);
        }
        catch(IOException e){

            serviceResponse  = new OperationResult<TResult>(null, OpResultStatus.Fail,"Net work error! Please try again.");

            Logger.getAnonymousLogger().log(Level.ALL,e.getMessage());
        }
        catch(Exception e){

            serviceResponse  = new OperationResult<TResult>(null,OpResultStatus.Fail,"Net work error! Please try again.");

            Logger.getAnonymousLogger().log(Level.ALL,e.getMessage());
        }

        return serviceResponse;
    }

    @Override
    public <TResult,TData>  OperationResult<TResult> ExecutePost(String url, TData tData,Class<TResult> klass) throws IOException {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        TResult result = null;

        Gson gson = new Gson();

        RequestBody body = RequestBody.create(JSON, gson.toJson(tData));

        OperationResult<TResult> serviceResponse = null;

        try{
            Request request;

            if(Token!=null && !Token.isEmpty()){
                request = new Request.Builder()
                        .url(url)
                        .header("Authorization",Token)
                        .post(body)
                        .build();
            }else{
                request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
            }

            Response response = client.newCall(request).execute();

            serviceResponse = ResolveResponseCode(response,klass);
        }
        catch(IOException e){

            serviceResponse  = new OperationResult<TResult>(result,OpResultStatus.Fail,"Net work error! Please try again.");

        }
        catch(Exception e){

            serviceResponse  = new OperationResult<TResult>(result,OpResultStatus.Fail,"Net work error! Please try again.");

        }

        return serviceResponse;
    }

    @Override
    public <TResult,TData>  OperationResult<TResult> ExecutePut(String url, TData tData,Class<TResult> klass) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Gson gson = new Gson();

        RequestBody body = RequestBody.create(JSON, gson.toJson(tData));

        OperationResult<TResult> serviceResponse;

        TResult result = null;

        try{
            Request request;

            if(!Token.isEmpty()){
                request = new Request.Builder()
                        .url(url)
                        .header("Authorization",Token)
                        .post(body)
                        .build();
            }else{
                request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
            }

            Response response = client.newCall(request).execute();

            serviceResponse = ResolveResponseCode(response,klass);
        }
        catch(IOException e){
            serviceResponse  = new OperationResult<TResult>(result,OpResultStatus.Fail,"Net work error! Please try again.");
        }
        catch(Exception e){
            serviceResponse  = new OperationResult<TResult>(result,OpResultStatus.Fail,"Server error! Please try again.");
        }

        return serviceResponse;
    }

    @NonNull
    private <TResult> OperationResult<TResult> ResolveResponseCode(Response response,Class<TResult> klass) throws IOException {
        TResult result = null;

        OperationResult<TResult> serviceResponse;

        Gson gson = new Gson();

        int code = response.code();

        switch(code){
            case 200:
                if(response.body()!=null) {
                    result  = gson.fromJson(response.body().string(), klass);
                }
                serviceResponse  = new OperationResult<TResult>(result, OpResultStatus.Success,"Request successful!");
                break;
            case 302:
                if(response.body()!=null) {
                    result  = gson.fromJson(response.body().string(), klass);
                }
                serviceResponse  = new OperationResult<TResult>(result,OpResultStatus.Success,"Request successful!");
                break;
            case 301:
                if(response.body()!=null) {
                    result  = gson.fromJson(response.body().string(), klass);
                }
                serviceResponse  = new OperationResult<TResult>(result,OpResultStatus.Fail,"Request successful!");
                break;
            case 400:
                serviceResponse  = new OperationResult<TResult>(null,OpResultStatus.Fail,"Bad Request");
                break;
            case 401:
                serviceResponse  = new OperationResult<TResult>(null,OpResultStatus.UnAuthorized,"Unauthorized Request");
                break;
            case 403:
                serviceResponse  = new OperationResult<TResult>(null,OpResultStatus.UnAuthorized,"Forbidden Request");
                break;
            case 409:
                serviceResponse  = new OperationResult<TResult>(null,OpResultStatus.Fail,"Bad Request");
                break;
            case 415:
            case 500:
                serviceResponse  = new OperationResult<TResult>(null,OpResultStatus.Fail,"Bad Request");
                break;
            default:
                serviceResponse  = new OperationResult<TResult>(null,OpResultStatus.Fail,"Bad Request");
        }
        return serviceResponse;
    }


}


