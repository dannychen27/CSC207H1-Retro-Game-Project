package com.project0607.game.object;

import com.project0607.R;
import com.project0607.activity.game.Game;
import com.project0607.util.Direction;

/** A player paddle that can only move horizontally based on player touches in Pong or Breakout. */
public class PlayerPaddle extends GameObject {

  /** The horizontal coordinate where the player paddle was last touched. */
  private int lastTouch;

  /**
   * Create a new player paddle.
   *
   * @param x This player paddle's horizontal coordinate.
   * @param y This player paddle's vertical coordinate.
   * @param vx This player paddle's horizontal velocity.
   */
  public PlayerPaddle(int x, int y, int vx) {
    super(x, y, vx, 0);
    lastTouch = x;
  }

  /**
   * Start moving the player paddle towards the location of a touch.
   *
   * @param game The game session this player paddle is in.
   * @param newX The new horizontal coordinate of this player paddle.
   * @param newY The new vertical coordinate of this player paddle.
   */
  @Override
  public void onTouch(Game game, int newX, int newY) {
    lastTouch = newX;
  }

  /**
   * Update the player paddle based on the game's current state.
   *
   * @param game The game this player paddle belongs to.
   */
  @Override
  public void update(Game game) {
    // The player's paddle has a speed limit to ensure that the paddle cannot
    // teleport to the player's touch location.
    boolean isFast = game.getSession().getConfiguration().getOrDefault(Game.FAST_PADDLE, false);
    int speed = isFast ? 10 : 3;

    if (lastTouch - 10 < x && x < lastTouch + 10) {
      vx = 0;
    } else if (lastTouch > x) {
      vx = speed;
    } else {
      vx = -speed;
    }

    x += vx;
  }

  @Override
  public void collideWall(Game game, Direction direction) {
    collideWallByBouncing(game, direction);
  }

  @Override
  public void render(Game game) {
    renderBySpriteId(game, R.drawable.sp_player);
  }
}
