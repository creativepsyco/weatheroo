/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.flow;

import android.view.View;

import flow.Flow;

/**
 * @author mohit
 */
public class BackSupport {

  private BackSupport() {
  }

  public static boolean onBackPressed(View childView) {
    if (childView == null)
      return false;
    if (childView instanceof HandlesBack) {
      if (((HandlesBack) childView).onBackPressed()) {
        return true;
      }
    }
    return Flow.get(childView).goBack();
  }
}
