package com.project0607.game.object;

import com.project0607.activity.game.Game;
import com.project0607.activity.game.GameView;
import com.project0607.util.Direction;

/**
 * A game object is an entity that has a sprite representation, responds to player
 * touches and touch-releases, supports collision detection, and can track certain
 * game statistics.
 */
public abstract class GameObject {

  /** The horizontal coordinate of this game object. */
  int x;

  /** The vertical coordinate of this game object. */
  int y;

  /** The horizontal velocity of this game object. */
  int vx;

  /** The vertical velocity of this game object. */
  int vy;

  /** Whether or not this object can deal damage on collision. */
  boolean destructive = false;

  /** Whether or not this object takes up physical space e.g. should be bounced off. */
  boolean solid = true;

  /** The width of this game object. */
  private int w = 0;

  /** The height of this game object. */
  private int h = 0;

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

  /**
   * Set the horizontal coordinate of this game object.
   *
   * @param x The horizontal coordinate of this game object.
   */
  private void setX(int x) {
    this.x = x;
  }

  /** @return The vertical coordinate of this game object. */
  public int getY() {
    return y;
  }

  /**
   * Set the vertical coordinate of this game object.
   *
   * @param y The vertical coordinate of this game object.
   */
  private void setY(int y) {
    this.y = y;
  }

  /** @return The width of this game object. */
  public int getW() {
    return w;
  }

  /** @return The height of this game object. */
  public int getH() {
    return h;
  }

  /**
   * Set the height of this game object.
   *
   * @param h The height of this game object.
   */
  public void setH(int h) {
    this.h = h;
  }

  /** @return The horizontal velocity of this game object. */
  public int getVx() {
    return vx;
  }

  /**
   * Set the horizontal velocity of this game object.
   *
   * @param vx The horizontal velocity of this game object.
   */
  private void setVx(int vx) {
    this.vx = vx;
  }

  /** @return The vertical velocity of this game object. */
  public int getVy() {
    return vy;
  }

  /**
   * Set the vertical velocity of this game object.
   *
   * @param vy The vertical velocity of this game object.
   */
  private void setVy(int vy) {
    this.vy = vy;
  }

  /** @return Whether this game object is destructive. */
  boolean isDestructive() {
    return destructive;
  }

  /** @return Whether this game object is solid. */
  boolean isSolid() {
    return solid;
  }

  /**
   * Set this game object's location.
   *
   * @param x The horizontal coordinate of this game object.
   * @param y The vertical coordinate of this game object.
   */
  public void setLocation(int x, int y) {
    this.setX(x);
    this.setY(y);
  }

  /**
   * Set this game object's velocity.
   *
   * @param vx The horizontal coordinate of this game object.
   * @param vy The vertical coordinate of this game object.
   */
  public void setVelocity(int vx, int vy) {
    this.setVx(vx);
    this.setVy(vy);
  }

  /**
   * React to a touch in a location.
   *
   * @param game The game session this game object is in.
   * @param newX The new horizontal coordinate of this game object.
   * @param newY The new vertical coordinate of this game object.
   */
  public void onTouch(Game game, int newX, int newY) {}

  /**
   * Render this game object on the Android screen.
   *
   * @param game The game session this renderable is in.
   */
  public void render(Game game) {}

  /**
   * Render this game object's sprite, given its sprite id.
   *
   * @param game The game this game object belongs to.
   * @param id The game object's sprite id.
   */
  void renderBySpriteId(Game game, int id) {
    GameView view = game.getView();
    view.renderDrawable(x, y, id);
    w = view.getDrawableWidth(id);
    h = view.getDrawableHeight(id);
  }

  /**
   * Update the game object based on the game's current state.
   *
   * @param game The game session this game object belongs to.
   */
  public void update(Game game) {}

  /**
   * Make this game object respond to a collision with another game object.
   *
   * @param game The game session this game object belongs to.
   * @param other The game object this game object collided with.
   * @param collideX Whether it collided on the x-axis.
   * @param collideY Whether it collided on the y-axis.
   *
   * Precondition: the other game object is not solid.
   */
  public void collide(Game game, GameObject other, boolean collideX, boolean collideY) {}

  /**
   * Make this game object bounce off of another game object.
   *
   * @param collideX Whether it collided on the x-axis.
   * @param collideY Whether it collided on the y-axis.
   *
   * Precondition: the other game object is solid.
   */
  void collideByBouncing(boolean collideX, boolean collideY) {
    if (collideX) {
      vx *= -1;
    }

    if (collideY) {
      vy *= -1;
    }
  }

  /**
   * Make this game object respond to a collision with a (solid) wall.
   *
   * @param game The game session this game object belongs to.
   * @param direction The wall's direction.
   */
  public void collideWall(Game game, Direction direction) {}

  /**
   * Make this game object respond to a collision with a (solid) wall.
   *
   * @param game The game session this game object belongs to.
   * @param direction The wall's direction.
   */
  void collideWallByBouncing(Game game, Direction direction) {
    switch (direction) {
      case LEFT:
        x = 0;
        vx *= -1;
        break;
      case RIGHT:
        x = game.getView().getWidth() - w;
        vx *= -1;
        break;
      case UP:
        y = 0;
        vy *= -1;
        break;
      case DOWN:
        y = game.getView().getHeight() - h;
        vy *= -1;
        break;
      default:
        throw new IllegalArgumentException("invalid direction");
    }
  }
}
