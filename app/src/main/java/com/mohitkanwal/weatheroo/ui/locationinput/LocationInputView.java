/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.ui.locationinput;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mohitkanwal.weatheroo.R;
import com.mohitkanwal.weatheroo.di.DaggerService;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * User: mohit
 * Date: 19/1/16
 */
public class LocationInputView extends LinearLayout {
  @Inject
  LocationInputScreen.Presenter presenter;

  @Bind(R.id.autoCompleteTextView)
  AutoCompleteTextView txtPlaceName;

  @Bind(R.id.location_data)
  TextView locationData;

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
  protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);
  }

  @Override
  protected void onDetachedFromWindow() {
    presenter.dropView(this);
    super.onDetachedFromWindow();
  }

  @OnClick(R.id.btn_choose_location)
  public void onClickButton() {
    presenter.dispatchChooseLocation();
  }
}
