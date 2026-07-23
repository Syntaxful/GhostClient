package ghostclient.module.modules.player;

import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Prevents arm swing when mining.
 */
public class SilentMine extends Module {

    public SilentMine() {
        super(Category.Player, "SilentMine", "Mine without swinging your arm.");
    }
}
