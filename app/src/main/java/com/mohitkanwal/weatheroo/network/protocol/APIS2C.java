/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.network.protocol;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * User: mohit
 * Date: 19/1/16
 */
public class APIS2C {
  public static class Response {
    @SerializedName("hourly_forecast")
    public List<Forecast> forecast;
  }

  public static class Forecast {
    @SerializedName("temp")
    public Temp temp;

    @SerializedName("FCTTIME")
    public FCTTIME fcttime;

    @SerializedName("condition")
    public String condition;
  }

  public static class FCTTIME {
    @SerializedName("epoch")
    public String epoch;
  }

  public static class Temp {
    @SerializedName("english")
    public String english;

    @SerializedName("metric")
    public String metric;
  }
}
