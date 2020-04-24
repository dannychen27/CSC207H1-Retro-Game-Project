package com.project0607.game.object;

import com.project0607.R;
import com.project0607.game.Game;
import com.project0607.game.action.update.RemoveObjectUpdateAction;
import com.project0607.game.action.update.UpdateAction;
import com.project0607.render.Renderer;

import java.util.ArrayList;
import java.util.List;

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
    sharp = true;
  }

  /**
   * Render the bullet's sprite on the Android screen.
   *
   * @param game The renderer.
   */
  @Override
  public void render(Game game) {
    int id = R.drawable.sp_bullet;
    Renderer renderer = game.getRenderer();
    renderer.renderDrawable(x, y, id);
    w = renderer.getDrawableWidth(id);
    h = renderer.getDrawableHeight(id);
  }

  /**
   * Update this bullet based on the game's current state.
   *
   * @param game The game this bullet belongs to.
   * @return Actions to take after this bullet is updated.
   */
  @Override
  public List<? extends UpdateAction> update(Game game) {
    ArrayList<UpdateAction> bulletsToUpdate = new ArrayList<>();
    x += vx;
    y += vy;
    if (this.hasLeftScreen(game)) {
      bulletsToUpdate.add(new RemoveObjectUpdateAction(this));
    }
    return bulletsToUpdate;
  }

  /**
   * @param game The game this bullet belongs to.
   * @return Whether this bullet has left the Android screen.
   */
  private boolean hasLeftScreen(Game game) {
    // A bullet leaves the horizontal or vertical boundaries of the Android screen.
    return (x < 0 || x > game.getRenderer().getWidth()) || (y < 0 || y > game.getRenderer().getHeight());
  }
}
