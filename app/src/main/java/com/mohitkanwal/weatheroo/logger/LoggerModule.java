/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.logger;

import com.mohitkanwal.weatheroo.BuildConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

/**
 * User: mohit
 * Date: 19/1/16
 */
@Module
public class LoggerModule {
  @Provides
  @Singleton
  public Timber.Tree providesLogTree() {
    if (BuildConfig.DEBUG) {
      return new Timber.DebugTree();
    } else {
      return new CrashReportingTree();
    }
  }
}
