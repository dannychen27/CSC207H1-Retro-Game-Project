package com.project0607.game.object;

import com.project0607.activity.game.Game;
import com.project0607.util.TypedKey;

/**
 * A statistics display that displays some generic statistic (like score, or number of enemies
 * killed).
 */
public class StatisticsDisplay extends GameObject {

  /** The statistic to be displayed. */
  private final TypedKey<Integer> statsKey;

  /**
   * Create a new statistics display.
   *
   * @param x The horizontal coordinate of this statistics display.
   * @param y The vertical coordinate of this statistics display.
   * @param statsKey The statistic to be displayed.
   */
  public StatisticsDisplay(int x, int y, TypedKey<Integer> statsKey) {
    super(x, y, 0, 0);
    this.statsKey = statsKey;
    solid = false;
  }

  @Override
  public void render(Game game) {
    int stats = game.getStatistics().getOrDefault(statsKey, 0);
    renderNumbers(game, stats);
  }

  /**
   * Render the digits for a statistic on screen.
   *
   * @param game The game this statistics display belongs to.
   * @param statsValue The value of the statistic to display.
   */
  private void renderNumbers(Game game, int statsValue) {
    game.getView().renderDigit(x - 100, y, (statsValue / 100) % 10);
    game.getView().renderDigit(x - 50, y, (statsValue / 10) % 10);
    game.getView().renderDigit(x, y, statsValue % 10);
  }
}
