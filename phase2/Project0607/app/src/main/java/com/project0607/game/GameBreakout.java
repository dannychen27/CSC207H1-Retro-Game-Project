package com.project0607.game;

import com.project0607.GlobalSession;
import com.project0607.activity.game.Game;
import com.project0607.activity.game.GameView;
import com.project0607.game.object.BreakoutBall;
import com.project0607.game.object.Brick;
import com.project0607.game.object.GameOverDisplay;
import com.project0607.game.object.LevelDisplay;
import com.project0607.game.object.LifeDisplay;
import com.project0607.game.object.Pause;
import com.project0607.game.object.PlayerPaddle;
import com.project0607.game.object.StatisticsDisplay;
import com.project0607.util.TypedKey;
import com.project0607.util.TypedMap;

/** A game of Breakout. */
public class GameBreakout extends Game {

  /** Various local statistics for Breakout. */
  public static final TypedKey<Integer> KEY_BRICKS_DESTROYED =
      new TypedKey<>("breakout.bricks_destroyed");
  public static TypedKey<Integer> KEY_HIGHEST_LEVEL_REACHED =
      new TypedKey<>("breakout.highest_level_reached");
  private static TypedKey<Integer> KEY_BREAKOUT_LIFE =
      new TypedKey<>("breakout.breakout_life");
  private static TypedKey<Integer> KEY_CURRENT_LEVEL_REACHED =
      new TypedKey<>("breakout.current_level_reached");

  /** The ball used for Breakout. */
  private BreakoutBall ball;

  /** The player paddle used for Breakout. */
  private PlayerPaddle paddle;

  /**
   * Create a new GameBreakout object.
   *
   * @param view The view.
   * @param session The game session.
   */
  GameBreakout(GlobalSession session, GameView view) {
    super(session, view);
    startNewGame();
  }

  @Override
  public void update() {
    super.update();
    if (gameObjects.stream().noneMatch(x -> x instanceof Brick)) {
      onWin();
    }
  }

  /** Store this game session's Breakout statistics. */
  @Override
  public void saveStatistics() {
    TypedMap globalStats = session.getStatistics();
    int bricksDestroyed = statistics.getOrDefault(KEY_BRICKS_DESTROYED, 0);
    globalStats.upsert(KEY_BRICKS_DESTROYED, x -> x + bricksDestroyed, bricksDestroyed);
    int levelReached = statistics.get(KEY_CURRENT_LEVEL_REACHED);
    globalStats.upsert(KEY_HIGHEST_LEVEL_REACHED, old -> Math.max(old, levelReached), levelReached);
  }

  @Override
  public void onWin() {
    statistics.update(GameBreakout.KEY_CURRENT_LEVEL_REACHED, x -> x + 1);
    setUpLevel();
  }

  @Override
  public void onLose() {
    statistics.upsert(GameBreakout.KEY_BREAKOUT_LIFE, n -> n - 1, 3);
    ball.setLocation(300, 1500);
    paddle.setLocation(300, 1600);
    paddle.onTouch(this, 300, 0);
    if (getStatistics().get(KEY_BREAKOUT_LIFE) == 0) {
      gameOver();
    }
  }

  /** Queue the game over screen with a lonely player paddle. */
  private void gameOver() {
    gameObjects.clear();
    GameOverDisplay gameOverDisplay = new GameOverDisplay(getView().getWidth() / 2, getView().getHeight() / 3);
    addGameObject(gameOverDisplay);
  }

  /** Initializes the game when it first starts up. */
  private void startNewGame() {
    statistics.put(KEY_CURRENT_LEVEL_REACHED, 1);
    setDisplays();
    setInitialLives(3);
    setBall();
    setPlayerPaddle();
    setPause();
    setUpLevel();
  }

  /** Initializes a new stage. */
  private void setUpLevel() {
    if (getStatistics().get(KEY_BREAKOUT_LIFE) == 0) {
      gameOver();
    } else {
      ball.setLocation(300, 1500);

      paddle.setLocation(300, 1600);
      paddle.setVelocity(0, 0);
      paddle.onTouch(this, 300, 0);

      int levelReached = statistics.get(KEY_CURRENT_LEVEL_REACHED);
      if (levelReached % 2 == 0) {
        setBricks(new DefaultBrickLayoutStrategy(Math.min(levelReached, 10)));
      } else {
        setBricks(new FullRectangleBrickLayoutStrategy(Math.min(levelReached, 10)));
      }
    }
  }

  /** Set the ball for Breakout. */
  private void setBall() {
    boolean isFast = session.getConfiguration().getOrDefault(Game.FAST_BALL, false);
    int speed = isFast ? 6 : 3;
    ball = new BreakoutBall(300, 1500, -speed, -speed);
    addGameObject(ball);
  }

  /** Set the player paddle for Breakout. */
  private void setPlayerPaddle() {
    paddle = new PlayerPaddle(300, 1600, 0);
    addGameObject(paddle);
  }

  /** Set the bricks for Breakout. */
  private void setBricks(BrickLayoutStrategy brickLayoutStrategy) {
    brickLayoutStrategy.setBricks(gameObjects);
  }

  /** Set the statistics and life displays for Breakout. */
  private void setDisplays() {
    addGameObject(new StatisticsDisplay(550, 100, GameBreakout.KEY_BRICKS_DESTROYED));
    addGameObject(new LifeDisplay(1000, 100, GameBreakout.KEY_BREAKOUT_LIFE));
    addGameObject(new LevelDisplay(1000, 200, GameBreakout.KEY_CURRENT_LEVEL_REACHED));
  }

  /** Set the initial number of lives for Breakout. */
  @SuppressWarnings("SameParameterValue")
  private void setInitialLives(int initialLives) {
    statistics.put(KEY_BREAKOUT_LIFE, initialLives);
  }

  /** Set a pause display for Pong. */
  private void setPause() {
    addGameObject(new Pause(50, 100));
  }
}
