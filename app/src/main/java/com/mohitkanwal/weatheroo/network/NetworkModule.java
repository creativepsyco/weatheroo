/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.network;

import com.mohitkanwal.weatheroo.network.rest.WUAutoCompleteService;
import com.mohitkanwal.weatheroo.network.rest.WUForecastService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * User: mohit
 * Date: 19/1/16
 */
@Module
public class NetworkModule {
  @Provides
  @Singleton
  WUAutoCompleteService wuAutoCompleteService() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://autocomplete.wunderground.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    return retrofit.create(WUAutoCompleteService.class);
  }

  @Provides
  @Singleton
  WUForecastService wuForecastService() {
    OkHttpClient client = new OkHttpClient.Builder()
        .addInterceptor(new LoggingInterceptor())
        .build();

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://api.wunderground.com/api/f5608959ac5fab9f/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build();
    return retrofit.create(WUForecastService.class);
  }
}
