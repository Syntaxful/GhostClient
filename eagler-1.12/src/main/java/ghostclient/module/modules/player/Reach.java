package ghostclient.module.modules.player;

import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.BooleanValue;
import ghostclient.setting.NumberValue;

/**
 * Increases block/entity reach.
 */
public class Reach extends Module {

    private final NumberValue reach = new NumberValue("Reach", "Extra reach distance", 3.5, 3.0, 6.0, 0.1);
    private final BooleanValue blocks = new BooleanValue("Blocks", "Extend block reach", true);
    private final BooleanValue entities = new BooleanValue("Entities", "Extend entity reach", true);

    public Reach() {
        super(Category.Player, "Reach", "Increase reach distance.");
        addSetting(reach);
        addSetting(blocks);
        addSetting(entities);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }
}
