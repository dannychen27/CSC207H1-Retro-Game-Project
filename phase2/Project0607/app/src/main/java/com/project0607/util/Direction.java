package com.project0607.util;

/** One of four directions. */
public enum Direction {
  UP,
  DOWN,
  LEFT,
  RIGHT;

  /** @return Whether this direction is horizontal. */
  public boolean isHorizontal() {
    return this == LEFT || this == RIGHT;
  }

  /** @return Whether this direction is vertical. */
  public boolean isVertical() {
    return this == UP || this == DOWN;
  }
}
