package com.project0607.activity.game;

/**
 * A GameView allows the game to display game objects on the Android screen,
 * given a sprite representation.
 */
public interface GameView {

  /** @return The width of the attached view (the screen). */
  int getWidth();

  /** @return The height of the attached view (the screen). */
  int getHeight();

  /** @return The rendered width of the Drawable. */
  int getDrawableWidth(int id);

  /** @return The rendered height of the Drawable. */
  int getDrawableHeight(int id);

  /**
   * Render a Drawable sprite on the Android screen using its id.
   *
   * @param x The horizontal position of this text sprite.
   * @param y The vertical position of this text sprite.
   * @param id The id of the text sprite.
   */
  void renderDrawable(int x, int y, int id);

  /**
   * Render a digit with the corresponding Drawable sprite.
   *
   * @param x The horizontal position of this text sprite.
   * @param y The vertical position of this text sprite.
   * @param d The digit to render.
   */
  void renderDigit(int x, int y, int d);

  /**
   * Render text on the Android screen.
   *
   * @param x The horizontal position of the text to be displayed.
   * @param y The vertical position of the text to be displayed.
   * @param text The text from renderTextByName.
   */
  void renderText(int x, int y, String text);
}
