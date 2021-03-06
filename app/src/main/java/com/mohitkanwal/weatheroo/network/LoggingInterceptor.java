/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

class LoggingInterceptor implements Interceptor {
  @Override
  public Response intercept(Interceptor.Chain chain) throws IOException {
    Request request = chain.request();

    long t1 = System.nanoTime();
    Timber.i("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers());

    Response response = chain.proceed(request);

    long t2 = System.nanoTime();
    Timber.i("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers());

    return response;
  }
}
