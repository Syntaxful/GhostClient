package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Increases entity hitboxes.
 */
public class Hitboxes extends Module {

    private final NumberValue size = new NumberValue("Size", "Hitbox expansion", 0.2, 0.0, 1.0, 0.05);

    public Hitboxes() {
        super(Category.Combat, "Hitboxes", "Increase entity hitboxes.");
        addSetting(size);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        // TODO: patch Entity#getEntityBoundingBox or use a render hook to expand boxes
    }
}
