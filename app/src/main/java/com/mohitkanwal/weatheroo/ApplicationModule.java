/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo;

import android.app.Application;

import com.mohitkanwal.weatheroo.logger.LoggerModule;
import com.mohitkanwal.weatheroo.network.NetworkModule;

import dagger.Module;
import dagger.Provides;

/**
 * User: mohit
 * Date: 19/1/16
 */
@Module(includes = {
    LoggerModule.class,
    NetworkModule.class
})
public class ApplicationModule {
  private final Application application;

  public ApplicationModule(Application application) {
    this.application = application;
  }

  @Provides
  Application providesApplication() {
    return application;
  }
}
