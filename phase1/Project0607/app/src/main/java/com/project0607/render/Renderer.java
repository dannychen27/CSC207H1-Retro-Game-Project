package com.project0607.render;

/**
 * Represents a renderer for game objects to use to display themselves. Specific implementations of
 * a renderer will decide what the sprite is from the sprite name.
 */
public interface Renderer {

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
   * Render a text sprite on the Android screen using its id.
   *
   * @param x The horizontal position of this text sprite.
   * @param y The vertical position of this text sprite.
   * @param id The id of the text sprite.
   */
  void renderTextById(int x, int y, int id);

  /**
   * Render a text sprite on the Android screen using its name.
   *
   * @param x The horizontal position of this text sprite.
   * @param y The vertical position of this text sprite.
   * @param spriteName The name of this text sprite.
   */
  void renderTextByName(int x, int y, String spriteName);

  /**
   * Render text on the Android screen.
   *
   * @param x The horizontal position of the text to be displayed.
   * @param y The vertical position of the text to be displayed.
   * @param text The text from renderTextByName.
   */
  void renderText(int x, int y, String text);
}
