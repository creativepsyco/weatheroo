/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.mortar;

/**
 * User: mohit
 * Date: 28/12/15
 */
public interface ScreenScope {
  /**
   * Scope name for this path (Screen). It should be as unique as possible.
   * A scope represents the lifecycle of presenters and other injected resources.
   * Therefore there should not be any clashes with any other resources with it.
   * <p/>
   * For example, 2 ChatListScreens should differ by their teamId or invite status
   * Also Presenters initialized within the same scopes are not released from memory until
   * the scope exits.
   *
   * @return a string representing the name of the scope
   */
  String getScopeName();
}
