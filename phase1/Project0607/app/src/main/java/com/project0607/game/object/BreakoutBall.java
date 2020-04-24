package com.project0607.game.object;

import com.project0607.game.Game;
import com.project0607.game.GameBreakout;

public class BreakoutBall extends Ball {
  /**
   * Construct a new ball object for Breakout.
   *
   * @param x This Breakout ball's horizontal coordinate.
   * @param y This Breakout ball's vertical coordinate.
   * @param vx This Breakout ball's horizontal velocity.
   * @param vy This Breakout ball's vertical velocity.
   */
  public BreakoutBall(int x, int y, int vx, int vy) {
    super(x, y, vx, vy);
  }

  /**
   * Handle a vertical wall collision.
   *
   * @param game The game the object is in.
   * @param top Whether or not the collision is on the top wall.
   */
  @Override
  public void collideVertical(Game game, boolean top) {
    super.collideVertical(game, top);
    if (!top) {
      reset(game);
      vy *= -1;
      game.getStatistics().upsert(GameBreakout.KEY_BREAKOUT_LIFE, n -> n - 1, 3);
    }
  }
}
