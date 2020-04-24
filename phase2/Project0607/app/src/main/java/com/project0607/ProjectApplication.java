package com.project0607;

import android.app.Application;
import android.content.Context;

/**
 * The application instance for this app.
 * An instance of this is available in all activities.
 */
public class ProjectApplication extends Application {

  /** This app's global session. */
  private final GlobalSession session;

  /** Construct a new ProjectApplication object. */
  public ProjectApplication() {
    session = new GlobalSession();
  }

  /**
   * @return The global session where global data is stored.
   * @param context The context the global data belongs to in the app.
   */
  public static GlobalSession getGlobalSession(Context context) {
    ProjectApplication app = (ProjectApplication) context.getApplicationContext();
    return app.getSession();
  }

  /** @return This app's global session. */
  private GlobalSession getSession() {
    return session;
  }

  /** Called when this is created. */
  @Override
  public void onCreate() {
    super.onCreate();
    session.initialize(this);
  }

  /** Called by GameActivity.onPause */
  @Override
  public void onTerminate() {
    super.onTerminate();
    session.save(this);
  }
}
