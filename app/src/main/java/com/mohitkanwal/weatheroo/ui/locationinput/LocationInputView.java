/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.ui.locationinput;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.mohitkanwal.weatheroo.di.DaggerService;

import javax.inject.Inject;

/**
 * User: mohit
 * Date: 19/1/16
 */
public class LocationInputView extends LinearLayout {
  @Inject
  LocationInputScreen.Presenter presenter;

  public LocationInputView(Context context, AttributeSet attrs) {
    super(context, attrs);
    DaggerService.<LocationInputScreen.Component>getDaggerComponent(context).inject(this);
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    presenter.takeView(this);
  }

  @Override
  protected void onDetachedFromWindow() {
    presenter.dropView(this);
    super.onDetachedFromWindow();
  }
}
