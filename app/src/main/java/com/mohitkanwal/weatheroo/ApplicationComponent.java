/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo;

import android.app.Application;

import com.mohitkanwal.weatheroo.mortar.lifecycle.LifecycleOwner;
import com.mohitkanwal.weatheroo.network.rest.WUAutoCompleteService;
import com.mohitkanwal.weatheroo.network.rest.WUForecastService;

import javax.inject.Singleton;

import dagger.Component;
import timber.log.Timber;

/**
 * User: mohit
 * Date: 4/8/15
 */
@Singleton
@Component(
    modules = {
        ApplicationModule.class
    }
)
public interface ApplicationComponent {

  void inject(WeatherooApp weatherooApp);

  Application application();

  LifecycleOwner lifecycleOwner();

  Timber.Tree provideTree();

  WUAutoCompleteService autocompleteService();

  WUForecastService forecastService();
}
