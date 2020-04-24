package com.project0607.game.ui;

import com.project0607.game.Game;
import com.project0607.util.TypedKey;

import java.util.Map;

public class DebugStatisticsDisplay extends UIElement {

  /**
   * Construct a new statistics display debugger.
   *
   * @param x The horizontal coordinate of this statistics display debugger.
   * @param y The vertical coordinate of this statistics display debugger.
   */
  public DebugStatisticsDisplay(int x, int y) {
    super(x, y);
  }

  /**
   * Render the statistics display debugger on screen.
   *
   * @param game The renderer.
   */
  @Override
  public void render(Game game) {
    StringBuilder str = new StringBuilder();
    for (Map.Entry<TypedKey<?>, Object> entry : game.getStatistics().entrySet()) {
      str.append(entry.getKey().toString());
      str.append(": ");
      str.append(entry.getValue());
      str.append("\n");
    }
    game.getRenderer().renderText(x, y, str.toString());
  }
}
