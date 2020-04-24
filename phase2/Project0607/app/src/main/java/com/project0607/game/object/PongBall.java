package com.project0607.game.object;

import com.project0607.activity.game.Game;
import com.project0607.game.GamePong;
import com.project0607.util.Direction;

/**
 * A ball that can bounce off walls and collide with either the player paddle
 * or the computer paddle in Pong.
 */
public class PongBall extends Ball {

  /**
   * Construct a new ball object for Pong.
   *
   * @param x This Pong ball's initial horizontal coordinate.
   * @param y This Pong ball's initial vertical coordinate.
   * @param vx This Pong ball's initial horizontal velocity.
   * @param vy This Pong ball's initial vertical velocity.
   */
  public PongBall(int x, int y, int vx, int vy) {
    super(x, y, vx, vy);
  }

  @Override
  public void collide(Game game, GameObject other, boolean collideX, boolean collideY) {
    super.collide(game, other, collideX, collideY);
    game.getStatistics().upsert(GamePong.KEY_TOTAL_TOUCHES, n -> n + 1, 1);
  }

  @Override
  public void collideWall(Game game, Direction direction) {
    super.collideWall(game, direction);
    if (direction == Direction.UP) {
      game.onWin();
    } else if (direction == Direction.DOWN) {
      game.onLose();
    }
  }
}
