package com.affinistechnologies.jamrocksingles.jamrocksingles.infrastructure;

/**
 * Created by clayt on 2/26/2017.
 */

import com.affinistechnologies.jamrocksingles.jamrocksingles.models.OperationResult;

import java.io.IOException;
/**
 * Created by clayt on 10/1/2016.
 */

public interface RestClient {
    void AddToken(String token);

    <TResult> OperationResult<TResult> ExecuteGet(String url,Class<TResult> klass)throws IOException;

    <TResult,TData> OperationResult<TResult> ExecutePost(String url,TData data,Class<TResult> klass)throws IOException;

    <TResult,TData> OperationResult<TResult> ExecutePut(String url, TData data, Class<TResult> klass)throws IOException;
}
