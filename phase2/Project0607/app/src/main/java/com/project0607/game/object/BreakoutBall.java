package com.project0607.game.object;

import com.project0607.activity.game.Game;
import com.project0607.util.Direction;

/**
 * A ball for Breakout that hits bricks and causes the player to lose a life
 * if the ball reaches the bottom of the screen.
 */
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
    destructive = true;
  }

  @Override
  public void collideWall(Game game, Direction direction) {
    super.collideWall(game, direction);
    if (direction == Direction.DOWN) {
      game.onLose();
    }
  }
}
