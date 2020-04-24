package com.project0607.game.object;

import com.project0607.R;
import com.project0607.game.Game;
import com.project0607.game.action.update.UpdateAction;
import com.project0607.render.Renderer;

import java.util.Collections;
import java.util.List;

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
    hard = true;
    sharp = true;
  }

  /**
   * Reset this ball's position in the middle of the screen.
   */
  void reset(Game game) {
    x = game.getRenderer().getWidth() / 2;
    y = game.getRenderer().getHeight() / 2;
  }

  /**
   * Update this ball based on the game's current state.
   *
   * @param game The game this ball belongs to.
   * @return Actions to take after this ball is updated.
   */
  @Override
  public List<? extends UpdateAction> update(Game game) {
    x += vx;
    y += vy;
    this.detectWallCollision(game);
    return Collections.emptyList();   // The ball should never be garbage collected.
  }

  /**
   * Render this ball on screen.
   *
   * @param game The renderer.
   */
  @Override
  public void render(Game game) {
    int id = R.drawable.sp_ball;
    Renderer renderer = game.getRenderer();
    renderer.renderDrawable(x, y, id);
    w = renderer.getDrawableWidth(id);
    h = renderer.getDrawableHeight(id);
  }
}