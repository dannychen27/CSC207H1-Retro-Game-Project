package com.project0607.game.object;

import com.project0607.R;
import com.project0607.game.Game;
import com.project0607.game.action.update.UpdateAction;
import com.project0607.render.Renderer;

import java.util.Collections;
import java.util.List;

public class Pause extends GameObject{

    /**
     * Create a new Pause object.
     *
     * @param x This pause object's horizontal coordinate.
     * @param y This brick object's vertical coordinate.
     */
    public Pause(int x, int y) {
        super(x, y, 0, 0);
    }

    /**
     * Update this pause object based on the game's current state.
     *
     * @param game The game this pause object belongs to.
     * @return Actions to take after this pause object is updated.
     */
    @Override
    public List<? extends UpdateAction> update(Game game) {
        return Collections.emptyList();
    }

    /**
     * Render the pause object sprite on screen.
     *
     * @param game The renderer.
     */
    @Override
    public void render(Game game) {
        int id = R.drawable.sp_pause;
        Renderer renderer = game.getRenderer();
        renderer.renderDrawable(x, y, id);
        w = renderer.getDrawableWidth(id);
        h = renderer.getDrawableHeight(id);
    }
}


