package ghostclient.module.modules.player;

import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Increases block/entity reach.
 */
public class Reach extends Module {

    private final NumberValue reach = new NumberValue("Reach", "Extra reach", 3.5, 3.0, 6.0, 0.1);

    public Reach() {
        super(Category.Player, "Reach", "Increase reach distance.");
        addSetting(reach);
    }

    @Override
    public void onEnable() {
        // removed
    }

    @Override
    public void onDisable() {
        // removed
    }
}
