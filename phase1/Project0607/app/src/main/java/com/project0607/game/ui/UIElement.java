package com.project0607.game.ui;

import com.project0607.render.Renderable;

public abstract class UIElement implements Renderable {

    /** The horizontal position of the UI element. */
    public int x;

    /** The vertical position of the UI element. */
    public int y;

    /**
     * Create a new UI element.
     *
     * @param x The horizontal coordinate of this UI element.
     * @param y The vertical coordinate of this UI element.
     */
    UIElement(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
