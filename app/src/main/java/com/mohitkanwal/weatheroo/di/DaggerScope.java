/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * @author Lukasz Piliszczuk <lukasz.pili@gmail.com>
 */
@Scope
@Retention(RetentionPolicy.SOURCE)
public @interface DaggerScope {
  Class<?> value();
}
