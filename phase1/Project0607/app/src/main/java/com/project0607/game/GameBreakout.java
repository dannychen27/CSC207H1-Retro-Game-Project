package com.project0607.game;

import com.project0607.GlobalSession;
import com.project0607.game.object.BreakoutBall;
import com.project0607.game.object.Brick;
import com.project0607.game.ui.LifeDisplay;
import com.project0607.game.object.Player;
import com.project0607.game.ui.StatisticsDisplay;
import com.project0607.render.Renderer;
import com.project0607.util.TypedKey;
import com.project0607.util.TypedMap;

/** A breakout game. */
public class GameBreakout extends Game {

  /** Various local statistics for Breakout. */
  public static final TypedKey<Integer> KEY_BRICKS_DESTROYED = new TypedKey<>("breakout.bricks_destroyed");
  public static TypedKey<Integer> KEY_BREAKOUT_LIFE = new TypedKey<>("breakout.breakout_life");

  /** Create a new GameBreakout object. */
  public GameBreakout(GlobalSession session, Renderer renderer) {
    super(session, renderer);
    boolean isFast = session.getConfiguration().getOrDefault(Game.FAST_BALL, false);
    int speed = isFast ? 6 : 3;
    this.setUpBreakoutGame(speed);
  }

  private void setUpBreakoutGame(int speed) {
    gameObjects.add(new BreakoutBall(300, 1500, -speed, -speed));

    gameObjects.add(new Player(300, 1600, 0));

    gameObjects.add(new Brick(200, 200));
    gameObjects.add(new Brick(400, 200));
    gameObjects.add(new Brick(600, 200));
    gameObjects.add(new Brick(100, 400));
    gameObjects.add(new Brick(300, 400));
    gameObjects.add(new Brick(500, 400));
    gameObjects.add(new Brick(700, 400));

    renderables.add(new StatisticsDisplay(530, 100, GameBreakout.KEY_BRICKS_DESTROYED));
    renderables.add(new LifeDisplay(1000, 100, GameBreakout.KEY_BREAKOUT_LIFE));

    getStatistics().put(KEY_BREAKOUT_LIFE, 3);
  }

  @Override
  public void saveStatistics() {
    TypedMap globalStats = session.getStatistics();

    int bricksDestroyed = statistics.getOrDefault(KEY_BRICKS_DESTROYED, 0);
    globalStats.upsert(KEY_BRICKS_DESTROYED, x -> x + bricksDestroyed, bricksDestroyed);
  }
}
