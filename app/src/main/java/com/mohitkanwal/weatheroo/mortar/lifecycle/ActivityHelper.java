/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.mortar.lifecycle;

import android.app.Activity;
import android.content.ContextWrapper;

import javax.inject.Inject;

public class ActivityHelper {

  @Inject
  public ActivityHelper() {
  }

  public Activity findActivity(ContextWrapper pathContext) {
    while (pathContext.getBaseContext() != null) {
      if (pathContext.getBaseContext() instanceof Activity) {
        return (Activity) pathContext.getBaseContext();
      }
      if (!(pathContext.getBaseContext() instanceof ContextWrapper)) {
        throw new IllegalStateException("BaseContext not ContextWrapper");
      }
      pathContext = (ContextWrapper) pathContext.getBaseContext();
    }
    throw new IllegalStateException("There should always be an Activity as a BaseContext");
  }
}
