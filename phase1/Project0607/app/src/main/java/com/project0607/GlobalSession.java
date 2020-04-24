package com.project0607;

import android.content.Context;

import com.project0607.savedata.InternalSave;
import com.project0607.util.TypedMap;

/** Global data. */
public class GlobalSession {
  /** This app's global statistics. */
  private TypedMap statistics;

  /** This app's global configuration. */
  private final TypedMap configuration;

  /** Construct a new GlobalSession object. */
  GlobalSession() {
    // statistics = new TypedMap();
    configuration = new TypedMap();
  }

  /**
   * Create game statistics.
   *
   * @param context The context this global session belongs to.
   */
  public void initialize(Context context) {
    InternalSave load = new InternalSave(context, "gameStatistics");
    statistics = load.getMap();
  }

  /**
   * Save game statistics.
   *
   * @param context The context this global session belongs to.
   */
  public void save(Context context) {
    InternalSave save = new InternalSave(context, "gameStatistics");
    save.saveMap(statistics);
  }

  /** @return The global statistics. */
  public TypedMap getStatistics() {
    return statistics;
  }

  /** @return The global configuration. */
  public TypedMap getConfiguration() {
    return configuration;
  }
}
