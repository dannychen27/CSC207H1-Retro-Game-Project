package com.project0607;

import android.content.Context;

import com.project0607.util.InternalSave;
import com.project0607.util.TypedMap;

/**
 * GlobalSession stores data about all of its game statistics and its
 * game configuration settings.
 */
public class GlobalSession {

  /** The file name to be used for saving and loading statistics. */
  private final String STATISTICS_FILE_NAME = "gameStatistics";

  /** The file name to be used for saving and loading configuration. */
  private final String CONFIGURATION_FILE_NAME = "gameConfiguration";

  /** This app's global statistics, as shown in StatisticsActivity. */
  private TypedMap statistics;

  /** This app's global configuration, as shown in ConfigurationActivity. */
  private TypedMap configuration;

  /** Construct a new GlobalSession object. */
  GlobalSession() {}

  /**
   * Create game statistics.
   *
   * @param context The context this global session belongs to.
   */
  void initialize(Context context) {
    InternalSave statLoad = new InternalSave(context, STATISTICS_FILE_NAME);
    statistics = statLoad.getMap();
    InternalSave confLoad = new InternalSave(context, CONFIGURATION_FILE_NAME);
    configuration = confLoad.getMap();
  }

  /**
   * Save game statistics and game configuration settings.
   *
   * @param context The context this global session belongs to.
   */
  void save(Context context) {
    InternalSave statSave = new InternalSave(context, STATISTICS_FILE_NAME);
    statSave.saveMap(statistics);
    InternalSave confSave = new InternalSave(context, CONFIGURATION_FILE_NAME);
    confSave.saveMap(configuration);
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
