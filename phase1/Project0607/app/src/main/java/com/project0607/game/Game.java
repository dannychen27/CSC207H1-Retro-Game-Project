package com.project0607.game;

import com.project0607.GlobalSession;
import com.project0607.game.action.update.UpdateAction;
import com.project0607.game.object.GameObject;
import com.project0607.render.Renderable;
import com.project0607.render.Renderer;
import com.project0607.util.TypedKey;
import com.project0607.util.TypedMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Game implements Serializable {
  /** The global session for this game. */
  final GlobalSession session;

  /** The renderer for this game. */
  private final Renderer renderer;

  /** A dictionary of local (per-game) statistics for this game. */
  final TypedMap statistics;

  /** A list of all game objects used in this game. */
  final List<GameObject> gameObjects;

  /** A list of non-game objects to render. */
  List<Renderable> renderables;

  /** A set of configurations to customize this game. */
  public static final TypedKey<Boolean> FAST_PADDLE = new TypedKey<>("config.fast_paddle");
  public static final TypedKey<Boolean> FAST_BALL = new TypedKey<>("config.fast_ball");
  public static final TypedKey<Boolean> MORE_ENEMIES = new TypedKey<>("config.more_enemies");

  /**
   * Construct a new game.
   *
   * @param session The current game session.
   * @param renderer The renderer.
   */
  Game(GlobalSession session, Renderer renderer) {
    this.session = session;
    this.renderer = renderer;
    statistics = new TypedMap();
    gameObjects = new CopyOnWriteArrayList<>();
    renderables = new ArrayList<>();
  }

  /** @return The global session for this game. */
  public GlobalSession getSession() {
    return session;
  }

  /** @return The renderer for this game. */
  public Renderer getRenderer() {
    return renderer;
  }

  /** @return The local statistics for this game. */
  public TypedMap getStatistics() {
    return statistics;
  }

  /** @return A list of game objects in this game. */
  public List<GameObject> getGameObjects() {
    return gameObjects;
  }

  /** Update a list of GameObjects, which indicates which objects to add and remove. */
  public void updateGameObjects() {
    List<UpdateAction> actionsToUpdate = new ArrayList<>();
    for (GameObject thisGameObject : gameObjects) {
      List<? extends UpdateAction> actions = thisGameObject.update(this);
      actionsToUpdate.addAll(actions);

      // Only check for collisions between any two hard GameObjects.
      if (!thisGameObject.isSharp()) {
        continue;
      }
      this.detectCollisions(thisGameObject, actionsToUpdate);
    }

    for (UpdateAction action : actionsToUpdate) {
      action.updateGame(this);
    }
  }

  /**
   * Check if an object collides with another object on screen.
   *
   * @param thisGameObject The object that we claim collides with another object on screen.
   * @param actionsToUpdate A list of actions to update.
   */
  private void detectCollisions(GameObject thisGameObject, List<UpdateAction> actionsToUpdate) {
    for (GameObject otherGameObject : gameObjects) {
      if (thisGameObject == otherGameObject) {
        continue;
      }
      if (!otherGameObject.isHard()) {
        continue;
      }
      // Check if ball is headed for object in x-direction.
      boolean collideX = collidesHorizontally(thisGameObject, otherGameObject);

      // Check if ball is headed for object in y-direction.
      boolean collideY = collidesVertically(thisGameObject, otherGameObject);

      // Do something if the two objects collide.
      if (collideX || collideY) {
        actionsToUpdate.addAll(thisGameObject.collide(this, collideX, collideY));
        actionsToUpdate.addAll(otherGameObject.collide(this, collideX, collideY));
      }
    }
  }

  /**
   * @return Whether two game objects collide vertically.
   *
   * @param g The first game object.
   * @param h The second game object.
   */
  private boolean collidesVertically(GameObject g, GameObject h) {
    return (g.getY() + g.getH() + g.getVx() > h.getY()) &&
            (g.getY() + g.getVy() < h.getY() + h.getH()) &&
            (g.getX() + g.getW() > h.getX()) &&
            (g.getX() < h.getX() + h.getW());
  }

  /**
   * @return Whether two game objects collide horizontally.
   *
   * @param g The first game object.
   * @param h The second game object.
   */
  private boolean collidesHorizontally(GameObject g, GameObject h) {
    return (g.getX() + g.getW() + g.getVx() > h.getX()) &&
            (g.getX() + g.getVx() < h.getX() + h.getW()) &&
            (g.getY() + g.getH() > h.getY()) &&
            (g.getY() < h.getY() + h.getH());
  }

  /** Render all game objects on the Android screen. */
  public void renderGameObjects() {
    for (GameObject g : gameObjects) {
      g.render(this);
    }

    for (Renderable r : renderables) {
      r.render(this);
    }
  }

  /**
   * Have each game object react to a touch in a location.
   *
   * @param newX The object's new horizontal position.
   * @param newY The object's new vertical position.
   */
  public void onTouch(int newX, int newY) {
    for (GameObject g : gameObjects) {
      g.onTouch(this, newX, newY);
    }
  }

  /**
   * Have each game object react to a touch-release in a location.
   *
   * @param newX The object's new horizontal position.
   * @param newY The object's new vertical position.
   */
  public void onTouchRelease(int newX, int newY) {
    for (GameObject g : gameObjects) {
      g.onTouchRelease(this, newX, newY);
    }
  }

  /**
   * Have each game object react to a moving touch in a location.
   *
   * @param newX The object's new horizontal position.
   * @param newY The object's new vertical position.
   */
  public void onTouchMove(int newX, int newY) {
    for (GameObject g : gameObjects) {
      g.onTouchMove(this, newX, newY);
    }
  }

  /** Save the game's statistics to the global statistics. */
  public abstract void saveStatistics();
}
