/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.network.rest;

import com.mohitkanwal.weatheroo.network.protocol.APIS2C;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * User: mohit
 * Date: 19/1/16
 */
public interface WUForecastService {
  @GET
  Call<APIS2C.Response> getHourlyForecast(@Url String queryUrl);
}
