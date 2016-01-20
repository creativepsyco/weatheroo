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

public class AQS2C {
  public static class LocationList {
    @SerializedName("RESULTS")
    public List<LocationResult> results;
  }

  public static class LocationResult {
    @SerializedName("name")
    public String name;

    @SerializedName("type")
    public String type;

    @SerializedName("c")
    public String country;

    @SerializedName("l")
    public String urlPath;
  }
}
