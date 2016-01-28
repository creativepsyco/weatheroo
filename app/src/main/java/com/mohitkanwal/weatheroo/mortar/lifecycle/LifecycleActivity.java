/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.mortar.lifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

public abstract class LifecycleActivity extends AppCompatActivity {

  public abstract LifecycleOwner getLifecycleOwner();

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    setIntent(intent);
    getLifecycleOwner().onNewIntent(intent);
  }

  @Override
  protected void onStart() {
    super.onStart();
    getLifecycleOwner().onActivityStart();
  }

  @Override
  protected void onResume() {
    super.onResume();
    getLifecycleOwner().onActivityResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    getLifecycleOwner().onActivityPause();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    //LayoutInflaterCompat.setFactory(LayoutInflater.from(this), new AppCompatViewInflaterProxy(this));
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void onStop() {
    super.onStop();
    getLifecycleOwner().onActivityStop();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    getLifecycleOwner().onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    getLifecycleOwner().onActivityResult(requestCode, resultCode, data);
  }
}
