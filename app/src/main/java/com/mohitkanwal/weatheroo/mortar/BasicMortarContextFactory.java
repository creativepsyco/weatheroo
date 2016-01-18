/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.mortar;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.LayoutInflater;

import flow.path.Path;
import flow.path.PathContextFactory;
import mortar.MortarScope;
import timber.log.Timber;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
public class BasicMortarContextFactory implements PathContextFactory {

  private final ScreenScoper screenScoper;

  public BasicMortarContextFactory(ScreenScoper screenScoper) {
    this.screenScoper = screenScoper;
  }

  @Override
  public Context setUpContext(Path path, Context parentContext) {
    String scopeName = getScopeName(path);
    MortarScope scope = screenScoper.getScreenScope(parentContext, scopeName, path);
    return new TearDownContext(parentContext, scope);
  }

  /**
   * If the path defines a scope name then use it otherwise just return default multi-verse version
   */
  private String getScopeName(Path path) {
    if (path instanceof ScreenScope) {
      return ((ScreenScope) path).getScopeName();
    }

    /**
     * This is default behaviour, it prevents clashes due to getName() + hashCode()
     * but if you have a use case of navigating from Path1 to Path2 of same type,
     * i.e. 2 path instances then
     * @see ReplyListScreen
     * you should probably override the {@link ScreenScope#getScopeName()}
     */
    return path.getClass().getName() + path.toString() + path.hashCode();
  }

  @Override
  public void tearDownContext(Context context) {
    TearDownContext.destroyScope(context);
  }

  static class TearDownContext extends ContextWrapper {
    private static final String SERVICE = "SNEAKY_MORTAR_PARENT_HOOK";

    private final MortarScope parentScope;

    private LayoutInflater inflater;

    public TearDownContext(Context context, MortarScope scope) {
      super(scope.createContext(context));
      this.parentScope = MortarScope.getScope(context);
    }

    static void destroyScope(Context context) {
      MortarScope scope = MortarScope.getScope(context);
      Timber.d("[MortarContext] Destroying scope %s", scope.getName());
      scope.destroy();
    }

    @Override
    public Object getSystemService(String name) {
      if (LAYOUT_INFLATER_SERVICE.equals(name)) {
        if (inflater == null) {
          inflater = LayoutInflater.from(getBaseContext()).cloneInContext(this);
        }
        return inflater;
      }

      if (SERVICE.equals(name)) {
        return parentScope;
      }

      return super.getSystemService(name);
    }
  }
}
