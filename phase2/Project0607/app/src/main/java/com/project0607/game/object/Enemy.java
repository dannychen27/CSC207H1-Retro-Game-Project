package com.project0607.game.object;

import com.project0607.activity.game.Game;
import com.project0607.game.GameShootEmUp;
import com.project0607.util.Direction;

/**
 * A generic enemy that can move downwards towards the player shooter in a zig zag
 * pattern in Shoot 'Em Up, and can be destroyed by bullets.
 */
public abstract class Enemy extends GameObject {

  /** This enemy's sprite state. */
  boolean state = false;

  /** The number of lives this enemy has. */
  private int life;

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
  Enemy(int x, int y, int vx, int vy, int life) {
    super(x, y, vx, vy);
    this.life = life;
  }

  /**
   * Update this enemy based on the game's current state.
   *
   * @param game The game this enemy belongs to.
   */
  @Override
  public void update(Game game) {
    // This creates the enemy's move animation.
    stateCounter = (stateCounter == 100) ? 0 : stateCounter + 1;

    // Enemy has a 50% chance of moving left or right.
    double tryMoveHorizontally = Math.random();
    if (tryMoveHorizontally < 0.5) {
      vx *= -1;
    }

    // Enemy has an 80% chance of moving down, and a 20% chance of moving up.
    double tryMoveVertically = Math.random();
    if (tryMoveVertically < 0.8 && vy < 0) {
      vy *= -1;
    } else if (tryMoveVertically >= 0.8 && vy > 0) {
      vy *= -1;
    }

    x += vx;
    y += vy;
  }

  @Override
  public void collide(Game game, GameObject other, boolean collideX, boolean collideY) {
    if (other.isDestructive()) {
      life--;
      if (life == 0) {
        onDeath(game);
        game.getStatistics().upsert(GameShootEmUp.KEY_TOTAL_ENEMIES_KILLED, n -> n + 1, 1);
        game.removeGameObject(this);
      }
    }
  }

  @Override
  public void collideWall(Game game, Direction direction) {
    collideWallByBouncing(game, direction);
    if (direction == Direction.DOWN) {
      game.onLose();
      game.removeGameObject(this);
    }
  }

  /**
   * Make this enemy do something upon its life reaching 0.
   *
   * @param game The game this enemy belongs to.
   */
  protected abstract void onDeath(Game game);
}
