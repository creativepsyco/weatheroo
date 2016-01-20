/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.network.rest;

import com.mohitkanwal.weatheroo.network.protocol.AQS2C;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * User: mohit
 * Date: 19/1/16
 */
public interface WUAutoCompleteService {
  /**
   * Returns the list of the locations from the Wunderground auto complete API
   */
  @GET("/aq")
  Call<AQS2C.LocationList> getAutoCompleteList(@Query("query") String query);
}
