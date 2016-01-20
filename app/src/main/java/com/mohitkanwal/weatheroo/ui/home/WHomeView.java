/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.ui.home;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mohitkanwal.weatheroo.R;
import com.mohitkanwal.weatheroo.di.DaggerService;
import com.mohitkanwal.weatheroo.network.protocol.APIS2C;
import com.mohitkanwal.weatheroo.network.protocol.AQS2C;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * User: mohit
 * Date: 19/1/16
 */
public class WHomeView extends LinearLayout {
  static String TEMP_FORMAT = "%s•C / %s•F";

  @Inject
  WeatherooHomeScreen.Presenter presenter;

  @Bind(R.id.img_main)
  ImageView mainImg;

  @Bind(R.id.txtLocationName)
  TextView txtLocationName;

  @Bind(R.id.txtWeatherStatus)
  TextView txtWeatherStatus;

  @Bind(R.id.txtTemp)
  TextView txtTemp;

  public WHomeView(Context context, AttributeSet attrs) {
    super(context, attrs);
    DaggerService.<WeatherooHomeScreen.Component>getDaggerComponent(context).inject(this);
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

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);
  }

  void bindData(AQS2C.LocationResult result, APIS2C.Forecast forecast) {
    setTemp(forecast.temp.english, forecast.temp.metric);
    setLocationName(result.name);
    setImage(forecast.condition);
  }

  void setTemp(String metric, String english) {
    txtTemp.setText(String.format(Locale.ENGLISH, TEMP_FORMAT, english, metric));
  }

  void setLocationName(String locationName) {
    txtLocationName.setText(locationName);
  }

  void setImage(String condition) {
    txtWeatherStatus.setText(condition);
    switch (condition.toLowerCase()) {
      case "thunderstorm":
        mainImg.setImageResource(R.drawable.thunderstorm);
    }
  }
}
