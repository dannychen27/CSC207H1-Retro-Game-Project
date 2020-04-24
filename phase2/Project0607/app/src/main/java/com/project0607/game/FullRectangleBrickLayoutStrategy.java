package com.project0607.game;

import com.project0607.game.object.Brick;
import com.project0607.game.object.GameObject;

import java.util.List;

public class FullRectangleBrickLayoutStrategy implements BrickLayoutStrategy {

  /**
   * The number of rows of bricks to set up.
   *
   * Representation Invariant: 0 <= numRowsOfBricks <= 10.
   * (10 is the maximum number of rows of bricks that still gives the
   * Breakout ball enough vertical space to move)
   */
  private int numRowsOfBricks;

  /**
   * Create a new full rectangle brick layout strategy.
   *
   * @param numRows The number of rows of bricks.
   */
  FullRectangleBrickLayoutStrategy(int numRows) {
    numRowsOfBricks = numRows;
  }

  @Override
  public void setBricks(List<GameObject> gameObjects) {
    final int initialX = 25;
    final int horizontalSpacing = 75;
    final int initialY = 300;
    final int verticalSpacing = 150;
    int bricksPerRow = 7;
    for (int y = initialY;
        y < horizontalSpacing * numRowsOfBricks + initialY;
        y += horizontalSpacing) {
      for (int x = initialX; x <= verticalSpacing * bricksPerRow; x += verticalSpacing) {
        gameObjects.add(new Brick(x, y, 1));
      }
    }
  }
}
