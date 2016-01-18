/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.logger;

/**
 * Not a real crash reporting library!
 */
public final class FakeCrashLibrary {
  private FakeCrashLibrary() {
    throw new AssertionError("No instances.");
  }

  public static void log(int priority, String tag, String message) {
    // TODO add log entry to circular buffer.
  }

  public static void logWarning(Throwable t) {
    // TODO report non-fatal warning.
  }

  public static void logError(Throwable t) {
    // TODO report non-fatal error.
  }
}
