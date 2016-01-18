/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.flow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mohitkanwal.weatheroo.R;

import flow.Flow;
import flow.path.Path;
import flow.path.PathContainer;
import flow.path.PathContainerView;

public class FramePathContainerView extends FrameLayout
    implements HandlesBack, PathContainerView {
  private final PathContainer container;

  private boolean disabled;

  @SuppressWarnings("UnusedDeclaration") // Used by layout inflation, of course!
  public FramePathContainerView(Context context, AttributeSet attrs) {
    this(context, attrs, new SimplePathContainer(R.id.screen_switcher_tag, Path.contextFactory()));
  }

  /**
   * Allows subclasses to use custom {@link PathContainer} implementations. Allows the use
   * of more sophisticated transition schemes, and customized context wrappers.
   */
  protected FramePathContainerView(Context context, AttributeSet attrs, PathContainer container) {
    super(context, attrs);
    this.container = container;
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    return !disabled && super.dispatchTouchEvent(ev);
  }

  @Override
  public ViewGroup getContainerView() {
    return this;
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
  }

  @Override
  public void dispatch(Flow.Traversal traversal, final Flow.TraversalCallback callback) {
    disabled = true;
    container.executeTraversal(this, traversal, new Flow.TraversalCallback() {
      @Override
      public void onTraversalCompleted() {
        callback.onTraversalCompleted();
        disabled = false;
      }
    });
  }

  @Override
  public boolean onBackPressed() {
    return BackSupport.onBackPressed(getCurrentChild());
  }

  @Override
  public ViewGroup getCurrentChild() {
    return (ViewGroup) getContainerView().getChildAt(0);
  }
}
