package com.project0607.game.object;

import com.project0607.game.Game;
import com.project0607.game.GameShmup;
import com.project0607.game.action.update.RemoveObjectUpdateAction;
import com.project0607.game.action.update.UpdateAction;
import com.project0607.render.Renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class Enemy extends GameObject {

  /** This enemy's sprite state. */
  boolean state = false;

  /** This enemy's sprite counter. */
  private int stateCounter = 0;

  /**
   * Create a new enemy object.
   *
   * @param x This enemy's horizontal coordinate.
   * @param y This enemy's vertical coordinate.
   * @param vx This enemy's horizontal velocity.
   * @param vy This enemy's vertical velocity.
   */
  Enemy(int x, int y, int vx, int vy) {
    super(x, y, vx, vy);
    hard = true;
    destructible = true;
    life = 1;
  }

  /**
   * Update this enemy based on the game's current state.
   *
   * @param game The game this computer paddle belongs to.
   * @return Actions to take after this computer paddle is updated.
   */
  @Override
  public List<? extends UpdateAction> update(Game game) {
    // This creates the enemy's move animation.
    if (stateCounter == 100) {
      state = !state;
      stateCounter = 0;
    }
    ++stateCounter;

    // Enemy has a 50% chance of moving left or right.
    double rx = Math.random();
    if (rx < 0.5) {
      vx *= -1;
    }
    // Enemy has an 80% chance of moving down, and a 20% chance of moving up.
    double ry = Math.random();
    if (ry < 0.8 && vy < 0) {
      vy *= -1;
    } else if (ry >= 0.8 && vy > 0) {
      vy *= -1;
    }
    x += vx;
    y += vy;

    ArrayList<UpdateAction> enemiesToUpdate = new ArrayList<>();
    detectWallCollision(game);
    if (y + h > game.getRenderer().getHeight() && vy > 0) {
      enemiesToUpdate.add(new RemoveObjectUpdateAction(this));
    }
    return enemiesToUpdate;
  }

  /**
   * Handle a vertical wall collision.
   *
   * @param game The game the object is in.
   * @param top Whether or not the collision is on the top wall.
   */
  @Override
  public void collideVertical(Game game, boolean top) {
    if (!top) {
      game.getStatistics().upsert(GameShmup.KEY_SHMUP_LIFE, n -> n - 1, 3);
    }
  }

  /** Update the statistics counting destructions. */
  @Override
  public void updateDestructionStats(Game game) {
    game.getStatistics().upsert(GameShmup.KEY_ENEMIES_KILLED, n -> n + 1, 1);
  }

  /** @return The enemy sprite. */
  abstract int getSprite();

  @Override
  public void render(Game game) {
    int id = getSprite();
    Renderer renderer = game.getRenderer();
    renderer.renderDrawable(x, y, id);
    w = renderer.getDrawableWidth(id);
    h = renderer.getDrawableHeight(id);
  }
}
