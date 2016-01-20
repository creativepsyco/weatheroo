/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo;

import java.util.Locale;

/**
 * User: mohit
 * Date: 19/1/16
 */
public final class AppConstants {
  // contains API_KEY
  public final static String WUNDERGROUND_URL_FMT = "/api/%s/hourly%s.json";

  public static String makeForecastUrl(String key) {
    return String.format(Locale.ENGLISH, WUNDERGROUND_URL_FMT, BuildConfig.WUNDERGROUND_API_KEY, key);
  }
}
