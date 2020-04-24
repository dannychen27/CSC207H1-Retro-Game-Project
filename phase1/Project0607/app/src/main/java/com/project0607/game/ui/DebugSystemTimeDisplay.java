package com.project0607.game.ui;

import com.project0607.game.Game;

public class DebugSystemTimeDisplay extends UIElement {

  /**
   * Construct a new system time display.
   *
   * @param x The horizontal coordinate of this system time display.
   * @param y The vertical coordinate of this system time display.
   */
  public DebugSystemTimeDisplay(int x, int y) {
    super(x, y);
  }

  /**
   * Render the system time display on screen.
   *
   * @param game The renderer.
   */
  @Override
  public void render(Game game) {
    game.getRenderer().renderText(x, y, System.currentTimeMillis() + "ms");
  }
}
