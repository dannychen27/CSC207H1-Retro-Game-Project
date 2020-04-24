package com.project0607.game.object;

import com.project0607.R;
import com.project0607.activity.game.Game;

/** A bullet that destroys enemies in Shoot 'Em Up. */
public class Bullet extends GameObject {

  /**
   * Create a new bullet object.
   *
   * @param x This bullet's horizontal coordinate.
   * @param y This bullet's vertical coordinate.
   * @param vx This bullet's horizontal velocity.
   * @param vy This bullet's vertical velocity.
   */
  Bullet(int x, int y, int vx, int vy) {
    super(x, y, vx, vy);
    this.destructive = true;
  }

  @Override
  public void render(Game game) {
    renderBySpriteId(game, R.drawable.sp_bullet);
  }

  @Override
  public void update(Game game) {
    x += vx;
    y += vy;
    if (this.hasLeftScreen(game)) {
      game.removeGameObject(this);
    }
  }

  @Override
  public void collide(Game game, GameObject other, boolean collideX, boolean collideY) {
    game.removeGameObject(this);
  }

  /**
   * @return Whether this bullet has left either horizontal or vertical boundaries of
   * the Android screen.
   *
   * @param game The game this bullet belongs to.
   */
  private boolean hasLeftScreen(Game game) {
    return (x < 0 || x > game.getView().getWidth()) || (y < 0 || y > game.getView().getHeight());
  }
}
