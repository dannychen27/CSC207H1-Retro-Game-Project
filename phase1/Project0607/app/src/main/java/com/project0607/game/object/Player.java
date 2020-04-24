package com.project0607.game.object;

import com.project0607.R;
import com.project0607.game.Game;
import com.project0607.game.action.update.UpdateAction;
import com.project0607.render.Renderer;

import java.util.Collections;
import java.util.List;

public class Player extends GameObject {

  /** Whether or not the paddle is moving. */
  private boolean move = false;

  /** Whether or not the paddle is moving to the right. */
  private boolean moveRight = false;

  /** The location of the last touch. */
  private int lastTouch = 0;

  /** The speed of the paddle. */
  private int speed;

  /**
   * Create a new player object.
   *
   * @param x This player's horizontal coordinate.
   * @param y This player's vertical coordinate.
   * @param vx This player's horizontal velocity.
   */
  public Player(int x, int y, int vx) {
    super(x, y, vx, 0);
    hard = true;
    speed = vx;
  }

  /**
   * Start moving towards the location of a touch.
   *
   * @param game The game session this object is in.
   * @param newX The new horizontal coordinate of this game object.
   * @param newY The new vertical coordinate of this game object.
   */
  @Override
  public void onTouch(Game game, int newX, int newY) {
    lastTouch = x;
  }

  /**
   * Stop moving on a touch-release.
   *
   * @param game The game session this object is in.
   * @param newX The new horizontal coordinate of this game object.
   * @param newY The new vertical coordinate of this game object.
   */
  @Override
  public void onTouchRelease(Game game, int newX, int newY) {
    move = false;
  }

  /**
   * Update the direction on a moving touch.
   *
   * @param game The game session this object is in.
   * @param newX The new horizontal coordinate of this game object.
   * @param newY The new vertical coordinate of this game object.
   */
  @Override
  public void onTouchMove(Game game, int newX, int newY) {
    move = true;
    moveRight = newX > lastTouch;       // What if newX == lastTouch?
    lastTouch = x;
  }

  /**
   * Update the player based on the game's current state.
   *
   * @param game The game this player belongs to.
   * @return Actions to take after this player is updated.
   */
  @Override
  public List<? extends UpdateAction> update(Game game) {
    boolean isFast = game.getSession().getConfiguration().getOrDefault(Game.FAST_PADDLE, false);
    speed = isFast ? 10 : 3;
    if (!move) {
      vx = 0;                             // Stay still.
    } else {
      vx = moveRight ? speed : -speed;    // Move left or right.
    }
    x += vx;
    y += vy;
    detectWallCollision(game);
    return Collections.emptyList();       // The player should not be garbage collected.
  }

  /**
   * Render this player sprite on the Android screen.
   *
   * @param game The renderer.
   */
  @Override
  public void render(Game game) {
    int id = R.drawable.sp_player;
    Renderer renderer = game.getRenderer();
    renderer.renderDrawable(x, y, id);
    w = renderer.getDrawableWidth(id);
    h = renderer.getDrawableHeight(id);
  }
}
