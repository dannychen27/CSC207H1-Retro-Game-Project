package com.project0607.game;

import com.project0607.GlobalSession;
import com.project0607.activity.game.Game;
import com.project0607.activity.game.GameView;
import com.project0607.game.object.CrabEnemy;
import com.project0607.game.object.Enemy;
import com.project0607.game.object.GameOverDisplay;
import com.project0607.game.object.LevelDisplay;
import com.project0607.game.object.LifeDisplay;
import com.project0607.game.object.OctopusEnemy;
import com.project0607.game.object.Pause;
import com.project0607.game.object.PlayerShooter;
import com.project0607.game.object.SquidEnemy;
import com.project0607.game.object.StatisticsDisplay;
import com.project0607.game.object.UfoEnemy;
import com.project0607.util.TypedKey;
import com.project0607.util.TypedMap;

import java.util.Random;

/** A game of Shoot 'Em Up. */
public class GameShootEmUp extends Game {

  /** Various local statistics for Shoot 'Em Up. */
  public static final TypedKey<Integer> KEY_CRAB_ENEMIES_KILLED =
      new TypedKey<>("shmup.crab_enemies_killed");
  public static final TypedKey<Integer> KEY_OCTOPUS_ENEMIES_KILLED =
      new TypedKey<>("shmup.octopus_enemies_killed");
  public static final TypedKey<Integer> KEY_SQUID_ENEMIES_KILLED =
      new TypedKey<>("shmup.squid_enemies_killed");
  public static final TypedKey<Integer> KEY_UFO_ENEMIES_KILLED =
      new TypedKey<>("shmup.ufo_enemies_killed");
  public static final TypedKey<Integer> KEY_TOTAL_ENEMIES_KILLED =
      new TypedKey<>("shmup.total_enemies_killed");
  private static final TypedKey<Integer> KEY_SHMUP_LIFE =
          new TypedKey<>("shmup.life");
  public static TypedKey<Integer> KEY_HIGHEST_LEVEL_REACHED =
      new TypedKey<>("shmup.highest_level_reached");
  private static TypedKey<Integer> KEY_CURRENT_LEVEL_REACHED =
      new TypedKey<>("shmup.current_level_reached");

  /** The player shooter for Shoot 'Em Up. */
  private PlayerShooter player;

  /** Create a new GameShootEmUp object. */
  GameShootEmUp(GlobalSession session, GameView view) {
    super(session, view);
    startNewGame();
  }

  @Override
  public void update() {
    super.update();
    if (gameObjects.stream().noneMatch(x -> x instanceof Enemy)) {
      onWin();
    }
  }

  @Override
  public void saveStatistics() {
    TypedMap globalStats = session.getStatistics();

    int crabEnemiesKilled = statistics.getOrDefault(GameShootEmUp.KEY_CRAB_ENEMIES_KILLED, 0);
    globalStats.upsert(
        GameShootEmUp.KEY_CRAB_ENEMIES_KILLED, x -> x + crabEnemiesKilled, crabEnemiesKilled);

    int octopusEnemiesKilled = statistics.getOrDefault(GameShootEmUp.KEY_OCTOPUS_ENEMIES_KILLED, 0);
    globalStats.upsert(
        GameShootEmUp.KEY_OCTOPUS_ENEMIES_KILLED, x -> x + octopusEnemiesKilled, octopusEnemiesKilled);

    int squidEnemiesKilled = statistics.getOrDefault(GameShootEmUp.KEY_SQUID_ENEMIES_KILLED, 0);
    globalStats.upsert(
        GameShootEmUp.KEY_SQUID_ENEMIES_KILLED, x -> x + squidEnemiesKilled, squidEnemiesKilled);

    int ufoEnemiesKilled = statistics.getOrDefault(GameShootEmUp.KEY_UFO_ENEMIES_KILLED, 0);
    globalStats.upsert(
        GameShootEmUp.KEY_UFO_ENEMIES_KILLED, x -> x + ufoEnemiesKilled, ufoEnemiesKilled);

    int enemiesKilled = statistics.getOrDefault(GameShootEmUp.KEY_TOTAL_ENEMIES_KILLED, 0);
    globalStats.upsert(GameShootEmUp.KEY_TOTAL_ENEMIES_KILLED, x -> x + enemiesKilled, enemiesKilled);

    int levelReached = statistics.get(KEY_CURRENT_LEVEL_REACHED);
    globalStats.upsert(
        GameShootEmUp.KEY_HIGHEST_LEVEL_REACHED, old -> Math.max(old, levelReached), levelReached);
  }

  @Override
  public void onLose() {
    getStatistics().upsert(KEY_SHMUP_LIFE, n -> n - 1, 3);
    if (getStatistics().get(KEY_SHMUP_LIFE) == 0) {
      gameOver();
    }
  }

  /** Queue the game over screen with a lonely player paddle. */
  private void gameOver() {
    gameObjects.clear();
    GameOverDisplay gameOverDisplay = new GameOverDisplay(getView().getWidth() / 2, getView().getHeight() / 3);
    addGameObject(gameOverDisplay);
  }

  @Override
  public void onWin() {
    getStatistics().update(GameShootEmUp.KEY_CURRENT_LEVEL_REACHED, n -> n + 1);
    setUpLevel();
  }

  /** Set up the game objects and UI elements for Shmup. */
  private void startNewGame() {
    placeGameObjects();
    setUpLevel();
  }

  /** Place all necessary game objects on screen. */
  private void placeGameObjects() {
    getStatistics().put(KEY_CURRENT_LEVEL_REACHED, 1);
    setInitialLives(3);
    setDisplays();
    setShooters();
    setPauseButton();
  }

  /** Set up the ball and paddles for Pong.*/
  private void setUpLevel() {
    if (getStatistics().get(KEY_SHMUP_LIFE) == 0) {
      gameOver();
    } else {
      boolean moreEnemies = session.getConfiguration().getOrDefault(Game.MORE_ENEMIES, false);
      spawnEnemies(moreEnemies);
    }
  }

  /** Set the initial number of lives for Shmup. */
  @SuppressWarnings("SameParameterValue")
  private void setInitialLives(int initialLives) {
    getStatistics().put(KEY_SHMUP_LIFE, initialLives);
  }

  /** Set a pause display for Pong. */
  private void setPauseButton() {
    addGameObject(new Pause(50, 100));
  }

  /** Get the player shooter for Shmup. */
  private void setShooters() {
    player = new PlayerShooter(300, 1500, 0);
    addGameObject(player);
  }

  /** Get the statistics and life displays for Shmup. */
  private void setDisplays() {
    addGameObject(new StatisticsDisplay(530, 100, GameShootEmUp.KEY_TOTAL_ENEMIES_KILLED));
    addGameObject(new LifeDisplay(1000, 100, GameShootEmUp.KEY_SHMUP_LIFE));
    addGameObject(new LevelDisplay(1000, 200, GameShootEmUp.KEY_CURRENT_LEVEL_REACHED));
    getStatistics().put(KEY_CURRENT_LEVEL_REACHED, 1);
  }

  /**
   * Spawn a group of enemies for Shmup based on whether the "more enemies" setting is enabled.
   *
   * @param moreEnemies Whether the setting to generate more enemies is enabled.
   */
  private void spawnEnemies(boolean moreEnemies) {
    for (int i = 0; i < getStatistics().get(KEY_CURRENT_LEVEL_REACHED); i++) {
      randomlyGenerateEnemies();
    }
    if (moreEnemies) {
      generateMoreEnemies();
    }
  }

  /** Generate more enemies for Shmup. */
  private void generateMoreEnemies() {
    for (int i = 0; i < getStatistics().get(KEY_CURRENT_LEVEL_REACHED); i++) {
      randomlyGenerateEnemies();
    }
  }

  /** Randomly generate enemies. */
  private void randomlyGenerateEnemies() {
    double choice = Math.random();
    choice = choice * 4;
    if (3 < choice && choice <= 4) {
      generateSquidEnemies(1);
    } else if (2 < choice && choice <= 3) {
      generateCrabEnemies(1);
    } else if (1 < choice && choice <= 2) {
      generateOctopusEnemies(1);
    } else {
      generateUfoEnemies(1);
    }
  }

  /**
   * Generate a collection of squid enemies for Shmup.
   *
   * @param numSquids The number of squids to generate.
   */
  @SuppressWarnings("SameParameterValue")
  private void generateSquidEnemies(int numSquids) {
    for (int i = 0; i < numSquids; i++) {
      int randomXPosition = (new Random()).nextInt(getView().getWidth());
      int randomYPosition = (new Random()).nextInt(getView().getHeight() / 3);
      addGameObject(new SquidEnemy(randomXPosition, randomYPosition));
    }
  }

  /**
   * Generate a collection of crab enemies for Shmup.
   *
   * @param numCrabs The number of crabs to generate.
   */
  @SuppressWarnings("SameParameterValue")
  private void generateCrabEnemies(int numCrabs) {
    for (int i = 0; i < numCrabs; i++) {
      int randomXPosition = (new Random()).nextInt(getView().getWidth());
      int randomYPosition = (new Random()).nextInt(getView().getHeight() / 3);
      addGameObject(new CrabEnemy(randomXPosition, randomYPosition));
    }
  }

  /**
   * Generate a collection of octopus enemies for Shmup.
   *
   * @param numOctopuses The number of octopus to generate.
   */
  @SuppressWarnings("SameParameterValue")
  private void generateOctopusEnemies(int numOctopuses) {
    for (int i = 0; i < numOctopuses; i++) {
      int randomXPosition = (new Random()).nextInt(getView().getWidth());
      int randomYPosition = (new Random()).nextInt(getView().getHeight() / 3);
      addGameObject(new OctopusEnemy(randomXPosition, randomYPosition));
    }
  }

  /**
   * Generate a collection of UFO enemies for Shmup.
   *
   * @param numUfos The number of octopus to generate.
   */
  @SuppressWarnings("SameParameterValue")
  private void generateUfoEnemies(int numUfos) {
    for (int i = 0; i < numUfos; i++) {
      int randomXPosition = (new Random()).nextInt(getView().getWidth());
      int randomYPosition = (new Random()).nextInt(getView().getHeight() / 3);
      addGameObject(new UfoEnemy(randomXPosition, randomYPosition));
    }
  }
}
