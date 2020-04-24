package com.project0607;

import android.app.Application;
import android.content.Context;

/** The application instance for this app. An instance of this is available in all activities. */
public class ProjectionApplication extends Application {
  /** This app's global session. */
  private final GlobalSession session;

  /** This app's file context. */
  private static Context fileContext;

  /** Construct a new ProjectApplication object. */
  public ProjectionApplication() {
    session = new GlobalSession();
  }

  /** @return This app's global session. */
  private GlobalSession getSession() {
    return session;
  }

  /**
   * @return The global session where global data is stored.
   *
   * @param context The context the global data belongs to in the app.
   */
  public static GlobalSession getGlobalSession(Context context) {
    ProjectionApplication app = (ProjectionApplication) context.getApplicationContext();
    return app.getSession();
  }

  /**
   * Set this app's file context.
   *
   * @param context The context this file belongs to in the app.
   */
  public static void setFileContext(Context context){
    fileContext = context;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    session.initialize(fileContext);
  }

  @Override
  public void onTerminate() {
    super.onTerminate();
    session.save(fileContext);
  }
}
