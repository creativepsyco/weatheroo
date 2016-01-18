/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.mortar;

/**
 * @author Lukasz Piliszczuk <lukasz.pili@gmail.com>
 */
public interface ScreenComponentFactory<T> {

  Object createComponent(T parent);
}
