/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.ui.locationinput;

import android.os.Bundle;

import com.mohitkanwal.weatheroo.MainActivity;
import com.mohitkanwal.weatheroo.R;
import com.mohitkanwal.weatheroo.di.DaggerScope;
import com.mohitkanwal.weatheroo.flow.Layout;
import com.mohitkanwal.weatheroo.mortar.ScreenComponentFactory;

import javax.inject.Inject;

import flow.path.Path;
import mortar.ViewPresenter;

/**
 * User: mohit
 * Date: 19/1/16
 */
@Layout(R.layout.ui_location_input)
public class LocationInputScreen extends Path implements ScreenComponentFactory<MainActivity.Component> {
  @Override
  public Object createComponent(MainActivity.Component parent) {
    return DaggerLocationInputScreen_Component
        .builder()
        .module(new Module())
        .component(parent)
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
    void inject(LocationInputView view);
  }

  @dagger.Module
  public static class Module {

  }

  @DaggerScope(Component.class)
  static class Presenter extends ViewPresenter<LocationInputView> {
    @Inject
    public Presenter() {
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
      super.onLoad(savedInstanceState);
    }
  }
}
