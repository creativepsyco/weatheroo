/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.ui.home;

import android.os.Bundle;

import com.mohitkanwal.weatheroo.AppConstants;
import com.mohitkanwal.weatheroo.MainActivity;
import com.mohitkanwal.weatheroo.R;
import com.mohitkanwal.weatheroo.di.DaggerScope;
import com.mohitkanwal.weatheroo.flow.Layout;
import com.mohitkanwal.weatheroo.mortar.ScreenComponentFactory;
import com.mohitkanwal.weatheroo.network.protocol.APIS2C;
import com.mohitkanwal.weatheroo.network.protocol.AQS2C;
import com.mohitkanwal.weatheroo.network.rest.WUForecastService;

import java.util.List;

import javax.inject.Inject;

import dagger.Provides;
import flow.path.Path;
import mortar.ViewPresenter;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * User: mohit
 * Date: 19/1/16
 */
@Layout(R.layout.ui_weather_view)
public class WeatherooHomeScreen extends Path implements ScreenComponentFactory<MainActivity.Component> {
  List<AQS2C.LocationResult> locations;

  public WeatherooHomeScreen(List<AQS2C.LocationResult> locations) {
    this.locations = locations;
  }

  @Override
  public Object createComponent(MainActivity.Component parent) {
    return DaggerWeatherooHomeScreen_Component
        .builder()
        .component(parent)
        .module(new Module(locations))
        .build();
  }

  @DaggerScope(Component.class)
  @dagger.Component(
      dependencies = {
          MainActivity.Component.class
      },
      modules = {
          Module.class
      }
  )
  public interface Component {
    void inject(WHomeView view);

    WUForecastService forecastService();

    List<AQS2C.LocationResult> locations();
  }

  @dagger.Module
  public static class Module {

    private List<AQS2C.LocationResult> locations;

    public Module(List<AQS2C.LocationResult> locations) {
      this.locations = locations;
    }

    @Provides
    List<AQS2C.LocationResult> locations() {
      return this.locations;
    }
  }

  @DaggerScope(Component.class)
  static class Presenter extends ViewPresenter<WHomeView> implements Callback<APIS2C.Response> {

    private final long epochNow;

    List<AQS2C.LocationResult> locationList;

    WUForecastService forecastService;

    @Inject
    public Presenter(List<AQS2C.LocationResult> locationList, WUForecastService forecastService) {
      this.locationList = locationList;
      this.forecastService = forecastService;
      this.epochNow = System.currentTimeMillis();
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
      super.onLoad(savedInstanceState);
      forecastService.getHourlyForecast(AppConstants.makeForecastUrl(locationList.get(0).urlPath)).enqueue(this);
    }

    @Override
    public void onResponse(Response<APIS2C.Response> response) {
      List<APIS2C.Forecast> forecast = response.body().forecast;
      if (forecast != null) {
        getView().bindData(locationList.get(0), forecast.get(0));
      }
    }

    @Override
    public void onFailure(Throwable t) {

    }
  }
}
