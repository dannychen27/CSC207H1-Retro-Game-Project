package com.project0607.game.object;

import com.project0607.R;
import com.project0607.game.Game;
import com.project0607.render.Renderer;

public class OctopusEnemy extends Enemy {
    /**
     * Create a new octopus enemy object.
     *
     * @param x This octopus's horizontal coordinate.
     * @param y This octopus's vertical coordinate.
     */
    public OctopusEnemy(int x, int y) {
        super(x, y, 2, 3);
        hard = true;
        destructible = true;
        life = 5;
    }

    /** @return The octopus sprite. */
    @Override
    int getSprite() {
        return state ? R.drawable.sp_enemy_octopus_1 : R.drawable.sp_enemy_octopus_0;
    }
}
