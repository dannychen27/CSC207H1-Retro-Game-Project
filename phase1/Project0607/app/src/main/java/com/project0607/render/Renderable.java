package com.project0607.render;

import com.project0607.game.Game;

public interface Renderable {

    /**
     * Render this renderable on the Android screen.
     *
     * @param game The game session this renderable is in.
     */
    void render(Game game);
}
