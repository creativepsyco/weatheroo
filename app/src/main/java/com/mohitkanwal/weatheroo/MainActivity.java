/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.mohitkanwal.weatheroo.di.DaggerService;
import com.mohitkanwal.weatheroo.di.PerActivity;
import com.mohitkanwal.weatheroo.flow.HandlesBack;
import com.mohitkanwal.weatheroo.mortar.GsonParceler;
import com.mohitkanwal.weatheroo.mortar.lifecycle.LifecycleActivity;
import com.mohitkanwal.weatheroo.mortar.lifecycle.LifecycleOwner;
import com.mohitkanwal.weatheroo.ui.locationinput.LocationInputScreen;

import javax.inject.Inject;

import flow.Flow;
import flow.FlowDelegate;
import flow.History;
import flow.path.PathContainerView;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;
import timber.log.Timber;

public class MainActivity extends LifecycleActivity implements Flow.Dispatcher {

  @Inject
  LifecycleOwner lifecycleOwner;

  private MortarScope activityScope;

  private PathContainerView container;

  private HandlesBack containerAsBackTarget;

  private FlowDelegate flowSupport;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Timber.d("==== Activity onCreate ====");
    GsonParceler parceler = new GsonParceler(new Gson());
    super.onCreate(savedInstanceState);

    FlowDelegate.NonConfigurationInstance nonConfig;
    nonConfig = (FlowDelegate.NonConfigurationInstance) getLastCustomNonConfigurationInstance();

    String scopeName = getLocalClassName() + "-task-" + getTaskId();
    activityScope = MortarScope.findChild(getApplicationContext(), scopeName);

    if (activityScope == null) {
      Component component = DaggerMainActivity_Component.builder()
          .applicationComponent(DaggerService.<ApplicationComponent>getDaggerComponent(getApplicationContext()))
          .build();

      activityScope = MortarScope.buildChild(getApplicationContext())
          .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
          .withService(DaggerService.SERVICE_NAME, component)
          .build(scopeName);
      Timber.d("Mortar Context - Creating scope for === %s", activityScope.getName());
    } else {
      Timber.d("Mortar Context - Reusing scope for === %s", activityScope.getName());
    }

    DaggerService.<Component>getDaggerComponent(this).inject(this);

    BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    History.Builder builder = History.emptyBuilder();
    builder.push(new LocationInputScreen());

    container = (PathContainerView) findViewById(R.id.flow_container);
    containerAsBackTarget = (HandlesBack) container;
    flowSupport = FlowDelegate.onCreate(
        nonConfig, getIntent(),
        savedInstanceState, parceler,
        builder.build(), this);


    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public LifecycleOwner getLifecycleOwner() {
    return lifecycleOwner;
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    flowSupport.onNewIntent(intent);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onBackPressed() {
    if (containerAsBackTarget.onBackPressed()) return;
    if (flowSupport.onBackPressed()) return;
    super.onBackPressed();
  }

  @Override
  protected void onResume() {
    super.onResume();
    flowSupport.onResume();
  }

  @Override
  protected void onPause() {
    flowSupport.onPause();
    super.onPause();
  }


  @Override
  public Object onRetainCustomNonConfigurationInstance() {
    return flowSupport.onRetainNonConfigurationInstance();
  }

  @Override
  public Object getSystemService(@NonNull String name) {
    if (flowSupport != null) {
      Object flowService = flowSupport.getSystemService(name);
      if (flowService != null) return flowService;
    }

    return activityScope != null && activityScope.hasService(name) ? activityScope.getService(name)
        : super.getSystemService(name);
  }

  @Override
  public void dispatch(Flow.Traversal traversal, final Flow.TraversalCallback callback) {
    container.dispatch(traversal, new Flow.TraversalCallback() {
      @Override
      public void onTraversalCompleted() {
        invalidateOptionsMenu();
        callback.onTraversalCompleted();
      }
    });
  }

  @Override
  protected void onDestroy() {
    if (isFinishing() && activityScope != null) {
      Timber.d("Mortar Context ======== Destroying Activity Scope %s =====", activityScope.getName());
      activityScope.destroy();
      activityScope = null;
    }

    super.onDestroy();
  }

  @PerActivity
  @dagger.Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
  public interface Component extends ApplicationComponent {
    void inject(MainActivity activity);
  }

  @dagger.Module
  static class ActivityModule {

  }
}
