package com.project0607.game.object;

import com.project0607.game.Game;
import com.project0607.game.action.update.RemoveObjectUpdateAction;
import com.project0607.game.action.update.UpdateAction;
import com.project0607.render.Renderable;

import java.util.ArrayList;
import java.util.List;

public abstract class GameObject implements Renderable {

  /** The horizontal coordinate of this game object. */
  int x;

  /** The vertical coordinate of this game object. */
  int y;

  /** The width of this game object. */
  int w = 0;

  /** The height of this game object. */
  int h = 0;

  /** The horizontal velocity of this game object. */
  int vx;

  /** The vertical velocity of this game object. */
  int vy;

  /** Whether or not this game object is hard. */
  boolean hard = false;

  /** Whether or not this game object is sharp. */
  boolean sharp = false;

  /** Whether or not this game object can be destroyed. */
  boolean destructible = false;

  /** The number of times to hit this object before it is destroyed.
   * Only applies if destructible is true.
   */
  int life = 1;

  /**
   * Construct a game object with the following coordinates.
   *
   * @param x The horizontal coordinate of this game object.
   * @param y The vertical coordinate of this game object.
   * @param vx The horizontal velocity of this game object.
   * @param vy The vertical velocity of this game object.
   */
  GameObject(int x, int y, int vx, int vy) {
    this.x = x;
    this.y = y;
    this.vx = vx;
    this.vy = vy;
  }

  /** @return The horizontal coordinate of this game object. */
  public int getX() {
    return x;
  }

  /** @return The vertical coordinate of this game object. */
  public int getY() {
    return y;
  }

  /** @return The width of this game object. */
  public int getW() {
    return w;
  }

  /** @return The height of this game object. */
  public int getH() {
    return h;
  }

  /** @return The horizontal velocity of this game object. */
  public int getVx() {
    return vx;
  }

  /** @return The vertical velocity of this game object. */
  public int getVy() {
    return vy;
  }

  /** @return Whether this game object is hard. */
  public boolean isHard() {
    return hard;
  }

  /** @return Whether this game object is sharp. */
  public boolean isSharp() {
    return sharp;
  }

  /** @return Whether this game object is destructible. */
  public boolean isDestructible() {
    return destructible;
  }

  /**
   * React to a touch in a location.
   *
   * @param game The game session this object is in.
   * @param newX The new horizontal coordinate of this game object.
   * @param newY The new vertical coordinate of this game object.
   */
  public void onTouch(Game game, int newX, int newY) {}

  /**
   * React to a moving touch in a location.
   *
   * @param game The game session this object is in.
   * @param newX The new horizontal coordinate of this game object.
   * @param newY The new vertical coordinate of this game object.
   */
  public void onTouchMove(Game game, int newX, int newY) {}

  /**
   * React to a touch-release in a location.
   *
   * @param game The game session this object is in.
   * @param newX The new horizontal coordinate of this game object.
   * @param newY The new vertical coordinate of this game object.
   */
  public void onTouchRelease(Game game, int newX, int newY) {}

  /**
   * Update the game object based on the game's current state.
   *
   * @param game The game session the object is in.
   * @return Actions to take after all objects are updated.
   */
  public abstract List<? extends UpdateAction> update(Game game);

  /**
   * Update the statistics counting collisions.
   *
   * @param game The game the object is in.
   */
  void updateCollisionStats(Game game) {}

  /**
   * Update the statistics counting destructions.
   *
   * @param game The game the object is in.
   */
  void updateDestructionStats(Game game) {}

  /**
   * Check to see if the object collides into the wall.
   *
   * @param game The game the object is in.
   */
  void detectWallCollision(Game game) {
    if (x < 0 && vx < 0) {
      collideHorizontal(game, true);
    } else if (x + w > game.getRenderer().getWidth() && vx > 0) {
      collideHorizontal(game, false);
    }
    if (y < 0 && vy < 0) {
      collideVertical(game, true);
    } else if (y + h > game.getRenderer().getHeight() && vy > 0) {
      collideVertical(game, false);
    }
  }

  /**
   * Handle a horizontal wall collision.
   *
   * @param game The game the object is in.
   * @param left Whether or not the collision is on the left wall.
   */
  private void collideHorizontal(Game game, boolean left) {
    if (left) {
      x = 0;
      vx *= -1;
    } else {
      x = game.getRenderer().getWidth() - w;
      vx *= -1;
    }
  }

  /**
   * Handle a vertical wall collision.
   *
   * @param game The game the object is in.
   * @param top Whether or not the collision is on the top wall.
   */
  public void collideVertical(Game game, boolean top) {
    if (top) {
      y = 0;
      vy *= -1;
    } else {
      y = game.getRenderer().getHeight() - h;
      vy *= -1;
    }
  }

  /**
   * React to a collision.
   */
  public List<? extends UpdateAction> collide(Game game, boolean collideX, boolean collideY) {
    ArrayList<UpdateAction> gameObjectsToUpdate = new ArrayList<>();
    if (hard) {
      if (collideX) {
        vx *= -1;
      }
      if (collideY) {
        vy *= -1;
      }
    } else {
      gameObjectsToUpdate.add(new RemoveObjectUpdateAction(this));
    }
    updateCollisionStats(game);
    if (destructible) {
      life -= 1;
      if (life == 0) {
        updateDestructionStats(game);
        gameObjectsToUpdate.add(new RemoveObjectUpdateAction(this));
      }
    }
    return gameObjectsToUpdate;
  }
}
