package com.project0607.game.object;

import com.project0607.R;
import com.project0607.activity.game.Game;
import com.project0607.util.Direction;

/**
 * A ball that can collide with some other game object, like a computer paddle
 * in Pong or a brick in BrickBreaker.
 */
public abstract class Ball extends GameObject {

  /**
   * Create a new ball object.
   *
   * @param x This ball's horizontal coordinate.
   * @param y This ball's vertical coordinate.
   * @param vx This ball's horizontal velocity.
   * @param vy This ball's vertical velocity.
   */
  Ball(int x, int y, int vx, int vy) {
    super(x, y, vx, vy);
  }

  @Override
  public void render(Game game) {
    renderBySpriteId(game, R.drawable.sp_ball);
  }

  @Override
  public void update(Game game) {
    x += vx;
    y += vy;
  }

  @Override
  public void collide(Game game, GameObject other, boolean collideX, boolean collideY) {
    if (other.isSolid()) {
      collideByBouncing(collideX, collideY);
    }
  }

  @Override
  public void collideWall(Game game, Direction direction) {
    collideWallByBouncing(game, direction);
  }
}
