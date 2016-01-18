/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.mortar;

import android.content.Context;
import android.util.AttributeSet;

import com.mohitkanwal.weatheroo.R;
import com.mohitkanwal.weatheroo.flow.FramePathContainerView;
import com.mohitkanwal.weatheroo.flow.SimplePathContainer;

import flow.path.Path;


public class MortarPathContainerView extends FramePathContainerView {

  public MortarPathContainerView(Context context, AttributeSet attrs) {
    super(
        context,
        attrs,
        new SimplePathContainer(
            R.id.screen_switcher_tag,
            Path.contextFactory(new BasicMortarContextFactory(new ScreenScoper()))));
  }
}
