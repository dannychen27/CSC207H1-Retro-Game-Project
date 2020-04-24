package com.project0607.game.object;

import com.project0607.R;
import com.project0607.game.Game;
import com.project0607.game.GameBreakout;
import com.project0607.game.action.update.UpdateAction;
import com.project0607.render.Renderer;

import java.util.Collections;
import java.util.List;

public class Brick extends GameObject {

  /**
   * Create a new Brick object.
   *
   * @param x This brick's horizontal coordinate.
   * @param y This brick's vertical coordinate.
   */
  public Brick(int x, int y) {
    super(x, y, 0, 0);
    destructible = true;
    hard = true;
    life = 1;
  }

  /**
   * Update this brick based on the game's current state.
   *
   * @param game The game this brick belongs to.
   * @return Actions to take after this brick is updated.
   */
  @Override
  public List<? extends UpdateAction> update(Game game) {
    return Collections.emptyList();
  }

  /**
   * Render the brick sprite on screen.
   *
   * @param game The renderer.
   */
  @Override
  public void render(Game game) {
    int id = R.drawable.sp_brick;
    Renderer renderer = game.getRenderer();
    renderer.renderDrawable(x, y, id);
    w = renderer.getDrawableWidth(id);
    h = renderer.getDrawableHeight(id);
  }

  /**
   * Update the statistics counting destructions.
   *
   * @param game The game session.
   */
  @Override
  public void updateDestructionStats(Game game) {
    game.getStatistics().upsert(GameBreakout.KEY_BRICKS_DESTROYED, n -> n + 1, 1);
  }
}
