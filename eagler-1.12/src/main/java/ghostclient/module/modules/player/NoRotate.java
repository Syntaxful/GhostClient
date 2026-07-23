package ghostclient.module.modules.player;

import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Prevents server from rotating the player.
 */
public class NoRotate extends Module {

    public NoRotate() {
        super(Category.Player, "NoRotate", "Ignore server rotation packets.");
    }
}
