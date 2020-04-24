package com.project0607.game.object;

import com.project0607.R;
import com.project0607.activity.game.Game;
import com.project0607.util.TypedKey;

/** A life display that displays the number of lives the player has remaining */
public class LifeDisplay extends StatisticsDisplay {

  /**
   * Create a new life display.
   *
   * @param x The horizontal coordinate of this life display.
   * @param y The vertical coordinate of this life display.
   * @param livesKey The number of lives the player has left.
   */
  public LifeDisplay(int x, int y, TypedKey<Integer> livesKey) {
    super(x, y, livesKey);
  }

  /**
   * Render the number of lives on screen, in the form
   *
   * HEART_SPRITE x *number of lives left*.
   *
   * @param game The game this score display belongs to.
   */
  @Override
  public void render(Game game) {
    game.getView().renderDrawable(x - 200, y, R.drawable.sp_heart);
    game.getView().renderText(x - 140, y, "x");
    super.render(game);
  }
}
