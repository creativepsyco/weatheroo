/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.utils;

import android.view.View;
import android.view.ViewTreeObserver;

/**
 * User: mohit
 * Date: 19/1/16
 */
public final class Utils {
  public static void waitForMeasure(final View view, final OnMeasuredCallback callback) {
    int width = view.getWidth();
    int height = view.getHeight();

    if (width > 0 && height > 0) {
      callback.onMeasured(view, width, height);
      return;
    }

    view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
      @Override
      public boolean onPreDraw() {
        final ViewTreeObserver observer = view.getViewTreeObserver();
        if (observer.isAlive()) {
          observer.removeOnPreDrawListener(this);
        }

        callback.onMeasured(view, view.getWidth(), view.getHeight());

        return true;
      }
    });
  }

  public interface OnMeasuredCallback {
    void onMeasured(View view, int width, int height);
  }
}
