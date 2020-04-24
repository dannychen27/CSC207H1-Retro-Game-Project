package com.project0607.game.object;

import com.project0607.R;
import com.project0607.game.Game;
import com.project0607.game.action.update.UpdateAction;
import com.project0607.render.Renderer;

import java.util.Collections;
import java.util.List;

public class ComputerPaddle extends GameObject {

  /** The ball this computer is following. */
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
    super(x, y, vx, 0);           // it should only move horizontally.
    hard = true;
    this.ball = ball;
  }

  /**
   * Update this computer paddle based on the game's current state.
   *
   * @param game The game this computer paddle belongs to.
   * @return Actions to take after this computer paddle is updated.
   */
  @Override
  public List<? extends UpdateAction> update(Game game) {
    x += vx;
    this.followBall();
    this.detectWallCollision(game);
    return Collections.emptyList();   // The ball should never be garbage collected.
  }

  /** Have the computer paddle always follow the ball. */
  private void followBall() {
    if (x < ball.getX() && vx < 0) {
      // If computer paddle is to the right of the ball, and computer paddle
      // is currently moving to the left.
      vx *= -1;
    } else if (x > ball.getX() && vx > 0) {
      // If computer paddle is to the left of the ball, and computer paddle
      // is currently moving to the right.
      vx *= -1;
    }
  }

  /**
   * Render the computer paddle sprite on screen.
   *
   * @param game The renderer.
   */
  @Override
  public void render(Game game) {
    int id = R.drawable.sp_computer;
    Renderer renderer = game.getRenderer();
    renderer.renderDrawable(x, y, id);
    w = renderer.getDrawableWidth(id);
    h = renderer.getDrawableHeight(id);
  }
}
