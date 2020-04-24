package com.project0607.game.object;

import com.project0607.R;
import com.project0607.activity.game.Game;
import com.project0607.game.GameBreakout;

/** A brick that can be destroyed upon collision with a ball in BrickBreaker. */
public class Brick extends GameObject {

  /** The number of times this brick can be hit before it is destroyed. */
  private int life;

  /**
   * Create a new brick object.
   *
   * @param x This brick's horizontal coordinate.
   * @param y This brick's vertical coordinate.
   * @param life Amount of hits the brick can take.
   */
  public Brick(int x, int y, int life) {
    super(x, y, 0, 0);
    this.life = life;
  }

  @Override
  public void render(Game game) {
    renderBySpriteId(game, R.drawable.sp_brick);
  }

  @Override
  public void collide(Game game, GameObject other, boolean collideX, boolean collideY) {
    if (other.isDestructive()) {
      life--;
      if (life == 0) {
        game.getStatistics().upsert(GameBreakout.KEY_BRICKS_DESTROYED, n -> n + 1, 1);
        game.removeGameObject(this);
      }
    }
  }
}
