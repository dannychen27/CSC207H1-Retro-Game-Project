package com.project0607.game;

import com.project0607.GlobalSession;
import com.project0607.game.ui.LifeDisplay;
import com.project0607.game.object.SquidEnemy;
import com.project0607.game.object.CrabEnemy;
import com.project0607.game.object.OctopusEnemy;
import com.project0607.game.ui.StatisticsDisplay;
import com.project0607.game.object.UfoEnemy;
import com.project0607.game.object.PlayerShooter;
import com.project0607.render.Renderer;
import com.project0607.util.TypedKey;
import com.project0607.util.TypedMap;

/** A shoot 'em up game. */
public class GameShmup extends Game {

  /** Various local statistics for Shoot 'Em Up. */
  public static final TypedKey<Integer> KEY_ENEMIES_KILLED = new TypedKey<>("shmup.enemies_killed");
  public static TypedKey<Integer> KEY_SHMUP_LIFE = new TypedKey<>("shmup.shmup_life");

  /** Create a new GameShmup object. */
  GameShmup(GlobalSession session, Renderer renderer) {
    super(session, renderer);
    this.setUpShmupGame();
  }

  private void setUpShmupGame() {
    // Start player off near the bottom when playing Brick Breaker.
    gameObjects.add(new PlayerShooter(300, 1500, 0));

    // Initialize enemies, depending on whether the player wants more enemies or not.
    boolean moreEnemies = session.getConfiguration().getOrDefault(Game.MORE_ENEMIES, false);
    if (moreEnemies) {
      gameObjects.add(new SquidEnemy(200, 150));
      gameObjects.add(new SquidEnemy(350, 150));
      gameObjects.add(new SquidEnemy(500, 150));
      gameObjects.add(new SquidEnemy(650, 150));
      gameObjects.add(new CrabEnemy(150, 200));
      gameObjects.add(new CrabEnemy(300, 500));
      gameObjects.add(new OctopusEnemy(450, 600));
      gameObjects.add(new UfoEnemy(600, 800));
    }
    gameObjects.add(new SquidEnemy(150, 150));
    gameObjects.add(new SquidEnemy(300, 150));
    gameObjects.add(new SquidEnemy(450, 150));
    gameObjects.add(new SquidEnemy(600, 150));
    gameObjects.add(new CrabEnemy(750, 150));
    gameObjects.add(new CrabEnemy(150, 300));
    gameObjects.add(new CrabEnemy(300, 300));
    gameObjects.add(new OctopusEnemy(450, 300));
    gameObjects.add(new OctopusEnemy(600, 300));
    gameObjects.add(new UfoEnemy(750, 300));

    renderables.add(new StatisticsDisplay(530, 100, GameShmup.KEY_ENEMIES_KILLED));
    renderables.add(new LifeDisplay(1000, 100, GameShmup.KEY_SHMUP_LIFE));

    getStatistics().put(KEY_SHMUP_LIFE, 3);
  }

  @Override
  public void saveStatistics() {
    TypedMap globalStats = session.getStatistics();

    int enemiesKilled = statistics.getOrDefault(KEY_ENEMIES_KILLED, 0);
    globalStats.upsert(KEY_ENEMIES_KILLED, x -> x + enemiesKilled, enemiesKilled);
  }
}