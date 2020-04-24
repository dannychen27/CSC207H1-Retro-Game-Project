package com.project0607.render;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.project0607.R;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

/** A renderer which renders text onto a view. */
public class AndroidViewRenderer implements Renderer {

  /** The context this render r is in. */
  private final Context context;

  /** The view this renderer is in. */
  private final ConstraintLayout view;

  /** An array that maps digits to sprites. */
  private static final int[] digitsToSprites = new int[] {
          R.drawable.sp_num_zero, R.drawable.sp_num_one,
          R.drawable.sp_num_two, R.drawable.sp_num_three,
          R.drawable.sp_num_four, R.drawable.sp_num_five,
          R.drawable.sp_num_six, R.drawable.sp_num_seven,
          R.drawable.sp_num_eight, R.drawable.sp_num_nine
  };

  /**
   * Construct a renderer for an ConstraintLayout view.
   *
   * @param context The context for this renderer.
   * @param view The view where text views will be added.
   */
  public AndroidViewRenderer(Context context, ConstraintLayout view) {
    this.context = context;
    this.view = view;
  }

  /**
   * @return The string representation of this sprite.
   *
   * @param context The context.
   * @param name The name of this sprite -- e.g. res/values/text_sprites.xml.
   */
  private static String getStringValueByName(Context context, String name) {
    Resources res = context.getResources();
    return res.getString(res.getIdentifier(name, "string", context.getPackageName()));
  }

  @Override
  public int getWidth() {
    return view.getWidth();
  }

  @Override
  public int getHeight() {
    return view.getHeight();
  }

  @Override
  public int getDrawableWidth(int id) {
    return ContextCompat.getDrawable(context, id).getIntrinsicWidth();
  }

  @Override
  public int getDrawableHeight(int id) {
    return ContextCompat.getDrawable(context, id).getIntrinsicHeight();
  }

  @Override
  public void renderDrawable(int x, int y, int id) {
    ImageView imageView = new ImageView(context);
    view.addView(imageView);
    Drawable drawable = ContextCompat.getDrawable(context, id);
    imageView.setImageDrawable(drawable);
    imageView.setX(x);
    imageView.setY(y);
  }

  /**
   * Display a digit on screen.
   *
   * @param x The horizontal position of this text sprite.
   * @param y The vertical position of this text sprite.
   * @param d The digit to render.
   *
   * Precondition: d is a digit between 0 and 9.
   */
  public void renderDigit(int x, int y, int d) {
    renderDrawable(x, y, digitsToSprites[d]);
  }

  @Override
  public void renderTextById(int x, int y, int id) {
    Resources res = context.getResources();
    TextView textView = new TextView(context);
    view.addView(textView);
    textView.setText(res.getString(id));
    textView.setX(x);
    textView.setY(y);
  }

  @Override
  public void renderTextByName(int x, int y, String spriteName) {
    TextView textView = new TextView(context);
    view.addView(textView);
    textView.setText(getStringValueByName(context, spriteName));
    textView.setX(x);
    textView.setY(y);
  }

  @Override
  public void renderText(int x, int y, String text) {
    TextView textView = new TextView(context);
    textView.setTextColor(Color.WHITE);
    view.addView(textView);
    textView.setText(text);
    textView.setX(x);
    textView.setY(y);
  }
}
