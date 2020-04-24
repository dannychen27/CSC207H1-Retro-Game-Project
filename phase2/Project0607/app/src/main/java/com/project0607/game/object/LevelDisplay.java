package com.project0607.game.object;

import com.project0607.activity.game.Game;
import com.project0607.util.TypedKey;

/** A level display that displays the current level of the game it belongs to. */
public class LevelDisplay extends StatisticsDisplay {

  /**
   * Create a new level display.
   *
   * @param x The horizontal coordinate of this level display.
   * @param y The vertical coordinate of this level display.
   * @param levelsKey This game's current level.
   */
  public LevelDisplay(int x, int y, TypedKey<Integer> levelsKey) {
    super(x, y, levelsKey);
  }

  /**
   * Render the current level of this game, in the form
   *
   * LEVEL *current level*.
   *
   * @param game The game this level display belongs to.
   */
  @Override
  public void render(Game game) {
    game.getView().renderText(x - 200, y, "Level");
    super.render(game);
  }
}
