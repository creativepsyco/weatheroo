/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.mortar.lifecycle;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

public class LifecycleActionViewPresenter<V extends View> extends LifecycleViewPresenter<V> {

  protected final ActivityHelper activityHelper;

  public LifecycleActionViewPresenter(@NonNull ActivityHelper helper, LifecycleOwner lifecycleOwner) {
    super(lifecycleOwner);
    this.activityHelper = helper;
  }

  public void navigateTo(Class cls, Bundle bundle) {
    Intent intent = new Intent();
    intent.putExtras(bundle);
    Activity activity = getActivity();
    activity.startActivity(intent);
  }

  public LayoutInflater getInflater() {
    return LayoutInflater.from(getView().getContext());
  }

  public Object getContextService(String serviceNo) {
    return getView().getContext().getSystemService(serviceNo);
  }

  public Context getContext() {
    return getView().getContext();
  }

  public void startActivity(Class cls) {
    navigateTo(cls, new Bundle());
  }

  protected final Activity getActivity() {
    return activityHelper.findActivity((ContextWrapper) getView().getContext());
  }
}
