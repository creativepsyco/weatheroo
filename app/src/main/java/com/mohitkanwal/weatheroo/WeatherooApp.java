/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo;

import android.app.Application;

import com.mohitkanwal.weatheroo.di.DaggerService;

import javax.inject.Inject;

import mortar.MortarScope;
import timber.log.Timber;

/**
 * User: mohit
 * Date: 19/1/16
 */
public class WeatherooApp extends Application {
  @Inject
  Timber.Tree timberTree;

  private MortarScope mortarScope;

  @Override
  public void onCreate() {
    super.onCreate();

    ApplicationComponent component = DaggerApplicationComponent
        .builder()
        .applicationModule(new ApplicationModule(this))
        .build();
    component.inject(this);

    Timber.plant(timberTree);
    mortarScope = MortarScope.buildRootScope()
        .withService(DaggerService.SERVICE_NAME, component)
        .build("Root");
  }


  @Override
  public Object getSystemService(String name) {
    return (mortarScope != null && mortarScope.hasService(name))
        ? mortarScope.getService(name) : super.getSystemService(name);
  }
}
