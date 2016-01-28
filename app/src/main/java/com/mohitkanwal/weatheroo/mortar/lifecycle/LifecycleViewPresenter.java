/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.mortar.lifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import mortar.ViewPresenter;
import timber.log.Timber;

/**
 * ViewPresenter that gets lifecycle events.
 */
public class LifecycleViewPresenter<V extends View>
    extends ViewPresenter<V>
    implements ActivityLifecycleListener {

  protected final LifecycleOwner lifecycleOwner;

  // This is only possible if the scope is retained for the presenter
  // If a new presenter is getting created then this is of no use
  PendingActivityResult pendingActivityResult;

  public LifecycleViewPresenter(LifecycleOwner lifecycleOwner) {
    this.lifecycleOwner = lifecycleOwner;
  }

  @Override
  protected void onLoad(Bundle savedInstanceState) {
    super.onLoad(savedInstanceState);
    // XXX: Needs to be re-evaluated.
    // registering the listener here should be unregistered with #dropView?
    lifecycleOwner.register(this);
    onActivityResume();
  }

  @Override
  public void dropView(V view) {
    onActivityPause();
    super.dropView(view);
  }

  @Override
  protected void onExitScope() {
    super.onExitScope();
    lifecycleOwner.unregister(this);
  }

  @Override
  public void onActivityResume() {
    // We have a pending result for this presenter
    // We can dispatch only when a view is attached.
    if (pendingActivityResult != null && hasView()) {
      Timber.i("Dispatching activity result %s", pendingActivityResult.data);
      dispatchActivityResult(pendingActivityResult.requestCode, pendingActivityResult.resultCode, pendingActivityResult.data);
      // reset to null afterwards
      pendingActivityResult = null;
    }
  }

  @Override
  public void onActivityPause() {
  }

  @Override
  public void onActivityStart() {
  }

  @Override
  public void onActivityStop() {
  }

  @Override
  public void onLowMemory() {
  }

  @Override
  public void dispatchActivityResult(int requestCode, int resultCode, Intent data) {
    if (hasView()) {
      onActivityResult(requestCode, resultCode, data);
    } else {
      // store in cache to propagate when onLoad fires.
      pendingActivityResult = new PendingActivityResult(requestCode, resultCode, data);
    }
  }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    // Overridden in child classes
  }

  @Override
  public void onNewIntent(Intent intent) {
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

  }

  /**
   * Shadow wrapper class to wrap & store the intent
   */
  static class PendingActivityResult {
    final int requestCode;

    final int resultCode;

    final Intent data;

    public PendingActivityResult(int requestCode, int resultCode, Intent data) {
      this.requestCode = requestCode;
      this.resultCode = resultCode;
      this.data = data;
    }
  }
}
