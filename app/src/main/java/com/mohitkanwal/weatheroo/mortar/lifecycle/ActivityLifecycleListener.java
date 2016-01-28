/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.mortar.lifecycle;

import android.content.Intent;

/**
 * Listen to lifecycle events.
 */
public interface ActivityLifecycleListener {

  void onActivityResume();

  void onActivityPause();

  void onActivityStart();

  void onActivityStop();

  void onLowMemory();

  void dispatchActivityResult(int requestCode, int resultCode, Intent data);

  void onNewIntent(Intent intent);

  void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);
}
