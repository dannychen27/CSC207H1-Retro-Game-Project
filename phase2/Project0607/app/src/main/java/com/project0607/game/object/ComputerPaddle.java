package com.project0607.game.object;

import com.project0607.R;
import com.project0607.activity.game.Game;
import com.project0607.util.Direction;

/** A computer paddle that moves automatically to follow the ball in Pong. */
public class ComputerPaddle extends GameObject {

  /** The ball this computer paddle is following. */
  private final Ball ball;

  /**
   * Create a new computer paddle object.
   *
   * @param x This computer paddle's horizontal coordinate.
   * @param y This computer paddle's vertical coordinate.
   * @param vx This computer paddle's horizontal velocity.
   * @param ball The ball this computer is following.
   */
  public ComputerPaddle(int x, int y, int vx, Ball ball) {
    super(x, y, vx, 0); // it should only move horizontally.
    this.ball = ball;
  }

  @Override
  public void render(Game game) {
    renderBySpriteId(game, R.drawable.sp_computer);
  }

  @Override
  public void update(Game game) {
    x += vx;
    followBall();
  }

  @Override
  public void collideWall(Game game, Direction direction) {
    collideWallByBouncing(game, direction);
  }

  /**
   * Have the computer paddle always follow the ball, and move in the opposite
   * direction if the ball hits a wall.
   */
  private void followBall() {
    if (x < ball.getX() && vx < 0) {
      vx *= -1;
    } else if (x > ball.getX() && vx > 0) {
      vx *= -1;
    }
  }
}
