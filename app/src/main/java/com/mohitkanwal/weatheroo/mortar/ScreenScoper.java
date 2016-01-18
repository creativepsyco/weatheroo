/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.mortar;

import android.content.Context;

import com.mohitkanwal.weatheroo.di.DaggerService;

import flow.path.Path;
import mortar.MortarScope;
import timber.log.Timber;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@SuppressWarnings("all")
public class ScreenScoper {

  public MortarScope getScreenScope(Context context, String name, Path path) {
    MortarScope parentScope = MortarScope.getScope(context);

    MortarScope childScope = parentScope.findChild(name);
    if (childScope != null) {
      Timber.d("[MortarContext] Reusing existing scope %s", name);
      return childScope;
    }

    if (!(path instanceof ScreenComponentFactory)) {
      throw new IllegalStateException("Path must imlement ComponentFactory");
    }
    ScreenComponentFactory screenComponentFactory = (ScreenComponentFactory) path;
    Object component = screenComponentFactory.createComponent(parentScope.getService(DaggerService.SERVICE_NAME));

    Timber.d("[MortarContext] Creating scope for %s", name);
    MortarScope.Builder builder = parentScope.buildChild()
        .withService(DaggerService.SERVICE_NAME, component);

    return builder.build(name);
  }
}
