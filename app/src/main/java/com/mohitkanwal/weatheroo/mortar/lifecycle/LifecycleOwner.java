/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.mortar.lifecycle;

import android.content.Intent;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Pass along the Android lifecycle events.
 * Tip to http://stackoverflow.com/questions/21927990/mortar-flow-with-third-party-libraries-hooked-to-activity-lifecycle/21959529?noredirect=1#21959529
 * on this one, mostly.
 */
public class LifecycleOwner {

  private static int count = 0;

  private List<ActivityLifecycleListener> registeredListeners
      = new ArrayList<>();

  public LifecycleOwner() {
    count++;
    Timber.i("New lifecycle owner init %d", count);
  }

  public void register(@NonNull ActivityLifecycleListener listener) {
    // Unregister the old one
    if (registeredListeners.contains(listener)) {
      registeredListeners.remove(listener);
    }
    registeredListeners.add(listener);
  }

  public void unregister(@NonNull ActivityLifecycleListener listener) {
    registeredListeners.remove(listener);
  }

  public void onActivityResume() {
    ActivityLifecycleListener[] lifecycleListeners = getArrayCopyOfRegisteredListeners();
    for (ActivityLifecycleListener c : lifecycleListeners) {
      c.onActivityResume();
    }
  }

  public void onActivityPause() {
    ActivityLifecycleListener[] lifecycleListeners = getArrayCopyOfRegisteredListeners();
    for (ActivityLifecycleListener c : lifecycleListeners) {
      c.onActivityPause();
    }
  }

  public void onActivityStart() {
    ActivityLifecycleListener[] lifecycleListeners = getArrayCopyOfRegisteredListeners();
    for (ActivityLifecycleListener c : lifecycleListeners) {
      c.onActivityStart();
    }
  }

  public void onActivityStop() {
    ActivityLifecycleListener[] lifecycleListeners = getArrayCopyOfRegisteredListeners();
    for (ActivityLifecycleListener c : lifecycleListeners) {
      c.onActivityStop();
    }
  }

  public void onLowMemory() {
    ActivityLifecycleListener[] lifecycleListeners = getArrayCopyOfRegisteredListeners();
    for (ActivityLifecycleListener c : lifecycleListeners) {
      c.onLowMemory();
    }
  }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    ActivityLifecycleListener[] lifecycleListeners = getArrayCopyOfRegisteredListeners();
    Timber.i("Number of listeners %d attached to lifecyle owner %d", lifecycleListeners.length, count);
    for (ActivityLifecycleListener c : lifecycleListeners) {
      c.dispatchActivityResult(requestCode, resultCode, data);
    }
  }

  /**
   * Creates a copy of the {@link #registeredListeners} list in order to safely iterate the
   * listeners. This was added to avoid {@link java.util.ConcurrentModificationException} that may
   * be triggered when listeners are added / removed during one of the lifecycle events.
   */
  private ActivityLifecycleListener[] getArrayCopyOfRegisteredListeners() {
    ActivityLifecycleListener[] registeredListenersArray =
        new ActivityLifecycleListener[registeredListeners.size()];
    registeredListeners.toArray(registeredListenersArray);
    return registeredListenersArray;
  }

  public void onNewIntent(Intent intent) {
    ActivityLifecycleListener[] lifecycleListeners = getArrayCopyOfRegisteredListeners();
    for (ActivityLifecycleListener c : lifecycleListeners) {
      c.onNewIntent(intent);
    }
  }

  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    ActivityLifecycleListener[] lifecycleListeners = getArrayCopyOfRegisteredListeners();
    for (ActivityLifecycleListener c : lifecycleListeners) {
      c.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
  }
}
