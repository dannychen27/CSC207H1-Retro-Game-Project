package com.project0607.activity.game;

import com.project0607.GlobalSession;
import com.project0607.game.object.GameObject;
import com.project0607.util.Direction;
import com.project0607.util.TypedKey;
import com.project0607.util.TypedMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A Game class is responsible for managing a set of game objects on screen.
 * Responsibilities include rendering game objects, updating them upon player touches,
 * checking for collision detection, tracking game statistics, and handling win/loss
 * scenarios. This class is analogous to Game presenter with respect to the MVP
 * architecture.
 */
public abstract class Game implements Serializable {

  /** A set of configurations to customize this game. */
  public static final TypedKey<Boolean> FAST_PADDLE =
          new TypedKey<>("config.fast_paddle");
  public static final TypedKey<Boolean> FAST_BALL =
          new TypedKey<>("config.fast_ball");
  public static final TypedKey<Boolean> MORE_ENEMIES =
          new TypedKey<>("config.more_enemies");

  /** The view for this game. */
  private final GameView view;

  /** A list of all game objects used in this game. */
  protected final List<GameObject> gameObjects;

  /** The global session for this game. */
  protected final GlobalSession session;

  /** A dictionary of local (per-game) statistics for this game. */
  protected final TypedMap statistics;

  /**
   * Construct a new game.
   *
   * @param session The current game session.
   * @param view The view.
   */
  protected Game(GlobalSession session, GameView view) {
    this.session = session;
    this.view = view;
    this.statistics = new TypedMap();
    this.gameObjects = new CopyOnWriteArrayList<>();
  }

  /** @return The global session for this game. */
  public GlobalSession getSession() {
    return session;
  }

  /** @return The view for this game. */
  public GameView getView() {
    return view;
  }

  /** @return The local statistics for this game. */
  public TypedMap getStatistics() {
    return statistics;
  }

  /**
   * Assign this game object to this game.
   *
   * @param obj The game object to be added.
   */
  public void addGameObject(GameObject obj) {
    gameObjects.add(obj);
  }

  /**
   * Remove this game object from this game.
   *
   * @param obj The game object to be removed.
   */
  public void removeGameObject(GameObject obj) {
    gameObjects.remove(obj);
  }

  /** Render all game objects on the Android screen. */
  void render() {
    for (GameObject g : gameObjects) {
      g.render(this);
    }
  }

  /**
   * Have each game object react to a touch in a location.
   *
   * @param newX The object's new horizontal position.
   * @param newY The object's new vertical position.
   */
  void onTouch(int newX, int newY) {
    for (GameObject g : gameObjects) {
      g.onTouch(this, newX, newY);
    }
  }

  /** Update a list of GameObjects, which indicates which objects to add and remove. */
  protected void update() {
    List<GameObject> copy = new ArrayList<>(gameObjects);
    for (int i = 0; i < copy.size(); i++) {
      GameObject gameObject = copy.get(i);
      gameObject.update(this);
      detectWallCollision(gameObject);
      for (int j = i + 1; j < copy.size(); j++) {
        GameObject otherGameObject = copy.get(j);
        detectCollision(gameObject, otherGameObject);
      }
    }
  }

  /** Save this game's local statistics to the global statistics. */
  protected abstract void saveStatistics();

  /** Make this game do something on a loss. */
  public abstract void onLose();

  /** Make this game do something on a win. */
  public abstract void onWin();

  /**
   * Check if a game object collides with another game object on screen.
   *
   * @param first The first game object that collides with the second game object. * @param second
   * @param second The second game object that collides with the first game object.
   */
  private void detectCollision(GameObject first, GameObject second) {
    boolean collideX = collidesHorizontally(first, second) || collidesHorizontally(second, first);
    boolean collideY = collidesVertically(first, second) || collidesVertically(second, first);
    if (collideX || collideY) {
      first.collide(this, second, collideX, collideY);
      second.collide(this, first, collideX, collideY);
    }
  }

  /**
   * @return Whether two game objects collide vertically.
   * @param first The first game object.
   * @param second The second game object.
   */
  private boolean collidesVertically(GameObject first, GameObject second) {
    return (first.getY() + first.getH() + first.getVy() > second.getY())
        && (first.getY() + first.getVy() < second.getY() + second.getH())
        && (first.getX() + first.getW() > second.getX())
        && (first.getX() < second.getX() + second.getW());
  }

  /**
   * @return Whether two game objects collide horizontally.
   * @param first The first game object.
   * @param second The second game object.
   */
  private boolean collidesHorizontally(GameObject first, GameObject second) {
    return (first.getX() + first.getW() + first.getVx() > second.getX())
        && (first.getX() + first.getVx() < second.getX() + second.getW())
        && (first.getY() + first.getH() > second.getY())
        && (first.getY() < second.getY() + second.getH());
  }

  /**
   * Check to see if this game object collides into a wall.
   *
   * @param obj The game object to check.
   */
  private void detectWallCollision(GameObject obj) {
    if (obj.getX() < 0 && obj.getVx() < 0) {
      obj.collideWall(this, Direction.LEFT);
    } else if (obj.getX() + obj.getW() > getView().getWidth() && obj.getVx() > 0) {
      obj.collideWall(this, Direction.RIGHT);
    }

    if (obj.getY() < 0 && obj.getVy() < 0) {
      obj.collideWall(this, Direction.UP);
    } else if (obj.getY() + obj.getH() > getView().getHeight() && obj.getVy() > 0) {
      obj.collideWall(this, Direction.DOWN);
    }
  }
}
